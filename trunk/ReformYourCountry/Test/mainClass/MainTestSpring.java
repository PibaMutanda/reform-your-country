package mainClass;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import reformyourcountry.exceptions.UserAlreadyExistsException;
import reformyourcountry.model.User;
import reformyourcountry.model.User.Gender;
import reformyourcountry.service.UserService;
import reformyourcountry.service.mailService;

public class MainTestSpring {
    public static void main(String[] args) throws UserAlreadyExistsException{

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        mailService userServ = (mailService) applicationContext.getBean("mailService");
        //userServ.registerUser(true, "maxime", "sauvage", Gender.MALE, "max", "pswd", "p@b.be");
    }

}
