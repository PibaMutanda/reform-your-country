package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;

@Controller
public class UserEditController extends BaseController<User> {
    
    @Autowired UserRepository userRepository;

    @RequestMapping("/useredit")
    public ModelAndView userEdit(
            @RequestParam(value="id", required=true) long userId) {
        
        User user = getRequiredEntity(userId); 
        
        ModelAndView mv=new ModelAndView("useredit");
        mv.addObject("user", user);
        return mv;
    }
}
