package blackbelt.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import blackbelt.model.User;

public class UserDao {

	private static List<User> listeUser = new ArrayList<>();

	// TODO maxime delete static modifier when using JPA
	public static void save(User user) {
		listeUser.add(user);
	}	
	
	// TODO maxime delete static modifier when using JPA
	public static User get(Long id) {
		User user = new User();

		Iterator it = listeUser.iterator();

		while (it.hasNext()) {
			user = (User) it.next();
			return id == user.getId() ? user : null;
		}

		return null;
	}

	public List<User> createListeUser() {

		User user = new User();
		user.setFirstName("Helios");
		user.setLastName("Toto");

		listeUser.add(user);

		return listeUser;
	}
	// TODO maxime delete static modifier when using JPA
	public static User getUserByEmail(String identifier) {
		for (User user : listeUser) {

			return identifier.equals(user.getMail()) ? user : null;
		}
		return null;
	}

	// TODO maxime delete static modifier when using JPA
	public static User getUserByNickName(String identifier) {
		for (User user : listeUser) {
			return identifier.equals(user.getNickName()) ? user : null;
		}

		return null;
	}

}
