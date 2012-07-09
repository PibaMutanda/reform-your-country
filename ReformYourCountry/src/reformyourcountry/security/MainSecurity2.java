package reformyourcountry.security;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import reformyourcountry.BatchCreate;
import reformyourcountry.exceptions.InvalidPasswordException;
import reformyourcountry.exceptions.UserAlreadyExistsException;
import reformyourcountry.exceptions.UserLockedException;
import reformyourcountry.exceptions.UserNotFoundException;
import reformyourcountry.exceptions.UserNotValidatedException;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;

public class MainSecurity2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        BatchSecurity batchsecurity = (BatchSecurity)applicationContext.getBean("batchSecurity");
        try {
            batchsecurity.run();
        } catch (UserNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidPasswordException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UserNotValidatedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UserLockedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (WaitDelayNotReachedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UserAlreadyExistsException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
