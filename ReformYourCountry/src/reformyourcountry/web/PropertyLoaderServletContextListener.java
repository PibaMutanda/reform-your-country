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
        Properties props = new Properties();
        //Open the properties file and send it to a Properties Object
        URL resource = getClass().getClassLoader().getResource("website_content.properties");    
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
        //Add special properties from config.properties
        sc.setAttribute("p_website_address",UrlUtil.getProdAbsoluteDomainName());  
        sc.setAttribute("p_website_name",UrlUtil.getWebSiteName());         
        sc.setAttribute("p_version",UrlUtil.getVersion());  
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        
    }

}
