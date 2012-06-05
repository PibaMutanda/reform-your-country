package test;
import java.util.Scanner;

import blackbelt.dao.UserDao;
import blackbelt.impl.UserServiceImpl;
import blackbelt.model.User;
import blackbelt.model.User.Gender;
import blackbelt.service.LoginService;

public class MainTestUser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		UserServiceImpl.registerUser(true, "maxime", "Sauvage", Gender.MR,
				"max", "max", "max@home.be");
		UserServiceImpl.registerUser(true, "piba", "mutunba", Gender.MR,
				"piba", "max", "piba@home.be");
		consoleFormRegistration();
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
		Scanner scan = new Scanner(System.in);
		String firstname, name, nickname, password, mail;
		Gender gender = null;
		System.out
				.println("welcome to the registration console\n we gonna register you\nplease enter your firstname");
		firstname = scan.next();
		System.out.println("please enter your lastname");
		name = scan.next();
		System.out.println("please enter your nickname");
		nickname = scan.next();
		System.out.println("please select your gender\n1."
				+ Gender.MR.toString() + "\n2." + Gender.MRS.toString()
				+ "\n3." + Gender.MSS.toString()
				+ "\n\nplease select a gender : ");
		int genderId = scan.nextInt();
		switch (genderId) {
		case 1:
			gender = Gender.MR;
			break;
		case 2:
			gender = Gender.MRS;
			break;
		case 3:
			gender = Gender.MSS;
			break;
		default:
			break;
		}
		System.out.println("please enter your password");
		password = scan.next();
		System.out.println("please enter your email");
		mail = scan.next();
		UserServiceImpl.registerUser(true, firstname, name, gender, nickname,
				password, mail);

	}

	public static void consoleFromLogin() throws Exception {
		String identifier, password;
		Scanner scan = new Scanner(System.in);
		System.out
				.println("welcome to the registration console\nplease enter your nickname or email\n");
		identifier = scan.next();
		System.out.println("please enter your password\n");
		password = scan.next();
		User loggedIn = testLogin(identifier, password);
		if (loggedIn != null)
			System.out.println("succesfull logged in as "
					+ loggedIn.getNickName());
	}

	public static User testLogin(String identifier, String clearPassword)
			throws Exception {
		LoginService login = new LoginService();
		return login.login(identifier, clearPassword, false);
		// return login.loginEncrypted(identifier,
		// "477bc098b8f2606137c290f9344dcee8", false);
	}
}
