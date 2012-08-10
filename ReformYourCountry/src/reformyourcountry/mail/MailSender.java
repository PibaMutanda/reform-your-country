package reformyourcountry.mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import blackbelt.HtmlToTextUtil;

import reformyourcountry.CurrentEnvironment.Environment;
import reformyourcountry.CurrentEnvironment.MailBehavior;
import reformyourcountry.model.Mail;
import reformyourcountry.model.User;
import reformyourcountry.repository.MailRepository;
import reformyourcountry.repository.UserRepository;



/** Really send the mail to the SMTP server
/* Checks the DB (Mail entity) for mails waiting to be sent.
 * We go through a DB to be sure the mails are sent if the server shuts down (sometimes, there are many mails to be sent (newsletter) 
 * and we may have to wait for a few hours between the decision to send a mail and its effective sent).
 */
@Service
public class MailSender extends Thread {

   private Logger logger = Logger.getLogger("jbbmail");

   //TODO rewiew time delay choice for production
    public final static int DELAY_BETWEEN_EACH_MAIL = 50;  // in ms. In case the SMTP server is too slow (cannot accept too many mails too fast). Use this const to temporize between 2 SMTP calls. 
    public final static int WAKE_UP_DELAY_WHEN_NO_MAIL = 15 * 1000;  // ms. When there is no mail anymore, how long should this batch sleep before querying the DB again for mails to be sent ?

    private boolean isShutDown = false;

    @Autowired	
    public MailRepository mailDao;
    @Autowired  
    public UserRepository userDao;

    @Autowired	
    public MailTemplateService mainTemplate;

    JavaMailSenderImpl javaMailSender;

    //@Value("${mail.from.notifier.address}") 
    String mailNotifierFrom;
    //@Value("${mail.from.notifier.alias}") 
    String mailNotifierAlias;
    //@Value("${mail.smtp.server}") 
    String smtpHost = "smtp.gmail.com"; // value setup for test //TODO configure
    //@Value("${mail.smtp.port}") 
    int smtpPort = 465; // value setup for test
    
    
    //TODO USE SPRING (not hardcoded)
    //@Value("${app.environment}") 
    Environment environment = Environment.DEV;
    //@Value("${mail.from.notifier.address}") 
    String notifier="no-reply@ryc.be";
    //@Value("${mail.from.notifier.alias}") 
    String aliasNotifier="ryc no-reply";
    
    
    /* setter for the test  purpose delete after spring configuration*/
    public void setMailDao( MailRepository dao){
    	mailDao = dao;
    	
    }
    
    /* setter for the test  purpose delete after spring configuration*/
    public void setMailTemplateService(MailTemplateService mainTemplate){
    	this.mainTemplate = mainTemplate;
    	
    }
    /* setter for the test  purpose delete after spring configuration*/
    public void setEnvironement(Environment env){
        
        environment = env;
        
        
    }
    
    
    public void setUserDao(UserRepository userDao){
        
        this.userDao = userDao;
    }

    @PostConstruct
    public void postConstruct() {
     	BasicConfigurator.configure();
        javaMailSender = new JavaMailSenderImpl();  // Class of Spring.
        javaMailSender.setProtocol("smtps");
        javaMailSender.setHost(smtpHost);
        javaMailSender.setPort(smtpPort);
        
        // TODO where did this came from on KBB?
        javaMailSender.setUsername("reformyourcountrytest@gmail.com");
	    javaMailSender.setPassword("technofutur");

        setName("MailSender"); // Sets the name of the thread to be visible in the prod server thread list.
       if(this.environment.getMailBehavior() != MailBehavior.NOT_STARTED){
        	this.start();
       } else {
       	logger.info("DevMode on, mail thread not started");
       }
        
    }

    @PreDestroy
    public void shutDown () {
        this.isShutDown = true;
        logger.info("MailSender shutting down");
        interrupt();  // In case the thread is sleeping or waiting.
    }	

    @Override
    public void run(){

        logger.info("MailSender thread started");
        try {
            //TODO: 1*60*1000
			Thread.sleep(1 * 10 * 1000);  // sleep 2 minute to make sure all the bean are ready
		} catch (InterruptedException e) {
	        logger.info("MailSender initial sleep interrupted");
		}

        logger.info("MailSender awaken from its initial sleep");

        mainLoop: while (!isShutDown) {

        	List<Mail> nextMailList = this.findNextMails();
            logger.info(nextMailList.size() + " mails found to send");
            
            while (nextMailList != null && nextMailList.size() > 0) {
                Mail nextMail = nextMailList.get(0);
                   
                // if the first mail is not groupable, all the mails of that list are not groupable.
                if (nextMail.getMailType()== MailType.IMMEDIATE || nextMail.getMailType()== MailType.SLOW_NOT_GROUPABLE || StringUtils.isNotBlank(nextMail.getEmailTarget())) {  

                	for (Mail mail : nextMailList) {

                		// Send the mail and remove it from the DB.
                		sendMailIndividually(mail);
                		mailDao.removeMails(Arrays.asList(mail));

                		this.sleepWell(DELAY_BETWEEN_EACH_MAIL);
                		if (isShutDown) {
                			break mainLoop;
                		}


                	}

                } else {// ...here we send a group of mails as one mail
                    // Send all these mails grouped as one mail and remove them from the DB.
                    sendMailsGrouped(nextMailList);
                    nextMail.getUser().setLastMailSentDate(new Date()); 
                    
                    userDao.merge(nextMail.getUser());
                    mailDao.removeMails(nextMailList); 
                                    
                    this.sleepWell(DELAY_BETWEEN_EACH_MAIL);
                }
                
                if (isShutDown) {
                    break mainLoop;
                }
                
                
                nextMailList = this.findNextMails();
                
            }

            sleepBad(WAKE_UP_DELAY_WHEN_NO_MAIL);
        }

        logger.info("MailSender thread ended");
    }

    /** Sleep even if a new urgent mail needs to be sent */
    private void sleepWell(int delayMs){
        if (isShutDown) {
            return;  // Don't sleep when shutting down
        }
        try {
            //There is no mail in database, sleep
            sleep(delayMs);
        } catch (InterruptedException e) {
            logger.info(e);
        }
    }

    /** Sleep but wake up in case the server creates a new mail must be sent */
    private void sleepBad(int delayMs){
        if (isShutDown) {
            return;  // Don't sleep when shutting down
        }
        try {
            //wait for new mail 
            synchronized (this) {
                wait(delayMs);
            }
        } catch (InterruptedException e) {
            logger.info(e);
        }
    }

   // 	private void sendToFile(MailTemplateService.MailSubjectAndContent mp) {
   // 	BufferedWriter writer;
    	//	try {
    //			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\testing\\Mails\\" + mp.subject + ".html")));
    //			writer.write(mp.content);
    //			writer.close();
    //		} catch (FileNotFoundException e) {
    //			// Temporary code.
    //			e.printStackTrace();
    //		} catch (IOException e) {
    //			// Temporary code.
    //			e.printStackTrace();
    //		}
    //	}

    public void sendMailIndividually(Mail mail) {
    	   
        MailTemplateService.MailSubjectAndContent mp = this.mainTemplate.templateMail(mail);

        //	this.sendToFile(mp);
        String emailTarget = mail.getUser() != null ? mail.getUser().getMail() : mail.getEmailTarget();
        String emailSender = mail.getReplyTo() != null ? mail.getReplyTo().getMail() : notifier;

        // Sanity Check
        if(StringUtils.isBlank(emailSender)){
        	
        	
       		logger.error("User with no email found : " + mail.getReplyTo().getFullName());
        	return; // Do not continue
        }
        
        sendMail(emailTarget, null, null, 
                mail.getReplyTo() == null ? null : mail.getReplyTo().getMail(), // reply to
                        emailSender,    // From
                        !emailSender.equals(notifier) ? mail.getReplyTo().getFullName() : aliasNotifier,  // Alias  
                                mp.subject, mp.content, true);
    }


    public void sendMailsGrouped(List<Mail> mails) {
    	

        MailTemplateService.MailSubjectAndContent mp = this.mainTemplate.templateMail(mails);

        //		this.sendToFile(mp);

        Mail firstMail = mails.get(0);
        String emailTarget = firstMail.getUser() != null ? firstMail.getUser().getMail() : firstMail.getEmailTarget();
        String emailSender = firstMail.getReplyTo() != null ? firstMail.getReplyTo().getMail() : notifier;

        sendMail(emailTarget, null, null, 
                firstMail.getReplyTo() == null ? null : firstMail.getReplyTo().getMail(), // reply to
                        emailSender,    // From
                        !emailSender.equals(notifier) ? firstMail.getReplyTo().getFullName() : aliasNotifier,  // Alias 
                                mp.subject, mp.content, true);
    }


    /**
     * returns a list of mails which are either immediate or grouped of a user.
     * 
     * @return a list of mails all to the same user.
     */
    public List<Mail> findNextMails(){

        User user;

        // 1. Send non groupables mails (i.e: user creation activation mail).
        user = mailDao.userHavingImmediateMails();
        if(user!=null){
            return mailDao.getMailsFromUser(MailType.IMMEDIATE, user);
        }

        // 2. There is no immediate (non groupable) mail to send (anymore) => we look for groupable mails.
        user = mailDao.userHavingGroupedMails();
        if(user!=null){
            return mailDao.getMailsFromUser(MailType.GROUPABLE, user);
        }

        // 3. There is no immediate and groupable mail to send (anymore) => we look for mails to send to electronic address mails (instead of user).
        List<Mail> mails = mailDao.getMailsEmailTarget(); 
        if(mails!=null && mails.size()>0) {
            return mails;
        }

        // 4. There is no immediate and groupable mail to send (anymore), we look for slow_not_groupabel(example newsletter) mails to send
        user = mailDao.userHavingSlowMails();
        if(user!=null){
            return mailDao.getMailsFromUser(MailType.SLOW_NOT_GROUPABLE, user);
        }

        // 5. There is no next mail to be sent.
        return new ArrayList<Mail>();  // Empty list, no mail to send.
    }



    private void sendMail(final String to, final String cc, final String bcc,
            final String replyTo, final String from, final String fromAlias,
            final String subject, final String text, final boolean textIsHtml) {
        try{ 
            if (to == null) {
                throw new IllegalArgumentException("'to' cannot be null");
            }

            final String rawText = (textIsHtml ? HtmlToTextUtil.convert(text) : text);

            StringBuilder log = new StringBuilder();
            log.append(Thread.currentThread().getName());
            log.append("\n================ MAIL ================\n");
            log.append("TO : \n");
            log.append(to);
            log.append("\n");
            log.append("CC : \n");
            log.append(cc);
            log.append("\n");
            log.append("BCC : \n");
            log.append(bcc);
            log.append("\n");
            log.append("FROM : ");
            log.append(from + " <" + fromAlias + ">");
            log.append("\n");
            log.append("SUBJECT : ");
            log.append(subject);
            log.append("\n");
            log.append("TEXT:\n");
            log.append(text);
            log.append("\n");
            log.append("============== END MAIL ==============");

           logger.info(log.toString());

            if (environment.getMailBehavior() == MailBehavior.SENT) { // Really send the mail to SMTP server now.
                MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
                    @Override
                    public void prepare(final MimeMessage mimeMessage) throws Exception {
                        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                        if (textIsHtml) {
                            mimeMessageHelper.setText(rawText, text);
                        } else {
                            mimeMessageHelper.setText(text);
                        }

                        mimeMessageHelper.setSubject(subject);
                        mimeMessageHelper.setFrom(from, fromAlias);
                        mimeMessageHelper.setTo(new String[]{to});

                        if (StringUtils.isNotBlank(replyTo)) {
                            mimeMessageHelper.setReplyTo(replyTo);
                        }

                        if (cc != null) {
                            mimeMessageHelper.setCc(new String[]{cc});
                        }
                        if (bcc != null) {
                            mimeMessageHelper.setBcc(new String[]{bcc});
                        }
                    }
                };
              
                     
                javaMailSender.send(mimeMessagePreparator);
                System.out.println("Mail send");
           }
        } catch(Exception e){//if we can't send the mail, continue
            // if we can't send for any reason, we don't stop the thread, we will just remove this mail from the database and we will continue to send mails.
            // Typical exception: the mail address is invalid.
            logger.error("Exception while sending mail", e);
        	
        }

    }
    
    public void sendTestMail(){
    	   MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
               @Override
               public void prepare(final MimeMessage mimeMessage) throws Exception {
                   MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                    mimeMessageHelper.setText("This is a test message");
    
                   mimeMessageHelper.setSubject("This is a test message");
                   mimeMessageHelper.setFrom("no-reply@knowledgeblackbelt.com", "KnowledgeBlackBelt Notifier");
                   mimeMessageHelper.setTo(new String[]{"nicolasbrasseur@yahoo.com"});
                   mimeMessageHelper.setReplyTo("no-reply@knowledgeblackbelt.com");
               }
           };

           javaMailSender.send(mimeMessagePreparator);
    }
}
