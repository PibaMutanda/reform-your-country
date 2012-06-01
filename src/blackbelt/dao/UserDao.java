package blackbelt.dao;

import java.util.ArrayList;
import java.util.List;

import blackbelt.model.User;


public class UserDao {
	
	private List<User> listeUser=new ArrayList<>();
	
	public void save(User user) {
		// TODO Auto-generated method stub
		
	}

	public User get(Long id) {
		// TODO Auto-generated method stub
		User user=new User("Pierre");
		
		return user;
	}
	
	public List<User> getListeUser() {
		//TODO resolve
//		listeUser.add(new User("Pierre"));
//		listeUser.add(new User("Jean"));
//		listeUser.add(new User("Mathieu"));
//		listeUser.add(new User("Marine"));
//		listeUser.add(new User("Piba"));
		User use = new User();
		use.setFirstName("Pierre");
		use.setLastName("bah");
		use.setNickName("pef");
		use.setMail("pierre@bah.be");
		listeUser.add(use);
		
		
		return listeUser;
	}

	public static User getUserByEmail(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	public static User getUserByNickName(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}	
	

}
