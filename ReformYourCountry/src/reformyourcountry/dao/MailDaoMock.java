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
	
	private static int number = 0;
	
	private static int nbtest = 0;
	
	

   private static MailDaoMock instance = null ;
	
   
   
	private MailDaoMock(){
		
		listOfMails = new ArrayList<Mail>();
		mailsToRemove = new ArrayList<Mail>();
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
    	
    	User user = new User();
    	
    	user.setFirstName("toto");
    	user.setLastMailSentDate(null);
    	user.setLastName("jojo");
    	user.setMail("reformyourcountrytest@gmail.com");
    	
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
    	

    	
    	return createListOfMail(user);
    	

    }
    /**
     * returns a user (the next one) having grouped mails that must be sent now.
     * @return a user
     */
    @SuppressWarnings("unchecked")
    public User userHavingGroupedMails(){
   

        //request to get a user having at least one old pending grouped mails (so old that we must send it now). 
        
	    User user = new User();
    	user.setFirstName("titi" + number++);
    	user.setLastMailSentDate(null);
    	user.setLastName("jojo");
    	user.setMail("reformyourcountrytest@gmail.com");
    
        return user;
    }

    /**
     * Get mails for not blackbelt user
     */
    @SuppressWarnings("unchecked")
    public List<Mail> getMailsEmailTarget(){
    	
     	return createListOfMail();
    }


    /**
     * returns a user containing slow not groupable mails
     * @return a user
     */
    @SuppressWarnings("unchecked")
    public User userHavingSlowMails(){
 	   User user = new User();
   	
    	user.setFirstName("bobo"  + number++);
   	    user.setLastMailSentDate(null);
    	user.setLastName("jojo");
   	    user.setMail("reformyourcountrytest@gmail.com");
       
       return user;
    }

    /**
     * remove a list of mails
     * @param mails
     */
    public void flushListeMailToRemove() {
    	
    	
    	listOfMails.removeAll(mailsToRemove);
    	mailsToRemove = new ArrayList<Mail>();
        /*if(!mails.isEmpty()){
        	if(listOfMails.containsAll(mails)){
        		listOfMails.removeAll(mails);
        	   System.out.println("mails removed");
        	}
        }     		*/
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
       User user = new User();
   	   user.setFirstName("bobo");
  	   user.setLastMailSentDate(null);
       user.setLastName("jojo");
  	   user.setMail("bobo@gmail.com");
  	   
  	   mail.setUser(user);
  	   listOfMails.add(mail);
      
    }
    
    
    private List<Mail> createListOfMail(User user){
    	
    	if(nbtest == 0){
    	nbtest ++;
    	Mail mail1 = new Mail(user," 1 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail2 = new Mail(user,"2 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
    	Mail mail3 = new Mail(user,"3 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail4 = new Mail(user,"4 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
    	Mail mail5 = new Mail(user,"5 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail6 = new Mail(user,"6 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.SLOW_NOT_GROUPABLE,false);
    	Mail mail7 = new Mail(user,"7 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail8 = new Mail(user,"8 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
    	Mail mail9 = new Mail(user,"9 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.SLOW_NOT_GROUPABLE,false);
    	Mail mail10 = new Mail(user,"10 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
    	Mail mail11 = new Mail(user,"11 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail12 = new Mail(user,"12 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
    	Mail mail13 = new Mail(user,"13 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail14 = new Mail(user,"14 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
    	Mail mail15 = new Mail(user,"15 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.SLOW_NOT_GROUPABLE,false);
    	Mail mail16 = new Mail(user,"16 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.SLOW_NOT_GROUPABLE,false);
    	Mail mail17 = new Mail(user,"17 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail18 = new Mail(user,"18 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
    	Mail mail19 = new Mail(user,"19 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail20 = new Mail(user,"20 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
    	Mail mail21 = new Mail(user,"21 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.SLOW_NOT_GROUPABLE,false);
    	Mail mail22 = new Mail(user,"22 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
    	Mail mail23 = new Mail(user,"23 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
    	Mail mail24 = new Mail(user,"24 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
    	
    	
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
    	
    
    	flushListeMailToRemove();
    	System.out.println("1flush mails "+listOfMails.size());
    	return listOfMails;
    	
    }
    
       private  List<Mail> createListOfMail(){
    	   
       
       	if(nbtest == 0){
        nbtest++;
       	Mail mail1 = new Mail("lionel.timmerman@gmail.com" , "1 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
       	Mail mail2 = new Mail("lionel.timmerman@gmail.com","2 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
       	Mail mail3 = new Mail("lionel.timmerman@gmail.com","3 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
       	Mail mail4 = new Mail("lionel.timmerman@gmail.com","4 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
       	Mail mail5 = new Mail("lionel.timmerman@gmail.com","5 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
       	Mail mail6 = new Mail("lionel.timmerman@gmail.com","6 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.SLOW_NOT_GROUPABLE,false);
       	Mail mail7 = new Mail("lionel.timmerman@gmail.com","7 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
       	Mail mail8 = new Mail("lionel.timmerman@gmail.com","8 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
       	Mail mail9 = new Mail("lionel.timmerman@gmail.com","9 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.SLOW_NOT_GROUPABLE,false);
       	Mail mail10 = new Mail("lionel.timmerman@gmail.com","10 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
       	Mail mail11 = new Mail("lionel.timmerman@gmail.com","11 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
       	Mail mail12 = new Mail("lionel.timmerman@gmail.com","12 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
       	Mail mail13 = new Mail("lionel.timmerman@gmail.com","13 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
       	Mail mail14 = new Mail("lionel.timmerman@gmail.com","14 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
       	Mail mail15 = new Mail("lionel.timmerman@gmail.com","15 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.SLOW_NOT_GROUPABLE,false);
       	Mail mail16 = new Mail("lionel.timmerman@gmail.com","16 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.SLOW_NOT_GROUPABLE,false);
       	Mail mail17 = new Mail("lionel.timmerman@gmail.com","17 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
       	Mail mail18 = new Mail("lionel.timmerman@gmail.com","18 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
       	Mail mail19 = new Mail("lionel.timmerman@gmail.com","19 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
       	Mail mail20 = new Mail("lionel.timmerman@gmail.com","20 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
       	Mail mail21 = new Mail("lionel.timmerman@gmail.com","21 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.SLOW_NOT_GROUPABLE,false);
       	Mail mail22 = new Mail("lionel.timmerman@gmail.com","22 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
       	Mail mail23 = new Mail("lionel.timmerman@gmail.com","23 Hello toto"+number,MailCategory.USER,"this is my mail content",MailType.IMMEDIATE,false);
       	Mail mail24 = new Mail("lionel.timmerman@gmail.com","24 Hello tata"+number,MailCategory.USER,"this is my mail content too",MailType.GROUPABLE,false);
       
       	
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
       	System.out.println("2flush mails");
    	flushListeMailToRemove();
       	return listOfMails;
    	   
       }
    

}
