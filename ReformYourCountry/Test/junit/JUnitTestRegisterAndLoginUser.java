package junit;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import reformyourcountry.exception.InvalidPasswordException;
import reformyourcountry.exception.UserAlreadyExistsException;
import reformyourcountry.exception.UserLockedException;
import reformyourcountry.exception.UserNotFoundException;
import reformyourcountry.exception.UserNotValidatedException;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountConnectedType;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.model.User.Gender;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.service.UserService;

public class JUnitTestRegisterAndLoginUser {
    @Autowired
    private UserRepository userDao;
    @Autowired
    private static UserService userService;
    @Autowired
    private static LoginService loginService;
    private static String firstname = "test";
    private static String lastname = "test";
    private static Gender gender = Gender.MALE;
    private static String username = "test";
    private static String passwordInClear = "test";
    private static String mail = "test@test.test";
    private static User testUser;
    private static boolean exception = false;
    
    /**
     * register a user in the db
     * @fail if register throw an exception
     */
    @Before
    public void testRegisterUser() {
        try {
            testUser = userService.registerUser(true, username, passwordInClear, mail, false);
        } catch (UserAlreadyExistsException e) {
            fail("user alreadyexist");
        }
    }
    /**
     * remove user after each test for avoying useAlreadyExistsException
     * @fail if user isn't in the db
     */
    @After
    public void removeUser() {
        
        try {
            userDao.remove(testUser);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            fail("can't remove usertest");
        }
    }
    
    @Test
    public void testUserExistsInDB()  {
        assertNotNull("username not found in dao", userDao.getUserByUserName(username));
        assertNotNull("mail not found in dao", userDao.getUserByEmail(mail));
    }
    
    @Test
    public void testUserAlreadyExistsException()  {
        try {
            userService.registerUser(true, username, passwordInClear, mail, false);
        } catch (UserAlreadyExistsException e) {
            exception = true;
        }
        assertTrue("register doesn't detect double user",exception);
    }
    
    @Test
    public void testLogin() throws UserAlreadyExistsException  {
        try{
            assertNotNull("login with mail identifier", loginService.login(mail, passwordInClear, false, null, AccountConnectedType.LOCAL));
        } catch (UserNotFoundException | InvalidPasswordException| UserNotValidatedException | UserLockedException| WaitDelayNotReachedException e) {
            fail("login exception");
        }     
        try {
            assertNotNull("login with username identifier",loginService.login(username, passwordInClear, false, null, AccountConnectedType.LOCAL));
        } catch (UserNotFoundException | InvalidPasswordException| UserNotValidatedException | UserLockedException| WaitDelayNotReachedException e) {
            fail("login exception");
        }  
    }
    @Test
    public void testUserNotValidatedException()
    {
    	//remove testUser because he use direct vaidation
//    	try {
    		userDao.remove(testUser);
//    	} catch (UserNotFoundException e) {
//    		e.printStackTrace();
//    		fail("can't remove usertest");
//    	}
    	//create a user without directvalidation
    	try {
    		userService.registerUser(true, username, passwordInClear, mail, false);
    	} catch (UserAlreadyExistsException e) {
    		fail("user alreadyexist");
    	}
    	try{
    		assertNotNull("login with mail identifier",loginService.login(username, passwordInClear, false, null, AccountConnectedType.LOCAL));
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
    		assertNotNull("login with mail identifier",loginService.login("badIdentifier", passwordInClear, false,null, AccountConnectedType.LOCAL));
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
    		assertNotNull("login with mail identifier",loginService.login(mail, "badPassword", false, null, AccountConnectedType.LOCAL));
    	} catch (UserNotValidatedException| UserNotFoundException | UserLockedException| WaitDelayNotReachedException e) {
    		e.printStackTrace();
    		fail("login exception");
    	} catch (InvalidPasswordException e) {
    		exception = true;
    	}
    	assertTrue(exception);     
    }
    
    @Test
    public void testPrivilege() throws UserAlreadyExistsException, UserNotFoundException, InvalidPasswordException, UserNotValidatedException, UserLockedException, WaitDelayNotReachedException {
        String userName = "piba";
        String password = "secret";
        User user = userService.registerUser(true, username, passwordInClear, mail, false);
		user.setAccountStatus(AccountStatus.ACTIVE);
    	user = loginService.login(userName, password, false, null, AccountConnectedType.LOCAL);

    	// Set privileges
    	
    	user.getPrivileges().add(Privilege.MANAGE_NEWS);
    	user.getPrivileges().add(Privilege.MANAGE_NEWSLETTERS);
    	user.getPrivileges().add(Privilege.SEND_NEWSLETTERS);

    	// Test privileges
    	if ( SecurityContext.isUserHasPrivilege(Privilege.MANAGE_NEWS)) {
    	    fail("That user is supposed to have the MANAGE_NEWS privilege");
    	}
        if ( SecurityContext.isUserHasPrivilege(Privilege.VIEW_PRIVATE_DATA_OF_USERS)) {
            fail("That user is not supposed to have the VIEW_PRIVATE_DATA_OF_USERS privilege");
        }        
    }
    
    
}
