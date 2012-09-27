package reformyourcountry.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.validation.Valid;

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
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.UserService;
import reformyourcountry.util.DateUtil;


@Controller
public class UserEditController extends BaseController<User> {
    
    @Autowired UserRepository userRepository;
    @Autowired UserService userService; 
    
    @RequestMapping("/useredit")
    public ModelAndView userEdit(@RequestParam(value="id", required=true) long userId) {
        
        User user = getRequiredEntity(userId); 
    	assertCurrentUserMayEditThisUser(user);
               
    	ModelAndView mv=new ModelAndView("useredit");
    	mv.addObject("id", userId); 
    	mv.addObject("user", user);
    	
    	// Sets an initial date in the form
    	Calendar birthCalendar = Calendar.getInstance();
    	if (user.getBirthDate()!=null) {
			birthCalendar.setTime(user.getBirthDate());
			mv.addObject("birthDay", birthCalendar.get(Calendar.DAY_OF_MONTH));
	    	mv.addObject("birthMonth", birthCalendar.get(Calendar.MONTH));
	    	mv.addObject("birthYear", birthCalendar.get(Calendar.YEAR));
		}
		

    	return mv;
    }
  
    @RequestMapping("/usereditsubmit")
    public ModelAndView userEditSubmit(@RequestParam("lastName") String newLastName,
                                        @RequestParam("firstName") String newFirstName,
                                        @RequestParam("userName") String newUserName,
                                        @RequestParam("gender") Gender newGender,
                                        @RequestParam("mail") String newMail,
                                        @RequestParam("birthDay") String day,
                                        @RequestParam("birthMonth") String month,
                                        @RequestParam("birthYear") String year,
                                        @RequestParam("nlSubscriber") boolean newNlSubscriber,
                                        @RequestParam("id") long id,
                                        @Valid @ModelAttribute User doNotUseThisUserInstance,  // To enable the use of errors param.
                                        Errors errors) {
    	
        User user = getRequiredEntity(id); 
    	assertCurrentUserMayEditThisUser(user);
        	  	
       
        //birthDate
        Date dateNaiss=DateUtil.parseyyyyMMdd(year+"-"+month+"-"+day);
        if (dateNaiss.after(new Date())) {

            ModelAndView mv=new ModelAndView("useredit");
            String errorBirthDate="Vous avez sélectionné une date dans le futur. Veuillez choisir une date de naissance passée.";

            mv.addObject("id", id); 
            mv.addObject("user", user);
            mv.addObject("errorBirthDate",errorBirthDate);
            return mv;
        }
   	
        // userName
        boolean hasUserAlreadyExist=false;
        newUserName = org.springframework.util.StringUtils.trimAllWhitespace(newUserName).toLowerCase();  // remove blanks
        if (! org.apache.commons.lang3.StringUtils.equalsIgnoreCase(user.getUserName(), newUserName)) {  // Want to change username
            // check duplicate
            User otherUser = userRepository.getUserByUserName(newUserName);
            if (otherUser != null) { // Another user already uses that userName
                errors.rejectValue("userName", null, "Ce pseudonyme est déjà utilisé par un autre utilisateur.");
                hasUserAlreadyExist=true;
            }
        }
        
        // e-mail
        newMail = org.springframework.util.StringUtils.trimAllWhitespace(newMail).toLowerCase();   // remove blanks
        if (! org.apache.commons.lang3.StringUtils.equalsIgnoreCase(user.getMail(), newMail)) {  // user wants to change e-mail
            // check duplicate
            User otherUser = userRepository.getUserByEmail(newMail);
            if (otherUser != null) {
                errors.rejectValue("mail", null, "Ce mail est déjà utilisé par un autre utilisateur.");
            }
        }
        
        if (errors.hasErrors()) {
            ModelAndView mv = new ModelAndView("useredit");
            if (doNotUseThisUserInstance.getUserName()==""|| hasUserAlreadyExist==true ) {
                doNotUseThisUserInstance.setUserName(user.getUserName());  // We need to restore the username, because the "Cancel" link in the JSP needs it.
            }
            mv.addObject("user", doNotUseThisUserInstance);  // Get out of here before we change the user entity (Hibernate could save because of dirty checking).
            mv.addObject("id", id); // need to pass 'id' apart because 'doNotUseThisUserInstance.id' is set to null
            return mv;
        }
        
        // We start modifiying user (that may then be automatically saved by hibernate due to dirty checking.
        if((!newFirstName.equals(user.getFirstName()))||(!newLastName.equals(user.getLastName()))||(!newUserName.equals(user.getUserName()))){
           userService.changeUserName(user, newUserName, newFirstName, newLastName); 
        }
        user.setBirthDate(dateNaiss);
        user.setMail(newMail);
        user.setGender(newGender);
        user.setNlSubscriber(newNlSubscriber);
        
        user = userRepository.merge(user);
    
        return new ModelAndView("redirect:user/"+user.getUserName());
    }
    
    

   

    private void assertCurrentUserMayEditThisUser(User user) {
    	if (user.equals(SecurityContext.getUser())) {
    		return; // Ok, a user may edit himself.
    	}
        SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_USERS);
    }
}
