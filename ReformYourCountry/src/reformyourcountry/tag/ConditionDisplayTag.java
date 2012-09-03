package reformyourcountry.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import javax.servlet.jsp.tagext.SimpleTagSupport;

import reformyourcountry.misc.CurrentEnvironment.Environment;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.web.ContextUtil;

/** Only display the body if the user has a specific privilege */
public class ConditionDisplayTag extends SimpleTagSupport {
    
    
    Privilege privilege;
    Environment environment;

    public Privilege getPrivilege() {
        return privilege;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Override
    public void doTag() throws JspException, IOException {
        //FIXME what if we want to test the two values? -- maxime 31/08/12
        if(environment != null && ContextUtil.getEnvironment().equals(environment)){
            
            getJspBody().invoke(null);
        }else if(privilege != null && SecurityContext.isUserHasPrivilege(privilege)){
            
            getJspBody().invoke(null);
        }

    }

}
