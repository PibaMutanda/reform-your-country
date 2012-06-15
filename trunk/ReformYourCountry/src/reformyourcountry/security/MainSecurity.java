package reformyourcountry.security;
import blackbelt.exceptions.InvalidPasswordException;
import blackbelt.exceptions.UserLockedException;
import blackbelt.exceptions.UserNotFoundException;
import blackbelt.exceptions.UserNotValidatedException;
import blackbelt.security.*;


import java.util.HashMap;
import java.util.HashSet;
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
			Set<User> listUser = MainSecurity.userListe();
		    
			
			
			//iterator to display all users of the list
			for(User u: listUser)
			{
				System.out.println("Firstname :"+u.getFirstName()+".   username:  "+u.getUserName());
			}
				
			
    }
	
	public static Set<User> userListe()
    {
		Set<User> usersListe = new HashSet<User>();
    	Set<Privilege>list1 = new HashSet<Privilege>();
    	list1.add(Privilege.CREATE_QUESTIONS);
    	list1.add(Privilege.EDIT_REF_MANUAL);
    	
		User usr1 = new User();
    	usr1.setFirstName("Bob");
    	usr1.setUserName("Usertest");
    	usr1.setPrivileges(list1);
    	
    	// an iterator to display all priveleges of the first user
    	System.out.println("Privilèges du premier utilisateur");
    	Iterator<Privilege> i=list1.iterator(); // on crée un Iterator pour parcourir notre HashSet
		while(i.hasNext()) // tant qu'on a un suivant
		{
			System.out.println(i.next()); // on affiche le suivant
		}
		
    	Set<Privilege>list2 = new HashSet<Privilege>();
    	list2.add(Privilege.APPROVE_PROPOSAL_ON_QUESTIONS);
    	list2.add(Privilege.EDIT_COMMUNITY_USERS_PRIVILEGES);
    	
    	User usr2 = new User();
    	usr2.setFirstName("Patrick");
    	usr2.setUserName("UserTest2");
    	usr2.setPrivileges(list2);
    	
    	// an iterator to display all priveleges of the second user
    	System.out.println("Privilèges du deuxième utilisateur");
    	Iterator<Privilege> j=list2.iterator(); // on crée un Iterator pour parcourir notre HashSet
		while(j.hasNext()) // tant qu'on a un suivant
		{
			System.out.println(j.next()); // on affiche le suivant
		}
		
		// add users to the user list
    	usersListe.add(usr1);
    	usersListe.add(usr2);
    	
    	return usersListe;
    	
    }

}
