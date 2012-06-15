package reformyourcountry.mail;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import reformyourcountry.dao.MailDaoMock;



public class TestMail {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	

		
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
