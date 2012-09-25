package reformyourcountry.exception;

/** We don't use MalformedURLException because it's a checked exception.
 * When intercepted by the error mechanism at the web filter level, it can show something nicer than a stacktrace and maybe meaningful for the user. */
public class InvalidUrlException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	String messageToUser;
	public InvalidUrlException(String messageToUser) {
		super(messageToUser);
		this.messageToUser = messageToUser;
	}
	public InvalidUrlException(String messageToUser, Exception e) {
		super(messageToUser,e);
		this.messageToUser = messageToUser;
	}
	public String getMessageToUser() {
		return messageToUser;
	}
	
}
