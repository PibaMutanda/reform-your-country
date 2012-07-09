package reformyourcountry.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reformyourcountry.exceptions.InvalidPasswordException;
import reformyourcountry.exceptions.UserAlreadyExistsException;
import reformyourcountry.exceptions.UserLockedException;
import reformyourcountry.exceptions.UserNotFoundException;
import reformyourcountry.exceptions.UserNotValidatedException;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.model.User.Gender;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.UserService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;

@Service
public class BatchSecurity {
    @Autowired
    private UserService userService;
    @Autowired
    private LoginService loginService;    
    @Autowired
    private SecurityContext securityContext;
    
    
    public void run() throws UserNotFoundException, InvalidPasswordException, UserNotValidatedException, UserLockedException, WaitDelayNotReachedException, UserAlreadyExistsException {
        
        
        String userName = "piba";
        String password = "secret";
        User user = userService.registerUser(false, "Piba", "M.", Gender.MALE, userName, "secret", "piba@mail.com");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user = loginService.login(userName, password, false);

        // Set privileges
        
        user.getPrivileges().add(Privilege.MANAGE_NEWS);
        user.getPrivileges().add(Privilege.MANAGE_NEWSLETTERS);
        user.getPrivileges().add(Privilege.SEND_NEWSLETTERS);

        // Test privileges
        if (!securityContext.isUserHasPrivilege(Privilege.MANAGE_NEWS)) {
            System.out.println("That user is supposed to have the MANAGE_NEWS privilege");
        }
        if (!securityContext.isUserHasPrivilege(Privilege.VIEW_PRIVATE_DATA_OF_USERS)) {
            System.out.println("That user is not supposed to have the VIEW_PRIVATE_DATA_OF_USERS privilege");
        } 
       
        //display the user, found with his id
        System.out.println("Username :"+securityContext.getUser().getUserName()+"\nPassword :"+securityContext.getUser().getPassword());
        
        
    }

}
