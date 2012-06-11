package reformyourcountry.test;

import java.util.Scanner;

import reformyourcountry.exceptions.UserAlreadyExistsException;
import reformyourcountry.model.User;
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


    /**
     * @param args
     */
    public static void main(String[] args) {
	consoleFormRegistration();
	consoleFormLogin();
    }

    public static void consoleFormRegistration() {
	Scanner scan = new Scanner(System.in);
	String firstname, name, username, password, mail,password2;
	Gender gender = null;
	int genderId;

	System.out.println("welcome to the registration console\n we gonna register you\nplease enter your firstname");
	firstname = scan.next();

	System.out.println("please enter your lastname");
	name = scan.next();

	System.out.println("please enter your username");
	username = scan.next();

	System.out.println("please select your gender\n1."+ Gender.MALE.toString() 
		+ "\n2." + Gender.FEMALE.toString()
		+ "\n\nplease select a gender : ");
	genderId = scan.nextInt();
	do {

	    if(genderId!=1 || genderId!=2)
	    {
		System.out.println("please select your gender\n1."+ Gender.MALE.toString() 
			+ "\n2." + Gender.FEMALE.toString()
			+ "\n\nplease select a gender : "); 
		genderId=scan.nextInt();
	    }

	} while (genderId!=1 && genderId!=2  );	

	switch (genderId) {
	case 1:
	    gender = Gender.MALE;
	    break;
	case 2:
	    gender = Gender.FEMALE;
	    break;
	}



	do {
	    System.out.println("please enter your password");
	    password = scan.next();
	    System.out.println("confirm your passeword");
	    password2=scan.next();
	    if(!password.equalsIgnoreCase(password2))
		System.out.println("The same password please! ");

	} while (!password.equalsIgnoreCase(password2));


	System.out.println("please enter your email");
	mail = scan.next();

	try {
	    userService.registerUser(true, firstname, name, gender, username,password, mail);
	} catch (UserAlreadyExistsException e) {
	    e.printStackTrace();
	}

    }

    public static void consoleFormLogin() {
	String identifier, password;
	Scanner scan = new Scanner(System.in);

	System.out.println("welcome to the registration console\nplease enter your username or email\n");
	identifier = scan.next();
	System.out.println("please enter your password\n");
	password = scan.next();

	User loggedIn = testLogin(identifier, password, false);
	if (loggedIn != null)
	    System.out.println("succesfull logged in as "  + loggedIn.getUserName());
    }

    public static User testLogin(String identifier, String clearPassword,
	    boolean masterpassword) {
	String password = null;
	User user = null;

	if (masterpassword) {
	    password = "477bc098b8f2606137c290f9344dcee8";
	} else {
	    password = clearPassword;
	}

	try {
	    user= loginService.login(identifier, password, false);
	} catch (UserNotFoundException | InvalidPasswordException| UserNotValidatedException | UserLockedException| WaitDelayNotReachedException e) {
	    e.printStackTrace();
	}

	return user;
    }
}
