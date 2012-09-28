package reformyourcountry.maintest;

import java.util.Scanner;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import reformyourcountry.mail.MailCategory;
import reformyourcountry.mail.MailSender;
import reformyourcountry.mail.MailTemplateService;
import reformyourcountry.mail.MailType;
import reformyourcountry.model.Mail;
import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.util.CurrentEnvironment.Environment;



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
		
		UserRepository userdao = UserRepository.getInstance();
	
		MailTemplateService templateService = new MailTemplateService();
		
		MailSender sender = new MailSender();
		
		MailService service = MailService.getInstance();
		
		
		//// Do what Spring would do:
		sender.setEnvironement(Environment.PROD);
		sender.setMailDao(dao);
		sender.setUserDao(userdao);
		sender.setMailTemplateService(templateService);
		sender.postConstruct();  // start the thread
		service.setMailDao(dao);
		service.setMailSender(sender);
		
		// test MailService
		User user = new User();
		user.setFirstName("Gaston");
		user.setLastName("Lagaffe");
		user.setId(515551L);
		user.setMail("reformyourcountrytest@gmail.com");
		
		User replyTo = new User();
		replyTo.setFirstName("Lionel");
		replyTo.setLastName("Timmerman");
		replyTo.setMail("lionel.timmerman@gmail.com");
		
		
		Mail mail1 = new Mail(user,"Test de mail service",MailCategory.USER,"Hello this is my test",MailType.IMMEDIATE,true);
		mail1.setReplyTo(replyTo);
		service.sendMail(mail1);
		
		//we stop the MailSender thread when we type "stop"
		Scanner scan = new Scanner(System.in);
	    if(scan.next().equals("stop"));
	    sender.shutDown();
	    scan.close();
		
		
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
