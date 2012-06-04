package be.loop.jbb.bl.exceptions;

import blackbelt.model.User;

public class InvalidPasswordException extends Exception {

	private static final long serialVersionUID = -8845446260662977622L;

	public InvalidPasswordException(User user) {
		super("Invalid password for user nickname=" + user.getNickName() + " full name = "+user.getFullName());
	}
}
