package reformyourcountry.mail;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import reformyourcountry.dao.MailDaoMock;
import reformyourcountry.model.Mail;
import reformyourcountry.model.User;



public class TestMail {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestMail test = new TestMail();

		test.startTestMailSender();

		
		

	}
	
	private void startTestMailSender(){
		
		MailDaoMock dao = MailDaoMock.getInstance();
	
		MailTemplateService templateService = new MailTemplateService();
		
		MailSender sender = new MailSender();
		
		sender.setMailDao(dao);
		sender.setMailTemplateService(templateService);
		
		//start the thread
		sender.postConstruct();
		
		
		MailService service = new MailService();
		service.setMailDao(dao);
		service.setMailSender(sender);
		
		User user = new User();
		user.setFirstName("Gaston");
		user.setLastName("Lagaffe");
		user.setId(515551L);
		user.setMail("reformyourcountrytest@gmail.com");
		
		User replyTo = new User();
		user.setFirstName("Lionel");
		user.setLastName("Timmerman");
		user.setMail("lionel.timmerman@gmail.com");
		
		
		Mail mail1 = new Mail(user,"Test de mail service",MailCategory.USER,"Hello this is my test",MailType.IMMEDIATE,true);
		mail1.setReplyTo(replyTo);
		
		
		service.sendMail(mail1);
		
		
	}
	
	 private void startTestJavaMailSender(){
		    JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		    javaMailSender.setPort(465);
		    javaMailSender.setProtocol("smtps");
		    javaMailSender.setHost("smtp.gmail.com");
		    javaMailSender.setUsername("lionel.timmerman@gmail.com");
		    javaMailSender.setPassword("");
		    
		    
	 	   MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
	           @Override
	           public void prepare(final MimeMessage mimeMessage) throws Exception {
	        	   
	               MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	              

	                mimeMessageHelper.setText("This is a test message abbbajahjzhauhzuahzauhzuahzfkefjeihgerhgorhghrhgrhgrhgorhghrghrghrhgfbnfnbutnberijezrerozeiruzeripzeterjhgjtng");

	               mimeMessageHelper.setSubject("This is a test message");
	               mimeMessageHelper.setFrom("no-reply@facebook.com", "lionel");
	               mimeMessageHelper.setTo(new String[]{"lionel.timmerman@gmail.com","julienciarma@gmail.com"});
	               mimeMessageHelper.setReplyTo("no-reply@test.com");
	           }
	       };

	       javaMailSender.send(mimeMessagePreparator);
		 
		 
	 }
	

}
