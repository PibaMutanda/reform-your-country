package reformyourcountry.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.model.User.Role;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

/** Prevents a JSP from executing if the user does not have a specific privilege or role */
public class ConditionDisplayTag extends SimpleTagSupport {
    
    
    Privilege privilege;
    Role role;

    public Privilege getPrivilege() {
        return privilege;
    }


    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }


    public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	@Override
    public void doTag() throws JspException, IOException {
        if(privilege != null && SecurityContext.isUserHasPrivilege(privilege)){
            getJspBody().invoke(null);
        }
        if(role != null && SecurityContext.isUserHasRole(role)){
        	getJspBody().invoke(null);
        }

    }

}
