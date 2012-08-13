package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import reformyourcountry.exception.InvalidPasswordException;
import reformyourcountry.exception.UserLockedException;
import reformyourcountry.exception.UserNotFoundException;
import reformyourcountry.exception.UserNotValidatedException;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;

@Controller
public class LoginController {
    
    @Autowired LoginService loginService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    
    @RequestMapping("/loginsubmit")
    public String loginSubmit(@RequestParam("identifier") String identifier,
                                   @RequestParam("password") String password,
                                   @RequestParam(value="keepLoggedIn",required=false) boolean keepLoggedIn) {
        //FIXME redirect to the right page
        
        try {
            loginService.login(identifier, password, keepLoggedIn);
        } catch (UserNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidPasswordException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UserNotValidatedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UserLockedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (WaitDelayNotReachedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "home";
    }
}
