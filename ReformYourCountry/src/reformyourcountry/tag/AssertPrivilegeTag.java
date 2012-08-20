package reformyourcountry.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

/** Prevents a JSP from executing if the user does not have a specific privilege */
public class AssertPrivilegeTag extends SimpleTagSupport {
    
    
    Privilege privilege;
    
    
    public Privilege getPrivilege() {
        return privilege;
    }


    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }


    @Override
    public void doTag() throws JspException, IOException {
        SecurityContext.assertUserHasPrivilege(privilege);
    }

}
