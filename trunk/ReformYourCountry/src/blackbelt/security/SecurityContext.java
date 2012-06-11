package blackbelt.security;

import static blackbelt.util.collection.BBCollectionUtils.adminGroupReg;
import static blackbelt.util.collection.BBCollectionUtils.groupRegToGroup;
import static blackbelt.util.collection.BBCollectionUtils.rootOrganizationGroupsOnly;
import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import be.loop.jbb.bo.CommunityUser;
import be.loop.jbb.bo.corp.Company;
import be.loop.jbb.bo.corp.CorpUser;
import be.loop.jbb.bo.corp.CorpUser.CorporateRole;
import be.loop.jbb.dao.CommunityUserDao;
import be.loop.jbb.dao.corp.CorpUserDao;
import be.loop.jbb.web.exceptions.UnauthorizedAccessException;
import blackbelt.dao.OrganizationDao;
import blackbelt.dao.UserDao;
import blackbelt.model.Group;
import blackbelt.model.Organization;
import blackbelt.model.Privilege;
import blackbelt.model.User;
import blackbelt.model.User.CommunityRole;
import blackbelt.service.LoginService;
import blackbelt.service.OrganizationService;
import blackbelt.util.BlackBeltException;
import blackbelt.web.ContextUtil;

/**
 * Holds the security related information during request execution.
 */
public abstract class SecurityContext {

    public static final Privilege MASTER_PRIVILEGE = Privilege.EDIT_COMMUNITY_USERS_PRIVILEGES;

    private static ThreadLocal<User> user = new ThreadLocal<User>();    // Lazyly retrieved from the DB if needed (if getUser() is called).
    private static ThreadLocal<Long> userId = new ThreadLocal<Long>();  // retrieved in from the session. Non null <=> user logged in.
    private static ThreadLocal<EnumSet<Privilege>> contextualPrivileges = new ThreadLocal<EnumSet<Privilege>>();
    private static ThreadLocal<Organization> organizations = new ThreadLocal<Organization>();    // Lazyly retrieved from the DB if needed (if getUser() is called).

    // Note from John 2009-07-01: should be useless with Vaadin (no need to communicate between action and JSP...)  ----
    // Contextual custom privileges are usually set by the action (any string) and checked by the jsp through the authorization tag, as non-custom contextual privileges.
    // The difference with the custom, is that you can invent any string for a very little fine grained temporary privilege. The jsp has to know the same string of course.
    // example: edit_is_own_profile; view_influence;...
    private static ThreadLocal<Set<String>> contextualCustomPrivileges = new ThreadLocal<Set<String>>();


    /**
     * Removes the security context associated to the request
     */
    public static void clear() {
        user.set(null);
        userId.set(null);
        contextualPrivileges.set(null);
        contextualCustomPrivileges.set(null);
        organizations.set(null);
    }

    /**
     * @throws AuthorizationException
     *             if the user has not the privilege
     */
    public static void assertUserHasPrivilege(Privilege privilege) {
        if (!isUserHasPrivilege(privilege)) {
            throw new UnauthorizedAccessException(privilege);
        }
    }

    /**
     * @throws AuthorizationException
     *             if the user is not loggedin
     */
    public static void assertUserIsLoggedIn() {
        if (getUser() == null) {
            throw new UnauthorizedAccessException();
        }
    }

    public static void assertUserBelongsTo(Company company) {
        assertUserIsLoggedIn();

        if (!getUser().isCorpUser()) {
            throw new UnauthorizedAccessException("Non-Corp user try to access Corp exam");
        }
        CorpUser corpUser = (CorpUser) getUser();
        if (!company.equals(corpUser.getCompany())) {
            throw new UnauthorizedAccessException("User from wrong company trying to access Corp exam from another company");
        }
    }

    /**
     * @return true if the user has the privilege
     */
    public static boolean isUserHasPrivilege(Privilege privilege) {
        return isUserHasAllPrivileges(EnumSet.of(privilege));
    }


    public static boolean isUserHasAllPrivileges(EnumSet<Privilege> privileges) {
        return isUserHasAllPrivileges(privileges, null);
    }
    
    public static boolean isUserHasPrivilege(User user, Privilege privilege) {
        return getAllAssociatedPrivileges(user).contains(privilege);
    }

    
    /**
     * @return true if the user has all the privileges
     */
    public static boolean isUserHasAllPrivileges(EnumSet<Privilege> privileges, String customPrivilege) {
        if (getUser() == null) {
            return false;
        }
        EnumSet<Privilege> currentPrivileges = getAllAssociatedPrivileges(getUser());
        currentPrivileges.addAll(getContextualPrivileges());
        boolean result = currentPrivileges.containsAll(privileges);
        if (customPrivilege != null) {
            result = result && isUserHasCustomPrivilege(customPrivilege);
        }
        return result;
    }


    public static boolean isUserHasOneOfPrivileges(EnumSet<Privilege> privileges) {
        return isUserHasOneOfPrivileges(privileges, null);
    }

    /**
     * @return true if the user has one of the privileges
     */
    public static boolean isUserHasOneOfPrivileges(EnumSet<Privilege> privileges, String customPrivilege) {
        if (getUser() == null) {
            return false;
        }
        if (Collections.disjoint(getAllAssociatedPrivileges(getUser()), privileges) == false) {  // There is at least one element in common.
            return true;
        }
        if (customPrivilege != null) {
            if (isUserHasCustomPrivilege(customPrivilege)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add new contextual privileges. This gives additional privileges to the
     * current user. Those privileges are not persited and are cleared at the
     * end of the application request.
     */
    @Deprecated  // John 2011/04 - seems to not be used anymore. TODO: Remove with Struts.
    public static void addContextualPrivileges(EnumSet<Privilege> contextualPrivileges) {
        getContextualPrivileges().addAll(contextualPrivileges);
    }

    /**
     * Remove all contextual privileges. You don't have to call this if you have
     * no reason to reset the contextual privileges during the execution of
     * request.
     */
    @Deprecated  // John 2011/04 - seems to not be used anymore. TODO: Remove with Struts.
    public static void clearContextualPrivileges() {
        getContextualPrivileges().clear();
    }

    /**
     * Adds some custom privilege for this context
     */
    @Deprecated  // John 2011/04 - TODO: Remove with Struts.
    public static void addContextualCustomPrivileges(String... customPrivileges) {
        getContextualCustomPrivileges().addAll(Arrays.asList(customPrivileges));
    }

    /**
     * Remove all contextual custom privileges. You don't have to call this if
     * you have no reason to reset the contextual privileges during the
     * execution of request.
     */
    @Deprecated  // John 2011/04 - TODO: Remove with Struts.
    public static void clearContextualCustomPrivileges() {
        getContextualCustomPrivileges().clear();
    }



    @Deprecated  // John 2011/04 - TODO: Remove with Struts.
    public static boolean isUserHasCustomPrivilege(String customPrivilege) {
        return getContextualCustomPrivileges().contains(customPrivilege);
    }

    /**
     * @return All privilege associated to a community role
     */
    public static EnumSet<Privilege> getAssociatedPrivilege(CommunityRole communityRole) {
        EnumSet<Privilege> associatedPrivileges = EnumSet.noneOf(Privilege.class);
        if (communityRole != null) {
            for (Privilege privilege : Privilege.values()) {
                if (communityRole.isHigerOrEquivalent(privilege.getAssociatedCommunityRole())) {
                    associatedPrivileges.add(privilege);
                }
            }
        }
        return associatedPrivileges;
    }

    /**
     * @return All privilege associated to a corporate role
     */
    public static EnumSet<Privilege> getAssociatedPrivilege(CorporateRole corporateRole) {
        EnumSet<Privilege> associatedPrivileges = EnumSet.noneOf(Privilege.class);
        if (corporateRole != null) {
            for (Privilege privilege : Privilege.values()) {
                if (corporateRole.isHigerOrEquivalent(privilege.getAssociatedCorporateLearnExamRole())) {
                    associatedPrivileges.add(privilege);
                }
            }
        }
        return associatedPrivileges;
    }

    /**
     * @return all the privilege associated to the given user including
     *         associations present in the DB and association derived from
     *         user's role.
     */
    public static EnumSet<Privilege> getAllAssociatedPrivileges(User user) {
        EnumSet<Privilege> allUserPrivileges = EnumSet.noneOf(Privilege.class);
        if (user != null) {
            allUserPrivileges.addAll(user.getPrivileges());

            allUserPrivileges.addAll(getAssociatedPrivilege(user.getCommunityRole()));
/*            if (user.isCorpUser()) {
                allUserPrivileges.addAll(getAssociatedPrivilege(((CommunityUser) user).getCommunityRole()));
            } else {
                allUserPrivileges.addAll(getAssociatedPrivilege(((CorpUser) user).getLearnExamRole()));
            }*/
        }
        return allUserPrivileges;
    }

    public static EnumSet<Privilege> filterUnviewablePrivileges(User user, EnumSet<Privilege> unflitered) {
        EnumSet<Privilege> filtered = EnumSet.noneOf(Privilege.class);
        for (Privilege privilege : unflitered) {
            boolean viewable = true;
            if (user.isCorpUser()) {
                viewable = privilege.isCorporateUserVisible();
            } else {
                viewable = privilege.isCommunityUserVisible() || user.getPrivileges().contains(MASTER_PRIVILEGE);
            }
            if (viewable) {
                filtered.add(privilege);
            }
        }
        return filtered;
    }

    public static User getUser() {
        if (getUserId() == null) {  // Not logged in.
            return null;
        }
        if (user.get() == null) {  // User not loaded yet.
            User user = ((UserDao) ContextUtil.getSpringBean("userDao")).get(getUserId());  // This is not beauty, but life is sometimes ugly. -- no better idea (except making SecurityContext a bean managed by Spring... but for not much benefit...) -- John 2009-07-02

            //TODO: UGLY PATCH - KILL THIS WHEN CorpUsers don't exist no more. ***********  John 2009-08-05
            // I do this, because suddenly, downcasting (CommunityUser) user in SecurityContext.getAllAssociatedPrivileges throws Caused by: java.lang.ClassCastException: be.loop.jbb.bo.User$$EnhancerByCGLIB$$301a82b1 cannot be cast to be.loop.jbb.bo.CommunityUser
            // I guess this is because hibernate proxies.
            //if (user.isCorpUser()) {
            //    user = ((CorpUserDao) ContextUtil.getSpringBean("corpUserDao")).get(getUserId());
            //} else {
                user = ((CommunityUserDao) ContextUtil.getSpringBean("communityUserDao")).get(getUserId());
            //}
            //END OF UGLY PATCH **********************************************************
            
            
            setUser( user );  // Lazy loading if needed.
        }
        return user.get();
    }
    
    public static Organization getOrganization() {
    	Organization organization = organizations.get();
    	if(organization != null){
    		return ((OrganizationDao) ContextUtil.getSpringBean("organizationDao")).get(organization);
    	}
        return null;
    }

    public static void assertCurrentOrganizationIs(Organization organization) {
    	if(organization == null && SecurityContext.getOrganization() != null){
			throw new  BlackBeltException("Invalid Organization Context");    		
    	}
		if(organization != null && organization.equals(SecurityContext.getOrganization())){
			throw new  BlackBeltException("Invalid Organization Context");
		}
    }    

    public static boolean currentOrganizationIs(Organization organization) {
		return (organization == null && SecurityContext.getOrganization() != null)||(organization != null && organization.equals(SecurityContext.getOrganization()));
    }    
    
    public static void setOrganization(Organization organization) {
    	organizations.set(organization);
    }
    
	public static void clearOrganization(Organization organization) {
		organizations.remove();
	}

    
    public static boolean loggedUserIs(User user){
    	if(user == null){
    		throw new RuntimeException("User cannot be null");
    	}
    	User loggedUser = getUser();
    	if(loggedUser == null) return false;
    	return loggedUser.equals(user);
    }

    public static void setUser(User userParam) {
        //Security constraint
        if (user.get() != null) {
            throw new IllegalStateException(
            "Could not set a new user on the security context once a user has already been set");
        }
        if (userId.get() == null) {
            userId.set(userParam.getId());
        }
        user.set(userParam);
    }

    public static boolean isUserLoggedIn() {
        return getUserId() != null;
    }

    public static boolean equalsLoggedUser(User user) {
        return isUserLoggedIn() && getUser().equals(user);
    }

    /** Returns null if user not logged in */
    public static Long getUserId() {
        if (userId.get() == null) { // Then try to get it from the HttpSession.
            Long id = ((LoginService) ContextUtil.getSpringBean("loginService")).getLoggedInUserIdFromSession();  // This is not beauty, but life is sometimes ugly. -- no better idea (except making SecurityContext a bean managed by Spring... but for not much benefit...) -- John 2009-07-02
            if (id != null) {  // A user is effectively logged in.
                userId.set(id);  // remember it in the SecurityContext.
            }
        }
        return userId.get();
    }

    public static void setUserId(Long id) {
        //Security constraint
        if (userId.get() != null || user.get() != null) {
            throw new IllegalStateException(
                    "Could not set a new userId on the security context once a userId or user" +
            " has already been set");
        }
        userId.set(id);
    }

    @Deprecated  // John 2011/04 - TODO: Remove with Struts.
    public static EnumSet<Privilege> getContextualPrivileges() {
        if (contextualPrivileges.get() == null) {  // not yet created.
            contextualPrivileges.set( EnumSet.noneOf(Privilege.class) );
        }
        return contextualPrivileges.get();
    }

    @Deprecated  // John 2011/04 - TODO: Remove with Struts.
    public static Set<String> getContextualCustomPrivileges() {
        if (contextualCustomPrivileges.get() == null) {  // not yet created.
            contextualCustomPrivileges.set( new HashSet<String>() );
        }
        return contextualCustomPrivileges.get();
    }

    public static boolean canCurrentUserViewPrivateData(User user2) {
        return canCurrentUserChangeUser(user2) || SecurityContext.isUserHasPrivilege(Privilege.VIEW_PRIVATE_DATA_OF_USERS); 
    }

    public static boolean canCurrentUserChangeUser(User user2) {
        return user2.equals(SecurityContext.getUser()) // If the user is editing himself
                || SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS)     // or If this user has the privilege to edit other users
                || SecurityContext.isAdmin(ContextUtil.getSpringBean(OrganizationService.class).getOrganization(user2)); // is the logged user admin of the org of the user
    }

	public static boolean isUserHasRole(CommunityRole role) {
		User user = getUser();
		if(user == null || user.getCommunityRole() == null){
			return false;
		} 
		return user.getCommunityRole() == role;
	}

	public static boolean userCanSee(Organization organization) {
		if(organization == null){ // No org : Access to public resource
			return true;
		} 
		
		if(SecurityContext.getUser() == null){ // No logged user -> no access to org data
			return false;
		}
		
		User user = SecurityContext.getUser();
		Collection<Group> orgRootGroups = filter(transform(user.getGroupRegs(), groupRegToGroup), rootOrganizationGroupsOnly);
		int size = orgRootGroups.size();
		if(size == 0) {
			return false;
		} 
		
		return organization.equals(orgRootGroups.iterator().next().getOrganization());
	}

	public static boolean userCanAdmin(User user, Organization organization) {
		if(user == null || organization == null){
			return false;
		}
		
		Collection<Group> orgRootGroups = filter(transform(filter(user.getGroupRegs(), adminGroupReg), groupRegToGroup), rootOrganizationGroupsOnly);
		int size = orgRootGroups.size();
		if(size == 0) {
			return false;
		} 
		
		return organization.equals(orgRootGroups.iterator().next().getOrganization());
	}
	
	public static boolean isAdmin(Organization organization) {
		return userCanAdmin(SecurityContext.getUser(), organization);
	}
	
	/**
	 * True if target url is corp and logged user is from this corp 
	 */
	public static boolean validOrganizationContext(Organization organization) {
		if(organization == null) {
			return false;
		}
		
		if(SecurityContext.getOrganization() == null){
			return false;
		}
		
		if(SecurityContext.getUser() == null) {
			return false;
		}
		
		// both contextual org and target org are the same and are the org of the logged user
		if(organization.equals(SecurityContext.getOrganization()) && organization.equals(ContextUtil.getSpringBean(OrganizationService.class).getOrganization(SecurityContext.getUser()))){
			return true;
		}
		return false;
	}




}
