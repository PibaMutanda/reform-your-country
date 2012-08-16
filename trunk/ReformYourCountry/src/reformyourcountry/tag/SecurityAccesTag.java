package reformyourcountry.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

public class SecurityAccesTag extends SimpleTagSupport {
    
    
    Privilege privilege;
    
    
    public Privilege getPrivilege() {
        return privilege;
    }


    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }


    @Override
    public void doTag() throws JspException, IOException {
       
        
        if(SecurityContext.isUserHasPrivilege(privilege)){
            
   
        getJspBody().invoke(null);
        
        
        }
        
    }

}
