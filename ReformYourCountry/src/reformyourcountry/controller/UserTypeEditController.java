package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.model.User.SpecialType;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

@Controller
public class UserTypeEditController {

	@Autowired UserRepository userRepository;
	

	@RequestMapping("/user/usertypeedit")
	public ModelAndView userTypeEdit(@RequestParam(value="id", required=true) long userId){
		SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_GROUP);
	    User user=userRepository.find(userId);
		  
		ModelAndView mv= new ModelAndView("usertypeedit");
				
		mv.addObject("user",user);
		mv.addObject("ListSpecialType",User.SpecialType.values());	
		return mv;
	}
	
	@RequestMapping("/user/usertypeeditsubmit")
	public ModelAndView userTypeEditSubmit(@RequestParam(value="id", required=true) long userId,@RequestParam String specialType){
				
		SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_GROUP);
		User user=userRepository.find(userId);
		
		
		user.setSpecialType(SpecialType.getSpecialType(specialType));
		
		
		userRepository.merge(user);	
				
		return new ModelAndView("redirect:"+user.getUserName());
	}
}
