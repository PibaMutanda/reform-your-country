package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.UserService;


@Controller
public class PrivilegeOverviewController extends BaseController<User>{

	@Autowired 
	private UserService userService;

	@RequestMapping("/privilegeoverview")
	public ModelAndView privilegeoverview(){
		SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_USERS);
		
		List<User> userList = userService.getUserLstWithRoleAndPrivilege();
		
		// Make a list of Information, which is easier to display than a list of users in the JSP.
		List<Information> infoList = new ArrayList<Information>(); 
		for(User u : userList){
			Information current = new Information();
			current.setUser(u);
			for(Privilege p : u.getPrivileges()){
				current.privileges +=  "- " + p.getName() + "</br>";
			}
			infoList.add(current);
		}
		return new ModelAndView("privilegeoverview", "infoList", infoList);
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
