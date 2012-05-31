package blackbelt.service;

public class UserNotFoundException extends Exception {

	public UserNotFoundException(String identifier) {
		System.out.println("UserNotFoundException "+identifier);
		printStackTrace();
	}

}
