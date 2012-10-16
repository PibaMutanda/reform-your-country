package reformyourcountry.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import reformyourcountry.mail.MailCategory;
import reformyourcountry.mail.MailType;
import reformyourcountry.model.User;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.MailService;

@Controller
public class ContactController extends BaseController<User> {

    @Autowired
    MailService mailService;
    @RequestMapping("/contact")
    public ModelAndView contactDisplay(){
        ModelAndView mv = new ModelAndView("contact");
     //   if (SecurityContext.getUser()!=null){
      //      mv.addObject("mailsender",SecurityContext.getUser().getMail());
            
      //  }
        return mv;
    }

    @RequestMapping("/sendmail")
    public ModelAndView sendMail(@RequestParam String sender, @RequestParam String subject, @RequestParam String content){
        ModelAndView mv = new ModelAndView("contact");
        
        if (StringUtils.isBlank(content)) {
            return prepareModelAndView(sender, subject, content,"Indiquez un contenu au message.");
        }
        
        if (StringUtils.isBlank(subject)) {
            return prepareModelAndView(sender, subject, content, "Indiquez le sujet de votre message.");
        }
        
        if (StringUtils.isBlank(sender)) {
            return prepareModelAndView(sender, subject, content, "Indiquez une adresse e-mail où l'on puisse vous répondre." );   
        }
        
        mailService.sendMail(mailService.ADMIN_MAIL, sender, subject,content, MailType.IMMEDIATE, MailCategory.CONTACT);
        return new ModelAndView("redirect:/");  // Go to home page.
        
        // TODO: add a message (JavaScript nicely - see John) : "Votre message est bien envoyé".
    }
    
    
    private ModelAndView prepareModelAndView(String sender,String subject,String content,String message) {
        ModelAndView mv = setMessage(new ModelAndView("contact"), message);
        mv.addObject("sender", sender);
        mv.addObject("subject",subject);
        mv.addObject("content", content);
        return mv;
    }
    
}
