package reformyourcountry.web;

import javax.servlet.ServletContextEvent;



public class CurrentUserServletContextListener implements javax.servlet.ServletContextListener {


    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext();
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        
    }

}
