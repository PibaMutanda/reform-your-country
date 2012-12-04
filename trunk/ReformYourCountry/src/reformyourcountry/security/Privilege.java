package reformyourcountry.security;

//import be.loop.jbb.bo.corp.CorpUser.CorporateRole;


import reformyourcountry.model.User;
import reformyourcountry.model.User.Role;

public enum Privilege {

    // Users management
    VIEW_PRIVATE_DATA_OF_USERS("View user's private data", Role.MODERATOR), // Private data includes e-mail and contributions points detail.
    MANAGE_USERS("Manage users (create / edit / delete / rename)", Role.ADMIN), //ok
    CERTIFY_USER("Can certify user",Role.SUBADMIN),
 
	// News related privileges
	MANAGE_NEWS("Manage news", Role.MODERATOR),
	MANAGE_NEWSLETTERS("Manage newsletters", Role.MODERATOR), 
	SEND_NEWSLETTERS("Send newsletters", Role.ADMIN), 

	DELETE("Delete Arg, Action, Article, User,...",Role.SUBADMIN),
	
    // Admin generated data
    MANAGE_ARTICLE("Manage article", Role.MODERATOR),
    MANAGE_ACTION("Manage actions and arguments", Role.MODERATOR),
    MANAGE_BOOK("Manage books", Role.MODERATOR),
    VIEW_UNPUBLISHED_ARTICLE("View unpublished articles",Role.MODERATOR),
    
    // User generated data
    MANAGE_GOODEXAMPLE("Manage good examples", Role.CORRECTOR),
    MANAGE_ARGUMENT("Manage arguments", Role.CORRECTOR),
    MANAGE_GROUP("Manage group", Role.SUBADMIN);
    
	String name;
	Role associatedRole;


	Privilege(String aName, Role aRole) {
	    this.name = aName;
		this.associatedRole = aRole;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isPartOfUserPrivileges(User user){
		if(user == null){
			return false;
		}
		return SecurityContext.getAllAssociatedPrivileges(user).contains(this);
	}

	public Role getAssociatedRole() {
		return this.associatedRole;
	}

	
	public void setAssociatedRole(Role associatedRole) {
		this.associatedRole = associatedRole;
	}


	public boolean isPrivilegeOfUser(User user) {
		return user.getPrivileges().contains(this);
		  
	}
	
}



