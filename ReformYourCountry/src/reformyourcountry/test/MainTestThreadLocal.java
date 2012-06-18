package reformyourcountry.test;

import com.sun.xml.internal.bind.CycleRecoverable.Context;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import reformyourcountry.dao.UserDao;
import reformyourcountry.exceptions.InvalidPasswordException;
import reformyourcountry.exceptions.UserLockedException;
import reformyourcountry.exceptions.UserNotFoundException;
import reformyourcountry.exceptions.UserNotValidatedException;
import reformyourcountry.model.User;
import reformyourcountry.model.User.CommunityRole;
import reformyourcountry.model.User.CorporateRole;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;

public class MainTestThreadLocal {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 
		doSomethingInContext(); 
	}

	 static void doSomethingInContext() {
		
		 ThreadLocal<User> threadUser = new ThreadLocal<User>(); 
		 User usr1=new User();
		 usr1.setFirstName("Bob");
	     usr1.setUserName("Usertest");
	     
		 ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
		 Integer numberexemple = new Integer(5);
		  
		 
	 
		    if (threadLocal.get() != null || threadUser.get() != null)
		      throw new IllegalStateException();
		    threadLocal.set(numberexemple);
		    threadUser.set(usr1);
		    try {
		     System.out.println(""+threadLocal.get());
		     System.out.println(""+threadUser.get());
		    }
		    finally {
		      threadLocal.remove();
		      threadUser.remove();
		    }
	 } 
}
