package reformyourcountry.model;

import java.util.Date;

import javax.persistence.*;
import reformyourcountry.mail.MailCategory;
import reformyourcountry.mail.MailType;

@Entity
public class Mail extends BaseEntity {
		
	//in case we send an email to a person in the database. Null if emailTarget is not null.
    @ManyToOne
	@JoinColumn(nullable = true)
	private User user;
	
	@ManyToOne
    @JoinColumn(nullable = true)
	private User replyTo;
    
	//in case we send an email to a person not in the database. Null if user is not null.
    @Column(nullable = true,length = 100)
    private String emailTarget;

	@Enumerated(EnumType.STRING)
	private MailCategory mailCategory;
		
	@Column(nullable = false,length = 255)
	private String subject;
	
	@Lob
	@Column(nullable = false)
	private String content;
	
	@Enumerated(EnumType.STRING)
	private MailType mailType;
	
	
	private boolean useTemplate;
	
	//Constructors
	public Mail() {}
	
	//For sending a mail to a user
	public Mail(User user,String subject, MailCategory mailSubject, String content, MailType mailType, boolean useTemplate) {
		this.user = user;
		this.replyTo = null;
		this.emailTarget = null;
		this.mailCategory = mailSubject;
		this.content = content;
		this.mailType=mailType;
		this.creationDate = new Date();
		this.subject=subject;
		this.useTemplate = useTemplate;
	}
	
	//For sending a mail to an outsider (not a user) 
	public Mail(String emailTarget, String subject, MailCategory mailSubject, String content, MailType mailType, boolean useTemplate) {
        this.user = null;
        this.replyTo = null;
        this.emailTarget = emailTarget;
        this.mailCategory = mailSubject;
        this.content = content;
        this.mailType=mailType;
        this.creationDate = new Date();
        this.subject=subject;
        this.useTemplate = useTemplate;
    }
	
	// Send a mail to a user and it contains information about the user who sends the mail 
    public Mail(User recipient,User replyTo, String subject, MailCategory mailSubject, String content, MailType mailType, boolean useTemplate) {
        this.user = recipient;
        this.replyTo = replyTo;
        this.emailTarget = null;
        this.mailCategory = mailSubject;
        this.content = content;
        this.mailType=mailType;
        this.creationDate = new Date();
        this.subject=subject;
        this.useTemplate = useTemplate;
    }
	
	
	
	public Mail(String recipient, User replyTo, String subject,
			MailCategory mailCategory, String content, MailType mailType, boolean useTemplate) {
		this.user = null;
        this.replyTo = replyTo;
        this.emailTarget = recipient;
        this.mailCategory = mailCategory;
        this.content = content;
        this.mailType=mailType;
        this.creationDate = new Date();
        this.subject=subject;
        this.useTemplate = useTemplate;
	}

	//Getters and setters

	public User getUser() {
		return this.user;
	}
	
	
	public User getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(User replyTo) {
        this.replyTo = replyTo;
    }

    /**
     *Set user instead of emailTarget
     */
	public void setUser(User user) {
		this.user = user;
		this.emailTarget = null;
	}
	
	public String getEmailTarget() {
        return this.emailTarget;
    }
	
	/**
     * Set emailTarget instead of user
     */
	public void setEmailTarget(String emailTarget) {
        this.user = null;
        this.emailTarget = emailTarget;
    }

	public MailCategory getMailCategory() {
		return this.mailCategory;
	}

	public String getContent() {
		return this.content;
	}


	public MailType getMailType() {
		return this.mailType;
	}
	
	public boolean getUseTemplate() {
		return this.useTemplate;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}
	
	public String getSubject(){
		return subject;
	}

}