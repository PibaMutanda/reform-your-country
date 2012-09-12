package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.InvalidPasswordException;
import reformyourcountry.exception.UserLockedException;
import reformyourcountry.exception.UserNotFoundException;
import reformyourcountry.exception.UserNotValidatedException;
import reformyourcountry.model.User;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.util.DateUtil;

@Controller
public class LoginController extends BaseController<User> {

    @Autowired LoginService loginService;
    @Autowired UserDisplayController userDisplayController;


    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 
     * @param password required=false because we don't use pswd in DEV
     * @param keepLoggedIn required=false because when user don't check the checkbox we get a 400 error
     * @return
     */
    @RequestMapping("/loginsubmit")
    public ModelAndView loginSubmit(@RequestParam("identifier") String userNameOrMail,
            @RequestParam(value="password",required=false) String password,
            @RequestParam(value="keepLoggedIn",required=false) boolean keepLoggedIn) {

        
        String errorMsg = null;
        User user = null;
        try {
            user = loginService.login(userNameOrMail, password, keepLoggedIn);


        } catch (UserNotFoundException e) {
            errorMsg="L'utilisateur '"+userNameOrMail+"' n'existe pas";

        } catch (InvalidPasswordException e) {
            errorMsg="Ce mot de passe n'est pas valide pour l'utilisateur '"+userNameOrMail+"'";

        } catch (UserNotValidatedException e) {
            errorMsg="L'utilisateur '"+userNameOrMail+"' n'a pas été valide. Vérifiez vos mails reçus et cliquez sur le lien du mail qui vous a été envoyé à l'enregistrement.";

        } catch (UserLockedException e) {
            errorMsg="L'utilisateur  '"+userNameOrMail+"' est verrouillé. Contacter un administrateur pour le déverrouiller.";

        } catch (WaitDelayNotReachedException e) {
            errorMsg="Suite à de multiples tentatives de login échouées, votre utilisateur s'est vu imposé un délai d'attente avant de pouvoir se relogguer, ceci pour des raisons de sécurité." +
                    " Actuellement, il reste "+ DateUtil.formatIntervalFromToNow(e.getNextPossibleTry()) +" à attendre.";
        }

        if (errorMsg != null) {
            ModelAndView mv = new ModelAndView("login");
            this.setMessage(mv, errorMsg);
            return mv;
        } else {
            return new ModelAndView("redirect:user", "username", user.getUserName());
        }
    }
    
    /**
     * 
     * @param password required=false because we don't use pswd in DEV
     * @param keepLoggedIn required=false because when user don't check the checkbox we get a 400 error
     * @return
     */
    @RequestMapping(value="ajax/loginsubmit",method=RequestMethod.POST)
    public ResponseEntity<String> loginSubmitAjax(@RequestParam("identifier") String userNameOrMail,
            @RequestParam(value="password",required=false) String password,
            @RequestParam(value="keepLoggedIn",required=false) boolean keepLoggedIn) {

        
        String errorMsg = null;
        User user = null;
        try {
            user = loginService.login(userNameOrMail, password, keepLoggedIn);


        } catch (UserNotFoundException e) {
            errorMsg="L'utilisateur '"+userNameOrMail+"' n'existe pas";

        } catch (InvalidPasswordException e) {
            errorMsg="Ce mot de passe n'est pas valide pour l'utilisateur '"+userNameOrMail+"'";

        } catch (UserNotValidatedException e) {
            errorMsg="L'utilisateur '"+userNameOrMail+"' n'a pas été valide. Vérifiez vos mails reçus et cliquez sur le lien du mail qui vous a été envoyé à l'enregistrement.";

        } catch (UserLockedException e) {
            errorMsg="L'utilisateur  '"+userNameOrMail+"' est verrouillé. Contacter un administrateur pour le déverrouiller.";

        } catch (WaitDelayNotReachedException e) {
            errorMsg="Suite à de multiples tentatives de login échouées, votre utilisateur s'est vu imposé un délai d'attente avant de pouvoir se relogguer, ceci pour des raisons de sécurité." +
                    " Actuellement, il reste "+ DateUtil.formatIntervalFromToNow(e.getNextPossibleTry()) +" à attendre.";
        }

        
        //ResponseEntity<String> response = new ResponseEntity<String>(errorMsg, null);
        
        if (errorMsg != null) {
         //   ModelAndView mv = new ModelAndView("login");
         //   this.setMessage(mv, errorMsg);
            return new ResponseEntity<String>(errorMsg, HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<String>("ok", HttpStatus.OK);
        }
    }
}
