package reformyourcountry.controller;

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
        if (SecurityContext.getUser()!=null){
            mv.addObject("mailsender",SecurityContext.getUser().getMail());
        }
        return mv;
    }

    @RequestMapping("/sendmail")
    public ModelAndView sendMail(@RequestParam String sender,@RequestParam String subject,@RequestParam String content){
        mailService.sendMail(mailService.ADMIN_MAIL, sender,subject,content,MailType.IMMEDIATE,MailCategory.CONTACT);
        ModelAndView mv = new ModelAndView("contact");
        if (SecurityContext.getUser()!=null){
            mv.addObject("mailsender",SecurityContext.getUser().getMail());
        }

        mv.addObject("message","Votre message est bien envoyé");
        return mv;
    }
}
