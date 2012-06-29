package mainClass;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import reformyourcountry.exceptions.UserAlreadyExistsException;
import reformyourcountry.model.User;
import reformyourcountry.model.User.Gender;
import reformyourcountry.service.UserService;

public class MainTestSpring {
    public static void main(String[] args) throws UserAlreadyExistsException{

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userServ = (UserService) applicationContext.getBean("userService");
        userServ.registerUser(true, "maxime", "sauvage", Gender.MALE, "max", "pswd", "p@b.be");
    }

}
