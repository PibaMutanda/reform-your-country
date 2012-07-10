package reformyourcountry.security;

import org.springframework.beans.factory.annotation.Autowired;
/**
 * 
 * @author forma305
 * the purpose of this class is to get the instance of SecurityContext from BaseEntity class where
 *  Autowired does not work properly with spring
 */
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
