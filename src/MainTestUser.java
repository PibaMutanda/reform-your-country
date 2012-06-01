import java.util.ArrayList;
import java.util.Iterator;

import blackbelt.dao.UserDao;
import blackbelt.model.User;


public class MainTestUser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	UserDao userdao=new UserDao();
	ArrayList myListe=(ArrayList<User>)userdao.getListeUser();
	Iterator it=myListe.iterator();
	while(it.hasNext()){
		User user=(User) it.next();
		System.out.println(user.toString());
	}
	
	}

}
