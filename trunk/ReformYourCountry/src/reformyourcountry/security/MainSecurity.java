package reformyourcountry.security;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;



import blackbelt.security.Privilege;
import blackbelt.security.SecurityContext;
import reformyourcountry.dao.UserDao;
import reformyourcountry.exceptions.InvalidPasswordException;
import reformyourcountry.exceptions.UserAlreadyExistsException;
import reformyourcountry.exceptions.UserLockedException;
import reformyourcountry.exceptions.UserNotFoundException;
import reformyourcountry.exceptions.UserNotValidatedException;
import reformyourcountry.model.User.Gender;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.service.UserService;
import reformyourcountry.test.MainTestUser;



public class MainSecurity {

	public static void main(String[] args) throws UserNotFoundException, InvalidPasswordException, UserNotValidatedException, UserLockedException, WaitDelayNotReachedException {
	   
		 
	     String identifier=null;
	     String password=null;
			UserService userService=new UserService();
		LoginService loginService=new LoginService();    
		UserDao userdao = new UserDao();


		try {
			userService.registerUser(false, "piba", "piba", Gender.MALE, "pibapiba", "passe", "piba@mail.com");
		} catch (UserAlreadyExistsException e) {
			throw new RuntimeException(e);
		} 

		System.out.println("\nWe have to validate the user registration");
         MainTestUser.validateUser();
         
         System.out.println("\nNow we try to log the user");
         System.out.println("Give your User name please!\n");
         identifier=consoleForm();
         
         System.out.println("Give your Password please\n");
         password=consoleForm();
         
         if(loginService.login(identifier,password,false).equals(null)) {
        	 System.out.println("the log has failed");
         }
         else {
        	 System.out.println("Sucess for the log");
        	 userdao.getUserByUserName(identifier).setId(145L);
        	 SecurityContext.setUserId(userdao.getUserByUserName(identifier).getId());
        	 
        	 
        	 System.out.println("Id"+SecurityContext.getUserId());
        	
         }
        System.out.println("Value of the current user found by id :"+userdao.get(SecurityContext.getUserId()));
        SecurityContext.setUser(userdao.get(SecurityContext.getUserId()));
    
        System.out.println("Value of threadlocal user :"+SecurityContext.getUser());
        
        EnumSet<Privilege> listPrivilege;
        privileges.add(Privilege.MANAGE_NEWS);
        privileges.add(Privilege.MANAGE_NEWSLETTERS);
        privileges.add(Privilege.SEND_NEWSLETTERS);
       
        userdao.get(SecurityContext.getUserId()).setPrivileges(listPrivilege);
           
       
	}
	
	
    public static String consoleForm(){
	     return   scanKeyBoard.next();
	}
			
    private static Scanner scanKeyBoard = new Scanner(System.in);
    
}	
