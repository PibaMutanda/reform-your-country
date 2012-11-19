
package reformyourcountry.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.google.api.Google;
import org.springframework.social.linkedin.api.LinkedIn;
import org.springframework.social.twitter.api.Twitter;

import reformyourcountry.mail.MailingDelayType;
import reformyourcountry.security.Privilege;

@Entity
@Table(name = "users")
public class User extends BaseEntity implements Cloneable, Comparable<User>, Serializable {
   
    private static final long serialVersionUID = 4144665927166518905L;

    //this is the MD5 print of the universal password
    public static final String UNIVERSAL_PASSWORD_MD5 = "477bc098b8f2606137c290f9344dcee8";
    public static final String UNIVERSAL_DEV_PASSWORD_MD5 = "e77989ed21758e78331b20e477fc5582";  // "dev" in clear. -> any developer can use "dev" to impersonate anybody when developing. Does not work in production. 


    public enum SpecialType {
        POLITIC("Politic","Partis"),
        UNION("Union","Syndicats"),
        ASSOCIATION("Association","Associations"),
        PRIVATE("Private","Particuliers");
        
        String name,familyName;
        
        SpecialType(String name,String familyName){
            this.name=name;
            this.familyName = familyName;
        }
        
        public String getName(){
            return name;
        }
        public String getFamilyName(){
            return familyName;
        }
        public static List<SpecialType> getValuesExceptPrivate(){         
            List<SpecialType> result = new ArrayList<SpecialType>(Arrays.asList(SpecialType.values()));
            result.remove(PRIVATE);
            return result;
        }
        public static SpecialType getSpecialType(String value){
            value = value.toLowerCase();
            boolean found = false;
            SpecialType result = null;
            for (SpecialType specialType : SpecialType.values()) {
               String name =  specialType.name.toLowerCase();
                if (name.equals(value)){
                    found = true;
                    result = specialType;
                }
            }
            if(!found){
                throw new RuntimeException("Special Type string value unsupported");
            }else{
                return result;     
            }
        }
         
    }
    
    /**
     * Status for an account :<br />
     * LOCKED: Account manually locked by an admin, normally a lock reason
     * should be available.<br />
     * NOTVALIDATED: The account exists but is not yet usable as the email
     * address as not been validated.<br />
     * ACTIVE: Account with valid email address.<br />
     */
    public enum AccountStatus {
        LOCKED("Locked"),
        NOTVALIDATED("Mail not validated yet"),     
        ACTIVE( "Active");

        String name;

        AccountStatus(String aName) {
            this.name = aName;
        }
        public String getName() {
            return name;
        }
    }


    public enum Gender {
        FEMALE, MALE;
    }
    
    public boolean isFemale(){
        return this.gender==Gender.FEMALE;
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
        public boolean isHigherOrEquivalent(Role level) {
            if (level == null)
                return this == Role.ANONYMOUS;
            return this.getLevel() <= level.getLevel();
        }

    }
    
    public enum AccountConnectedType{
        LOCAL("User connected with local account","local", null),
        FACEBOOK("User connected with his Facebook account","facebook", Facebook.class),
        TWITTER("User connected with his Twitter account","twitter",Twitter.class),
        LINKEDIN("User connected with his LinkedIn account","linkedIn",LinkedIn.class),
        GOOGLE("User connected with his google account","google",Google.class);


        private String detail;
        private String providerId;  // Used in spring social to identify the provider.
        private Class<?> providerClass;
        private AccountConnectedType(String detail,String providerId,Class<?> providerClass){
            this.detail = detail;
            this.providerId = providerId;
            this.providerClass = providerClass;
        }

        public String getDetail(){
            return detail;
        }
        public String getProviderId(){
            return providerId;
        }
        public Class<?> getProviderClass(){
            return providerClass;
        }
        public static AccountConnectedType getProviderType(String providerId){
            providerId = providerId.toLowerCase();
            switch(providerId){
            case "facebook" : return AccountConnectedType.FACEBOOK;
            case "twitter"  : return AccountConnectedType.TWITTER;
            case "google"   : return AccountConnectedType.GOOGLE;
            case "linkedin" : return AccountConnectedType.LINKEDIN;
            default : throw new RuntimeException("Provider cannot be identified");
            }
        }

    }
 
    @Enumerated(EnumType.STRING)
    private SpecialType specialType;
    
    private boolean askedGroup;
    
    @Enumerated(EnumType.STRING)
    private AccountConnectedType accountConnectedType;
    
    @Column(length = 50)
    @Size(max=50, message="votre prénom ne peut contenir que 50 caractères maximum")
    private String firstName;// in case of a special user we use the firstname to display the name of the association.
    
    @Column(length = 50)
    @Size(max=50, message="votre nom ne peut contenir que 50 caractères maximum")
    private String lastName;

    @Column(unique = true, nullable=false)
    @Size(max = 15, message = "votre pseudonyme ne peut contenir que 15 caractères maximum")
    @NotBlank(message="entrer votre pseudonyme")
    @Pattern(message ="ne peut contenir que des caractère alphanumériques, sans accents. Les 2 caractères \"-\" et \"_\" sont autorisés, mais pas les espaces.", regexp="[A-Za-z0-9_-]{2,256}")
    private String userName; 
    
    @Column(length = 100)
    @NotBlank(message="entrer votre mail")
    @Email(message ="adresse mail pas valide", regexp="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
    private String mail;
    
    private String title;
    
    private Date certificationDate;
    
    @Column(length = 100)
    //@NotBlank(message="entrer votre mot de passe")
    @Size(min = 4, message = "votre mot de passe doit contenir au moins 4 caractères")
    private String password;

    private boolean isPasswordKnownByTheUser;  // true if account created through social network login, and random password not sent to the user.
    
    @Lob
    /*Forcing type definition to have text type column in postgresql instead of automatic indirect storage of large object (postgresql store lob in a separate table named pg_largeobject and store his id in the "content" column).
     *Without forcing, JDBC driver use write() method of the BlobOutputStream to store Clob into the database;
     * this method take an int as parameter an convert it into a byte causing lose of 3 byte information so character are render as ASCII instead of UTF-8 expected .
     * @see http://stackoverflow.com/questions/9993701/cannot-store-euro-sign-into-lob-string-property-with-hibernate-postgresql
     * @see http://stackoverflow.com/questions/5043992/postgres-utf-8-clobs-with-jdbc
     */
    @Type(type="org.hibernate.type.StringClobType")
    private String nameChangeLog; // record the name changes

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    
   // @Temporal(TemporalType.TIMESTAMP)
  //  @NotNull(message="you must indicate your birthdate in format yyyy-MM-dd")
    private Date birthDate;

    private boolean picture;  // Picture name = user id + ".jpg"

    private Date lastMailSentDate;

    @ElementCollection(targetClass=Privilege.class, fetch=FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @JoinTable(name="users_privileges")
    @Column(name="privilege", nullable=false,length = 50)
    private Set<Privilege> privileges = new HashSet<Privilege>();

    private Date lastAccess;

        
    @Column(length = 40)
    private String lastLoginIp;

    private int consecutiveFailedLogins = 0; // Reset to 0 every time users logins sucessfully.
    private Date lastFailedLoginDate; // Null if consecutiveFailedLogins == 0

    private String validationCode;  // sent to the user with the validation mail at registration. 

    private String lockReason;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    private boolean nlSubscriber = true; 

    private boolean spammer = false;

    @ManyToOne
    private User spamReporter;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    @Column(nullable = false)
    private List<GroupReg> groupRegs = new ArrayList<GroupReg>();
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    @Column(nullable = false)
    private Set<Badge> badges = new HashSet<Badge>();
    
    @Enumerated(EnumType.STRING)
    private MailingDelayType mailingDelayType = MailingDelayType.IMMEDIATELY; 
    public MailingDelayType getMailingDelayType() {
        return this.mailingDelayType;
    }

    
    @OneToMany(mappedBy="user")
    private List <VoteAction> voteActions = new ArrayList <VoteAction>();
    
    @OneToMany(mappedBy="user")
    private List <VoteArgument> voteArguments = new ArrayList <VoteArgument>();
   
    private Role role = Role.USER;

    
    /////////////////////////////////////////: GETTERS & SETTERS //////////////////////////
    /////////////////////////////////////////: GETTERS & SETTERS //////////////////////////
    /////////////////////////////////////////: GETTERS & SETTERS //////////////////////////
    /////////////////////////////////////////: GETTERS & SETTERS //////////////////////////
    /////////////////////////////////////////: GETTERS & SETTERS //////////////////////////
    
    
    
    
    public Date getCertificationDate() {
		return certificationDate;
	}

	public Set<Badge> getBadges() {
		return badges;
	}

	public void setCertificationDate(Date certificationDate) {
		this.certificationDate = certificationDate;
	}

    
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
    
    public Role getRole() {
        return role;
    }

	public SpecialType getSpecialType() {
        return specialType;
    }

    public void setSpecialType(SpecialType specialType) {
        this.specialType = specialType;
    }

    public boolean isAskedGroup() {
        return askedGroup;
    }

    public void setAskedGroup(boolean askedGroup) {
        this.askedGroup = askedGroup;
    }

    public AccountConnectedType getAccountConnectedType() {
        return accountConnectedType;
    }

    public void setAccountConnectedType(AccountConnectedType accountConnectedType) {
        this.accountConnectedType = accountConnectedType;
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

    public boolean isPasswordKnownByTheUser() {
        return isPasswordKnownByTheUser;
    }

    public void setPasswordKnownByTheUser(boolean isPasswordKnownByTheUser) {
        this.isPasswordKnownByTheUser = isPasswordKnownByTheUser;
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


    public boolean isPicture() {
        return picture;
    }

    public void setPicture(boolean picture) {
        this.picture = picture;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
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

    public List<GroupReg> getGroupRegs() {
        return groupRegs;
    }

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

    public void setMailDelayType(MailingDelayType mailingDelay) {
        this.mailingDelayType = mailingDelay;
    }
    
    public List<VoteAction> getVoteActions() {
        return voteActions;
    }
    
    public void addVoteAction(VoteAction va){
        voteActions.add(va);
    }
    
    public List<VoteArgument> getVoteArguments() {
        return voteArguments;
    }
    
    public void addVoteArgument(VoteArgument va){
        voteArguments.add(va);
    }
    
}
