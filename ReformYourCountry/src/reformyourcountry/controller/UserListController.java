package reformyourcountry.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;

@Controller
public class UserListController {

	@Autowired	UserRepository userRepository;
	
	@RequestMapping("/userlist")
	public ModelAndView userListDisplay(@RequestParam(value="name", required=false) String name){
		String errorMsg = null;
		ModelAndView mv = new ModelAndView("userlist");
		
		if (name==null) {  // It's not a search, it's a first time display.
			return mv;
		} else {  // It's a search
			name = name.trim();
			List<User> usersList = userRepository.searchUsers(name);
			if(usersList.isEmpty()) {
				errorMsg="Il n'existe aucun utilisateur ayant "+name+" comme pseudo, prénom ou nom.";
				mv.addObject("errorMsg",errorMsg);
			}
			mv.addObject("userList",usersList);
			
			return mv;
		}
	}
	
		
}
