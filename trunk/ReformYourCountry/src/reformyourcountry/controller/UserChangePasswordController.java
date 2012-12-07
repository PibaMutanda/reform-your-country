package reformyourcountry.controller;

import java.util.HashMap;
import java.util.Map;

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
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.util.NotificationUtil;
import reformyourcountry.util.SecurityUtils;

@Controller
@RequestMapping ("/user")
public class UserChangePasswordController {

	@Autowired UserRepository userRepository;

	@RequestMapping("/changepassword")
	public ModelAndView userChangePassword(@ModelAttribute User user) {

		ModelAndView mv= new ModelAndView("userchangepassword");
		mv.addObject("user",user);
		mv.addObject("verifyOldPassword", needToVerifyOldPassword(user));
		return mv;
	}

	@RequestMapping("/changepasswordsubmit")
	public ModelAndView userChangePasswordSubmit(@Valid @ModelAttribute User user, BindingResult result,
			@RequestParam(value= "oldPassword",  required=false) String oldPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword) {

		SecurityContext.assertCurrentUserMayEditThisUser(user);

		if (result.hasErrors()) {
			return new ModelAndView("redirect:user","id",user.getId());
		}
		else {
			Map<String, String> errorMsgMap = new HashMap<String, String>();



			if(needToVerifyOldPassword(user)){
				if (!(SecurityUtils.md5Encode(oldPassword)).equals(user.getPassword())) {
					errorMsgMap.put("errorNoOld", "Vous n'avez pas bien encodé votre ancien mot de passe");
				}
			}

			if (newPassword.equals("") || confirmPassword.equals("")) {
				errorMsgMap.put("errorEmpty", "Veuillez encoder un nouveau mot de passe et/ou la confirmation de ce mot de passe");
			} else if (!confirmPassword.equals(newPassword)) {
				errorMsgMap.put("errorDiff", "La confirmation du mot de passe et le mot de passe ne correspondent pas");
			}


			if (!errorMsgMap.isEmpty()) {
				ModelAndView errorDspMv = new ModelAndView("userchangepassword");
				for (String msgKey : errorMsgMap.keySet()) {
					errorDspMv.addObject(msgKey, errorMsgMap.get(msgKey));	
				}
				errorDspMv.addObject("user", user);
				return errorDspMv;
			} else {
				user.setPassword(SecurityUtils.md5Encode(confirmPassword));
				user.setPasswordKnownByTheUser(true);
				userRepository.merge(user);
				NotificationUtil.addNotificationMessage("Votre nouveau mot de passe est bien enregistré");
				return new ModelAndView("redirect:/user/"+user.getUserName());
			}				

		}

	}

	
	private boolean needToVerifyOldPassword(User user) {
		// If admin and edits somebody else => we don't ask for the old password.
		if(SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS) && !user.equals(SecurityContext.getUser())){
			return false;
		} else {
			return true;
		}
	}
	

	@ModelAttribute
	public User findUser(@RequestParam("id") Long id){
		return  userRepository.find(id);
	}


}
