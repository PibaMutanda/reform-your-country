package reformyourcountry.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import reformyourcountry.model.User;
import reformyourcountry.service.LoginService;
import reformyourcountry.util.CurrentEnvironment;
import reformyourcountry.web.ContextUtil;
import reformyourcountry.web.UrlUtil;



@Controller
public class LogoutController extends BaseController<User>{
    
    @Autowired LoginService loginService;

    @RequestMapping("/logout")
    public String logout(WebRequest request) {
  
         loginService.logout();
         addNotificationMessage("Vous ête à present deconnecté de "+UrlUtil.getWebSiteName(), request);
         
        return "redirect:/";
    }
    @RequestMapping("/ajax/logout")
    public ResponseEntity<String> logoutAjax() {
   
         loginService.logout();
         
         
        return new ResponseEntity<String>("loged out", HttpStatus.OK);
    }
   
}
