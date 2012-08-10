package reformyourcountry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import reformyourcountry.web.ContextUtil;

public class MainTestUpdate {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        BatchTestUpdate batch = (BatchTestUpdate)applicationContext.getBean("batchTestUpdate");
        
        ContextUtil.contextInitialized(applicationContext);
        batch.run();

    }

}
