package blackbelt.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import blackbelt.exceptions.UserNotFoundException;
import blackbelt.model.User;

public class UserDao {

	private static List<User> listeUser = new ArrayList<User>();

	public static List<User> getListeUser() {
		return listeUser;
	}

	// TODO maxime delete static modifier when using JPA
	public static void save(User user) {
		System.out.println("dao je save " + user);
		listeUser.add(user);
		System.out.println(listeUser.size());
	}

	// TODO maxime delete static modifier when using JPA
	public static User get(Long id) throws UserNotFoundException {

		for (User user : listeUser) {
			if (id.equals(user.getId()))
				return user;
		}
		throw new UserNotFoundException(id.toString());
	}

	// use createTestUser() user in main instead
	@Deprecated
	public List<User> createListeUser() {

		User user = new User();
		user.setFirstName("Helios");
		user.setLastName("Toto");

		listeUser.add(user);

		return listeUser;
	}

	// TODO maxime delete static modifier when using JPA
	public static User getUserByEmail(String identifier) throws UserNotFoundException {
			for (User user : listeUser) {
				if (identifier.equals(user.getMail()))
					return user;
			}
			throw new UserNotFoundException(identifier);
	}

	// TODO maxime delete static modifier when using JPA
	public static User getUserByNickName(String identifier)
			throws UserNotFoundException {
		for (User user : listeUser) {
			if (identifier.equals(user.getNickName()))
				return user;
		}
		throw new UserNotFoundException(identifier,
				"le user avnt le nickname précédent n'a pu être trouvé");
	}

}
