package reformyourcountry.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.Repository.UserRepository;
import reformyourcountry.exceptions.InvalidPasswordException;
import reformyourcountry.exceptions.UserAlreadyExistsException;
import reformyourcountry.exceptions.UserLockedException;
import reformyourcountry.exceptions.UserNotFoundException;
import reformyourcountry.exceptions.UserNotValidatedException;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.model.User.Gender;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.service.UserService;
import reformyourcountry.web.ContextUtil;

@Service
@Transactional
public class BatchSecurity {
  
    @Autowired 
    private UserRepository ur;
    
    public void run() throws UserNotFoundException, InvalidPasswordException, UserNotValidatedException, UserLockedException, WaitDelayNotReachedException, UserAlreadyExistsException {
        LoginService loginService = (LoginService) ContextUtil.getSpringBean("loginService"); 
        UserService userService = (UserService)ContextUtil.getSpringBean("userService");
      
        
     
        String userName = "tintin";
        String password = "secret";
        User user;
        
        if(loginService.identifyUser(userName) == null){
        user = userService.registerUser(true, userName, "M.", Gender.MALE, userName, "secret", "piba@mail.com");
        user.setAccountStatus(AccountStatus.ACTIVE);
        user = loginService.login(userName, password, false);
        }
        else
          user = loginService.login(userName, password, false);
          
        user.setMail("toto2@mail.com");
        user = ur.merge(user);
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
