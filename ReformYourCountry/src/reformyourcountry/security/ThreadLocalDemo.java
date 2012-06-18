package reformyourcountry.security;

import java.util.Set;

import reformyourcountry.model.User;

//a small programm with two differents way to use thread, first with normal thread and second with localthread.
public class ThreadLocalDemo extends Thread{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//two threads with only a string as value.
		MyThreadLocal t = new MyThreadLocal("A");
		MyThreadLocal t2 = new MyThreadLocal("  B");
        t.start();
        t2.start();
		// TODO Auto-generated method stub

		
		
		//two locals threads with a user as value
		/*Thread threadOne = new ThreadLocalDemo();
		System.out.println(threadOne.getState());
		threadOne.start();

		System.out.println(threadOne.getState());
		Thread threadTwo = new ThreadLocalDemo();
		threadTwo.start();
		
		System.out.println(threadTwo.getState());*/
	}
	
	//definition of the run method which define what a thread must do (in the example with locals threads)
	/*public void run() {
		
		User usr1 = new User();
		usr1.setFirstName("Bob");		

		// set the context object in thread local to access it somewhere else
		MyThreadLocal.set(usr1);

		System.out.println(MyThreadLocal.get());
	}*/

}
