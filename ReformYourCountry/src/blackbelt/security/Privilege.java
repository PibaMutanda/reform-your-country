package blackbelt.security;

import be.loop.jbb.bo.corp.CorpUser.CorporateRole;
import blackbelt.model.User.CommunityRole;
import blackbelt.security.SecurityContext;

public enum Privilege {

	/**
	 * Description of these enums (for screen) are in file
	 * globalMessages.properties
	 */

	//Questions related privileges
	CREATE_QUESTIONS("Create new questions", true, false, CommunityRole.USER), // ok
	EDIT_QUESTIONS("Edit questions", true, false, CommunityRole.USER), // ok
	PROPOSE_ON_QUESTIONS("Make proposal on questions", true, false, CommunityRole.USER), // ok
	VOTE_ON_QUESTIONS("Vote on questions", true, false, CommunityRole.USER), // ok
	APPROVE_PROPOSAL_ON_QUESTIONS("Approve proposal on questions", true, false, CommunityRole.MODERATOR), // ok 
	MOVE_QUESTIONS("Move questions to other categories", true, false, CommunityRole.MODERATOR), // ok
	LIST_RELEASED_QUESTIONS("List released questions", true, false, CommunityRole.MODERATOR), // Can list (in QuestionManager) questions that are released.

	//Wiki pages privileges
	MANAGE_WIKIS("Manage Wiki pages (create / edit / delete / rename)", true, null, CommunityRole.COMMUNITY_MANAGER), //ok
	EDIT_WIKIS("Edit Wiki pages", true, null, CommunityRole.COMMUNITY_MANAGER), //ok

	//Exam related privileges. Add categories (via category manager), create/edit questDef/ExamTask, create challenge.
	MANAGE_COMMUNITY_EXAMS("Manage community exams (create / edit / delete / rename)", true, null), //ok
	MANAGE_CORP_EXAMS("Manage corp private exams", false, true, CorporateRole.ADMIN), 
	MANAGE_EXAM_RESULTS("Manage exam results", true, true, CommunityRole.COMMUNITY_MANAGER),

	//Users related privileges  -- MANAGE & EDIT USERS probably to be merged when corp edition scrapped.
	MANAGE_USERS("Manage users (create / edit / delete / rename)", true, true, CommunityRole.COMMUNITY_MANAGER), //ok
	EDIT_COMMUNITY_USERS_PRIVILEGES("Edit community user privileges", true, null), // ok
	EDIT_CORPORATE_USERS_PRIVILEGES("Edit corporate user privileges", false, true, CorporateRole.ADMIN), // of your own company...

	VIEW_PRIVATE_DATA_OF_USERS("View user's private data", true, null, CommunityRole.COMMUNITY_MANAGER), // Private data includes e-mail and contributions points detail.
	VIEW_CORP_USERS_DETAILS_FROM_COMMUNITY("View corp users details from community", true, false),
	EDIT_INFLUENCE_OF_USERS("Edit influence of users", true, null, CommunityRole.COMMUNITY_MANAGER), 
	GIVE_CONTRIBUTION_POINTS("Give contribution points", true, null, CommunityRole.COMMUNITY_MANAGER),
	GET_NOTIFIED_WHEN_USERS_REACH_MANY_POINTS("Get notified when users reach many points (x1000)", true, null),
    GET_NOTIFIED_WHEN_USERS_REACH_BROWN_BELT("Get notified when users reach brown belt level", true, null),
	MANAGE_COACHES("Manage coaches", true, null),  // Manage the levels of coaches and who is a coach or not.
	EDIT_GOOD_CONTRIBUTOR_STATUS("Edit user 'Good Contributor' status", true, null, CommunityRole.COMMUNITY_MANAGER), 

	// Courses related privileges
	MANAGE_COACH_OFFERINGS("Manage course offerings", true, null, CommunityRole.ADMIN),
	MANAGE_COURSES("Manage courses and topics", true, null),
	MANAGE_COURSEREGS("Manage course registrations", true, null),   // Change course registrations (and probably help students lost in finding a coach).
	MANAGE_GROUPCOURSEREGS("Manage group course registrations", true, null),
	
	//News related privileges
	MANAGE_NEWS("Manage news", true, null, CommunityRole.COMMUNITY_MANAGER),
	MANAGE_NEWSLETTERS("Manage newsletters", true, null, CommunityRole.COMMUNITY_MANAGER), //ok
	SEND_NEWSLETTERS("Send newsletters", true, null, CommunityRole.COMMUNITY_MANAGER), //ok

	//Auction related privileges
	MANAGE_AUCTIONS("Manage auctions", true, null, CommunityRole.COMMUNITY_MANAGER), //ok

	//Sponsors privileges
	MANAGE_SPONSORS("Manage sponsors", true, null, CommunityRole.COMMUNITY_MANAGER),

	//Corp clients privileges
	MANAGE_CORP_CLIENTS("Manage enterprise edition accounts", false, null, CommunityRole.COMMUNITY_MANAGER),

	//Vouchers related priviledges
	MANAGE_VOUCHERS("May emit vouchers", false, null, CommunityRole.ADMIN),
	
	
	//status privileges
	VIEW_STATS("View stats", false, null, CommunityRole.COMMUNITY_MANAGER), //ok

	//Operations privileges
	SET_CLEAN_SHUT_DOWN("Perform clean shut down", false, null), //ok

	//Misc
	LEAD_VIDEOCONFERENCES("Lead video conferences", true, null),  // Conferendum.
	MANAGE_INVENTORY("Manage inventory items", true, null, CommunityRole.COMMUNITY_MANAGER),  // InventoryItems
	MANAGE_FORUM("Manage forum discussions", true, null, CommunityRole.COMMUNITY_MANAGER), // Forum Moderator
	MANAGE_TECHNO("Manage technologies and tracks", true, null, CommunityRole.COMMUNITY_MANAGER), // Technologies
	
	//Edit Reference Manual
	EDIT_REF_MANUAL("Edit reference manual", true, null, CommunityRole.COMMUNITY_MANAGER);

	//All com users having the associated role inherit this privilege
	private CommunityRole associatedCommunityRole;

	//All corp users having the associated role inherit this privilege
	private CorporateRole associatedCorporateLearnExamRole;

	//Used as a 3 position flag (null is non applicable, true is applicable and visible, false is applicable and non visible)  -- note: could be refactored to an enum
	private Boolean communityUserApplicable;

	//Used as a 3 position flag (null is non applicable, true is applicable and visible, false is applicable and non visible)   -- could be an enum.
    private Boolean corporateUserApplicable;

	String name;

	/*
	 * Privilege constructed with this constructor are not associated to any
	 * role.
	 * 
	 * DEFINITION of "Applicable". A privilege could be not applicable if the
	 * feature has no reason in the context. example: a corp user never face the
	 * notion of auction -> the privilege to edit auctions is not applicable to
	 * a corp user. DEFINITION of "Viewable". A privilege which existance in the
	 * app should not be exposed to usual end users. example: an end user should
	 * not see in a list that the priviledge "clean shut down" exists (even if
	 * he has no right to do that).
	 * 
	 * 
	 */
	Privilege(String aName, Boolean communityUserApplicable, Boolean corporateUserApplicable) {
		this.name = aName;
		this.communityUserApplicable = communityUserApplicable;
		this.corporateUserApplicable = corporateUserApplicable;
	}

	Privilege(String aName, Boolean communityUserVisible, Boolean corporateUserVisible, CommunityRole associatedCommunityRole) {
		this(aName, communityUserVisible, corporateUserVisible);
		this.associatedCommunityRole = associatedCommunityRole;
	}

	Privilege(String aName, Boolean communityUserVisible, Boolean corporateUserVisible, CorporateRole associatedCorporateLearnExamRole) {
		this(aName, communityUserVisible, corporateUserVisible);
		this.associatedCorporateLearnExamRole = associatedCorporateLearnExamRole;
	}

	Privilege(String aName, Boolean communityUserVisible, Boolean corporateUserVisible, CommunityRole associatedCommunityRole,
			CorporateRole associatedCorporateLearnExamRole) {
		this(aName, communityUserVisible, corporateUserVisible);
		this.associatedCommunityRole = associatedCommunityRole;
		this.associatedCorporateLearnExamRole = associatedCorporateLearnExamRole;
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

	public CommunityRole getAssociatedCommunityRole() {
		return this.associatedCommunityRole;
	}

	public CorporateRole getAssociatedCorporateLearnExamRole() {
		return this.associatedCorporateLearnExamRole;
	}

	public void setAssociatedCorporateLearnExamRole(CorporateRole associatedCorporateLearnExamRole) {
		this.associatedCorporateLearnExamRole = associatedCorporateLearnExamRole;
	}

	public boolean isCommunityUserVisible() {
		return this.communityUserApplicable != null && this.communityUserApplicable;
	}

	public boolean isCorporateUserVisible() {
		return this.corporateUserApplicable != null && this.corporateUserApplicable;
	}

	public boolean isPrivilegeOfUser(User user) {
		return user.getPrivileges().contains(this);
	}

}
