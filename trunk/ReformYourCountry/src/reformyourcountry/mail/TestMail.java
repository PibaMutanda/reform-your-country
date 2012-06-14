package reformyourcountry.mail;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import reformyourcountry.dao.MailDao;



public class TestMail {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		
/*
		MailSender sender = new MailSender();
		
		MailDao dao = new MailDao();
		
		sender.setMailDao(dao);
		
		sender.postConstruct();*/
		
	    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
	    javaMailSender.setProtocol("smtps");
	    javaMailSender.setHost("smtp.gmail.com");
	    javaMailSender.setPort(587);
	    //javaMailSender.
	    javaMailSender.setUsername("lionel.timmerman@gmail.com");
	    javaMailSender.setPassword("");
	    
	    
 	   MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
           @Override
           public void prepare(final MimeMessage mimeMessage) throws Exception {
        	   
               MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                mimeMessageHelper.setText("This is a test message");

               mimeMessageHelper.setSubject("This is a test message");
               mimeMessageHelper.setFrom("no-reply@knowledgeblackbelt.com", "KnowledgeBlackBelt Notifier");
               mimeMessageHelper.setTo(new String[]{"lionel.timmerman@gmail.com"});
               mimeMessageHelper.setReplyTo("no-reply@knowledgeblackbelt.com");
           }
       };

       javaMailSender.send(mimeMessagePreparator);
		
		

	}

}
