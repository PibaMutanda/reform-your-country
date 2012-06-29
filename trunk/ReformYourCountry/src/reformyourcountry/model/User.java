
package reformyourcountry.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Index;

import reformyourcountry.mail.MailingDelayType;
import reformyourcountry.security.Privilege;

 @Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User extends BaseEntity implements Cloneable, Comparable<User>,
		Serializable {
	

		private List <VoteAction> voteActions = new ArrayList <VoteAction>();
		private List <VoteArgument> voteArguments = new ArrayList <VoteArgument>();




		public List<VoteAction> getVoteActions() {
			return voteActions;
		}
	
	

	private static final long serialVersionUID = 4144665927166518905L;
	//this is the MD5 print of the universal password
	public static final String UNIVERSAL_PASSWORD_MD5 = "477bc098b8f2606137c290f9344dcee8";
	public static final String UNIVERSAL_DEV_PASSWORD_MD5 = "e77989ed21758e78331b20e477fc5582";  // "dev" in clear. -> any developer can use "dev" to impersonate anybody when developping. Does not work in production. 
	public static final Long COMMUNICATION_MANAGER_ID = 16616697L; // storing Id to avoid problem when users changes their nickname
	
	public static final String JAVA_TECH_LEADER_NICKNAME = null; // = "hekonsek";
	public static final float DEFAULT_INFLUENCE = 1f;
	public static final float MAX_POSSIBLE_INFLUENCE = 10f; // For users (like John) having special privilege to edit influences. 

	
	
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
		FEMALE,MALE;
	}
	

	public enum Role {
	    NONE("non", -1), 
	    ADMIN("Administrator", 10),
	    MODERATOR("Moderator", 100), 
	    USER("User", 1000),
	    ANONYMOUS("Anonymous", 10000);


	    private int level;
	    String name;

	    private Role(String aName, int level) {
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
	     * Test if the current role is lower or equivalent than the role given as parameter.<br/>
	     * Note that you test role and not its value.
	     *
	     * @param level
	     * to test against this <tt>CommunityRole</tt>.
	     * @return true or false.
	     */
	    
	
	    public boolean isLowerOrEquivalent(Role level) {
		if (level == null)
		    return this == Role.ANONYMOUS;
		return this.getLevel() >= level.getLevel();
	    }

	    /**
	     * Test if the current role is higher or equivalent than the role given as parameter.<br/>
	     * Note that you test role and not its value.
	     *
	     * @param level
	     * to test against this <tt>CommunityRole</tt>.
	     * @return true or false.
	     */
	    public boolean isHigerOrEquivalent(Role level) {
		if (level == null)
		    return this == Role.ANONYMOUS;
		return this.getLevel() <= level.getLevel();
	    }

	}

	private String firstName;

	private String lastName;
	
	
	

	 @Column(unique = true)
	 @Index(name = "username_idx")
	private String userName; 

	private String mail;

	private String password;
	
	@Lob
	 private String nameChangeLog; // record the name changes

	

    @Column(nullable = true)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	private Date birthDate;

	private boolean picture;

	private String pictureName;

	private Date lastMailSentDate;

	private boolean guest;


	@ElementCollection(targetClass=Privilege.class, fetch=FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@JoinTable(name="users_privileges",joinColumns={@JoinColumn(name="userid")})
	@Column(name="privilege", nullable=false)
	private Set<Privilege> privileges = new HashSet<Privilege>();

	private Date lastAccess;

	private Date registrationDate;

	private String lastLoginIp;

	private int consecutiveFailedLogins = 0; // Reset to 0 every time users logins sucessfully.
	private Date lastFailedLoginDate; // Null if consecutiveFailedLogins == 0

	private boolean dev = false;

	private String validationCode;

	private String lockReason;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus;
	
	private boolean nlSubscriber = true; 

	private boolean spammer = false;

	@ManyToOne
	@JoinColumn(name = "spamReporterId")
	private User spamReporter;
	/**
	 * influence of automated privilege compution
	 */
	// In V5, the influence have been changed to be autocomputed with an integer
	// formula. Despite this, we kept the V4 float value because the formula may
	// change in the future
	private float influence = DEFAULT_INFLUENCE; // when this user vote, factor used to take his opinion into account.
	// In V5 influence is auto computed but we want some users (eg. john,
	// nicolas, henryk, ...) to have a fixed non-recomputed value
	private boolean influenceAutoComputed = true;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "influenceAssignerUserId", nullable = true)
	private User influenceAssigner; // User who has given the value for influence field. Can be null (system gives influence when new belt or root influencers, as John & Nicolas).
//	// Contains all groups link including the primary group as well
	@OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
	private Set<GroupReg> groupRegs = new HashSet<GroupReg>();
//	// Link to the Primary Group : maybe null if in no group and GroupRegs is
//	// empty
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "primaryGroupRegId")
	private GroupReg primaryGroupReg;
	@Enumerated(EnumType.STRING)
	
	private MailingDelayType mailingDelay; 
	public MailingDelayType getMailingDelay() {
		return this.mailingDelay;
	}

	public void setMailDelayType(MailingDelayType mailingDelay) {
		this.mailingDelay = mailingDelay;
	}

	private Role role = Role.USER;

	
	public Role getRole() {
		return role;
	}

	public void setRole(Role roleParam) {
		this.role = roleParam;
	}

	public boolean hasAdminPrivileges() {
		return role == Role.ADMIN;
	}

	public boolean hasModeratorPrivileges() {
		return role == Role.ADMIN
				|| role == Role.MODERATOR;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
    public String toString() {
        return "User [group_regs=" + groupRegs + ", voteActions="
                + voteActions + ", voteArguments=" + voteArguments + ", id="
                + id + ", firstName=" + firstName + ", lastName=" + lastName
                + ", userName=" + userName + ", mail=" + mail + ", password="
                + password + ", nameChangeLog=" + nameChangeLog + ", gender="
                + gender + ", birthDate=" + birthDate + ", picture=" + picture
                + ", pictureName=" + pictureName + ", lastMailSentDate="
                + lastMailSentDate + ", guest=" + guest + ", privileges="
                + privileges + ", lastAccess=" + lastAccess
                + ", registrationDate=" + registrationDate + ", lastLoginIp="
                + lastLoginIp + ", consecutiveFailedLogins="
                + consecutiveFailedLogins + ", lastFailedLoginDate="
                + lastFailedLoginDate + ", dev=" + dev + ", validationCode="
                + validationCode + ", lockReason=" + lockReason
                + ", accountStatus=" + accountStatus + ", nlSubscriber="
                + nlSubscriber + ", spammer=" + spammer + ", spamReporter="
                + spamReporter + ", influence=" + influence
                + ", influenceAutoComputed=" + influenceAutoComputed
                + ", influenceAssigner=" + influenceAssigner
                + ", mailingDelay=" + mailingDelay + ", communityRole="
                + role + "]";
    }

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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


	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
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

	public boolean isGuest() {
		return guest;
	}

	public void setGuest(boolean guest) {
		this.guest = guest;
	}

	public int compareTo(User o) {
		return this.getLastName().compareTo(o.getLastName());
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public AccountStatus getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(AccountStatus accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getUniversalPasswordMd5() {
		return UNIVERSAL_PASSWORD_MD5;
	}
//TODO maxime uncomment when using influence system
//	public static float getInfluenceAssignerMaxFactor() {
//		return INFLUENCE_ASSIGNER_MAX_FACTOR;
//	}

	public static float getMaxPossibleInfluence() {
		return MAX_POSSIBLE_INFLUENCE;
	}

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
	public void setGroupRegs(Set<GroupReg> groupRegs) {
		this.groupRegs = groupRegs;
	}

	public Set<GroupReg> getGroupRegs() {
		return groupRegs;
	}

	public void setPrimaryGroupReg(GroupReg primaryGroupReg) {
		this.primaryGroupReg = primaryGroupReg;
	}

	public GroupReg getPrimaryGroupReg() {
		return primaryGroupReg;
	}

	//TODO maxime uncoment when using facebook integration
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
	//TODO maxime use?
//	
//	@Override
//	public int hashCode() {
//	    final int prime = 31;
//	    int result = 1;
//	    result = prime * result + ((mail == null) ? 0 : mail.hashCode());
//	    result = prime * result
//		    + ((userName == null) ? 0 : userName.hashCode());
//	    return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//	    if (this == obj)
//		return true;
//	    if (obj == null)
//		return false;
//	    if (getClass() != obj.getClass())
//		return false;
//	    User other = (User) obj;
//	    if (mail == null) {
//		if (other.mail != null)
//		    return false;
//	    } else if (!mail.equals(other.mail))
//		return false;
//	    if (userName == null) {
//		if (other.userName != null)
//		    return false;
//	    } else if (!userName.equals(other.userName))
//		return false;
//	    return true;
//	}

}
