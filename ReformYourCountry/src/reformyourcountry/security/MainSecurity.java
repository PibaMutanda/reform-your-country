package reformyourcountry.security;
import blackbelt.exceptions.InvalidPasswordException;
import blackbelt.exceptions.UserLockedException;
import blackbelt.exceptions.UserNotFoundException;
import blackbelt.exceptions.UserNotValidatedException;
import blackbelt.security.*;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;



import reformyourcountry.model.User;
import reformyourcountry.service.LoginService;
import reformyourcountry.service.LoginService.WaitDelayNotReachedException;


public class MainSecurity {

	/**
	 * @param args
	 */
    
	public static void main(String[] args) {

		// TODO Auto-generated method stub
			Set<User> listUser = LoginService.userListe();
		    
			
			//iterator to display all users of the list
			Iterator<User> i=listUser.iterator(); // on crée un Iterator pour parcourir notre HashSet
			while(i.hasNext()) // tant qu'on a un suivant
			{
				System.out.println(i.next()); // on affiche le suivant
			}
    }

}
