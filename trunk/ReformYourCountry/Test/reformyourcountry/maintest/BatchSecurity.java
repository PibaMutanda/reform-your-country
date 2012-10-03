package reformyourcountry.maintest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reformyourcountry.exception.InvalidPasswordException;
import reformyourcountry.exception.SocialAccountAlreadyExistException;
import reformyourcountry.exception.UserAlreadyExistsException;
import reformyourcountry.exception.UserLockedException;
import reformyourcountry.exception.UserNotFoundException;
import reformyourcountry.exception.UserNotValidatedException;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.service.UserService;
import reformyourcountry.web.ContextUtil;

@Service
@Transactional
public class BatchSecurity {
  
    @Autowired 
    private UserRepository ur;

    public void run(User user) throws UserNotFoundException, InvalidPasswordException, UserNotValidatedException, UserLockedException, WaitDelayNotReachedException, UserAlreadyExistsException ,SocialAccountAlreadyExistException{
        LoginService loginService = (LoginService) ContextUtil.getSpringBean("loginService"); 
        UserService userService = (UserService)ContextUtil.getSpringBean("userService");

        if(loginService.identifyUserByEMailOrName(user.getUserName()) == null){
            user = userService.registerUser(true,user.getUserName(),user.getPassword(),user.getMail());
            user.setAccountStatus(AccountStatus.ACTIVE);
            user = loginService.login(user.getUserName(), user.getPassword(), false,user.getId());
        } else {
            user = loginService.login(user.getUserName(), user.getPassword(), false,user.getId());
        }          

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
