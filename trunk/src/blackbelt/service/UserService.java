package blackbelt.service;

import java.util.List;

import blackbelt.model.User;

public interface UserService {

	String getUserSecurityString(User user);

	void changeUserName(User user, String newFirstName, String newLastName);

	List<String> assertNicknamesExists(List<String> nicknames,
			boolean resultSorted);

	String ensureNicknameUniqueness(String nickNameToTest);

}
