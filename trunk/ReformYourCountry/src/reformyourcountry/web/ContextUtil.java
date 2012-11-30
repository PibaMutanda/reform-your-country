package reformyourcountry.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import reformyourcountry.util.CurrentEnvironment;
import reformyourcountry.util.CurrentEnvironment.Environment;

public class ContextUtil implements Filter, ServletContextListener {

	

	public static ServletContext servletContext;  // Remembers the servlet context and exposes useful values to the application through static methods.
    public static ApplicationContext springContext;
    public static boolean devMode;
    public static Environment environment;
    
    // Initialized by filter
    private static ThreadLocal<HttpServletRequest> httpServletRequest = new ThreadLocal<HttpServletRequest>();
    private static ThreadLocal<HttpServletResponse> httpServletResponse = new ThreadLocal<HttpServletResponse>();
    

	
    @Override
    public void contextInitialized(ServletContextEvent ev) {
        servletContext = ev.getServletContext();
        servletContext.setAttribute(Current.ATTRIBUTE_KEY, new Current());
        contextInitialized( WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext) );
    }


   public static void contextInitialized(ApplicationContext ac) {
      springContext = ac;        
      environment = springContext.getBean(CurrentEnvironment.class).getEnvironment();
	  devMode = (environment == Environment.DEV); 
    }
 
   
	public void contextDestroyed(ServletContextEvent ev) {
		servletContext = null;
		springContext = null;
	}
	


	public static Environment getEnvironment(){
		if(environment == null){
			environment = springContext.getBean(CurrentEnvironment.class).getEnvironment();
		}
		return environment;
	}
	
	/** Returns "/ReformYourCountry" in dev, and "/" in prod. */
	public static String getRealContextPath() {
		return servletContext.getContextPath();
	}	
	
    public static ServletContext getServletContext() {
		return servletContext;
	}
    
    /** The current thread most probably runs inside a batch because the servlet context has not been initialized here */
    public static boolean isInBatchNonWebMode() {
    	return getServletContext() == null;
    }

    /** The current thread is probably created by tomcat to process a request.
     * But sometimes, not. For example, the background thread sending mails is not bound to a specific web request.
     */
    public static boolean isInWebRequestProcessingThread() {
        return httpServletRequest.get() != null;
        
    }

   public static Object getSpringBean(String name) {
		return springContext.getBean(name);
	}

    public static <T> T getSpringBean(Class<? extends T> beanClass) {
        return (T)springContext.getBean(beanClass);
   }

	@Override
	public void destroy()  { /* Nothing to do... */ }

	@Override
	public void init(FilterConfig arg0) throws ServletException { /* Nothing to do... */ }

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {


		if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {
			try{
	            init((HttpServletRequest)req, (HttpServletResponse)res);			
				chain.doFilter(req, res);
			} catch(Throwable t){
				throw t;
			} finally{
				clear();
			}
		} else { // Other kind of request/response...
			chain.doFilter(req, res);
		}
	}
	
	public static void init(HttpServletRequest req, HttpServletResponse res) {
		if (httpServletRequest.get() != null || httpServletResponse.get() != null) {
			throw new IllegalStateException("Request or response already set for this thread. It can be set only once per request.");
		}
		httpServletRequest.set(req);
		httpServletResponse.set(res);
	}
	
	public static void clear() {
		httpServletRequest.set(null);
		httpServletResponse.set(null);
	}
	
	public static HttpServletRequest getHttpServletRequest() {
	    if (httpServletRequest.get() == null) {
	    	throw new IllegalStateException("This thread has not been initialized by the filter. Request not found.");
	    }
	    return httpServletRequest.get();
	}
	public static HttpServletResponse getHttpServletResponse() {
	    if (httpServletResponse.get() == null) {
	    	throw new IllegalStateException("This thread has not been initialized by the filter. Response not found.");
	    }
	    return httpServletResponse.get();
	}
	public static HttpSession getHttpSession() {
		return getHttpServletRequest().getSession();
	}

	public static String getSessionId() {
		HttpSession session = getHttpServletRequest().getSession(false);
		if(session != null){
			return session.getId();
		}
		return null;
	}

	
}	
	
