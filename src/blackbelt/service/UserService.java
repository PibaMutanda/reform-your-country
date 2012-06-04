package blackbelt.service;

import java.util.List;

import blackbelt.exceptions.UserNotFoundException;
import blackbelt.model.User;

public interface UserService {

	String getUserSecurityString(User user);

	void changeUserName(User user, String newFirstName, String newLastName);

	List<String> assertNicknamesExists(List<String> nicknames,
			boolean resultSorted) throws UserNotFoundException;

	String ensureNicknameUniqueness(String nickNameToTest) throws UserNotFoundException;

}
