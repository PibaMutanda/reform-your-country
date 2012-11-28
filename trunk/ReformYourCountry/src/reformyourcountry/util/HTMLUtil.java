package reformyourcountry.util;

import java.util.ArrayList;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

public class HTMLUtil {
	
	private static String ANTISAMY_XML_FILE_LOCATION = Thread.currentThread().getContextClassLoader().getResource("antisamy-tinymce-1.4.4.xml").getFile();
	private static Policy antiSamyPolicyInstance = null;
	private static AntiSamy antiSamyInstance = null;
    
	/**
     * Remove as much HTML as possible.
     * */
    public static String removeHmlTags(String textToFormat) {
        String result = textToFormat.replaceAll("\\<(.|\n)*?>", "");
        return result;
    }
    
    public static String cleanHtml(String htmlToCLean) {
		String result = "if you see this something goes wrong with cleanHtml method";

		try {
			Policy policy = Policy.getInstance(ANTISAMY_XML_FILE_LOCATION);
			AntiSamy as = new AntiSamy(); // Create AntiSamy object
			CleanResults cr = as.scan(htmlToCLean, policy, AntiSamy.SAX);// Scan dirtyInput
			result = cr.getCleanHTML();

		} catch (PolicyException | ScanException e) {
			throw new RuntimeException(e);
		}

		return result;
    }

    public static boolean isHtmlSecure(String htmlToCheck) {
    	ArrayList<?> error = null;

    	try {
    		error = getAntiSamyInstance().scan(htmlToCheck, antiSamyPolicyInstance, AntiSamy.SAX).getErrorMessages();
    		System.out.println(error.toString());
    	} catch (ScanException | PolicyException e) {
    		return false;
    	}
		return error.isEmpty();
    	
    }

    private synchronized static AntiSamy getAntiSamyInstance() {
    	if(antiSamyInstance == null){
    		try {
    			if(antiSamyPolicyInstance == null){
    				antiSamyPolicyInstance = Policy.getInstance(ANTISAMY_XML_FILE_LOCATION);
    			} 
    		} catch (PolicyException e) {
    			throw new RuntimeException("cannot get antiSamy policy instance");
    		}
    		antiSamyInstance = new AntiSamy(); // Create AntiSamy object
    	}
    	return antiSamyInstance;
    }
}
