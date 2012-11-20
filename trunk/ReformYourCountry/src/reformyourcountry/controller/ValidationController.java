package reformyourcountry.controller;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountConnectedType;

import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.service.LoginService;

import reformyourcountry.util.Logger;
import reformyourcountry.util.NotificationUtil;

@Controller
public class ValidationController extends BaseController<User>{
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
                
                if (user.getAccountStatus().equals(User.AccountStatus.ACTIVE)  ) {
                    result = Result.ALREADY_VALIDATED;
                } else {  // Not validated yet (or locked...)
                    if (user.getAccountStatus().equals(User.AccountStatus.NOTVALIDATED)) {
                        user.setAccountStatus(User.AccountStatus.ACTIVE);
                        em.merge(user);  
                        result = Result.VALID_BUT_NOT_LOGGED;
                        log.debug(user.getMail()+" just validated");
                    } else {
                        NotificationUtil.addNotificationMessage("Impossible de valider votre compte. Son état actuel est: "+user.getAccountStatus().getName());
                    }
                    
                    try {
                        loginService.loginEncrypted(user.getMail(), user.getPassword(), true,user.getId(),AccountConnectedType.LOCAL);
                        result = Result.VALID_AND_LOGGED;
                    } catch (Exception e) {
                        result = Result.VALID_BUT_NOT_LOGGED;  
                    }
                }
            } else { // Not validated
                result = Result.INVALID_CODE;
            }
        } 
        
        mv.addObject("result", result);
        mv.addObject("code",validationCode);
        return mv;
    }
    
    
    
    
   }
