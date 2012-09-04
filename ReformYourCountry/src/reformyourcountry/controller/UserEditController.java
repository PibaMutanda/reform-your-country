package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.model.User.Gender;
import reformyourcountry.repository.UserRepository;


@Controller
public class UserEditController extends BaseController<User> {
    
    @Autowired UserRepository userRepository;

    @RequestMapping("/useredit")
    public ModelAndView userEdit(@RequestParam(value="id", required=true) long userId) {
     
        User user = getRequiredEntity(userId); 
        
        ModelAndView mv=new ModelAndView("useredit","user", user);
        return mv;
    }
  
    @RequestMapping("/usereditsubmit")
    public ModelAndView userEditSubmit(@RequestParam("lastName") String newLastName,
                                        @RequestParam("firstName") String newFirstName,
                                        @RequestParam("userName") String newUserName,
                                        @RequestParam("gender") Gender newGender,
                                        @RequestParam("mail") String newMail,
                                        @RequestParam("nlSubscriber") boolean newNlSubscriber,
                                        @RequestParam("id") long id,
                                        @ModelAttribute User doNotUseThisUserInstance,  // To enable the use of errors param.
                                        Errors errors) {
        

        User user = getRequiredEntity(id); 
        
        boolean errorDetected = false;
        
        // userName
        newUserName = org.springframework.util.StringUtils.trimAllWhitespace(newUserName).toLowerCase();  // remove blanks
        if (! org.apache.commons.lang3.StringUtils.equalsIgnoreCase(user.getUserName(), newUserName)) {  // Want to change username
            // check duplicate
            User otherUser = userRepository.getUserByUserName(newUserName);
            if (otherUser != null) { // Another user already uses that userName
                errors.rejectValue("userName", null, "Ce pseudonyme est déjà utilisé par un autre utilisateur.");
                errorDetected = true;
            }
        }
        
        // e-mail
        newMail = org.springframework.util.StringUtils.trimAllWhitespace(newMail).toLowerCase();   // remove blanks
        if (! org.apache.commons.lang3.StringUtils.equalsIgnoreCase(user.getMail(), newMail)) {  // user wants to change e-mail
            // check duplicate
            User otherUser = userRepository.getUserByEmail(newMail);
            if (otherUser != null) {
                errors.rejectValue("mail", null, "Ce mail est déjà utilisé par un autre utilisateur.");
                errorDetected = true;
            }
        }
        
        if (errorDetected) {
            return new ModelAndView("useredit", "user", doNotUseThisUserInstance);  // Get out of here before we change the user entity (Hibernate could save because of dirty checking).
        }
        
        // We start modifiying user (that may then be automatically saved by hibernate due to dirty checking.
        user.setUserName(newUserName);
        user.setMail(newMail);
        user.setLastName(newLastName);
        user.setFirstName(newFirstName);
        user.setGender(newGender);
        user.setNlSubscriber(newNlSubscriber);
        
        user = userRepository.merge(user);
    
        return new ModelAndView("redirect:user", "id", user.getId());
    }
    
    

   
    
}
