package reformyourcountry.test;

import blackbelt.security.Privilege;

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
import reformyourcountry.model.User.Role;
import reformyourcountry.model.User.CorporateRole;

import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;

public class MainTestThreadLocal {

	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
		doSomethingWithUser(); 
	}

	 static void doSomethingWithUser() {
		
		 ThreadLocal<User> threadUser = new ThreadLocal<User>(); 
		 User usr1=new User();
		 usr1.setFirstName("Bob");
	     usr1.setUserName("Usertest");
	     Set<Privilege>list1 = new HashSet<Privilege>();
	    	list1.add(Privilege.CREATE_QUESTIONS);
	    	list1.add(Privilege.EDIT_REF_MANUAL);
	    
	     usr1.setPrivileges(list1);
	     
		 ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
		 Integer numberexemple = new Integer(5);
		  
		 
	 
		    if (threadLocal.get() != null || threadUser.get() != null)
		      throw new IllegalStateException();
		    threadLocal.set(numberexemple);
		    threadUser.set(usr1);
		    try {
		     System.out.println(""+threadLocal.get());
		     System.out.println(""+threadUser.get().getPrivileges());
		    }
		    finally {
		   
		      threadLocal.remove();
		      threadUser.remove();
		    }
	 } 
}
