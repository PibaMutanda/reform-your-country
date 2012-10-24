package reformyourcountry.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.SecurityUtils;

@Controller
@RequestMapping ("/user")
public class UserChangePasswordController {

	@Autowired UserRepository userRepository;

	@RequestMapping("/changepassword")
	public ModelAndView userChangePassword(@ModelAttribute User user) {
		
		ModelAndView mv= new ModelAndView("userchangepassword");
		mv.addObject("user",user);
		return mv;
	}

	@RequestMapping("/changepasswordsubmit")
	public ModelAndView userChangePasswordSubmit(@Valid @ModelAttribute User user, BindingResult result,
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword) {

		SecurityContext.assertCurrentUserMayEditThisUser(user);
		
		String errorNoOld=null;
		String errorEmpty=null;
		String errorDiff=null;

		if (result.hasErrors()) {
			return new ModelAndView("redirect:user","id",user.getId());
		}
		else {
			if(((!(SecurityUtils.md5Encode(oldPassword)).equals(user.getPassword()))||newPassword.equals("") || confirmPassword.equals("")||!confirmPassword.equals(newPassword))){
				ModelAndView mv = new ModelAndView("userchangepassword");
				if (!(SecurityUtils.md5Encode(oldPassword)).equals(user.getPassword())) {
					errorNoOld= "Vous n'avez pas bien encodé votre ancien mot de passe";
				}else if (newPassword.equals("") || confirmPassword.equals("")) {
					errorEmpty= "Veuillez encoder un nouveau mot de passe et/ou la confirmation de ce mot de passe";
				}else if (!confirmPassword.equals(newPassword)) {
					errorDiff= "La confirmation du password et le nouveau password ne correspondent pas";
				}
				mv.addObject("user",user);
				mv.addObject("errorNoOld",errorNoOld);
				mv.addObject("errorEmpty",errorEmpty);
				mv.addObject("errorDiff",errorDiff);
				return mv;
			}
			user.setPassword(SecurityUtils.md5Encode(confirmPassword));
			user.setPasswordKnownByTheUser(true);
			userRepository.merge(user);
			return new ModelAndView("redirect:/user/"+user.getUserName());	

		}

	}


	@ModelAttribute
	public User findUser(@RequestParam("id") Long id){
		return  userRepository.find(id);
	}
	
   
}
