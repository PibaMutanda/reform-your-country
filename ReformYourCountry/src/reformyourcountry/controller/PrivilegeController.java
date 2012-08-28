package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.model.User.Role;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

/** To edit the role and privileges of a user */
@Controller
public class PrivilegeController extends BaseController<User> {
	
	// Stores 3 values for each privilege. It eases the job of the JSP putting these values in a table.
	public class PrivilegeTriplet{
		public boolean permitted = false;
		public Privilege privilege;
		public Role role;

		public boolean isPermitted() {
			return permitted;
		}
		public void setPermitted(boolean permitted) {
			this.permitted = permitted;
		}
		public Privilege getPrivilege() {
			return privilege;
		}
		public void setPrivilege(Privilege privilege) {
			this.privilege = privilege;
		}
		public Role getRole() {
			return role;
		}
		public void setRole(Role role) {
			this.role = role;
		}

	}
	
	@Autowired 	UserRepository userRepository;
	
	
	@RequestMapping(value="/privilegeedit")
	public ModelAndView privilegeEdit(@RequestParam(value="id") Long userId) {
		SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_USERS);
		User user = userRepository.find(userId);
		return createModelAndView(user);
	}
	
	
	
	@RequestMapping(value="/roleeditsubmit")
	public ModelAndView roleEditSubmit(@RequestParam(value="role")String role,@RequestParam(value="id") Long userId) {
		SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_USERS);
		User user = userRepository.find(userId);
		user.setRole(Role.valueOf(role));
		return createModelAndView(user);
	}

	@RequestMapping(value="/privilegeeditsubmit")
	public ModelAndView privilegeEditSubmit(@RequestParam Map <String, String> params, @RequestParam("id") long id){
		SecurityContext.assertUserHasPrivilege(Privilege.MANAGE_USERS);
		ModelAndView mv = new ModelAndView("redirect:user", "id", id);
		User user = getRequiredEntity(id);
		params.remove("id");
		user.getPrivileges().clear();		
		for (Map.Entry<String, String> entry: params.entrySet()) {
			try {
				user.getPrivileges().add(Privilege.valueOf(entry.getKey()));
			} catch (Exception e) {
				throw new IllegalArgumentException("parameters should only contains Id and privileges");
			}
		}
		mv.addObject("user", user);
		return mv;
	}
	
	private ModelAndView createModelAndView(User user) {
		ModelAndView mv =new ModelAndView("privilege");
		mv.addObject("user", user);
		
		// Create triplet list
		List<PrivilegeTriplet> triplets = new ArrayList<PrivilegeTriplet>();
		EnumSet<Privilege> usersprivilege = SecurityContext.getAllAssociatedPrivileges(user);
		for (Privilege privilege : Privilege.values()) {
			PrivilegeTriplet newTriplet = new PrivilegeTriplet();
			newTriplet.setPrivilege(privilege);
			if (usersprivilege.contains(privilege)) {
				newTriplet.setPermitted(true);
			}
			newTriplet.setRole(privilege.getAssociatedRole());
			triplets.add(newTriplet);
		}
		mv.addObject("privilegetriplets", triplets);
		return mv;
	}
	

}
