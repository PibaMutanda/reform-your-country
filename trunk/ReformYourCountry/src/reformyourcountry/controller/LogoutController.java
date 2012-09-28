package reformyourcountry.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import reformyourcountry.service.LoginService;



@Controller
public class LogoutController {
    
    @Autowired LoginService loginService;

    @RequestMapping("/ajax/logout")
    public ResponseEntity<String> logout() {
        
         loginService.logout();
        
         
        return new ResponseEntity<String>("loged out", HttpStatus.OK);
    }
   
}