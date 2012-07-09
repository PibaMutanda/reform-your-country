package reformyourcountry.security;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class MainSecurity2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        BatchSecurity batchsecurity = (BatchSecurity)applicationContext.getBean("batchSecurity");
 
        try {
            batchsecurity.run();
 
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
