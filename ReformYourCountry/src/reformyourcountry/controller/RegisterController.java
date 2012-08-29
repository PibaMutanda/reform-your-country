package reformyourcountry.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.UserAlreadyExistsException;
import reformyourcountry.exception.UserAlreadyExistsException.IdentifierType;
import reformyourcountry.model.User;
import reformyourcountry.service.UserService;

@Controller
public class RegisterController {
    
    @Autowired UserService userService;
    
    @RequestMapping("/register")
    public String register(@ModelAttribute User user){
        return  "register";
    }

    
    @RequestMapping("/registersubmit")
    public ModelAndView registerSubmit(@Valid @ModelAttribute User user,BindingResult result){
        if(result.hasErrors()){
            ModelAndView mv = new ModelAndView("redirect:register");
            String msg = null;
            for (ObjectError error : result.getAllErrors())
            {
                msg+=error.toString()+"<br/>";
            }
            mv.addObject("error",msg);
            return mv;
        }else{
            try {
                userService.registerUser(false, user.getUserName(), user.getPassword(), user.getMail());
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
        }

        return new ModelAndView("redirect:home");
    }

}