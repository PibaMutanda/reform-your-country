package reformyourcountry.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.service.UserService;
import reformyourcountry.web.ContextUtil;
import reformyourcountry.web.UrlUtil;

public class RetrievePasswordController extends BaseController<User>{
    
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;
    
    @RequestMapping("/resendpasswordsubmit")
    public ModelAndView resendPasswordSubmit(@RequestParam("email") String email,WebRequest request){

        ModelAndView mv = new ModelAndView("redirect:resendpassword");  
        if(email == null || StringUtils.isBlank(email)){       
            mv.addObject("mailerrormessage", "Veuillez entrer un adresse email valide."); 
            return mv;
        }
        User user =  userRepository.getUserByEmail(email);
        if(user == null){
            mv.addObject("mailerrormessage", "L'e-mail que vous avez entrée ne correspond à aucun utilisateur enregistré sur "+UrlUtil.getWebSiteName());  
            return mv;

        }else{
            userService.generateNewPasswordForUserAndSendEmail(user);
            addNotificationMessage("Votre nouveau mot de passe viens de vous être envoyé sur votre adresse e-mail à "+user.getMail(),request);
            return new ModelAndView("redirect:home");
        }

    }
    
    

}
