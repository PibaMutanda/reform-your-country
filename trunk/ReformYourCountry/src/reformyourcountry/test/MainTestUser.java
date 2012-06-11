package reformyourcountry.test;

import java.util.Scanner;

import reformyourcountry.dao.UserDao;
import reformyourcountry.exceptions.UseAlreadyExistsException;
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
    private static UserDao UserDao = new UserDao();
    private static UserService userService = new UserService();

    /**
     * @param args
     */
    public static void main(String[] args) {

	try {
	    userService.registerUser(true, "maxime", "Sauvage", Gender.MALE,
		    "max", "max", "max@home.be");
	} catch (UseAlreadyExistsException e1) {
	    e1.printStackTrace();
	}
	try {
	    userService.registerUser(true, "piba", "mutunba", Gender.MALE,
		    "piba", "max", "piba@home.be");
	} catch (UseAlreadyExistsException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	consoleFormRegistration();
	try {
	    System.out.println("recherche par le dao"
		    + UserDao.getUserByUserName("max"));
	    System.out.println("recherche par le dao"
		    + UserDao.getUserByEmail("max@home.be"));
	    System.out.println("recherche par le dao"
		    + UserDao.getUserByEmail("piba@home.be"));
	    User loggedIn = testLogin("max", "max", false);
	    if (loggedIn != null)
		System.out.println("succesfull logged in as "
			+ loggedIn.getUserName());
	    User loggedIn2 = testLogin("piba@home.be", "max", false);
	    if (loggedIn2 != null)
		System.out.println("succesfull logged in as "
			+ loggedIn2.getUserName());
	} catch (Exception e) {
	    e.printStackTrace();
	}

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
	} catch (UseAlreadyExistsException e) {
	    e.printStackTrace();
	}

    }

    public static void consoleFromLogin() {
	String identifier, password;
	Scanner scan = new Scanner(System.in);
	
	System.out.println("welcome to the registration console\nplease enter your username or email\n");
	identifier = scan.next();
	System.out.println("please enter your password\n");
	password = scan.next();
	
	User loggedIn = testLogin(identifier, password, false);
	if (loggedIn != null)
	    System.out.println("succesfull logged in as "
		    + loggedIn.getUserName());
    }

    public static User testLogin(String identifier, String clearPassword,
	    boolean masterpassword) {
	LoginService login = new LoginService();
	String password = null;
	User user = null;
	
	if (masterpassword) {
	    password = "477bc098b8f2606137c290f9344dcee8";
	} else {
	    password = clearPassword;
	}
	
	try {
	    user= login.login(identifier, password, false);
	} catch (UserNotFoundException | InvalidPasswordException| UserNotValidatedException | UserLockedException| WaitDelayNotReachedException e) {
	    e.printStackTrace();
	}
	
	return user;
    }
}
