package reformyourcountry.social;


import org.springframework.social.connect.Connection;

import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import reformyourcountry.service.LoginService;
import reformyourcountry.web.ContextUtil;

public class RycSignInAdapter implements SignInAdapter{

    
 
     
    @Override
    public String signIn(String localId, Connection<?> connection, NativeWebRequest request) {
        
        try {
            LoginService loginService = ContextUtil.getSpringBean(LoginService.class);
                    
            loginService.login(Long.parseLong(localId));
                   
        } catch (Exception e) {
            
   
            throw new RuntimeException(e);
        }
             
        return null;
    }
    
    
    

}
