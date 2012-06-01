package blackbelt.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import blackbelt.model.User;


public class UserDao {
	
	private List<User> listeUser=new ArrayList<>();
	
	
	public void save(User user) {
		// TODO Auto-generated method stub
		listeUser.add(user);		
	}

	public User get(Long id) {
		// TODO Auto-generated method stub
		User user=new User();
		
		Iterator it=listeUser.iterator();
		
		while(it.hasNext()){
			user=(User)it.next();
			return id==user.getId() ? user:null;
		}
	 
	 return null;
	}
	
	public List<User> createListeUser() {

		   User user=new User();
		        user.setFirstName("Helios");
		        user.setLastName("Toto");
		        
		        listeUser.add(user);
		
		return listeUser;
	}

	public User getUserByEmail(String identifier) {
		// TODO Auto-generated method stub
		for (User user : listeUser) {
			   
			return identifier.equals(user.getMail())?user:null;
		}
		return null;
	}

	public  User getUserByNickName(String identifier) {
		// TODO Auto-generated method stub
		for (User user: listeUser) {
			 return identifier.equals(user.getNickName())?user:null;
		}
		
		
		return null;
	}	
	

}
