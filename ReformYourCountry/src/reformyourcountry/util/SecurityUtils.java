package reformyourcountry.util;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;

import reformyourcountry.model.User;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

/**
 * @author Aymeric Levaux
 */
public class SecurityUtils {

	public static String generateRandomPassword(int minLenght, int maxLenght) {
		StringBuffer password = new StringBuffer(maxLenght);

		for (int i = 0; i < (minLenght + Math.round(Math.random()
				* (maxLenght - minLenght))); i++) {
			int group = (int) Math.round(Math.random() * 3);
			int toAppend;
			switch (group) {
			// For char from 0 to 9
			case 0: {
				toAppend = (int) (48 + Math.round(Math.random() * 9));
				password.append((char) toAppend);
				break;
			}

				// for char from A to Z

			case 1: {
				toAppend = (int) (65 + Math.round(Math.random() * 25));
				password.append((char) toAppend);
				break;
			}

				// for char from a to z
			case 2: // We mant more normal char
			case 3: {
				toAppend = (int) (97 + Math.round(Math.random() * 25));
				password.append((char) toAppend);
				break;
			}
			}
		}
		return password.toString();
	}

	/**
	 * Digest password using md5 algorithm and convert the result to a
	 * corresponding hexa string. If exception occurs, null is returned.
	 * 
	 * @param Password
	 *            to encrypt.
	 * @return The md5 encrypted password.
	 */
	public static String SHA1Encode(String credentials) {
		return encode(credentials, "SHA");
	}

	/**
	 * Digest password using md5 algorithm and convert the result to a
	 * corresponding hexa string. If exception occurs, null is returned.
	 * 
	 * @param Password
	 *            to encrypt.
	 * @return The md5 encrypted password.
	 */
	public static String md5Encode(String credentials) {
		return encode(credentials, "md5");
	}

	private static String encode(String credentials, String mode) {

		try {
			// Obtain a new message digest with md5 encryption
			MessageDigest md = (MessageDigest) MessageDigest.getInstance(mode)
					.clone();
			// encode the password
			md.update(credentials.getBytes());
			// Digest the credentials and return as hexadecimal
			return new String(Hex.encodeHex(md.digest()));
		} catch (Exception ex) {
			throw new RuntimeException("Could not encode credentials", ex);
		}
	}
	
	 


}
