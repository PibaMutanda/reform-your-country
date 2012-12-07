package reformyourcountry.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import reformyourcountry.model.User;

public class HTMLUtil {
	

	private static Whitelist whiteList = Whitelist.basic().addTags("span").addAttributes(":all", "style");
	

    
	/**
     * Remove as much HTML as possible.
     * */
    public static String removeHmlTags(String textToFormat) {
        String result = textToFormat.replaceAll("\\<(.|\n)*?>", "");
        return result;
    }
    
 

	public static boolean isHtmlSecure(String htmlToCheck) {
    	//sometimes controller send null parameter or empty so it isn't unsecure...
    	if ( htmlToCheck == null || htmlToCheck.isEmpty() ) {
    		return true;
    	} 
    	return Jsoup.isValid(htmlToCheck, whiteList);
    	
    }
	
	/**
	 * check if the entry contains character who can cause buggy html (< , > , " ,  ')
	 * @return a String containing forbidden char or null
	 */
	public static String getContainedForbiddenHtmlCharacter(String htmlToCheck) {
        String result = new String();
        String[] forbiddenChar = { "<" , ">" , "\"" ,  "'" };
        
        for( String ch : forbiddenChar ) {
            if( htmlToCheck.contains(ch) ) {
                result += ch;
            }
        }
	    
	    return result.isEmpty()?null:result;
	}


	
	
	public static String getUserPageLink(User user){
	    
	    return "<a href='/user/"+user.getUserName()+"'>"+user.getFullName()+"</a>";
	}
}
