package reformyourcountry.mail;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import reformyourcountry.model.Mail;
import reformyourcountry.model.User;
import reformyourcountry.repository.MailRepository;
import reformyourcountry.service.UserService;
import reformyourcountry.web.UrlUtil;

/**
 * @author Julien Van Assche
 * @author Pierre Mistrot
 * @author Johan Gusbin
 * Generates a Html string based on a mail
 */
@Service
@Configurable(preConstruction=true)
public final class MailTemplateService {

    @Autowired MailRepository dao;
    @Autowired UserService userService;

    public MailTemplateService() {}

    private String templateBody(Mail mail) {
    	
    	String template = "<div style=\"width:100%\">"
            + "<div style=\"background:#FFFFFF;\">"
            + "<font face=\"Arial\" color=\"#000000\" size=\"4\">"
            +  mail.getSubject()
            + "</font><br><div align=\"right\"><font color=\"#BBBBBB\" size=\"1\">"
            +  mail.getCreatedOn()
            + "</font></div></div>"
            + "<div style=\"background:#ffffff;padding:2px;padding-left:5px;\">"
            + mail.getContent() +
            "</div></div><br>";
    	
        return template;
    }
    
    /**
     * Determine the footer for a given mail. If the mail is for a user, we insert a link to this account
     * and if the mail's type is groupable, the footer will have options for the user. 
     * For any other mails the footer will simply contain a url link of the website. 
     * @param user
     * @param isGrouped
     * @return the footer string with the user option if there is one or just the web site url.
     */
    private String templateFooter(User user, boolean isGrouped, boolean isNewsletter) {
        
        String footer="";
        if (user !=null){
            footer = "<br><hr><br><div align=\"justify\">your account : <a href='"+UrlUtil.getAbsoluteUrl("user/" + user.getUserName())+"'>"+user.getUserName()+"</a>";
            footer = "";

            
            if (isGrouped) {
                MailingDelayType mailDelayOption;
                MailingDelayType secondDelayOption;
                MailingDelayType thirdDelayOption;
                if(user.getMailingDelayType() == MailingDelayType.DAILY) {
                    mailDelayOption = user.getMailingDelayType();
                    secondDelayOption = MailingDelayType.IMMEDIATELY;
                    thirdDelayOption = MailingDelayType.WEEKLY;
                } else if(user.getMailingDelayType() == MailingDelayType.WEEKLY) {
                    mailDelayOption = user.getMailingDelayType();
                    secondDelayOption = MailingDelayType.IMMEDIATELY;
                    thirdDelayOption = MailingDelayType.DAILY;
                } else {
                    mailDelayOption = user.getMailingDelayType();
                    secondDelayOption = MailingDelayType.DAILY;
                    thirdDelayOption = MailingDelayType.WEEKLY;
                }
                footer += "<br>group option is "
                    + mailDelayOption
                    + "  change to : "
                    
                    + "secondDelayOption"
                    + secondDelayOption
                    
                    + "thirdDelayOption"
                    + thirdDelayOption;
            }
            if (isNewsletter) {
            	footer += "If you wish to unsubscribe from the "+UrlUtil.getProdAbsoluteDomainName()+" newsletter, please follow ";
            		// TODO: FEATURE TO IMPLEMENT http://..../unsubscribenewsletter/" + user.getUserName() + "/" +  String userSecurityString = userService.getUserSecurityString(user);
            }
        } else {
            footer = "<br><hr><br><div align=\"justify\"><a href='"+UrlUtil.getAbsoluteUrl("")+"'>"+UrlUtil.getProdAbsoluteDomainName()+"</a></div>";
        }
      
        return footer;
    }

    public class MailSubjectAndContent{

        public String subject = "";
        public String content = "";

    }

    public MailSubjectAndContent templateMail(Mail mail) {
    	return templateMail(Arrays.asList(mail));
    }

    
    /** Builds the complete content and subject of the one mail to be sent.
     * If the mails parameter contains more than a mail, they are all groupables. Individual mails are always passed individually to this method.
     */
    public MailSubjectAndContent templateMail(List<Mail> mails) {
    	// We generate links that use Vaadin Navigator7. 
    	// But this could be called by the MailSender thread before Vaadin servlet did initialize Navigator7.
    	// We launch manually that initialization here (there are checks in the init method to prevent double init). 
    	//WebApplication.init(BlackBeltWebApplication.class);
    	
    	
        // Params check
        if (mails == null){
            throw new IllegalArgumentException("bug: No list defined for 'mails'.");
        } else if (mails.size() == 0){
            throw new IllegalArgumentException("bug: No mail defined for template.");
        } else if ((mails.get(0).getUser() == null && mails.get(0).getEmailTarget() == null) || (mails.get(0).getUser() != null && mails.get(0).getEmailTarget() != null)){
            throw new IllegalArgumentException("bug: No user or address mail defined (the user is obtained by the first mail in the list).");
        }

        MailSubjectAndContent msc = new MailSubjectAndContent();
        User recipient = mails.get(0).getUser();
        //content = this.templateHeader(user);
        
        // Building of content
        for (Mail mail : mails) {
        	msc.content += (mail.getUseTemplate()) ?
        			this.templateBody(mail) // Case of most mails.
        			:
        				mail.getContent();  // This would be the case of a newsletter: we don't add a template around.

        }

        //If the mail is a newsletter, we add the right footer
        if(mails.get(0).getMailType() == MailType.SLOW_NOT_GROUPABLE){
        	msc.content  += this.templateFooter(recipient, false, true);
        } else {
        	msc.content  += this.templateFooter(recipient, true, false);
        }


        

        // Building of subject
        if (mails.get(0).getMailType() == MailType.GROUPABLE) {  // We have a list of groupable mails.
            msc.subject = this.mainSubjectOfGroupedMails(mails);
        } else {
            msc.subject = mails.get(0).getSubject();
        }

        return msc;
    }

    /** From a list of grouped mail, makes a single subject string that represents every mail
     *
     *  **See Example in MailCategory**   
     * 
     * */ 
    private String mainSubjectOfGroupedMails(List<Mail> mails) {

        if(mails.size()==1) {  // One mail => we just take the subject of the mail.
            return mails.get(0).getSubject();

        } else {  // Multiple mail => we have to build a generic subject.
            String subjectOfGroupedMails = "";
            MailCategory[] mailCategoryValues = MailCategory.values();
            int numerOfCategories = mailCategoryValues.length;
            
            for (MailCategory mailCategory : mailCategoryValues) {
                numerOfCategories--;
                // Count the mails of the current subject.
                int amountOfMailsForTheCurrentSubject = 0;
                for (Mail mail : mails) {
                    if (mail.getMailCategory() == mailCategory) {
                        amountOfMailsForTheCurrentSubject++;
                    }
                }

                if (amountOfMailsForTheCurrentSubject != 0){
                    subjectOfGroupedMails += (Integer.toString(amountOfMailsForTheCurrentSubject) + " - " + mailCategory.getText());
                    
                    // if it is the last categorie to be added to the subject don't add a comma at the end of the subject
                    if(numerOfCategories<1){
                        subjectOfGroupedMails += ", ";
                    }
                }
                //Make a space to avoid that two mailcategory are glued
                subjectOfGroupedMails += " ";
                
            }
            return subjectOfGroupedMails;
        }
    }
    //talk to john what's this method
    /*private String userNameWithAbsoluteLink(User user){
    	return UrlUtil.getAbsoluteUrl((new EntityPageResource(UserPage.class, user)));
    }*/
}
