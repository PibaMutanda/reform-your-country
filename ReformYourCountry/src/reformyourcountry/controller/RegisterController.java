package reformyourcountry.controller;



import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.UserAlreadyExistsException;
import reformyourcountry.exception.UserAlreadyExistsException.IdentifierType;
import reformyourcountry.service.UserService;

@Controller
public class RegisterController {
    
    @Autowired UserService userService;
    
    @RequestMapping("/register")
    public String register(){
        return  "register";
    }

    
    @RequestMapping("/registersubmit")
    public ModelAndView registerSubmit(@Valid @RequestParam("userName")String userName,
                                  @Valid @RequestParam("password")String password,
                                  @RequestParam("mail")String mail,HttpServletRequest request){
        try {
            userService.registerUser(false, userName, password, mail);
        } catch (UserAlreadyExistsException uaee) {
            ModelAndView mv = new ModelAndView("register");
            String msg;
            if (uaee.getType() == IdentifierType.MAIL) {
                msg = "Un autre utilisateur a déjà choisi cet e-mail";
            } else if (uaee.getType() == IdentifierType.USERNAME) {
                msg = "Un autre utilisateur a déjà choisi ce pseudonyme";
            } else {  // defensive coding
                throw new RuntimeException("Unsupported type: " + uaee.getType());
            }
            mv.addObject("error", msg);
            return mv;
        }

        return new ModelAndView("home");
    }

}