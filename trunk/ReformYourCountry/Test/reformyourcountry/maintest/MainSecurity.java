package reformyourcountry.maintest;

import org.springframework.beans.factory.annotation.Autowired;

import reformyourcountry.exception.InvalidPasswordException;
import reformyourcountry.exception.UserAlreadyExistsException;
import reformyourcountry.exception.UserLockedException;
import reformyourcountry.exception.UserNotFoundException;
import reformyourcountry.exception.UserNotValidatedException;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.model.User.Gender;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.service.UserService;



public class MainSecurity {


    @Autowired
    private static UserService userService;
    @Autowired
    private static LoginService loginService;    
  
    public static void main(String[] args) throws UserNotFoundException, InvalidPasswordException, UserNotValidatedException, UserLockedException, WaitDelayNotReachedException, UserAlreadyExistsException {
       
        String userName = "piba";
        String password = "secret";
        User user = userService.registerUser(false, userName,password, userName+"@mail.com");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user = loginService.login(userName, password, false);

        // Set privileges
        
        user.getPrivileges().add(Privilege.MANAGE_NEWS);
        user.getPrivileges().add(Privilege.MANAGE_NEWSLETTERS);
        user.getPrivileges().add(Privilege.SEND_NEWSLETTERS);

        // Test privileges
        if (!SecurityContext.isUserHasPrivilege(Privilege.MANAGE_NEWS)) {
            System.out.println("That user is supposed to have the MANAGE_NEWS privilege");
        }
        if (!SecurityContext.isUserHasPrivilege(Privilege.VIEW_PRIVATE_DATA_OF_USERS)) {
            System.out.println("That user is not supposed to have the VIEW_PRIVATE_DATA_OF_USERS privilege");
        } 
       
        //display the user, found with his id
        System.out.println("Username :"+SecurityContext.getUser().getUserName()+"\nPassword :"+SecurityContext.getUser().getPassword());
     

	}
}   
