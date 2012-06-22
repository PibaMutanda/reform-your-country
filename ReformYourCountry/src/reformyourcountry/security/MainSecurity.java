package reformyourcountry.security;

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
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.service.UserService;
import reformyourcountry.test.MainTestUser;



public class MainSecurity {

    public static Long userID;
    public static void main(String[] args) throws UserNotFoundException, InvalidPasswordException, UserNotValidatedException, UserLockedException, WaitDelayNotReachedException {
       
         
         String identifier=null;
         String password=null;
         UserService userService=new UserService();
         //LoginService loginService=new LoginService();    
         UserDao userdao = new UserDao();
         
        
         try {
            userService.registerUser(false, "piba", "piba", Gender.MALE, "pibapiba", "passe", "piba@mail.com");
        } catch (UserAlreadyExistsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("The user is already registered.");
        } 
         System.out.println("\nWe have to validate the user registration");
         MainTestUser.validateUser();
         
         System.out.println("\nNow we try to log the user");
         System.out.println("Give your User name please!\n");
         identifier=consoleForm();
         
         System.out.println("Give your Password please\n");
         password=consoleForm();
         
         if(MainTestUser.login(identifier,password,false).equals(null)) {
             System.out.println("the log has failed");
         }
         else {
             System.out.println("Sucess for the log");
             userdao.getUserByUserName(identifier).setId(145L);
             SecurityContext.setUserId(userdao.getUserByUserName(identifier).getId());
             
             
             System.out.println("Id"+SecurityContext.getUserId());
            
         }
        System.out.println("recupéraion du user sur base de l'id :"+userdao.get(SecurityContext.getUserId()));
        SecurityContext.setUser(userdao.get(SecurityContext.getUserId()));
    
        System.out.println("Value of threadlocal user :"+SecurityContext.getUser());
        
        Set<Privilege> listPrivilege = new HashSet<Privilege>();
        listPrivilege.add(Privilege.CREATE_QUESTIONS);
        listPrivilege.add(Privilege.EDIT_QUESTIONS);
        listPrivilege.add(Privilege.MANAGE_WIKIS);
        listPrivilege.add(Privilege.EDIT_WIKIS);
       
        userdao.get(SecurityContext.getUserId()).setPrivileges(listPrivilege);
           
        SecurityContext.setThreadPrivilege(userdao.get(SecurityContext.getUserId()).getPrivileges());
        
        System.out.println("List of privileges for the user :"+SecurityContext.getThreadPrivilege().get());
    }
    
    
    public static String consoleForm(){
         return   scanKeyBoard.next();
    }
            
    private static Scanner scanKeyBoard = new Scanner(System.in);
    
}   
