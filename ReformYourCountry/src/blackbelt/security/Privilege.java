package blackbelt.security;

//import be.loop.jbb.bo.corp.CorpUser.CorporateRole;
import blackbelt.security.SecurityContext;
import reformyourcountry.model.User;
import reformyourcountry.model.User.Role;

public enum Privilege {

    // Users management
    VIEW_PRIVATE_DATA_OF_USERS("View user's private data", Role.COMMUNITY_MANAGER), // Private data includes e-mail and contributions points detail.
    MANAGE_USERS("Manage users (create / edit / delete / rename)", Role.COMMUNITY_MANAGER), //ok

 
	// News related privileges
	MANAGE_NEWS("Manage news", Role.COMMUNITY_MANAGER),
	MANAGE_NEWSLETTERS("Manage newsletters", Role.COMMUNITY_MANAGER), 
	SEND_NEWSLETTERS("Send newsletters", Role.COMMUNITY_MANAGER), 

	//status privileges
	VIEW_STATS("View stats", Role.COMMUNITY_MANAGER); 


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



