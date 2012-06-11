package reformyourcountry.test;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import reformyourcountry.dao.UserDao;
import reformyourcountry.exceptions.UserAlreadyExistsException;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.model.User.Gender;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.service.UserService;
import blackbelt.exceptions.InvalidPasswordException;
import blackbelt.exceptions.UserLockedException;
import blackbelt.exceptions.UserNotFoundException;
import blackbelt.exceptions.UserNotValidatedException;

public class MainTestUser {
    private static UserService userService = new UserService();
    private static LoginService loginService = new LoginService();
    private static Scanner scanKeyBoard = new Scanner(System.in);
    private static UserDao userDao = new UserDao();
    private static User user=null;

    /**
     * @param args
     */
    public static void main(String[] args) {
	String enter = "0";
	do {
	    System.out
	    .println("1.register a user\n2.validate a user\n3.login\n4.quit");

	    enter = scanKeyBoard.next();
	    try {
		Integer.parseInt(enter);
	    } catch (NumberFormatException e) {
		System.out.println("enter a number!");
		enter = "0";
	    }
	    switch (Integer.parseInt(enter)) {
	    case 1:
		consoleFormRegistration();
		break;
	    case 2:
		validateUser();
		break;
	    case 3:
		consoleFormLogin();
		break;
	    default:
		System.out.println("***quit***");
		System.exit(0);
		break;
	    }
	} while (Integer.parseInt(enter) != 4);

    }

    public static void consoleFormRegistration() {
	String firstname, name, username, password, mail, password2;
	Gender gender = null;
	String genId;
	Pattern p = Pattern
		.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$");
	int genderId;

	System.out
	.println("welcome to the registration console\n we gonna register you\nplease enter your firstname");
	firstname = scanKeyBoard.next();

	System.out.println("please enter your lastname");
	name = scanKeyBoard.next();

	System.out.println("please enter your username");
	username = scanKeyBoard.next();

	System.out.println("please select your gender\n1."
		+ Gender.MALE.toString() + "\n2." + Gender.FEMALE.toString()
		+ "\n\nplease select a gender : ");

	genderId = scanKeyBoard.nextInt();
	do {
	    System.out.println("please enter your password");
	    password = scanKeyBoard.next();
	    System.out.println("confirm your password");
	    password2 = scanKeyBoard.next();
	    if (!password.equalsIgnoreCase(password2))
		System.out.println("The same password please! ");

	} while (!password.equalsIgnoreCase(password2));

	System.out.println("please enter your email");

	try {
	    mail = scanKeyBoard.next();

	    user = userService.registerUser(false, firstname, name, gender,
		    username, password, mail);
	} catch (UserAlreadyExistsException e) {
	    e.printStackTrace();
	}
	    System.out.println("\n***successfull user registred***\n");

    }

    public static void consoleFormLogin() {
	String identifier, password;
	System.out.println("welcome to the registration console\nplease enter your username or email\n");
	identifier = scanKeyBoard.next();
	System.out.println("please enter your password\n");
	password = scanKeyBoard.next();

	User loggedIn = login(identifier, password, false);
	if (loggedIn != null)
	    System.out.println("\n***succesfull logged in as "+ loggedIn.getUserName()+"***\n");
    }

    public static User login(String identifier, String clearPassword,
	    boolean masterpassword) {
	String password = null;
	User user = null;

	if (masterpassword) {
	    password = "477bc098b8f2606137c290f9344dcee8";
	} else {
	    password = clearPassword;
	}

	try {
	    user = loginService.login(identifier, password, false);
	} catch (InvalidPasswordException | UserLockedException
		| WaitDelayNotReachedException e) {
	    System.out.println("YOU BREAK MY SOFTWARE!!!!!!!");
	} catch (UserNotFoundException e) {
	    System.out.println("user not found");
	} catch (UserNotValidatedException e) {
	    System.out.println("please validate your account");
	}

	return user;
    }
    public static void validateUser()
    {
	System.out.println("enter your username or email \n");
	
	User result;
	String identifier = null;
	
	identifier = scanKeyBoard.next();
	identifier = identifier.toLowerCase();
	result = userDao.getUserByEmail(identifier);
	
	if (result == null) {
	    result = userDao.getUserByUserName(identifier);
	}
	if (result != null) {
	    result.setAccountStatus(AccountStatus.ACTIVE);
	    System.out.println("\n***successfull user validated***\n");
	} else {
	    System.out.println("user not found");
	}
    }


}
