package blackbelt.mail;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import reformyourcountry.model.User;

import blackbelt.dao.MailDao;
import blackbelt.model.Mail;
import blackbelt.model.MailCategory;
import blackbelt.model.MailType;
import blackbelt.spring.util.Logger;
import blackbelt.util.BlackBeltException;

/**
 * Service of mail.
 */
@Component
public class MailService {
	
	public final static String JBB_ADMIN_MAIL = "info@KnowledgeBlackBelt.com";  // TODO: change to KnowledgeBlackBelt.com after v5
	public final static String BUG_MAIL = "bug@KnowledgeBlackBelt.com";  // TODO: change to KnowledgeBlackBelt.com after v5

	@Logger Log logger;

    @Autowired	private MailDao mailDao;
    @Autowired	private MailSender mailSender;

 
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
            }else if (object instanceof String ){
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
    		logger.error(new BlackBeltException("Trying to send mail without giving an emailTarget"));
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
     * Use this methode if you want to use a black belt template
     * 
     */
    public void sendMail(Mail mail){
        //FIXME remove body at integration(), check with mathieu how dao function in blackbelt 

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

