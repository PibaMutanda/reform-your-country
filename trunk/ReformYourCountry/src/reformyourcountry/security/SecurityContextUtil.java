package reformyourcountry.security;

import org.springframework.beans.factory.annotation.Autowired;

public class SecurityContextUtil {
    
    
    
    
    static SecurityContext securityContext ;
    
    
    public SecurityContextUtil(){
        
    }
    

        
    
    public static SecurityContext getSecurityContext(){
        
        return securityContext;
    }
    
    public static void setSecurityContextUtil(SecurityContext ctx){
        
        securityContext = ctx;
    }

}
