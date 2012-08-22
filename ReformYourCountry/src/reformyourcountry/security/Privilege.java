package reformyourcountry.security;

//import be.loop.jbb.bo.corp.CorpUser.CorporateRole;


import reformyourcountry.model.User;
import reformyourcountry.model.User.Role;

public enum Privilege {

    // Users management
    VIEW_PRIVATE_DATA_OF_USERS("View user's private data", Role.MODERATOR), // Private data includes e-mail and contributions points detail.
    MANAGE_USERS("Manage users (create / edit / delete / rename)", Role.MODERATOR), //ok

 
	// News related privileges
	MANAGE_NEWS("Manage news", Role.MODERATOR),
	MANAGE_NEWSLETTERS("Manage newsletters", Role.MODERATOR), 
	SEND_NEWSLETTERS("Send newsletters", Role.MODERATOR), 

	//status privileges
	VIEW_STATS("View stats", Role.MODERATOR),

    //article related privileges
    
    EDIT_ARTICLE("Edit article",Role.MODERATOR),
    
    EDIT_BOOK("Edit book",Role.MODERATOR);

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



