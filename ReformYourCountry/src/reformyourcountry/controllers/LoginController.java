package reformyourcountry.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;

import reformyourcountry.exceptions.InvalidPasswordException;
import reformyourcountry.exceptions.UserLockedException;
import reformyourcountry.exceptions.UserNotFoundException;
import reformyourcountry.exceptions.UserNotValidatedException;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;

@Controller
public class LoginController {
    
    @Autowired LoginService loginService;
    
    @RequestMapping("/login")
    public String showlogin()
    {
        return "login";        
    }
    
//    @RequestMapping("/loginsubmit")
//    public ModelAndView loginSubmit(@RequestParam("identifier")String identifier,
//                                    @RequestParam("password")String clearPassword,
//                                    @RequestParam("keepLoggedIn")boolean keepLoggedIn)
//    {
//        ModelAndView mv = null;
//        try {
//            loginService.login(identifier, clearPassword, keepLoggedIn);
//        } catch (UserNotFoundException e) {
//          //TODO return error to page
//        } catch (InvalidPasswordException e) {
//          //TODO return error to page
//        } catch (UserNotValidatedException e) {
//          //TODO return error to page
//        } catch (UserLockedException e) {
//          //TODO return error to page
//        } catch (WaitDelayNotReachedException e) {
//          //TODO return error to page
//        }
//        
//        return null;
//    }

}
