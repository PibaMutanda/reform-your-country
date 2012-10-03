package reformyourcountry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.security.SecurityContext;

@Controller
public class ContactController {

    @RequestMapping("/contact")
    public ModelAndView contactDisplay(){
        ModelAndView mv = new ModelAndView("contact");
        mv.addObject("user",SecurityContext.getUser());
        return mv;
    }
}
