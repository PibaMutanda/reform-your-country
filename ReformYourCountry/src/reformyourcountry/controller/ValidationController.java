package reformyourcountry.controller;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.service.LoginService;
import reformyourcountry.util.Logger;

@Controller
public class ValidationController {
    //TODO review
    @Autowired private UserRepository userRepository;
    @Autowired private LoginService loginService;
    @Logger Log log;

    
    public enum Result {  ALREADY_VALIDATED, INVALID_CODE, VALID_AND_LOGGED, VALID_BUT_NOT_LOGGED; }
    
    @RequestMapping("/validationsubmit")
    public ModelAndView validationSubmit(@RequestParam(value = "code" ,required = false)String validationCode) {
        ModelAndView mv = new ModelAndView("validation");
        Result result;
        if(validationCode == null){
            result = Result.INVALID_CODE;
        } else {
            User user = userRepository.getUserByValidationCode(validationCode);
            if(user != null){
                log.debug("validationSubmit found : "+user.getUserName());
                if(user.getAccountStatus().equals(User.AccountStatus.NOTVALIDATED)) { //must be activited only if the user is NOTVALIDATED otherwhise the user can be unlock if we verify only if isn't validated
                    user.setAccountStatus(User.AccountStatus.ACTIVE);
                    log.debug(user.getMail()+" just validated");
                    try {
                        loginService.loginEncrypted(user.getMail(), user.getPassword(), true);
                        result = Result.VALID_AND_LOGGED;
                    } catch (Exception e) {
                        result = Result.VALID_BUT_NOT_LOGGED;  
                    }

                } else { 
                    result = Result.ALREADY_VALIDATED;
                }

            } else { // Not validated
                result = Result.INVALID_CODE;
            }
        } 
        
        mv.addObject("result", result);
        return mv;
    }
}
