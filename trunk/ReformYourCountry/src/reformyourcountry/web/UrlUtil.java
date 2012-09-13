package reformyourcountry.web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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
    
  
	/** Returns the domain name for use in the cookies.
	 */
	public static String getCookieDomainName() {
		switch(ContextUtil.getEnvironment()) {
			case DEV  : return ".localhost.local/ReformYourCountry/"; //Domain name need two dots to be valid so we have to write 127.0.0.1 or .localhost.local (just "localhost" will not work).
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

    // TODO: test this code (is it a reasonable way to test an URL ?) - John 2012-09-05 
    public static boolean isUrlValid(String outUrl) {
        URL u;
        try {
            u = new URL(outUrl);
            HttpURLConnection huc;
            huc = ( HttpURLConnection )  u.openConnection();
            huc.setRequestMethod("GET"); 
            huc.connect () ;         
            if(HttpURLConnection.HTTP_OK != huc.getResponseCode()) return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

}
