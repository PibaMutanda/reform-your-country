package reformyourcountry.controller;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.UserAlreadyExistsException;
import reformyourcountry.exception.UserAlreadyExistsException.IdentifierType;
import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.service.UserService;
import reformyourcountry.util.HTMLUtil;
import reformyourcountry.util.NotificationUtil;

@Controller
public class RegisterController extends BaseController<User> {
    
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;
    
    @RequestMapping("/register")   
    public String register(@ModelAttribute User user){
        return  "register";
    }

    @RequestMapping("/registersubmit")
    public ModelAndView registerSubmit(@Valid @ModelAttribute User user, BindingResult result,WebRequest request){
        if(result.hasErrors()){
        	
            ModelAndView mv = new ModelAndView("register");
            return mv;
            
        } else if (!HTMLUtil.isHtmlSecure(user.getUserName())) {
            //TODO review
        	ModelAndView mv = new ModelAndView("register");
            NotificationUtil.addNotificationMessage("vous avez introduit du HTML/Javascript dans vos informations d'enregistrement " + user.getUserName());
            return mv;
        } else {
            try {
                user = userService.registerUser(false, user.getUserName(), user.getPassword(), user.getMail(),false);
                NotificationUtil.addNotificationMessage("Un message de confirmation de votre inscription vous a été envoyé sur votre email :"+user.getMail()+". Merci d'activer votre compte (en cliquant sur le lien de confirmation)afin de pouvoir l'utiliser.");
                
            } catch (UserAlreadyExistsException uaee) {
                ModelAndView mv = new ModelAndView("register");
                
                if (uaee.getType() == IdentifierType.MAIL) {
                	NotificationUtil.addNotificationMessage("Un autre utilisateur a déjà choisi cet e-mail. Cela veut soit dire que vous avez déjà un compte chez nous" + 
                    		" (si vous ne vous souvenez plus du mot de passe, vous pouvez vous en <a href='/resendpassword'>faire envoyer un nouveau</a>), " +
                    		"ou bien cela peut vouloir dire que l'e-mail que vous avez introduit n'est pas correct.");
                    
                } else if (uaee.getType() == IdentifierType.USERNAME) {
                	NotificationUtil.addNotificationMessage("Un autre utilisateur a déjà choisi ce pseudonyme. Merci d'en spécifier un autre. " +
                    		"A moins que cela veuille dire que vous avez déjà un compte chez nous (si vous ne vous souvenez plus du mot de passe, vous pouvez vous en <a href='/resendpassword'>faire envoyer un nouveau</a>."); 
                } else {  // defensive coding
                    throw new RuntimeException("Bug - Unsupported type: " + uaee.getType());
                }
                
                return mv;
            }
            
            return new ModelAndView("redirect:home");
        }
    }
    
 
    
  
}