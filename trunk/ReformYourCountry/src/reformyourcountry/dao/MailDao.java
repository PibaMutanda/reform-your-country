package reformyourcountry.dao;

import java.util.ArrayList;
import java.util.List;

import reformyourcountry.model.Mail;
import reformyourcountry.model.User;
import blackbelt.mail.MailCategory;
import blackbelt.mail.MailType;


public class MailDao {
	
	
    /**
     * returns a user containing immediate mails
     * @return a user
     */

    @SuppressWarnings("unchecked")
    public User userHavingImmediateMails(){
    	
    	User user = new User();
    	
    	user.setFirstName("toto");
    	user.setLastMailSentDate(null);
    	user.setLastName("jojo");
    	user.setMail("@gmail.com");
    	
        return user;
        
    }
		
    
    /**
     * Get mails, immediate or groupable  from a given user.
     * @param isImmediate indicates if we want immediate mails or grouped mails.
     * @param user the user who we want the mail.
     * @return a list of mails 
     */
    @SuppressWarnings("unchecked")
    public List<Mail> getMailsFromUser(MailType mailType, User user){
    	
    	Mail mail1 = new Mail(user,"Hello toto",MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail2 = new Mail(user,"Hello tata",MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
    	
    	List<Mail> liste = new ArrayList<Mail>();
    	
    	liste.add(mail1);
    	liste.add(mail2);
    	
    	return liste;
    	

    }
    /**
     * returns a user (the next one) having grouped mails that must be sent now.
     * @return a user
     */
    @SuppressWarnings("unchecked")
    public User userHavingGroupedMails(){
   

        //request to get a user having at least one old pending grouped mails (so old that we must send it now). 
        
	   User user = new User();
    	
    	user.setFirstName("titi");
    	user.setLastMailSentDate(null);
    	user.setLastName("jojo");
    	user.setMail("@gmail.com");
        
        return user;
    }

    /**
     * Get mails for not blackbelt user
     */
    @SuppressWarnings("unchecked")
    public List<Mail> getMailsEmailTarget(){
    	
     	Mail mail1 = new Mail("toto@gmail.com","Hello toto",MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail2 = new Mail("tutu@gmail.com","Hello tutu",MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
    	
    	List<Mail> liste = new ArrayList<Mail>();
    	
    	liste.add(mail1);
    	liste.add(mail2);
        return liste;
    }


    /**
     * returns a user containing slow not groupable mails
     * @return a user
     */
    @SuppressWarnings("unchecked")
    public User userHavingSlowMails(){
 	   User user = new User();
   	
    	user.setFirstName("bobo");
   	 user.setLastMailSentDate(null);
    	user.setLastName("jojo");
   	 user.setMail("@gmail.com");
       
       return user;
    }

    /**
     * remove a list of mails
     * @param mails
     */
    public void removeMails(List<Mail> mails) {
        if(!mails.isEmpty()){
        	System.out.println("mails removed");
                 mails = null;
        }
    }

}
