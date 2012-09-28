package reformyourcountry.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;


@Controller
@RequestMapping("/user")
public class UserDisplayController extends BaseController<User> {
	
    @Autowired UserRepository userRepository;
    
    @RequestMapping("/{userName}")
    public ModelAndView userDisplayByUrl(@PathVariable("userName") String userName) {
        
        User user = userRepository.getUserByUserName(userName);
        
        ModelAndView mv = new ModelAndView("userdisplay", "user", user);
        mv.addObject("canEdit", canEdit(user));
        return mv;
        
    }

	private boolean canEdit(User user) {
		return user.equals(SecurityContext.getUser()) || SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS);
	}

       
}