package reformyourcountry.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import reformyourcountry.service.LoginService;



@Controller
public class LogoutController {
    
    @Autowired LoginService loginService;

    @RequestMapping("/logout")
    public String logout() {
        
         loginService.logout();
        
 
        return "redirect:home";
    }
   
}
