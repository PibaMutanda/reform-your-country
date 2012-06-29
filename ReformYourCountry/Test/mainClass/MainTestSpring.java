package mainClass;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainTestSpring {
    public static void main(String[] args){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }

}
