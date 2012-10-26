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
        URL resource = getClass().getClassLoader().getResource("website_content.properties");    
        //String path = sc.getRealPath("website_content.properties");  // TODO : load this file through the classloader (via Spring).
        try {
            props.load(new InputStreamReader(resource.openStream(), "UTF8"));
            //props.load(new FileInputStream(path));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Enumeration<?> keys = props.propertyNames();
        //loop through all properties and add each to the ServletContext
        while(keys.hasMoreElements()){
            String key = (String)keys.nextElement();
            sc.setAttribute("p_" + key, props.getProperty(key));
        }
        sc.setAttribute("p_website_address",UrlUtil.getProdAbsoluteDomainName());  
        sc.setAttribute("p_website_name",UrlUtil.getWebSiteName());         
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        
    }

}
