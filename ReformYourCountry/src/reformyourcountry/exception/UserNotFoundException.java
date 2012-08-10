package reformyourcountry.exception;

/**
 * @author Lionel Bartel
 *
 */
public class UserNotFoundException extends Exception {
	
	private String userName;

	private static final long serialVersionUID = -3223912152680791798L;

	public UserNotFoundException(String userName) {
		super();
		this.userName = userName;
	}
	
	public UserNotFoundException(String userName, String message) {
		super(message);
		this.userName = userName;
	}

	/**
	 * @return Returns the userName.
	 */
	public String getUserName() {
		return userName;
	}

}
