package reformyourcountry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.model.User.Role;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.Privilege;
@Controller
public class PrivilegeController {
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
	@Autowired
	UserRepository ur;
	@RequestMapping(value="/editprivilege")
	public ModelAndView EditPrivilege(@RequestParam(value="role",required=false) Role role) {
		ModelAndView mv =new ModelAndView("Privilege");
		User uzr=ur.find(1l);//TODO change to threadlocal...
		List<PrivilegeTriplet> triplets = new ArrayList<PrivilegeTriplet>();
		for(Privilege privilege : Privilege.values()){
			PrivilegeTriplet newTriplet = new PrivilegeTriplet();
			newTriplet.setPrivilege(privilege);
			if(uzr.getPrivileges().contains(privilege)){
				newTriplet.setPermitted(true);
			}
			newTriplet.setRole(privilege.getAssociatedRole());
			triplets.add(newTriplet);
		}
		if (role!=null) {
			mv.addObject("role", role.name());
		}else{
			mv.addObject("role","");
		}
		mv.addObject("user", uzr);
		mv.addObject("privilegetriplets",triplets);
		
		return mv;
	}
}
