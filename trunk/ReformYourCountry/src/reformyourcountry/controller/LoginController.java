package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.InvalidPasswordException;
import reformyourcountry.exception.UserLockedException;
import reformyourcountry.exception.UserNotFoundException;
import reformyourcountry.exception.UserNotValidatedException;
import reformyourcountry.misc.DateUtil;
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
    public ModelAndView loginSubmit(@RequestParam("identifier") String identifier,
                                    @RequestParam("password") String password,
                                    @RequestParam(value="keepLoggedIn",required=false) boolean keepLoggedIn) {
        
        String errorMsg = null;
        try {
            loginService.login(identifier, password, keepLoggedIn);
            
            
        } catch (UserNotFoundException e) {
             errorMsg="L'utilisateur '"+identifier+"' n'existe pas";
             
        } catch (InvalidPasswordException e) {
            errorMsg="Ce mot de passe n'est pas valide pour l'utilisateur '"+identifier+"'";
            
        } catch (UserNotValidatedException e) {
            errorMsg="L'utilisateur '"+identifier+"' n'a pas été valide. Vérifiez vos mails reçus et cliquez sur le lien du mail qui vous a été envoyé à l'enregistrement.";
            
        } catch (UserLockedException e) {
            errorMsg="L'utilisateur  '"+identifier+"' est verrouillé. Contacter un administrateur pour le déverrouiller.";
            
        } catch (WaitDelayNotReachedException e) {
            errorMsg="Suite à de multiples tentatives de login échouées, votre utilisateur s'est vu imposé un délai d'attente avant de pouvoir se relogguer, ceci pour des raisons de sécurité." +
            		" Actuellement, il reste "+ DateUtil.formatIntervalFromToNow(e.getNextPossibleTry()) +" à attendre.";
        }
        
        if (errorMsg != null) {
            ModelAndView mv = new ModelAndView("login");
            mv.addObject("error", errorMsg);
            return mv;
        } else {    
            
               
            return  new ModelAndView("welcome");
        }
    }
}
