package reformyourcountry.web;

import reformyourcountry.model.User;
import reformyourcountry.security.SecurityContext;

/** An instance of Current will be bound to the servletContext, in order to enable JSPs to refer it through EL.
 * For example: ${current.user.firstName}
 * 
 * We could have stored a User instance in the HttpSession, but it would be an "old detached" instance. 
 * If the user changes its firstName, for example, that HttpSession User instance would not have been refreshed automatically.
 * => we have this mechanism.
 */
public class Current {

    public static final String ATTRIBUTE_KEY = "current";  
    private static final String VERSION = "0.0.2";
    
    public User getUser(){
        return SecurityContext.getUser();
    }
                            
    public  String getVersion() {
        return VERSION;
    }
}
