package reformyourcountry.maintest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import reformyourcountry.web.ContextUtil;



public class MainSecurity2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        BatchSecurity batchsecurity = (BatchSecurity)applicationContext.getBean("batchSecurity");
        ContextUtil.contextInitialized(applicationContext);    

    }

}
