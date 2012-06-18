package reformyourcountry.security;

import java.util.Scanner;

import reformyourcountry.exceptions.InvalidPasswordException;
import reformyourcountry.exceptions.UserLockedException;
import reformyourcountry.exceptions.UserNotFoundException;
import reformyourcountry.exceptions.UserNotValidatedException;
import reformyourcountry.model.User;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;


public class MainSecurity {

	
	public static void main(String[] args) {
	    
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
        } catch (UserLockedException
                | WaitDelayNotReachedException e) {
            System.out.println("YOU BREAK MY SOFTWARE!!!!!!!");
        } catch (UserNotFoundException e) {
            System.out.println("user not found");
        } catch (UserNotValidatedException e) {
            System.out.println("please validate your account");
        } catch (InvalidPasswordException e) {
            System.out.println("incorrect password");
        }

        return user;
    }
	
	private static LoginService loginService=new LoginService();
    private static Scanner scanKeyBoard = new Scanner(System.in);

}	
