package reformyourcountry.util;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import reformyourcountry.model.User;

public class HTMLUtil {
	

	private static Whitelist whiteList = Whitelist.basic();
	

    
	/**
     * Remove as much HTML as possible.
     * */
    public static String removeHmlTags(String textToFormat) {
        String result = textToFormat.replaceAll("\\<(.|\n)*?>", "");
        return result;
    }
    
 

	public static boolean isHtmlSecure(String htmlToCheck) {
    	ArrayList<?> error = null;
    	//sometimes controller send null parameter or empty so it isn't unsecure...
    	if ( htmlToCheck == null || htmlToCheck.isEmpty() ) {
    		return true;
    	} 
    	return Jsoup.isValid(htmlToCheck, whiteList);
    	
    }


	
	
	public static String getUserPageLink(User user){
	    
	    return "<a href='/user/"+user.getUserName()+"'>"+user.getFullName()+"</a>";
	}
}
