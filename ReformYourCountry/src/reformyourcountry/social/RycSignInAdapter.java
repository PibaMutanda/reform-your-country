package reformyourcountry.social;


import org.springframework.social.connect.Connection;

import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;

import reformyourcountry.controller.LoginController;
import reformyourcountry.service.LoginService;
import reformyourcountry.web.ContextUtil;

public class RycSignInAdapter implements SignInAdapter {

    @Override
    public String signIn(String localId, Connection<?> connection, NativeWebRequest request) {
        
        try {
            LoginService loginService = ContextUtil.getSpringBean(LoginService.class);
            
            // this value is setup in logincontroller (/ajax/autologin)
            Boolean autologin =  (Boolean) request.getAttribute(LoginController.AUTOLOGIN,RequestAttributes.SCOPE_SESSION);
           // with the autologin flag the login service will create a cookie for the user login
            if(autologin != null)     
            loginService.login(null,null,autologin.booleanValue(),Long.parseLong(localId));
            else
                loginService.login(null,null,false,Long.parseLong(localId));
            
              request.removeAttribute(LoginController.AUTOLOGIN, RequestAttributes.SCOPE_SESSION);
              
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
             
        return null;
    }
    
    
    

}
