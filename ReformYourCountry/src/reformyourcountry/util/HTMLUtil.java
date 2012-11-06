package reformyourcountry.util;


public class HTMLUtil {
    
	/**
     * Remove as much HTML as possible.
     * */
    public static String removeHmlTags(String textToFormat) {
        String result = textToFormat.replaceAll("\\<(.|\n)*?>", "");
        return result;
    }

}
