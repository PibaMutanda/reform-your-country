package reformyourcountry.batch;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import reformyourcountry.util.HtmlToTextUtil;
import reformyourcountry.util.Logger;

@Service
public class TestMail implements Runnable {
    
    @Logger
    static Log log;

    static JavaMailSenderImpl javaMailSender; // This is not a Spring bean so @Autowired not usable
    
    
    String smtpHost="smtp.gmail.com"; 
    int smtpPort=465; 
    @Value("${mail.from.notifier.address}") String notifier;//domain can't be harcoded because it seems that simple class are intacied after spring (npe with contextutil)
    @Value("${mail.from.notifier.alias}")   String aliasNotifier;
    
    private String to = "mr.maxime.sauvage@gmail.com";
    
    private String testContent="test content @ me ";
    
    /**
     * @param args
     */
    public static void main(String[] args) {
        BatchUtil.startSpringBatch(TestMail.class);

    }

    @Override
    public void run() {
        
        sendMail(to, "", "", notifier, notifier, notifier, "test", testContent, true);
        
    }
    
    @PostConstruct
    public void postConstruct() {
        BasicConfigurator.configure();
        javaMailSender = new JavaMailSenderImpl();  // Class of Spring.
        javaMailSender.setProtocol("smtp");
        javaMailSender.setHost(smtpHost);
        javaMailSender.setPort(smtpPort);
       
    }
    
    private static void sendMail(final String to, final String cc, final String bcc,
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

            System.out.println(strLog);

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

                        if (cc != null && ! cc.isEmpty()) {
                            mimeMessageHelper.setCc(new String[]{cc});
                        }
                        if (bcc != null && ! bcc.isEmpty()) {
                            mimeMessageHelper.setBcc(new String[]{bcc});
                        }
                    }
                };
              
                     
                javaMailSender.send(mimeMessagePreparator);
                System.out.println("Mail send");
        } catch(Exception e){//if we can't send the mail, continue
            // if we can't send for any reason, we don't stop the thread, we will just remove this mail from the database and we will continue to send mails.
            // Typical exception: the mail address is invalid.
            log.error("Exception while sending mail", e);
            
        }

    }

}
