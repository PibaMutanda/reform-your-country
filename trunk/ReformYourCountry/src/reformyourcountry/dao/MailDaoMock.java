package reformyourcountry.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import reformyourcountry.mail.MailCategory;
import reformyourcountry.mail.MailType;
import reformyourcountry.model.Mail;
import reformyourcountry.model.User;


public class MailDaoMock {
	
	List<Mail> listOfMails;
	
	List<Mail> mailsToRemove;
	
	private User user;
	
	private static int number = 0;
	
	private static int nbtest = 0;
	
	   private static int nbtestGroup = 0;

   private static MailDaoMock instance = null ;
	
   
   
	private MailDaoMock(){
		
		 listOfMails = new ArrayList<Mail>();
		 mailsToRemove = new ArrayList<Mail>();
		 user = new User();
	     user.setFirstName("bobo");
	     user.setLastMailSentDate(null);
	     user.setLastName("jojo");
	     user.setMail("reformyourcountrytest@gmail.com");
	}
	
	/**
	 * create the unique instance of MailDaoMock if it doesn t exist yet
	 * @return MailDaoMock instance
	 */
	public static synchronized MailDaoMock getInstance(){
		
		
		if(instance == null){
		    instance =  new MailDaoMock();
		    
		}
		
	
			return instance ;	
	}
	
    /**
     * returns a user containing immediate mails
     * @return a user
     */
    @SuppressWarnings("unchecked")
    public User userHavingImmediateMails(){
    	
      if(nbtest > 0 ) return null;
    	
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
    	
           if(mailType == MailType.IMMEDIATE)
    	
    	return createListOfMail(user);
           
           else
        	   if(mailType == MailType.GROUPABLE)
        		   return createListOfMailToGroup(user);
        	   else
        		   return createListOfMail(user);
    	

    }
    /**
     * returns a user (the next one) having grouped mails that must be sent now.
     * @return a user
     */
    @SuppressWarnings("unchecked")
    public User userHavingGroupedMails(){
   

        //request to get a user having at least one old pending grouped mails (so old that we must send it now). 
        // if the test is executed more than one time return null for the user
        if( nbtestGroup > 0) return null;
    
        return user;
    }

    /**
     * Get mails for not blackbelt user
     */
    @SuppressWarnings("unchecked")
    public List<Mail> getMailsEmailTarget(){
    	
     	return new ArrayList<Mail>();
    }


    /**
     * returns a user containing slow not groupable mails
     * @return a user
     */
    @SuppressWarnings("unchecked")
    public User userHavingSlowMails(){
 	   
   	
  
       
       return user;
    }

    /**
     * remove a list of mails
     * @param mails
     */
    public void flushListeMailToRemove() {
    	
        if(!mailsToRemove.isEmpty()){
    	
    	listOfMails.removeAll(mailsToRemove);
    	mailsToRemove = new ArrayList<Mail>();
        }
     
    }
    
    public void removeMails(List<Mail> mails) {
    	
    	mailsToRemove.addAll(mails);
    }
    
    
    /**
     * saves a mail in the database
     * @param mail
     * @param idUser
     */
    public void save(Mail mail, Long idUser) {

  	   listOfMails.add(mail);
      
    }
    
    
    private List<Mail> createListOfMail(User user){
        flushListeMailToRemove();
    	if(nbtest == 0){
    	nbtest ++;
    	Mail mail1 = new Mail(user," 1 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail2 = new Mail(user,"2 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content too",MailType.IMMEDIATE,false);
    	Mail mail3 = new Mail(user,"3 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail4 = new Mail(user,"4 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content too",MailType.IMMEDIATE,false);
    	Mail mail5 = new Mail(user,"5 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail6 = new Mail(user,"6 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content too",MailType.IMMEDIATE,false);
    	Mail mail7 = new Mail(user,"7 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail8 = new Mail(user,"8 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content too",MailType.IMMEDIATE,false);
    	Mail mail9 = new Mail(user,"9 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail10 = new Mail(user,"10 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content too",MailType.IMMEDIATE,false);
    	Mail mail11 = new Mail(user,"11 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail12 = new Mail(user,"12 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content too",MailType.IMMEDIATE,false);
    	Mail mail13 = new Mail(user,"13 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail14 = new Mail(user,"14 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content too",MailType.IMMEDIATE,false);
    	Mail mail15 = new Mail(user,"15 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail16 = new Mail(user,"16 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content too",MailType.IMMEDIATE,false);
    	Mail mail17 = new Mail(user,"17 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail18 = new Mail(user,"18 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content too",MailType.IMMEDIATE,false);
    	Mail mail19 = new Mail(user,"19 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail20 = new Mail(user,"20 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content too",MailType.IMMEDIATE,false);
    	Mail mail21 = new Mail(user,"21 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail22 = new Mail(user,"22 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content too",MailType.IMMEDIATE,false);
    	Mail mail23 = new Mail(user,"23 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail24 = new Mail(user,"24 Hello "+user.getFirstName()+" "+number,MailCategory.USER,"this is my mail content too",MailType.IMMEDIATE,false);
    	
    	
    	mail1.setReplyTo(user);
    	mail2.setReplyTo(user);
    	mail3.setReplyTo(user);
    	mail4.setReplyTo(user);
    	mail5.setReplyTo(user);
    	mail6.setReplyTo(user);
    	mail7.setReplyTo(user);
    	mail8.setReplyTo(user);
    	mail9.setReplyTo(user);
    	mail10.setReplyTo(user);
    	mail11.setReplyTo(user);
    	mail12.setReplyTo(user);
    	mail13.setReplyTo(user);
    	mail14.setReplyTo(user);
    	mail15.setReplyTo(user);
    	mail16.setReplyTo(user);
    	mail17.setReplyTo(user);
    	mail18.setReplyTo(user);
    	mail19.setReplyTo(user);
    	mail20.setReplyTo(user);
    	mail21.setReplyTo(user);
    	mail22.setReplyTo(user);
    	mail23.setReplyTo(user);
    	mail24.setReplyTo(user);
    
    	
    	
    	
    	listOfMails.add(mail1);
    	listOfMails.add(mail2);
    	listOfMails.add(mail3);
    	listOfMails.add(mail4);
    	listOfMails.add(mail5);
    	listOfMails.add(mail6);
    	listOfMails.add(mail7);
    	listOfMails.add(mail8);
    	listOfMails.add(mail9);
    	listOfMails.add(mail10);
    	listOfMails.add(mail11);
    	listOfMails.add(mail12);
    	listOfMails.add(mail13);
    	listOfMails.add(mail14);
    	listOfMails.add(mail15);
    	listOfMails.add(mail16);
    	listOfMails.add(mail17);
    	listOfMails.add(mail18);
    	listOfMails.add(mail19);
    	listOfMails.add(mail20);
    	listOfMails.add(mail21);
    	listOfMails.add(mail22);
    	listOfMails.add(mail23);
    	listOfMails.add(mail24);
    	
    	
    	return listOfMails;
    	}
    	
    
    	
    	return listOfMails;
    	
    }
    
       private  List<Mail> createListOfMailToGroup(User user){
           flushListeMailToRemove();
       
       	if(nbtestGroup == 0){
       	 nbtestGroup++;
       	Mail mail1 = new Mail(user , "1 vous avez obtenu une nouvelle ceinture",MailCategory.BELT,"voici votre ceinture jaune",MailType.GROUPABLE,true);
       	Mail mail2 = new Mail(user,"2 vous avez obtenu une nouvelle ceinture",MailCategory.BELT,"voici votre ceinture orange",MailType.GROUPABLE,true);
       	Mail mail3 = new Mail(user,"3 vous avez obtenu une nouvelle ceinture",MailCategory.BELT,"voici votre ceinture verte",MailType.GROUPABLE,true);
       	Mail mail4 = new Mail(user,"4 vous avez obtenu une nouvelle ceinture",MailCategory.BELT,"voici votre ceinture bleue",MailType.GROUPABLE,true);
       	Mail mail5 = new Mail(user,"5 vous avez obtenu une nouvelle ceinture",MailCategory.BELT,"voici votre ceinture brune",MailType.GROUPABLE,true);
       	Mail mail6 = new Mail(user,"6 vous avez obtenu une nouvelle ceinture",MailCategory.BELT,"voici votre ceinture noir",MailType.GROUPABLE,true);
  
       mail1.setReplyTo(user);
       mail2.setReplyTo(user);
       mail3.setReplyTo(user);
       mail4.setReplyTo(user);
       mail5.setReplyTo(user);
       mail6.setReplyTo(user);
       
   /*    mail1.setEmailTarget(user.getMail());
       mail2.setEmailTarget(user.getMail());
       mail3.setEmailTarget(user.getMail());
       mail4.setEmailTarget(user.getMail());
       mail5.setEmailTarget(user.getMail());
       mail6.setEmailTarget(user.getMail());*/
       	
       	listOfMails.add(mail1);
       	listOfMails.add(mail2);
       	listOfMails.add(mail3);
       	listOfMails.add(mail4);
       	listOfMails.add(mail5);
       	listOfMails.add(mail6);

       	
       	
       	return listOfMails;
       	}

    
       	return listOfMails;
    	   
       }
    

}
