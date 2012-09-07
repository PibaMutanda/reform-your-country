package reformyourcountry.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;


@Controller
public class UserDisplayController extends BaseController<User> {
	
    @Autowired UserRepository userRepository;
    
    @RequestMapping("/user")
    public ModelAndView userDisplay(@RequestParam(value="username", required=true) String username) {
    	
        User user = userRepository.getUserByUserName(username);
        
        ModelAndView mv = new ModelAndView("userdisplay", "user", user);
        mv.addObject("canEdit", canEdit(user));
        return mv;
        
    }
     

	private boolean canEdit(User user) {
		return user.equals(SecurityContext.getUser()) || SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS);
	}

       
}
