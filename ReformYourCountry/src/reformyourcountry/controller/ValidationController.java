package reformyourcountry.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.InvalidPasswordException;
import reformyourcountry.exception.UserLockedException;
import reformyourcountry.exception.UserNotFoundException;
import reformyourcountry.exception.UserNotValidatedException;
import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
@Controller
public class ValidationController {
    @Autowired private UserRepository userRepository;
    @Autowired private LoginService loginService;
    private Logger logger = Logger.getLogger(this.getClass());
    
    @RequestMapping("/validationsubmit")
    public ModelAndView validationSubmit(@RequestParam("code")String validationCode){
        ModelAndView mv = new ModelAndView("validation");
        String message = null ;
        logger.debug("someone is submit vaidation code");
        
        User user = userRepository.getUserByValidationCode(validationCode);
        if(user != null){
            logger.debug("validationSubmit found : "+user.getUserName());
            user.setAccountStatus(User.AccountStatus.ACTIVE);
            logger.info(user.getMail()+" just validated");
            
            try {
                loginService.loginEncrypted(user.getMail(), user.getPassword(), true);
            } catch (UserNotFoundException e) {
                // TODO bug shouldn't be throw
                logger.debug("validationSubmit failed to autologin for "+user.getMail(), e);
            } catch (InvalidPasswordException e) {
                message+="<br/>erreur d'autoconnexion ,veuillez vous connecter manuellement";
                logger.debug("validationSubmit failed to autologin for "+user.getMail(), e);
            } catch (UserNotValidatedException e) {
                // TODO bug shouldn't be throw, user just validated 10 lines before
                logger.debug("validationSubmit failed to autologin for "+user.getMail(), e);
            } catch (UserLockedException e) {
                // TODO  possible?
                logger.debug("validationSubmit failed to autologin for "+user.getMail(), e);
            } catch (WaitDelayNotReachedException e) {
                // TODO possible?
                logger.debug("validationSubmit failed to autologin for "+user.getMail(), e);
            }
            logger.info(user.getMail()+" just validated and autologged");
            
            message="adresse e-mail validée";
        }
        //FIXME why dead code warning?
        else{
            message="mauvais code de validation";
        }
        
        mv.addObject("message",message);
        return mv;
    }

}
