package reformyourcountry.controller;

import java.util.Calendar;
import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.Article;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.model.User.Gender;
import reformyourcountry.model.User.Role;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.UserService;
import reformyourcountry.util.DateUtil;
import reformyourcountry.util.HTMLUtil;
import reformyourcountry.util.NotificationUtil;


@Controller
@RequestMapping("/user")
public class UserEditController extends BaseController<User> {
    
    @Autowired UserRepository userRepository;
    @Autowired UserService userService; 
    
    @RequestMapping("/edit")
    public ModelAndView userEdit(@RequestParam(value="id", required=true) long userId) {
        
        User user = getRequiredEntity(userId); 
    	SecurityContext.assertCurrentUserMayEditThisUser(user);
               
    	ModelAndView mv=prepareModelAndView(userId, user);
    	
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
    
    private ModelAndView prepareModelAndView(Long userId,User user) { 
    	ModelAndView mv=new ModelAndView("useredit");
    	mv.addObject("id", userId); 
    	mv.addObject("user", user);
    	mv.addObject("canChangeUserName", (SecurityContext.canCurrentUserChangeUser(getRequiredEntity(userId)) /// If we have a validation error, the given user is a fake, used for error handling;
    												&& getRequiredEntity(userId).getCertificationDate() == null)// in that case, we have to compare the current user with the real one, hence the getRequiredEntity
    												|| SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS));
    	return mv;
    }
    
    @RequestMapping("/delete")
    public ModelAndView userDelete(@RequestParam(value="id") Long idUser){
        User user  = userRepository.find(idUser);
        SecurityContext.assertCurrentUserMayEditThisUser(user);
        return getConfirmBeforeDeletePage(user.getUserName(), "/user/deleteconfirmed", "/user/"+user.getUserName(), idUser);

    }

    @RequestMapping("/deleteconfirmed")
    public ModelAndView userDeleteConfirmed(@RequestParam(value="id") Long idUser){
        User user  = userRepository.find(idUser);
        SecurityContext.assertCurrentUserMayEditThisUser(user);
        userService.setAnonymous(user);
        NotificationUtil.addNotificationMessage("L'utilisateur est bien supprimé");
        return new ModelAndView("home");
    }
    
    
    
    @RequestMapping("/editsubmit")
    public ModelAndView userEditSubmit(@RequestParam(value="lastName",required=false) String newLastName,
                                       @RequestParam(value="firstName",required=false) String newFirstName,
                                       @RequestParam(value="userName") String newUserName,
                                       @RequestParam(value="gender",required=false) Gender newGender,
                                       @RequestParam(value="mail") String newMail,
                                       @RequestParam(value="birthDay") String day,
                                       @RequestParam(value="birthMonth") String month,
                                       @RequestParam(value="birthYear") String year,
                                       @RequestParam(value="nlSubscriber", required=false) Boolean newNlSubscriber,
                                       @RequestParam(value="title") String title,
                                       @RequestParam(value="certified", required=false) Boolean certified,
                                       @RequestParam("id") long userId,
                                       @Valid @ModelAttribute User doNotUseThisUserInstance,  // To enable the use of errors param.
                                       Errors errors) {
    	
        User user = getRequiredEntity(userId); 
    	SecurityContext.assertCurrentUserMayEditThisUser(user);
        
    	//field html dangerousity check
    	checkFieldContainDangerousHtml(newLastName,"lastName", errors);
    	checkFieldContainDangerousHtml(newFirstName,"firstName", errors);
    	checkFieldContainDangerousHtml(newUserName,"userName", errors);  
    	checkFieldContainDangerousHtml(newMail,"mail", errors);
    	checkFieldContainDangerousHtml(title,"title", errors);

       
        //birthDate
    	Date dateNaiss = null;
        if ((!day.equals("null"))&&(!month.equals("null"))&&(!year.equals("null"))) {
			dateNaiss = DateUtil.parseyyyyMMdd(year + "-" + month + "-" + day);
			if (dateNaiss.after(new Date())) {
				////constrcut modelandview because brithday is split in 3 input so we connat use the erros variable.
				ModelAndView mv = prepareModelAndView(userId, user);
				mv.addObject("errorBirthDate", "Vous avez sélectionné une date dans le futur. Veuillez choisir une date de naissance passée.");
				return mv;
			}
        }
		else {
			  
			    String errorBirthDate=null;
			    if (day.equals("null"))      errorBirthDate = "Vous devez sélectionner le jour SVP";
			    if (month.equals("null"))    errorBirthDate = "Vous devez sélectionner le mois SVP";    
			    if (year.equals("null"))     errorBirthDate = "Vous devez sélectionner  l'année SVP";      
			    ModelAndView mv=prepareModelAndView(userId, user);
			    mv.addObject("errorBirthDate", errorBirthDate);
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
            ModelAndView mv = prepareModelAndView(userId, doNotUseThisUserInstance);
            if (doNotUseThisUserInstance.getUserName()==""|| hasUserAlreadyExist==true ) {
                doNotUseThisUserInstance.setUserName(user.getUserName());  // We need to restore the username, because the "Cancel" link in the JSP needs it.
            }
            return mv;
        }
        
        // We start modifiying user (that may then be automatically saved by hibernate due to dirty checking.
        if ((certified == null || certified ==  false || SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS)) &&
        	( !ObjectUtils.equals(newFirstName, user.getFirstName()) || !ObjectUtils.equals(newLastName, user.getLastName()) || !ObjectUtils.equals(newUserName, user.getUserName()))){
           userService.changeUserName(user, newUserName, newFirstName, newLastName); 
        }
        user.setBirthDate(dateNaiss);
        user.setMail(newMail);
        user.setGender(newGender);
        user.setNlSubscriber(newNlSubscriber != null ? newNlSubscriber : false);
        user.setTitle(title);
        
        if (SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS)) {  // User sees the check box
            if ((certified == null || certified ==  false)) {  // Check box not in the request (=> unchecked)
            	user.setCertificationDate(null);
            } else if (user.getCertificationDate() == null) {  // User is not yet certified
            	user.setCertificationDate(new Date());
            }
        	
        }
        
        user = userRepository.merge(user);
        
        userService.grantIfUserIsComplete(user);
        
        return new ModelAndView("redirect:/user/"+user.getUserName());
    }

	private void checkFieldContainDangerousHtml(String toCheck,String fieldName, Errors errors) {
		if( !HTMLUtil.isHtmlSecure(toCheck)) {
			errors.rejectValue(fieldName, null, "vous avez introduit du HTML/Javascript dans vos informations " + toCheck);
		}
	}
}
