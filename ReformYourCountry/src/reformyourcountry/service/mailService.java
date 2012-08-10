package reformyourcountry.service;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reformyourcountry.mail.MailCategory;
import reformyourcountry.mail.MailSender;
import reformyourcountry.mail.MailType;
import reformyourcountry.model.Mail;
import reformyourcountry.model.User;
import reformyourcountry.repository.MailRepository;


/**
 * Service of mail.
 */
@Service
public class mailService {
    
    
    // TODO: remove singleton when using Spring
//    private static mailService uniqueInstance = new mailService();
//    private mailService() {}
//    public static mailService getInstance() {
//        return uniqueInstance;
//    }
//    
    
	public final static String JBB_ADMIN_MAIL = "info@KnowledgeBlackBelt.com";  // TODO: take the value from a property file (provided by the specialized project, as enseignement2.be)
	public final static String BUG_MAIL = "bug@KnowledgeBlackBelt.com";  // TODO: take the value from a property file (provided by the specialized project, as enseignement2.be)

	//@Logger Log logger;

    @Autowired	
    public MailRepository mailDao;
     @Autowired	
    public MailSender mailSender;

    
    
    public void setMailDao(MailRepository dao){
    	mailDao = dao;
    	
    }
    
    public void setMailSender(MailSender sender){
    	
    	mailSender = sender;
    	
    }
 
    /**
     * Send a mail to a blackbelt user or to none blackbelt user
     * This method uses blackbelt template, if you don't want to use it call the method sendMail(Mail mail)
     * The collection contains Users and strings(electronic email address) 
      * 
     */
    public void sendMail(Collection<? extends Object> targets, String subject, String content, MailType mailType, MailCategory mailCategory){

        for(Object object : targets){
            if (object instanceof User){
                this.sendMail(new Mail((User)object, subject, mailCategory, content, mailType, true ));
            } else if (object instanceof String ){
                this.sendMail(new Mail((String)object, subject, mailCategory, content, mailType, true ));
            } else {
                throw new IllegalArgumentException("Bug: we only accept Strings and Users. Current object's class: " + object.getClass());
            }
        }
    }
    
    @PostConstruct
	public void postConstruct() {
								// the prod server thread list.
	}
    /** Send a mail to a blackbelt user
     * this methode uses a blackbelt template, if you don't want to use it call the method sendMail(Mail mail) */
    public void sendMail(User recipient, String subject, String content, MailType mailType, MailCategory mailCategory){
        this.sendMail(new Mail(recipient, subject, mailCategory, content, mailType, true ));
    }
    
    /** Send a mail to a list of blackbelt user
     * this methode uses a blackbelt template, if you don't want to use it call the method sendMail(Mail mail) */
    public void sendMail(List<User> recipient, String subject, String content, MailType mailType, MailCategory mailCategory){
    	for (User user : recipient){
    		this.sendMail(new Mail(user, subject, mailCategory, content, mailType, true ));
    	}
    }

    /** Sends a mail to an electronic email
     * this methode uses a blackbelt template, if you don't want to use it call the method sendMail(Mail mail) */
    public void sendMail(String emailTarget, String subject, String content, MailType mailType, MailCategory mailCategory){
    	if(StringUtils.isNotBlank(emailTarget)){
    		this.sendMail(new Mail(emailTarget, subject, mailCategory, content, mailType, true ));
    	} else {
    		//logger.error(new BlackBeltException("Trying to send mail without giving an emailTarget"));
    	}
    }
    
    /** Sends a mail to some electronic emails
     * this methode uses a blackbelt template, if you don't want to use it call the method sendMail(Mail mail) */
    public void sendMail(String[] emailTarget, String subject, String content, MailType mailType, MailCategory mailCategory){
    	for(String target : emailTarget) {
    		this.sendMail(new Mail(target, subject, mailCategory, content, mailType, true ));
    	}
    }
    
    public void sendMail(User recipient, User replyTo, String subject, String content, MailType mailType, MailCategory mailCategory){
        this.sendMail(new Mail(recipient, replyTo, subject, mailCategory, content, mailType, true ));
    }
    
    public void sendMail(String recipient, User replyTo, String subject, String content, MailType mailType, MailCategory mailCategory){
        this.sendMail(new Mail(recipient, replyTo, subject, mailCategory, content, mailType, true ));
    }


    /**
     * Save a mail to database
     * Use this method if you want to use a black belt template
     * 
     */
    public void sendMail(Mail mail){
     

        if (mail.getUser()==null){
            mailDao.save(mail, null); 
        }else{
            mailDao.save(mail, mail.getUser().getId());
        }

        // wake up the thread each time we send a mail to database because if there isn't any mail to send the thread goes to sleep for a certain time.
        synchronized(mailSender) {
            mailSender.notify();
        }
    }

}

