package reformyourcountry.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;


@Controller
public class UserDisplayController extends BaseController<User> {
    
    @RequestMapping("/userdisplay")
    public ModelAndView userDisplay(
            @RequestParam(value="id", required=true) long userId) {
        
        User user = getRequiredEntity(userId);

        return new ModelAndView("userdisplay", "user", user);
    }


       
}
