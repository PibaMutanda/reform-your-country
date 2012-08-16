package reformyourcountry.test;

import org.springframework.context.ApplicationContext;

public class ContextTestUtil {
    
    
    
    private static ApplicationContext ac ;

    public static ApplicationContext getAc() {
        return ac;
    }

    public static void setAc(ApplicationContext ac) {
        ContextTestUtil.ac = ac;
    }
    
    
    

}
