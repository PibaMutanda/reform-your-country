package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.service.UserService;

@Controller
@RequestMapping("/user")
public class UserListController {

	@Autowired UserRepository userRepository;
	@Autowired UserService userService;
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView userListDisplay(@RequestParam(value="name", required=false) String name){
		boolean search = false;
		String errorMsg = null;
		ModelAndView mv = new ModelAndView("userlist");
		List<User> topUserList = userRepository.FindUserOrderByContribution(100);
		List<User> lastUsersRegistered = userRepository.FindLastUsersRegistred(100);
		mv.addObject("topUserList", topUserList);
		mv.addObject("lastUsersRegistered", lastUsersRegistered);
		List<User> userRoleList = userService.getUserLstWithRoleAndPrivilege();
		
		// Make a list of Information, which is easier to display than a list of users in the JSP.
		List<Information> infoList = new ArrayList<Information>(); 
		for(User u : userRoleList){
			Information current = new Information();
			current.setUser(u);
			for(Privilege p : u.getPrivileges()){
				current.privileges +=  "- " + p.getName() + "</br>";
			}
			infoList.add(current);
		}
		
		mv.addObject("infoList", infoList);
		
		if (name==null) {  // It's not a search, it's a first time display.
			return mv;
		} else {  // It's a search
			search = true;
			name = name.trim();
			List<User> usersList = userRepository.searchUsers(name);
			if(usersList.isEmpty()) {
				errorMsg="Il n'existe aucun utilisateur ayant "+name+" comme pseudo, prénom ou nom.";
				mv.addObject("errorMsg",errorMsg);
			}
			mv.addObject("userList",usersList);
			mv.addObject("search", search);
			return mv;
		}
	}
	
	public static class Information{
		private User user;
		private String privileges = "";// String ready to display.

		public User getUser(){
			return user;
		}
		public void setUser(User user) {
			this.user = user;
		}
		public String getPrivileges() {
			return privileges;
		}
		public void setPrivileges(String privileges) {
			this.privileges = privileges;
		}
	}
}
