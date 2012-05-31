package blackbelt.service;

import blackbelt.model.User;

public class InvalidPasswordException extends Exception {

	public InvalidPasswordException(User user) {
		System.out.println("InvalidPasswordException "+user);
		printStackTrace();
	}

}
