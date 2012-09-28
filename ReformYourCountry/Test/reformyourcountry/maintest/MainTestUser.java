package reformyourcountry.maintest;

import java.util.Scanner;

import reformyourcountry.exception.InvalidPasswordException;
import reformyourcountry.exception.UserAlreadyExistsException;
import reformyourcountry.exception.UserLockedException;
import reformyourcountry.exception.UserNotFoundException;
import reformyourcountry.exception.UserNotValidatedException;
import reformyourcountry.model.User;
import reformyourcountry.model.User.AccountStatus;
import reformyourcountry.model.User.Gender;
import reformyourcountry.regex.EmailValidator;
import reformyourcountry.repository.UserRepository;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;
import reformyourcountry.service.UserService;

public class MainTestUser {
    private static Integer TRYAGECOUNT = 5;
    private static UserService userService = UserService.getInstance();
    private static LoginService loginService = LoginService.getInstance();
    private static Scanner scanKeyBoard = new Scanner(System.in);
    private static UserRepository userDao = UserRepository.getInstance();
    private static EmailValidator emailValidator = new EmailValidator();
    /**
     * @param args
     */
    public static void main(String[] args) {
        String enter = "0";
        String genId="0";
        do {
            System.out.println("1.register a user\n2.validate a user\n3.login\n4.quit");

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
        String firstname, name, username, password, mail = null;
        Gender gender = null;
        String genId;
        int genderId;

        System.out.println("welcome to the registration console\n we gonna register you\nplease enter your firstname");
        firstname = scanKeyBoard.next();

        System.out.println("please enter your lastname");
        name = scanKeyBoard.next();

        System.out.println("please enter your username");
        username = scanKeyBoard.next();
        do {

            System.out.println("please select your gender\n1."+ Gender.MALE.toString() 
                    + "\n2." + Gender.FEMALE.toString()
                    + "\n\nplease select a gender : ");
            genId=scanKeyBoard.next();

            try {
                Integer.parseInt(genId);
            } catch (NumberFormatException e) {
                System.out.println("Don't choose a letter ");
                genId="0";
            }
        } while (Integer.parseInt(genId)!=1 && Integer.parseInt(genId)!=2);


        System.out.println("please enter your password");
        password = scanKeyBoard.next();
        System.out.println("confirm your password");
        for(int i = 0 ; i<= TRYAGECOUNT;i++)
        {
            if(i == TRYAGECOUNT)
            {
                System.out.println("TRYAGECOUNT REACHED PLEASE TRY LATER!!!");
                password = null;
            }
            if (!password.equals(scanKeyBoard.next()))
                System.out.println("The same password please! ");
            else
                break;
        }
        if (password != null) {
            for(int i = 0 ; i<= TRYAGECOUNT;i++)
            {
                System.out.println("please enter your email\n");
                mail = scanKeyBoard.next();
                if (emailValidator.validate(mail)) {
                    break;
                } else {
                    System.out.println("\npleaze enter a valid email(*@*.XX)\n");
                }
            }
            try {

                userService.registerUser(false, firstname, name, gender,username, password, mail);
                System.out.println("\n***successfull user registred***\n");

            } catch (UserAlreadyExistsException e) {
                System.out.println("USER ALREADY EXISTS!!! \n");
            }
        }
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
            if(result.getAccountStatus().equals(AccountStatus.ACTIVE))
            {
                System.out.println("\nUSER ALREADY VALIDATED !!!\n");
            }
            else
            {
                result.setAccountStatus(AccountStatus.ACTIVE);
                System.out.println("\n***successfull user validated***\n");
            }
        } else {
            System.out.println("user not found");
        }
    }


}
