package reformyourcountry.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import reformyourcountry.dao.UserDao;
import reformyourcountry.exceptions.UserAlreadyExistsException;
import reformyourcountry.model.User.Gender;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.service.UserService;
import blackbelt.exceptions.InvalidPasswordException;
import blackbelt.exceptions.UserLockedException;
import blackbelt.exceptions.UserNotFoundException;
import blackbelt.exceptions.UserNotValidatedException;

public class JUnitTestRegisterAndLoginUser {

    private static UserDao UserDao = new UserDao();
    private static UserService userService = new UserService();
    private static LoginService loginService = new LoginService();
    private static String firstname = "test";
    private static String lastname = "test";
    private static Gender gender = Gender.MALE;
    private static String username = "test";
    private static String passwordInClear = "test";
    private static String mail = "test@test.test";
    private static boolean exception = false;
    /**
     * register a user in the db
     * @fail if register throw an exception
     */
    @Before
    public void testRegisterUser()
    {
	try {
	    userService.registerUser(true, firstname, lastname, gender, username, passwordInClear, mail);
	} catch (Exception e) {
	    fail("user alreadyexist");
	}
    }
    /**
     * remove user after each test for avoying useAlreadyExistsException
     * @fail if user isn't in the db
     */
    @After
    public void removeUser()
    {
	try {
	    UserDao.remove(username);
	} catch (UserNotFoundException e) {
	    e.printStackTrace();
	    fail("can't remove usertest");
	}
    }
    @Test
    public void testUserExistsInDB()  {
	assertNotNull("username not found in dao", UserDao.getUserByUserName(username));
	assertNotNull("mail not found in dao", UserDao.getUserByEmail(mail));
    }
    @Test
    public void testUserAlreadyExistsException()  {
	try {
	    userService.registerUser(true, firstname, lastname, gender, username, passwordInClear, mail);
	} catch (UserAlreadyExistsException e) {
	    exception = true;
	}
	assertTrue("register doesn't detect double user",exception);
    }
    @Test
    public void testLogin() throws UserAlreadyExistsException
    {
	try{
	    assertNotNull("login with mail identifier",loginService.login(mail, passwordInClear, false));
	} catch (UserNotFoundException | InvalidPasswordException| UserNotValidatedException | UserLockedException| WaitDelayNotReachedException e) {
	    e.printStackTrace();
	    fail("login exception");
	}     
	try {
	    assertNotNull("login with username identifier",loginService.login(username, passwordInClear, false));
	} catch (UserNotFoundException | InvalidPasswordException| UserNotValidatedException | UserLockedException| WaitDelayNotReachedException e) {
	    e.printStackTrace();
	    fail("login exception");
	}  
    }
    @Test
    public void testUserNotValidatedException()
    {
	//remove testUser because he use direct vaidation
	try {
	    UserDao.remove(username);
	} catch (UserNotFoundException e) {
	    e.printStackTrace();
	    fail("can't remove usertest");
	}
	//create a user without directvalidation
	try {
	    userService.registerUser(false, firstname, lastname, gender, username, passwordInClear, mail);
	} catch (UserAlreadyExistsException e) {
	    fail("user alreadyexist");
	}
	try{
	    assertNotNull("login with mail identifier",loginService.login(mail, passwordInClear, false));
	} catch (InvalidPasswordException| UserNotFoundException | UserLockedException| WaitDelayNotReachedException e) {
	    e.printStackTrace();
	    fail("login exception");
	} catch (UserNotValidatedException e) {
	    exception = true;
	}
	assertTrue(exception);     
    }
    @Test
    public void testUserNotFoundException()
    {
	try{
	    assertNotNull("login with mail identifier",loginService.login("badIdentifier", passwordInClear, false));
	} catch (InvalidPasswordException| UserNotValidatedException | UserLockedException| WaitDelayNotReachedException e) {
	    e.printStackTrace();
	    fail("login exception");
	} catch (UserNotFoundException e) {
	    exception = true;
	}
	assertTrue(exception);     
    }
    @Test
    public void testInvalidPasswordException()
    {
	try{
	    assertNotNull("login with mail identifier",loginService.login(mail, "badPassword", false));
	} catch (UserNotValidatedException| UserNotFoundException | UserLockedException| WaitDelayNotReachedException e) {
	    e.printStackTrace();
	    fail("login exception");
	} catch (InvalidPasswordException e) {
	    exception = true;
	}
	assertTrue(exception);     
    }
}
