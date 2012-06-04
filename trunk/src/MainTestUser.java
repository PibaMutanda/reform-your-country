import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import blackbelt.dao.UserDao;
import blackbelt.exceptions.InvalidPasswordException;
import blackbelt.exceptions.UserLockedException;
import blackbelt.exceptions.UserNotFoundException;
import blackbelt.exceptions.UserNotValidatedException;
import blackbelt.impl.UserServiceImpl;
import blackbelt.model.User;
import blackbelt.model.User.Gender;
import blackbelt.service.LoginService;
import blackbelt.service.LoginService.WaitDelayNotReachedException;
import blackbelt.service.UserService;
import blackbelt.util.SecurityUtils;

public class MainTestUser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		UserServiceImpl.registerUser(true, "maxime", "Sauvage", Gender.MR,
				"max", "max", "max@home.be");
		UserServiceImpl.registerUser(true, "piba", "mutunba", Gender.MR,
				"piba", "max", "piba@home.be");

		try {
			System.out.println("recherche par le dao"
					+ UserDao.getUserByNickName("max"));
			System.out.println("recherche par le dao"
					+ UserDao.getUserByEmail("max@home.be"));
			System.out.println("recherche par le dao"
					+ UserDao.getUserByEmail("piba@home.be"));
			User loggedIn = testLogin("max", "max");
			if (loggedIn != null)
				System.out.println("succesfull logged in as "
						+ loggedIn.getNickName());
			User loggedIn2 = testLogin("piba@home.be", "max");
			if (loggedIn2 != null)
				System.out.println("succesfull logged in as "
						+ loggedIn2.getNickName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void consoleFormRegistration() {

	}

	public static User testLogin(String identifier, String clearPassword)
			throws Exception {
		LoginService login = new LoginService();
		return login.login(identifier, clearPassword, false);
		// return login.loginEncrypted(identifier,
		// "477bc098b8f2606137c290f9344dcee8", false);
	}
}
