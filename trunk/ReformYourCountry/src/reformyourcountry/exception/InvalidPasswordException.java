package reformyourcountry.exception;

import reformyourcountry.model.User;

public class InvalidPasswordException extends Exception {

	private static final long serialVersionUID = -8845446260662977622L;
    private User user;
    
	public InvalidPasswordException(User user) {
		super("Invalid password for user Username=" + user.getUserName()+ " full name = "+user.getFullName());
		this.user=user;
	}
	
}
