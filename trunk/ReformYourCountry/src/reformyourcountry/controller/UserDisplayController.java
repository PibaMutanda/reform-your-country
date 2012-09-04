package reformyourcountry.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;


@Controller
public class UserDisplayController extends BaseController<User> {
    
    @RequestMapping("/user")
    public ModelAndView userDisplay(
            @RequestParam(value="id", required=true) long userId) {
        
        User user = getRequiredEntity(userId);
        
        ModelAndView mv = new ModelAndView("userdisplay", "user", user);
        mv.addObject("canEdit", user.equals(SecurityContext.getUser()) || SecurityContext.isUserHasPrivilege(Privilege.MANAGE_USERS));
        return mv;
        
    }


       
}
