package reformyourcountry.web;

import javax.servlet.ServletContextEvent;

import reformyourcountry.service.LoginService;



/**
 * Application Lifecycle Listener implementation class ServletContextListener
 *
 */
public class CurrentUserServletContextListener implements javax.servlet.ServletContextListener {

    /**
     * Default constructor. 
     */
    
    public CurrentUserServletContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see CurrentUserServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext();
    }

	/**
     * @see CurrentUserServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
