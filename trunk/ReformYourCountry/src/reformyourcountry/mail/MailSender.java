package reformyourcountry.mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import reformyourcountry.model.Mail;
import reformyourcountry.model.User;
import reformyourcountry.repository.MailRepository;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.util.CurrentEnvironment.Environment;
import reformyourcountry.util.CurrentEnvironment.MailBehavior;
import reformyourcountry.util.HtmlToTextUtil;
import reformyourcountry.util.Logger;
import reformyourcountry.web.ContextUtil;



/** Really send the mail to the SMTP server
/* Checks the DB (Mail entity) for mails waiting to be sent.
 * We go through a DB to be sure the mails are sent if the server shuts down (sometimes, there are many mails to be sent (newsletter) 
 * and we may have to wait for a few hours between the decision to send a mail and its effective sent).
 */
@Service
public class MailSender extends Thread {

    @Logger Log log;

   
    public final static int DELAY_BETWEEN_EACH_MAIL = 50;  // in ms. In case the SMTP server is too slow (cannot accept too many mails too fast). Use this const to temporize between 2 SMTP calls. 
    public final static int WAKE_UP_DELAY_WHEN_NO_MAIL = 15 * 1000;  // ms. When there is no mail anymore, how long should this batch sleep before querying the DB again for mails to be sent ?

    private boolean isShutDown = false;

    @Autowired	public MailRepository mailDao;
    @Autowired  public UserRepository userDao;
    
    @Autowired	public MailTemplateService mainTemplate;

    JavaMailSenderImpl javaMailSender; // This is not a Spring bean so @Autowired not usable

    @Value("${app.environment}") // can't use ContextUtil because Spring beans (including this one starting) are initialized before ContextUtil Listener
    private Environment environment ;
    
    @Value("${mail.smtp.server}")           String smtpHost; 
    @Value("${mail.smtp.port}")             int smtpPort; 
    @Value("${mail.from.notifier.address}") String notifier;//domain can't be harcoded because it seems that simple class are intacied after spring (npe with contextutil)
    @Value("${mail.from.notifier.alias}")   String aliasNotifier;
    
    @PostConstruct
    public void postConstruct() {
     	BasicConfigurator.configure();
        javaMailSender = new JavaMailSenderImpl();  // Class of Spring.
        javaMailSender.setProtocol("smtp");
        javaMailSender.setHost(smtpHost);
        javaMailSender.setPort(smtpPort);

        if(environment == Environment.DEV){ // u need to set port 587 and smtp.gmail.com in config.properties file too .
            javaMailSender.setUsername("reformyourcountrytest@gmail.com");
            javaMailSender.setPassword("technofutur");
            javaMailSender.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
            javaMailSender.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
        }
        setName("MailSender"); // Sets the name of the thread to be visible in the prod server thread list.
        if(environment.getMailBehavior() != MailBehavior.NOT_STARTED){
        	this.start();
        	log.info("Mail Server Started !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " + environment.getMailBehavior());
        } else {
        	log.info("DevMode on, mail thread not started");
        }

       
    }

    @PreDestroy
    public void shutDown () {
        this.isShutDown = true;
        log.info("MailSender shutting down");
        interrupt();  // In case the thread is sleeping or waiting.
    }	

    @Override
    public void run(){

        log.info("MailSender thread started");
        try {
			Thread.sleep(2 * 60 * 1000);  // sleep 2 minute to make sure all the bean are ready
		} catch (InterruptedException e) {
	        log.info("MailSender initial sleep interrupted");
		}

        log.info("MailSender awaken from its initial sleep");

        mainLoop: while (!isShutDown) {

        	List<Mail> nextMailList = this.findNextMails();
            log.info(nextMailList.size() + " mails found to send");
            
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

        log.info("MailSender thread ended");
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
            log.info(e);
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
            log.info(e);
        }
    }


    public void sendMailIndividually(Mail mail) {
    	   
        MailTemplateService.MailSubjectAndContent mp = this.mainTemplate.templateMail(mail);

        //	this.sendToFile(mp);
        String emailTarget = mail.getUser() != null ? mail.getUser().getMail() : mail.getEmailTarget();
        String emailSender = mail.getReplyTo() != null ? mail.getReplyTo().getMail() : 
                (mail.getEmailReplyTo()!=null ?mail.getEmailReplyTo():notifier);
      
        // Sanity Check
        if(StringUtils.isBlank(emailSender)){
       		log.error("User with no email found : " + mail.getReplyTo().getFullName());
        	return; // Do not continue
        }
        
        String nameOfSender = "";
        if (emailSender.equals(notifier)) {
            nameOfSender = aliasNotifier;
        } else if (mail.getReplyTo() != null){
            nameOfSender = mail.getReplyTo().getFullName(); 
        }
        
        sendMail(emailTarget, null, null, 
                mail.getReplyTo() == null ? null : mail.getReplyTo().getMail(), // reply to
                        emailSender,    // From
                        nameOfSender,  // Alias  
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

            StringBuilder strLog = new StringBuilder();
            strLog.append(Thread.currentThread().getName());
            strLog.append("\n================ MAIL ================\n");
            strLog.append("TO : \n");
            strLog.append(to);
            strLog.append("\n");
            strLog.append("CC : \n");
            strLog.append(cc);
            strLog.append("\n");
            strLog.append("BCC : \n");
            strLog.append(bcc);
            strLog.append("\n");
            strLog.append("FROM : ");
            strLog.append(from + " <" + fromAlias + ">");
            strLog.append("\n");
            strLog.append("SUBJECT : ");
            strLog.append(subject);
            strLog.append("\n");
            strLog.append("TEXT:\n");
            strLog.append(text);
            strLog.append("\n");
            strLog.append("============== END MAIL ==============");

            log.info(strLog.toString());

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
            log.error("Exception while sending mail", e);
        	
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
