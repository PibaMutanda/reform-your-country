package reformyourcountry.model;

public class VoteArguments {
	
	private  int value = +2;  // -2 = against; +2 = pro.
	private Argument argument;
	private User user;  // or group. Can be null if the vote is made by a group.
	
	public VoteArguments() {
		
	}

	public VoteArguments(int value, Argument argument, User user) {
		
		this.value = value;
		this.argument = argument;
		this.user = user;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Argument getArgument() {
		return argument;
	}

	public void setArgument(Argument argument) {
		this.argument = argument;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
		
	public String toString () {
		
		return "vote value : " + Integer.toString(value);
	}
	

}
