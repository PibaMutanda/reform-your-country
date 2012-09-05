package reformyourcountry.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;

import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.LoginService;

/**
 * Automates the login of a user that has his userid and password (encoded) in a cookie.
 * Sets up the SecurityContext.
 */
public class SecurityFilter  extends GenericFilterBean implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
        LoginService loginService = (LoginService) wac.getBean("loginService");
        
        // 1. Try to login (it does nothing if already logged in).
        loginService.tryAutoLoginFromCookies();
        // 2. No need to initialize the SecurityContext, it will initialize itself lazily if needed (from the userid in the HttpSession)
        // But need to clear the Threadlocals.
     
        try {
            chain.doFilter(request , response);
        } catch (Throwable t) {
            throw t;           
        } finally{
            SecurityContext.clear();
        }     
	}

}
