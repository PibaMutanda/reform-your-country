package reformyourcountry.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
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
        System.out.println("SecurityContext.isUserHasPrivilege(privilege)" +SecurityContext.isUserHasPrivilege(privilege)+" privilege:"+privilege);
        
        if(SecurityContext.isUserHasPrivilege(privilege)){
            System.out.println("SecurityContext.isUserHasPrivilege(privilege)" +SecurityContext.isUserHasPrivilege(privilege)+" privilege:"+privilege);
       // JspWriter out = this.getJspContext().getOut();
        getJspBody().invoke(null);
        
        
        }
        
    }

}
