package reformyourcountry.security;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;



import blackbelt.security.Privilege;
import blackbelt.security.SecurityContext;
import blackbelt.web.ContextUtil;

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
	     Set<User>list=listUser();
	     User userIdentify=new User();
	     
	     boolean successfulLogin=true;
	    
	  if(list==null)
	        System.out.println("there isn't privilege list");
	     else{
	    	 
	         do {
	             
	             System.out.println("Give your User name please!\n\n");
	             identifier=consoleForm();
	             
	             System.out.println("Give your Password please\n\n");
                 password=consoleForm();  
                 
	              for (User user : list) {
                
	           	  if(identifier.equals(user.getUserName()) && password.equals(user.getPassword()))
                  {
	           	      
	           	     
	           	      successfulLogin=true;
	           	      userIdentify.setUserName(user.getUserName());                                               
                      userIdentify.setLastName(user.getLastName());
                      userIdentify.setPassword(user.getPassword());
                      userIdentify.setPrivileges(user.getPrivileges());  
                      userIdentify.setMail(user.getMail());
                      userIdentify.setId(user.getId());
	           	     
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
                    System.out.println("security context by: user"+ SecurityContext.getUser().toString());
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
	 
	 }
	
    public static String consoleForm(){
	     return   scanKeyBoard.next();
	}
	
	
	public static Set<User> listUser(){
	    
	    Set<User> list=new HashSet<User>();
	    Set<Privilege>privilegeP=new HashSet<Privilege>();
	    Set<Privilege>privilegeJ=new HashSet<Privilege>();
	    UserService userService=new UserService();
	    
	    User userP=new User();
	    UserDao userdao=new UserDao();
	    // if the user is not yet registered in database, we create and register a new user
	    if(userdao.getUserByUserName("Piba")==null)
	    {
	    	userP.setFirstName("Piba");
	         userP.setLastName("Piba");
	         userP.setPassword("passe");
	         userP.setMail("piba@mail.com");
	         userP.setGender(Gender.MALE);
	         userP.setUserName("pibapiba");
	         userP.setId(12345L);
	         //we add some random privileges for the user (only for the test)
	         privilegeP.add(Privilege.CREATE_QUESTIONS);
	         privilegeP.add(Privilege.EDIT_COMMUNITY_USERS_PRIVILEGES);
	         privilegeP.add(Privilege.EDIT_INFLUENCE_OF_USERS);
	         privilegeP.add(Privilege.GET_NOTIFIED_WHEN_USERS_REACH_MANY_POINTS);
	         userP.setPrivileges(privilegeP);
	        
	         // the first field is always true(only for the test)
	         try {
	     		userService.registerUser(true,userP.getFirstName(),userP.getLastName(),userP.getGender(),userP.getUserName(),userP.getPassword(),userP.getMail());
	     	} catch (UserAlreadyExistsException e) {
	     		// TODO Auto-generated catch block
	     		e.printStackTrace();
	     	}
	    }
	    else
	    {
	    	userP=userdao.getUserByUserName("Piba");
	    }
	         

		 User userJ=new User(); 
	 //we do the same with a second user
	    if(userdao.getUserByUserName("Jerome")==null)
	    {
	    
		       userJ.setFirstName("Jerome");  
		       userJ.setLastName("Jerome");
		       userJ.setPassword("Jerome");
		       userJ.setMail("jerome@mail.com");
		       userJ.setGender(Gender.MALE);
		       userJ.setUserName("jeromejerome");
		       userJ.setId(67890L);
		            privilegeJ.add(Privilege.EDIT_WIKIS);
		            privilegeJ.add(Privilege.EDIT_REF_MANUAL);
		            privilegeJ.add(Privilege.MANAGE_COACH_OFFERINGS);
		       userJ.setPrivileges(privilegeJ);
		   
		       
		  
		       try {
		   		userService.registerUser(true,userJ.getFirstName(),userJ.getLastName(),userJ.getGender(),userJ.getUserName(),userJ.getPassword(),userJ.getMail());
		   	} catch (UserAlreadyExistsException e) {
		   		// TODO Auto-generated catch block
		   		e.printStackTrace();
		   	}     
	    }
	    else
	    {
	    	userJ=userdao.getUserByUserName("Jerome");
	    } 
	    list.clear();
	    list.add(userP);  
	  
	    list.add(userJ);
	    
	    return  list;
	}
	
	private static LoginService loginService=new LoginService();
    private static Scanner scanKeyBoard = new Scanner(System.in);
    
}	
