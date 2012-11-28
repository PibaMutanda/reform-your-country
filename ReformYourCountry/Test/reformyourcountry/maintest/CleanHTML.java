package reformyourcountry.maintest;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;


public class CleanHTML {

	public static void main(String[]args) {
		System.out.println(cleanHtml("bouh"));
	}
	
	public static String cleanHtml(String htmlToCLean) {
		String result = "if you see this something goes wrong with cleanHtml method";

		try {
			System.out.println(Thread.currentThread().getContextClassLoader().getResource("secret.properties").getFile());
			Policy policy = Policy.getInstance(Thread.currentThread().getContextClassLoader().getResource("secret.properties").getFile());
			AntiSamy as = new AntiSamy(); // Create AntiSamy object
			CleanResults cr = as.scan(htmlToCLean, policy, AntiSamy.SAX);// Scan dirtyInput
			result = cr.getCleanHTML();

		} catch (PolicyException | ScanException e) {
			e.printStackTrace();
		}

		return result;
    }
}
