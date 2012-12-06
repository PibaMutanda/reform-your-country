package reformyourcountry.web;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;





public class PropertyLoaderServletContextListener implements javax.servlet.ServletContextListener {


    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc= sce.getServletContext();
        
        //Open the properties file attach each property value to the servlet context.
        URL resource = getClass().getClassLoader().getResource("strings_unchanged_in_application.properties");    
        sendPropsToContext(sc, resource);
        resource = getClass().getClassLoader().getResource("strings_specific_to_application.properties");    
        sendPropsToContext(sc, resource);
        
        //Add special properties from config.properties that we want available with EL (expression language) in JSPs.
        sc.setAttribute("p_website_address",UrlUtil.getProdAbsoluteDomainName());  
        sc.setAttribute("p_website_name",UrlUtil.getWebSiteName());         
        sc.setAttribute("p_version",UrlUtil.getVersion());  
    }

    public void sendPropsToContext(ServletContext sc, URL resource) {

        Properties props = new Properties();
        try {
            props.load(new InputStreamReader(resource.openStream(), "UTF8"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //Save a map with each property names
        Enumeration<?> keys = props.propertyNames();
        
        //loop through all properties and add each to the ServletContext
        while(keys.hasMoreElements()){
            String key = (String)keys.nextElement();
            sc.setAttribute("p_" + key, props.getProperty(key));
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        
    }
    
    public static String getProprerty(String propertyName) {
        return (String) ContextUtil.servletContext.getAttribute(propertyName);
    }

}
