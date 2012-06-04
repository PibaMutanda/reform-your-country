
package blackbelt.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/*import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;

import javax.persistence.Table; */

import org.apache.commons.lang.StringUtils;
//import org.hibernate.annotations.Index;
//import org.hibernate.annotations.Parameter;
//import org.hibernate.annotations.Sort;
//import org.hibernate.annotations.SortType;
//import org.hibernate.annotations.Type;

import be.loop.jbb.bl.impl.UserServiceImpl;
//import be.loop.jbb.bo.Belt;
//import be.loop.jbb.bo.Decoration;
//import be.loop.jbb.bo.tests.ExamPerformed;
//import be.loop.jbb.bo.tests.ExamTaskPerformed;
//import be.loop.jbb.bo.tests.Questionnaire;
//import blackbelt.hibernate.util.EnumSetType;
//import blackbelt.ui.coach.CoachMailOption;
//import blackbelt.util.collection.BBCollectionUtils;
//
//import com.google.common.base.Predicates;
//import com.google.common.collect.Collections2;


//TODO piba uncomment
// @Entity
//@Table(name = "users")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User /*extends Identifiable*/ implements Cloneable, Comparable<User>,
		Serializable {
	private static long generated_id ; 

	private static final long serialVersionUID = 4144665927166518905L;

	// this is the MD5 print of the universal password
	public static final String UNIVERSAL_PASSWORD_MD5 = "477bc098b8f2606137c290f9344dcee8";
	public static final String UNIVERSAL_DEV_PASSWORD_MD5 = "e77989ed21758e78331b20e477fc5582"; // "dev"
																								// in
																								// clear.
																								// ->
																								// any
																								// developer
																								// can
																								// use
																								// "dev"
																								// to
																								// impersonate
																								// anybody
																								// when
																								// developping.
																								// Does
																								// not
																								// work
																								// in
																								// production.

	public static final Long COMMUNICATION_MANAGER_ID = 16616697L; // storing Id
																	// to avoid
																	// problem
																	// when
																	// users
																	// changes
																	// their
																	// nickname
	public static final String JAVA_TECH_LEADER_NICKNAME = null; // =
																	// "hekonsek";
	public static final float DEFAULT_INFLUENCE = 1f;

	// TODO V5 remove ()
	// A user having an influence > 1 can assign an influence to another user.
	// The highest possible value he can assign is his own influence * this
	// constant.
	public static final float INFLUENCE_ASSIGNER_MAX_FACTOR = 0.7f; // 70%
	public static final float MAX_POSSIBLE_INFLUENCE = 10f; // For users (like
															// John) having
															// special privilege
															// to edit
															// influences.

	/**
	 * Status for an account :<br />
	 * LOCKED: Account manually locked by an admin, normally a lock reason
	 * should be available.<br />
	 * NOTVALIDATED: The account exists but is not yet usable as the email
	 * address as not been validated.<br />
	 * ACTIVE: Account with valid email address.<br />
	 */
	public enum AccountStatus {
		LOCKED("Locked"), NOTVALIDATED("Mail not validated yet"), ACTIVE(
				"Active");

		String name;

		AccountStatus(String aName) {
			this.name = aName;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * Genders for a user
	 */
	public enum Gender {
		MR, MRS, MSS;
	}





	 public enum CommunityRole {
	 NONE("non", -1), ADMIN("Administrator", 10), COMMUNITY_MANAGER(
	 "Community Manager", 50), // Added 2011/08/29
	 MODERATOR("Moderator", 100), USER("User", 1000), ANONYMOUS("Anonymous",
	 10000);
	
	 private int level;
	 String name;
	
	 private CommunityRole(String aName, int level) {
	 this.level = level;
	 this.name = aName;
	 }
	
	 public String getName() {
	 return name;
	 }
	
	 public int getLevel() {
	 return level;
	 }
	
	 /**
	 * Test if the current role is lower or equivalent than the role given
	 * as parameter.<br/>
	 * Note that you test role and not its value.
	 *
	 * @param level
	 * to test against this <tt>CommunityRole</tt>.
	 * @return true or false.
	 */
	 public boolean isLowerOrEquivalent(CommunityRole level) {
	 if (level == null)
	 return this == CommunityRole.ANONYMOUS;
	 return this.getLevel() >= level.getLevel();
	 }
	
	 /**
	 * Test if the current role is higher or equivalent than the role given
	 * as parameter.<br/>
	 * Note that you test role and not its value.
	 *
	 * @param level
	 * to test against this <tt>CommunityRole</tt>.
	 * @return true or false.
	 */
	 public boolean isHigerOrEquivalent(CommunityRole level) {
	 if (level == null)
	 return this == CommunityRole.ANONYMOUS;
	 return this.getLevel() <= level.getLevel();
	 }
	
	 }

	// TODO uncomment
	// @Type(type = "blackbelt.hibernate.util.EnumSetType", parameters =
	// @Parameter(name = EnumSetType.TYPE, value =
	// "blackbelt.model.DisplayContactInfo"))
	// @Basic
	// private Set<DisplayContactInfo> contactInfosPolicy =
	// EnumSet.noneOf(DisplayContactInfo.class);

	// TODO uncomment
	// public Set<DisplayContactInfo> getContactInfosPolicy() {
	// return contactInfosPolicy;
	// }

	// TODO uncomment
	// public void setContactInfosPolicy(Set<DisplayContactInfo>
	// contactInfosPolicy) {
	// this.contactInfosPolicy = contactInfosPolicy;
	// }

	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	//
	// User info
	//
	private String firstName;

	private String lastName;

	// @Column(unique = true)
	// @Index(name = "nickname_idx")
	private String nickName; // TODO V5: Rename nickname to userName, and change
								// that in the UI too (userName sounds more
								// natural to users).


	private String mail;

	private String password;

//	 @Lob
	 private String nameChangeLog; // record the name changes


	//@Column(nullable = true)
	//@Enumerated(EnumType.STRING)
	private Gender gender;

	private Timestamp birthDate;

	private boolean picture;

	private String pictureName;

	private Date lastMailSentDate;


	private boolean guest;



	//TODO uncomment
//	@ElementCollection(targetClass = Privilege.class, fetch = FetchType.EAGER)
//	@Enumerated(EnumType.STRING)
//	@JoinTable(name = "users_privileges", joinColumns = { @JoinColumn(name = "userid") })
//	@Column(name = "privilege", nullable = false)
//	private Set<Privilege> privileges = new HashSet<Privilege>();

	private Date lastAccess;

	private Date registrationDate;

	private String lastLoginIp;

	private int consecutiveFailedLogins = 0; // Reset to 0 every time users
												// logins sucessfully.
	private Date lastFailedLoginDate; // Null if consecutiveFailedLogins == 0

	private boolean dev = false;

	private String validationCode;

	private String lockReason;

	//@Column(nullable = false)
	//@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus;

	private boolean nlSubscriber = true; //TODO maxime : use?

	//TODO delete?
//	private String videoId; // Id on Youtube or Vimeo.

	//TODO uncomment or delete?
//	@Enumerated(EnumType.STRING)
//	private VideoType videoType;

	private boolean spammer = false;

	//@ManyToOne
	//@JoinColumn(name = "spamReporterId")
	private User spamReporter;

	// How User wants to receive e-mails.

	//@Column(columnDefinition = "text")
	private String activityComments = "";

	//TODO maxime : use?
	// In V5, the influence have been changed to be autocomputed with an integer
	// formula. Despite this, we kept the V4 float value because the formula may
	// change in the future
	private float influence = DEFAULT_INFLUENCE; // when this user vote, factor
													// used to take his opinion
													// into account.
	//TODO maxime : use?
	// In V5 influence is auto computed but we want some users (eg. john,
	// nicolas, henryk, ...) to have a fixed non-recomputed value
	private boolean influenceAutoComputed = true;

	//TODO maxime : use?
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "influenceAssignerUserId", nullable = true)
	private User influenceAssigner; // User who has given the value for
									// influence field. Can be null (system
									// gives influence when new belt or root
									// influencers, as John & Nicolas).
	//TODO delete?
//	@OneToMany(mappedBy = "user")
//	private Set<Badge> badges = new HashSet<Badge>();
	//TODO delete?
//	// Contains all groups link including the primary group as well
//	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
//	private Set<GroupReg> groupRegs = new HashSet<GroupReg>();
	//TODO delete?
//	// Link to the Primary Group : maybe null if in no group and GroupRegs is
//	// empty
//	@OneToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "primaryGroupRegId")
//	private GroupReg primaryGroupReg;
										// anymore).
	//TODO delete?
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "recruiterId")
//	private User recruiter;
	//TODO delete?
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "reactivatorId")
//	private User reactivator;

	//TODO delete?
//	@Enumerated(EnumType.STRING)
//	private CommunityRole communityRole = CommunityRole.USER;
	//TODO delete?
//	public boolean isCorpUser() {
//		Collection<Organization> orgs = new HashSet<Organization>(
//				Collections2.filter(Collections2.transform(getGroupRegs(),
//						BBCollectionUtils.groupRegToOrganization), Predicates
//						.notNull()));
//		return orgs.size() >= 1;
//	}

	
//	public CommunityRole getCommunityRole() {
//		return communityRole;
//	}
//
//	public void setCommunityRole(CommunityRole communityRole) {
//		this.communityRole = communityRole;
//	}
//
//	public boolean hasAdminPrivileges() {
//		return communityRole == CommunityRole.ADMIN;
//	}
//
//	public boolean hasModeratorPrivileges() {
//		return communityRole == CommunityRole.ADMIN
//				|| communityRole == CommunityRole.MODERATOR;
//	}

//	public Set<Country> getCoachCountriesSelection() {
//		return this.coachCountriesSelection;
//	}
//
//	public boolean isShowAsCoach() {
//		return showAsCoach;
//	}
//
//	public void setShowAsCoach(boolean showAsCoach) {
//		this.showAsCoach = showAsCoach;
//	}
//
//	public CoachMailOption getCoachMailOption() {
//		return this.coachMailOption;
//	}

//	public void setCoachMailOption(CoachMailOption mailCoachOption) {
//		this.coachMailOption = mailCoachOption;
//	}
//
//	public Set<Badge> getBadges() {
//		return badges;
//	}
//
//	public void setBadges(Set<Badge> badges) {
//		this.badges = badges;
//	}
//
//	public SortedSet<CoachOffering> getCoachOfferings() {
//		return coachOfferings;
//	}
//
//	public void setCoachOfferings(SortedSet<CoachOffering> coachOfferings) {
//		this.coachOfferings = coachOfferings;
//	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName.toLowerCase();
	}

//	@Override
	public Long getId() {
		return id;
	}
//	}

	public User(Long id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User() {

		this.id = generated_id++;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + ", nickName=" + nickName + ", mail=" + mail
				+ ", password=" + password + ", gender=" + gender
				+ ", birthDate=" + birthDate + ", picture=" + picture
				+ ", pictureName=" + pictureName + ", lastMailSentDate="
				+ lastMailSentDate + ", guest=" + guest + ", lastAccess="
				+ lastAccess + ", registrationDate=" + registrationDate
				+ ", lastLoginIp=" + lastLoginIp + ", consecutiveFailedLogins="
				+ consecutiveFailedLogins + ", lastFailedLoginDate="
				+ lastFailedLoginDate + ", dev=" + dev + ", validationCode="
				+ validationCode + ", lockReason=" + lockReason
				+ ", accountStatus=" + accountStatus + ", nlSubscriber="
				+ nlSubscriber + ", spammer=" + spammer + ", spamReporter="
				+ spamReporter + ", activityComments=" + activityComments
				+ ", influence=" + influence + ", influenceAutoComputed="
				+ influenceAutoComputed + ", influenceAssigner="
				+ influenceAssigner + "]";
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	

//	public MailingDelayType getMailingDelay() {
//		return mailingDelayType;
//	}
//
//	public void setMailingDelayType(MailingDelayType mailingDelayType) {
//		this.mailingDelayType = mailingDelayType;
//	}

	public Date getLastMailSentDate() {
		return lastMailSentDate;
	}

	public void setLastMailSentDate(Date lastMailSendedDate) {
		this.lastMailSentDate = lastMailSendedDate;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail != null ? mail.toLowerCase().trim() : null;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password != null ? password.toLowerCase() : password;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date now) {
		this.registrationDate = now;
	}

	public Date getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

//	public String getSignature() {
//		return signature;
//	}
//
//	public void setSignature(String signature) {
//		this.signature = signature;
//	}
//
//	public Country getCountry() {
//		return country;
//	}
//
//	public void setCountry(Country country) {
//		this.country = country;
//	}

	public String getLockReason() {
		return lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}

	public String getValidationCode() {
		return validationCode;
	}

	public void setValidationCode(String validationCode) {
		this.validationCode = validationCode;
	}

	public boolean isDev() {
		return dev;
	}

	public void setDev(boolean dev) {
		this.dev = dev;
	}

	public boolean isPicture() {
		return picture;
	}

	public void setPicture(boolean picture) {
		this.picture = picture;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

//	public String getSkypeId() {
//		return skypeId;
//	}
//
//	public void setSkypeId(String skypeId) {
//		this.skypeId = skypeId;
//	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

//	public String getShortInfo() {
//		return shortInfo;
//	}
//
//	public void setShortInfo(String shortInfo) {
//		this.shortInfo = shortInfo;
//	}

	public Timestamp getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Timestamp birthDate) {
		this.birthDate = birthDate;
	}

	public String getFullName() {
		String lnChar = (lastName == null || lastName.isEmpty()) ? "" : " "
				+ StringUtils.capitalize(lastName);
		return StringUtils.capitalize(firstName) + lnChar;
	}

	public boolean isNlSubscriber() {
		return nlSubscriber;
	}

	public void setNlSubscriber(boolean nlSubscriber) {
		this.nlSubscriber = nlSubscriber;
	}

//	public List<Decoration> getDecorations() {
//		return decorations;
//	}
//
//	public void setDecorations(List<Decoration> decorations) {
//		this.decorations = decorations;
//	}

	public boolean isGuest() {
		return guest;
	}

	public void setGuest(boolean guest) {
		this.guest = guest;
	}

	public int compareTo(User o) {
		return this.getLastName().compareTo(o.getLastName());
	}

//	public String getLongInfo() {
//		return longInfo;
//	}
//
//	public void setLongInfo(String longInfo) {
//		this.longInfo = longInfo;
//	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public String getActivityComments() {
		return activityComments;
	}

	public void setActivityComments(String activityComments) {
		this.activityComments = activityComments;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

//	public Set<Privilege> getPrivileges() {
//		return privileges;
//	}
//
//	public void setPrivileges(Set<Privilege> privileges) {
//		this.privileges = privileges;
//	}

	public float getInfluence() {
		return this.influence;
	}

	public void setInfluence(float influence) {
		this.influence = influence;
	}

	public User getInfluenceAssigner() {
		return influenceAssigner;
	}

	public void setInfluenceAssigner(User influanceAssigner) {
		this.influenceAssigner = influanceAssigner;
	}

//	public float getLastContribMailPoints() {
//		return lastContribMailPoints;
//	}
//
//	public void setLastContribMailPoints(float lastContribMailPoints) {
//		this.lastContribMailPoints = lastContribMailPoints;
//	}
//
//	public Set<CourseReg> getStudentCourseRegs() {
//		return studentCourseRegs;
//	}
//
//	public void setStudentCourseRegs(Set<CourseReg> studentCourseRegs) {
//		this.studentCourseRegs = studentCourseRegs;
//	}
//
//	public Set<CourseReg> getCoachCourseRegs() {
//		return coachCourseRegs;
//	}
//
//	public void setCoachCourseRegs(Set<CourseReg> coachCourseRegs) {
//		this.coachCourseRegs = coachCourseRegs;
//	}
//
//	public Set<CourseRegApply> getCourseRegApplies() {
//		return courseRegApplies;
//	}
//
//	public void setCourseRegApplies(Set<CourseRegApply> courseRegApplies) {
//		this.courseRegApplies = courseRegApplies;
//	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getUniversalPasswordMd5() {
		return UNIVERSAL_PASSWORD_MD5;
	}

	public static float getInfluenceAssignerMaxFactor() {
		return INFLUENCE_ASSIGNER_MAX_FACTOR;
	}

	public static float getMaxPossibleInfluence() {
		return MAX_POSSIBLE_INFLUENCE;
	}

//	public Float getGlobalSatisfactionScoreCoach() {
//		return globalSatisfactionScoreCoach;
//	}
//
//	public void setGlobalSatisfactionScoreCoach(
//			Float globalSatisfactionScoreCoach) {
//		this.globalSatisfactionScoreCoach = globalSatisfactionScoreCoach;
//	}
//
//	public int getGlobalSatisfactionCountCoach() {
//		return globalSatisfactionCountCoach;
//	}
//
//	public void setGlobalSatisfactionCountCoach(int globalSatisfactionCountCoach) {
//		this.globalSatisfactionCountCoach = globalSatisfactionCountCoach;
//	}
//
//	public String getVideoId() {
//		return videoId;
//	}
//
//	public void setVideoId(String videoId) {
//		this.videoId = videoId;
//	}
//
//	public VideoType getVideoType() {
//		return videoType;
//	}
//
//	public void setVideoType(VideoType videoType) {
//		this.videoType = videoType;
//	}

	public boolean isSpammer() {
		return spammer;
	}

	public void setSpammer(boolean spammer) {
		this.spammer = spammer;
	}

	public User getSpamReporter() {
		return spamReporter;
	}

	public void setSpamReporter(User spamReporter) {
		this.spamReporter = spamReporter;
	}

//	@Override
//	public String toString() {
//		return getFullName();
//	}
//
//	public void setGroupRegs(Set<GroupReg> groupRegs) {
//		this.groupRegs = groupRegs;
//	}
//
//	public Set<GroupReg> getGroupRegs() {
//		return groupRegs;
//	}
//
//	public void setPrimaryGroupReg(GroupReg primaryGroupReg) {
//		this.primaryGroupReg = primaryGroupReg;
//	}
//
//	public GroupReg getPrimaryGroupReg() {
//		return primaryGroupReg;
//	}
//
//	public void setoAuthCredentials(Set<SocialIntegration> oAuthCredentials) {
//		this.oAuthCredentials = oAuthCredentials;
//	}
//
//	public Set<SocialIntegration> getoAuthCredentials() {
//		return oAuthCredentials;
//	}

	public int getConsecutiveFailedLogins() {
		return consecutiveFailedLogins;
	}

	public void setConsecutiveFailedLogins(int consecutiveFailedLogins) {
		this.consecutiveFailedLogins = consecutiveFailedLogins;
	}

	public Date getLastFailedLoginDate() {
		return lastFailedLoginDate;
	}

	public void setLastFailedLoginDate(Date lastFailedLoginDate) {
		this.lastFailedLoginDate = lastFailedLoginDate;
	}

//	public void setRecruiter(User recruiter) {
//		this.recruiter = recruiter;
//	}
//
//	public User getRecruiter() {
//		return recruiter;
//	}

//	public void setReactivator(User reactivator) {
//		this.reactivator = reactivator;
//	}
//
//	public User getReactivator() {
//		return reactivator;
//	}
//
	public String getNameChangeLog() {
		return nameChangeLog;
	}

	public void addNameChangeLog(String nameChange) {
		if (this.nameChangeLog == null) {
			this.nameChangeLog = nameChange;
		} else {
			this.nameChangeLog += "\n" + nameChange;
		}
	}

	public void setInfluenceAutoComputed(boolean influenceAutoComputed) {
		this.influenceAutoComputed = influenceAutoComputed;
	}

	public boolean isInfluenceAutoComputed() {
		return influenceAutoComputed;
	}

//	public void setCredibility(ContributorCredibility credibility) {
//		this.credibility = credibility;
//	}
//
//	public ContributorCredibility getCredibility() {
//		return credibility;
//	}
//
//	@Override
//	public Class<?> getConcreteClass() {
//		return User.class;
//	}

	// public Set<InventoryItem> getInventoryItems() {
	// return inventoryItems;
	// }
}
