package reformyourcountry;



import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;





public class MainCreate {
    
   
    
    public static void main(String [] args){
    	
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        BatchCreate batch = (BatchCreate)applicationContext.getBean("batchCreate");
        batch.run();
    
    }

}
