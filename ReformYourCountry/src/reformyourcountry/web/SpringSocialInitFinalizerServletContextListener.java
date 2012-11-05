package reformyourcountry.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.social.connect.web.ProviderSignInController;

/**
 * Application Lifecycle Listener implementation class SpringSocialInitFinalizerServletContextListener
 *
 */

public class SpringSocialInitFinalizerServletContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public SpringSocialInitFinalizerServletContextListener() {
       
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
        
        ProviderSignInController controller =   ContextUtil.getSpringBean(ProviderSignInController.class);
        /** In SocialConfig execution, Spring has not been initialized yet (it's initializing).
         * But calling UrlUtil.getAbsoluteUrl() needs Spring and then ContextUtil to have been initalized.
         * => we need to finish this part here, in this ServleContextListener that is executed after Spring's ContextLoaderListener and RYC's ContextUtil. */
        String url = UrlUtil.getAbsoluteUrl("");
        String prepareurl = url.substring(0,url.lastIndexOf("/"));
        controller.setApplicationUrl(prepareurl);  // 1st part of the URL, where "/signing/facebook" will be appended by Spring Social. Then the full URL is given to facebook who will redirect there when facebook login done.
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
