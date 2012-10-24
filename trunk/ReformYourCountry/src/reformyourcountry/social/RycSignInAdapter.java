package reformyourcountry.social;


import org.apache.commons.lang3.StringUtils;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import reformyourcountry.controller.BaseController;
import reformyourcountry.controller.LoginController;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountConnectedType;
import reformyourcountry.service.LoginService;
import reformyourcountry.web.ContextUtil;
@Component
public class RycSignInAdapter implements SignInAdapter {

    @Override
    public String signIn(String localId, Connection<?> connection, NativeWebRequest request) {
        
        try {
            LoginService loginService = ContextUtil.getSpringBean(LoginService.class);
            User user = null;
            // this value is setup in logincontroller (/ajax/autologin)
            Boolean autologin =  (Boolean) request.getAttribute(LoginController.AUTOLOGIN_KEY,RequestAttributes.SCOPE_SESSION);
            autologin = autologin == null ? false : autologin;
            request.removeAttribute(LoginController.AUTOLOGIN_KEY, RequestAttributes.SCOPE_SESSION);
                    
            user = loginService.login(null, null, autologin, Long.parseLong(localId), AccountConnectedType.getProviderType(connection.getKey().getProviderId()));
                     
            BaseController.addNotificationMessage("Vous êtes a présent connecté sur enseignement2.be avec votre compte "+connection.getKey().getProviderId(),request);                                           

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
             
        return null;
    }
    
    
    

}
