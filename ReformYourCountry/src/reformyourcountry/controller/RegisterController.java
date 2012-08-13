package reformyourcountry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import reformyourcountry.exception.UserAlreadyExistsException;
import reformyourcountry.service.UserService;

@Controller
public class RegisterController {
    
    @Autowired UserService userService;
    
    @RequestMapping("/register")
    public String register(){
        return  "register";
    }

    
    @RequestMapping("/registersubmit")
    public String registerSubmit( @RequestParam("userName")String userName,
                                  @RequestParam("password")String password,
                                  @RequestParam("mail")String mail){
        try {
            userService.registerUser(false, userName, password, mail);
        } catch (UserAlreadyExistsException e) {
           
            e.printStackTrace();
        }

        return "home";
    }

}