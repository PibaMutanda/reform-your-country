import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import blackbelt.dao.UserDao;
import blackbelt.exceptions.UserNotFoundException;
import blackbelt.impl.UserServiceImpl;
import blackbelt.model.User;
import blackbelt.model.User.Gender;
import blackbelt.service.UserService;
import blackbelt.util.SecurityUtils;

public class MainTestUser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		UserServiceImpl.registerUser(true, "maxime", "Sauvage", Gender.MR,
				"max", "max", "max@home.be");
		UserServiceImpl.registerUser(true, "piba", "mutunba", Gender.MR, "max",
				"max", "piba@home.be");

		try {
			System.out.println("recherche par le dao"
					+ UserDao.getUserByNickName("max"));
			System.out.println("recherche par le dao"
					+ UserDao.getUserByEmail("max@home.be"));
			System.out.println("recherche par le dao"
					+ UserDao.getUserByEmail("piba@home.be"));
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	 public static void consoleFormRegistration() {
	
	 }

	 public static boolean testLogin() {
	 return false;
	
	 }
}
