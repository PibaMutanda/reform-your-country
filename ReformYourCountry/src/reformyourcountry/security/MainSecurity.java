package reformyourcountry.security;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;



import blackbelt.security.Privilege;
import blackbelt.security.SecurityContext;
import blackbelt.util.SecurityUtils;
import reformyourcountry.dao.UserDao;
import reformyourcountry.exceptions.InvalidPasswordException;
import reformyourcountry.exceptions.UserAlreadyExistsException;
import reformyourcountry.exceptions.UserLockedException;
import reformyourcountry.exceptions.UserNotFoundException;
import reformyourcountry.exceptions.UserNotValidatedException;
import reformyourcountry.model.User;
import reformyourcountry.model.User.Gender;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.service.UserService;
import reformyourcountry.test.MainTestUser;



public class MainSecurity {

	public static Long userID;
	public static void main(String[] args) throws UserNotFoundException, InvalidPasswordException, UserNotValidatedException, UserLockedException, WaitDelayNotReachedException {
	   
		 
	     String identifier=null;
	     String password=null;
	     UserService userService=new UserService();
	     LoginService loginService=new LoginService();    
	     UserDao userdao = new UserDao();
         
	    
         try {
			userService.registerUser(false, "piba", "piba", Gender.MALE, "pibapiba", "passe", "piba@mail.com");
		} catch (UserAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("The user is already registered.");
		} 
         System.out.println("We have to validate the user registration");
         MainTestUser.validateUser();
         
         System.out.println("Now we try to log the user");
         System.out.println("Give your User name please!\n\n");
         identifier=consoleForm();
         
         System.out.println("Give your Password please\n\n");
         password=consoleForm();
         
         if(MainTestUser.login(identifier,password,false).equals(null)) {
        	 System.out.println("the log has failed");
         }
         else {
        	 userdao.getUserByUserName(identifier).setId(145L);
        	 SecurityContext.setUserId(userdao.getUserByUserName(identifier).getId());
        	 
        	 
        	 System.out.println("Id"+SecurityContext.getUserId());
        	
         }
        System.out.println("recupéraion du user sur base de l'id :"+userdao.get(SecurityContext.getUserId()));
        SecurityContext.setUser(userdao.get(SecurityContext.getUserId()));
    
        
       
         
	}
	 /* if(list==null)
	        System.out.println("there isn't privilege list");
	     else{
	    	 
	         do {
	             
	             System.out.println("Give your User name please!\n\n");
	             identifier=consoleForm();
	             
	             System.out.println("Give your Password please\n\n");
                 password=consoleForm();  
                
	              for (User user : list) {
                
	           	  if(identifier.equals(user.getUserName()) && passwordmd5.equals(user.getPassword()))
                  {
	           	      
	           	     
	           	      successfulLogin=true;
	           	     
                	  break;
	           	      
	           	      
                  }
                  else
                  {
                	 System.out.println("incorrect  username or   Password \n\n\n\n");
                	  successfulLogin=false; 
                	 loginService.assertNoInvalidDelay(user);
                	  
                  }
	            	                    
                }      
                             
            } while (!successfulLogin );
	         
            if(successfulLogin){
                System.out.println("Name: "+userIdentify.getLastName()+"\n"+"Password: "+userIdentify.getPassword()+"\n"+"Privilege(s): "+userIdentify.getPrivileges());
                SecurityContext.setUser(userIdentify);
                userID=SecurityContext.getUserId();
                SecurityContext.setThreadPrivilege(userIdentify.getPrivileges());
                System.out.println(SecurityContext.getUserId());
                
                
                
                try {
                    System.out.println("security context by: user "+ SecurityContext.getUser().toString());
                    System.out.println("security context by: Privilege "+ SecurityContext.getThreadPrivilege().get());
                    System.out.println("security context by: Id "+ SecurityContext.getUserId().toString());
                    
                } catch (UserNotFoundException | InvalidPasswordException
                        | UserNotValidatedException | UserLockedException
                        | WaitDelayNotReachedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
                //we check if the user recently added could be find with his e-mail
                try {
                    userIdentify=loginService.identifyUser(userIdentify.getMail());
                    System.out.println(userIdentify.getLastName());
                } catch (UserNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
               
               
                 
            }
            else
                System.out.println("I am sorry, you can not connect");
	    
	     }
	 
	 }*/
	
    public static String consoleForm(){
	     return   scanKeyBoard.next();
	}
	
	
	/*public static Set<User> listUser(){
	    
	    Set<User> list=new HashSet<User>();
	    Set<Privilege>privilegeP=new HashSet<Privilege>();
	    Set<Privilege>privilegeJ=new HashSet<Privilege>();
	    UserService userService=new UserService();
	    
	    
	  
	    list.clear();
	    list.add(userP);  
	  
	    list.add(userJ);
	    
	    return  list;
	}*/
	
	
    private static Scanner scanKeyBoard = new Scanner(System.in);
    
}	
