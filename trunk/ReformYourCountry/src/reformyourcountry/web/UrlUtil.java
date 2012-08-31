package reformyourcountry.web;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlUtil {

    public final static String TEST_ABSOLUTE_DOMAIN_NAME = "enseignement2.be"; 
    public final static String PROD_ABSOLUTE_DOMAIN_NAME = "enseignement2.be"; 
    public final static String DEV_ABSOLUTE_DOMAIN_NAME = "localhost:8080";


    // Usually for images.
    public static String getAbsoluteUrl(String path){
        if(path.startsWith("/")){ //We need to normalize the incoming string. we remove the first / in the path if there is one.
            path = path.substring(1); 
        }
        return getAbsoluteUrl(path, false);
    }

    // Usually for images.
    public static String getAbsoluteUrl(String path, boolean forceProdUrl){
        return getAbsoluteUrl(path, forceProdUrl, false);
    }
    
  
	
	public static String getDomainName() {
		switch(ContextUtil.getEnvironment()) {
			case DEV : return ".localhost.local/ReformYourCountry/";
			case TEST : return TEST_ABSOLUTE_DOMAIN_NAME;
			case PROD : return PROD_ABSOLUTE_DOMAIN_NAME;
		}
		throw new RuntimeException("Unknown Environement");
	}
	
    public static String getAbsoluteUrl(String path, boolean forceProdUrl,  boolean addVaadinPrefix){
        
        String result;
        if (!ContextUtil.devMode || forceProdUrl || ContextUtil.isInBatchNonWebMode()) {
            // In dev, it should be "http://enseignement2.be/"  (because images are not loaded on developers machines).
        	
            result = "http://" + PROD_ABSOLUTE_DOMAIN_NAME        + "/"; // The "/" is crucial at the end of the project name. Without it, you loose the session.
            // i.e.:  http://  + enseignement2.be                 +  /
            
        } else {  // We are in dev and we don't force prod urls.
            result = "http://" + DEV_ABSOLUTE_DOMAIN_NAME +  ContextUtil.getRealContextPath() + "/";  // The "/" is crucial at the end of the project name. Without it, you loose the session.
            // i.e.:  http://    localhost:8080           +  /ReformYourCountry               +  /
        }
        result += path;    //        mypage?params
        return result;
    }

    public static String getRelativeUrl(String path){
        return  ContextUtil.getRealContextPath() + path;
    }
    
    //return a valid url, ex  http://test for image.png    to    http://test%20for%20image.png
    public static String getUrlWithNoBlank(String url){
    	try {
			String validUrl = new URI(null, null, url).toString();
			return validUrl.substring(1);// remove the '#' before the string (that is automatically added by URI
		} catch (URISyntaxException e) {
			throw new RuntimeException("invalid URL : "+e);
		}
    }
    
}
