package reformyourcountry.security;

import java.util.Set;

import reformyourcountry.model.User;

public class MyThreadLocal extends Thread{
	
	
		 
       //definition of the constructor and the run method (for the example with normal thread). 
        public MyThreadLocal(String name){
                super(name);
        }
        
        public void run(){
                
                for(int i = 0; i < 10; i++)
                                System.out.println(this.getName());
                
        }       


    // definition of the local thread and his methods.    
	/*public static final ThreadLocal<User> userThreadLocal = new ThreadLocal<User>();

	public static void set(User user) {
		userThreadLocal.set(user);
	}

	public static void unset() {
		userThreadLocal.remove();
	}

	public static User get() {
		return userThreadLocal.get();
	}*/
}
