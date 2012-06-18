package reformyourcountry.model;

public class Group {
	
	private String name; // a group has a lot of users
	private User user; // a user belong to a group
	
	public Group(){
		
	}

	public Group(String name, User user) {
		
		this.name = name;
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String toString(){
		return name;
	}
	

}
