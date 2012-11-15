package reformyourcountry.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import reformyourcountry.mail.MailCategory;
import reformyourcountry.mail.MailType;
import reformyourcountry.model.User;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.MailService;
import reformyourcountry.util.NotificationUtil;

@Controller
public class ContactController extends BaseController<User> {

    @Autowired
    MailService mailService;
    @RequestMapping("/contact")
    public ModelAndView contactDisplay(){
        ModelAndView mv = new ModelAndView("contact");
        if (SecurityContext.getUser()!=null){
            mv.addObject("sender",SecurityContext.getUser().getMail());
            
        }
        return mv;
    }

    @RequestMapping("/sendmail")
    public ModelAndView sendMail(@RequestParam String sender, @RequestParam String subject, @RequestParam String content, WebRequest request){
        ModelAndView mv = new ModelAndView("contact");
        
        if (StringUtils.isBlank(content)) {
            return prepareModelAndView(sender, subject, content,"Indiquez un contenu au message.", request);
        }
        
        if (StringUtils.isBlank(subject)) {
            return prepareModelAndView(sender, subject, content, "Indiquez le sujet de votre message.", request);
        }
        
        if (StringUtils.isBlank(sender)) {
            return prepareModelAndView(sender, subject, content, "Indiquez une adresse e-mail où l'on puisse vous répondre.", request);   
        }
        
        mailService.sendMail(mailService.ADMIN_MAIL, sender, subject,content, MailType.IMMEDIATE, MailCategory.CONTACT);
        NotificationUtil.addNotificationMessage("Votre message est bien envoyé");

        return new ModelAndView("redirect:/");  // Go to home page.
    }
    
    
    private ModelAndView prepareModelAndView(String sender,String subject,String content,String message, WebRequest request) {
    	NotificationUtil.addNotificationMessage(message);
        ModelAndView mv = new ModelAndView("contact");
        
        // Refill fields to prevent to user to retype them.
        mv.addObject("sender", sender);
        mv.addObject("subject", subject);
        mv.addObject("content", content);
        return mv;
    }
    
}
