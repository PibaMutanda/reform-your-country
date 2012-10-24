package reformyourcountry.controller;



import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.exception.UserAlreadyExistsException;
import reformyourcountry.exception.UserAlreadyExistsException.IdentifierType;
import reformyourcountry.model.User;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.service.UserService;

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
            
        } else {
            try {
                user = userService.registerUser(false, user.getUserName(), user.getPassword(), user.getMail(),false);
                this.addNotificationMessage("Un message de confirmation de votre inscription vous a été envoyé sur votre email :"+user.getMail()+". Merci d'activer votre compte (en cliquant sur le lien de confirmation)afin de pouvoir l'utiliser.", request);
                
            } catch (UserAlreadyExistsException uaee) {
                ModelAndView mv = new ModelAndView("register");
                
                if (uaee.getType() == IdentifierType.MAIL) {
                    this.addNotificationMessage("Un autre utilisateur a déjà choisi cet e-mail. Cela veut soit dire que vous avez déjà un compte chez nous" + //TODO put link
                    		" (si vous ne vous souvenez plus du mot de passe, vous pouvez vous en LINXXXXX faire envoyer un nouveau  XXXXXXXXXXXXXXLINK), " +
                    		"ou bien cela peut vouloir dire que l'e-mail que vous avez introduit n'est pas correct.",request);
                    
                } else if (uaee.getType() == IdentifierType.USERNAME) {
                    this.addNotificationMessage("Un autre utilisateur a déjà choisi ce pseudonyme. Merci d'en spécifier un autre. " +
                    		"A moins que cela veuille dire que vous avez déjà un compte chez nous (si vous ne vous souvenez plus du mot de passe, vous pouvez vous en LINXXXXX faire envoyer un nouveau  XXXXXXXXXXXXXXLINK).",request); //TODO put link
                } else {  // defensive coding
                    throw new RuntimeException("Bug - Unsupported type: " + uaee.getType());
                }
                
                return mv;
            }
            
            return new ModelAndView("redirect:home");
        }
    }
    
    @RequestMapping("/resendpassword")
    public String resendPassword(){
        return "resendpassword";
    }
    
  /*  
    @RequestMapping("/resendpasswordsubmit")
    public ModelAndView resendPasswordByEmail(@RequestParam("email") String email){
        
        ModelAndView mv = new ModelAndView("redirect:resendpassword");  
        if(email == null || StringUtils.isBlank(email)){       
            mv.addObject("mailerrormessage", "Veuillez entrer un adresse email valide."); 
            return mv;
        }
        
         User user =  userRepository.getUserByEmail(email);
         
         if(user == null){
             
             mv.addObject("mailerrormessage", "L'email que vous avez entrée ne correspond à aucun utilisateur enregistré sur enseignement2.be"); 
             
             return mv;
             
         }
         
         //TODO continue
        
        
    }
*/
}