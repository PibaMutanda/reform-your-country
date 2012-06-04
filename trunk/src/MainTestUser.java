import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import blackbelt.dao.UserDao;
import blackbelt.impl.UserServiceImpl;
import blackbelt.model.User;
import blackbelt.model.User.Gender;
import blackbelt.service.UserService;

public class MainTestUser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		User test = createTestUser("maxime", "Sauvage", Gender.MR, "max", "max");
		UserServiceImpl service = new UserServiceImpl();
		service.registerUser(test, true);
		try {
			service.deleteUser(test, true, true);
		} catch (UnsupportedOperationException e) {
			System.out
					.println("on ne peut pas encore supprimer de user , c'est balot...");
		}
		System.out.println("recherche par le dao"
				+ UserDao.getUserByNickName("max"));

		UserDao userdao = new UserDao();
		ArrayList myListe = (ArrayList<User>) userdao.createListeUser();
		Iterator it = myListe.iterator();
		while (it.hasNext()) {
			User user = (User) it.next();
			System.out.println(user.toString() + "\n");
		}

	}

	public static User createTestUser(String firstName, String lastName,
			Gender gender, String nickname, String password) {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setNickName(nickname);
		user.setPassword(password);
		return user;
	}

	public static void consoleFormRegistration() {

	}
}
