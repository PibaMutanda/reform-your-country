package reformyourcountry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.security.SecurityContext;

@Controller
public class ContactController extends BaseController<User> {

    @RequestMapping("/contact")
    public ModelAndView contactDisplay(){
        ModelAndView mv = new ModelAndView("contact");
        if (SecurityContext.getUser()!=null){
            mv.addObject("mailsender",SecurityContext.getUser().getMail());
        }
        return mv;
    }
}
