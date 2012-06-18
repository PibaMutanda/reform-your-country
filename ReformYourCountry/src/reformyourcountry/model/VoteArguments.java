package reformyourcountry.model;

public class VoteArguments {
	
	private  int value = +2;  // -2 = against; +2 = pro.
	private Argument argument;
	private User user;  // or group. Can be null if the vote is made by a group.
	private Group group; // or user. Can be null if the vote is made by a user.
	
	public VoteArguments() {
		
	}

	public VoteArguments(int value, Argument argument, User user, Group group) {
		
		this.value = value;
		this.argument = argument;
		this.user = user;
		this.group = group;
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
	
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Override
	public String toString () {
		
		if(user == null ){
			throw new NullPointerException("Bug: You are not a member. Please login or register to vote this argument .");			
		}
		
		return "vote value : " + Integer.toString(value);
		
	}
	

}
