package reformyourcountry.web;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import reformyourcountry.util.CurrentEnvironment;

public class UrlUtil {

    private static String PROD_ABSOLUTE_DOMAIN_NAME = null;
    public final static String DEV_ABSOLUTE_DOMAIN_NAME = "localhost:8080";
    private static String WebSiteName = null;;
    private static String version = null;
    
    
    //Send the Software Version (Value is in the config.properties)
    public static String getVersion(){
        if (version==null){
            version= ((CurrentEnvironment)ContextUtil.springContext.getBean(CurrentEnvironment.class)).getVersion();
        }
        return version;
    }
    //Send the Domain Name (x.y.com) (Value is in the config.properties)
    public static String  getProdAbsoluteDomainName(){
        if (PROD_ABSOLUTE_DOMAIN_NAME==null) {
            PROD_ABSOLUTE_DOMAIN_NAME = ((CurrentEnvironment)ContextUtil.springContext.getBean(CurrentEnvironment.class)).getSiteAddress();
        }
        return PROD_ABSOLUTE_DOMAIN_NAME;
    }
    //Send the Project Name (Value is in the config.properties)
    public static String  getWebSiteName(){
        if(WebSiteName==null){
            WebSiteName =   ((CurrentEnvironment)ContextUtil.springContext.getBean(CurrentEnvironment.class)).getSiteName();
        }
        return WebSiteName;
    }

    // Usually for images.
    public static String getAbsoluteUrl(String path){
      
        return getAbsoluteUrl(path, null);
    }

   
  
	/** Returns the domain name for use in the cookies.
	 */
	public static String getCookieDomainName() {
		switch(ContextUtil.getEnvironment()) {
			case DEV  : return ".localhost.local" + ContextUtil.getRealContextPath()+"/"; //Domain name need two dots to be valid so we have to write 127.0.0.1 or .localhost.local (just "localhost" will not work).
		//	case TEST : return TEST_ABSOLUTE_DOMAIN_NAME;
			case PROD : return getProdAbsoluteDomainName();
		}
		throw new RuntimeException("Unknown Environement");
	}
	
	public static enum  Mode {DEV, PROD}; 
	
	
    public static String getAbsoluteUrl(String path, Mode forceMode){
        if(path.startsWith("/")){ //We need to normalize the incoming string. we remove the first / in the path if there is one.
            path = path.substring(1); 
        }
        String result;
        Mode resultType = forceMode;
        if (resultType == null) {
            if (ContextUtil.devMode && !ContextUtil.isInBatchNonWebMode()) {
                resultType = Mode.DEV;
            } else {
                resultType = Mode.PROD;
            }
        }
        if (resultType == Mode.PROD) {
            // In dev, it should be "http://ryc.be/"  (because images are not loaded on developers machines).
        	
            result = "http://" + getProdAbsoluteDomainName()        + "/"; // The "/" is crucial at the end of the project name. Without it, you loose the session.
            // i.e.:  http://  + ryc.be                 +  /
            
        } else {  // We are in dev and we don't force prod urls.
            result = "http://"
                + DEV_ABSOLUTE_DOMAIN_NAME    // localhost:8080
                + "/";  // The "/" is crucial at the end of the project name. Without it, you loose the session.
        }
        result += path;    //   mypage?params
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
            if(HttpURLConnection.HTTP_OK != huc.getResponseCode()) {
            	return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    /** nameParam is something like "Java & OO - Fundamentals". The urlFragment is set to "Java_OO_Fundamentals" */ 
    public static String computeUrlFragmentFromName(String nameParam) {
    	nameParam.trim();
    	nameParam = nameParam.replaceAll("é", "e");
    	nameParam = nameParam.replaceAll("è", "e");
    	nameParam = nameParam.replaceAll("ê", "e");
    	nameParam = nameParam.replaceAll("ë", "e");
    	nameParam = nameParam.replaceAll("ï", "i");
    	nameParam = nameParam.replaceAll("î", "i");
    	nameParam = nameParam.replaceAll("à", "a");
    	nameParam = nameParam.replaceAll("ç", "c");
    	nameParam = nameParam.replaceAll("ù", "u");
        nameParam = nameParam.replaceAll("[^A-Za-z0-9]", "_"); // remove all non alphanumeric values (blanks, spaces,...).
    	nameParam = nameParam.replaceAll("___", "_"); //Sometimes, there is a '&' in the title. The name is compute like : Java___OO_etc. Whith this method, the name will be Java_OO_etc.
        return nameParam;
    }
}
