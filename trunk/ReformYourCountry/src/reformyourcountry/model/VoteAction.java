package reformyourcountry.model;

import java.util.*;


public class VoteAction {
	
	private  int value = 0;  // -2 = against; 0 = neutral; +2 = pro.
	private Action action;
	private User user;  // or group. Can be null if the vote is made by a group.
	private Group group; // or user. Can be null if the vote is made by a user.
	
	
	public VoteAction() {
		
	}
		
	public VoteAction(int value, Action action, User user, Group group) {
		//super();
		this.value = value;
		this.action = action;
		this.user = user;
		this.group = group;
	}

	public int getValue() {
		return value;
	}
	public void setValue(int valueParam) {
		if (valueParam > 2 || valueParam < -2) {
			throw new IllegalArgumentException("Bug: vote out of range (value = " + valueParam +").");
		}
		this.value = valueParam;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
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


	// TODO: toString (! user could be null)
	
	public String toString(String name)
	{
		if(user == null && group == null){
			throw new IllegalArgumentException("Bug: You are not a member. Please login or register to vote this action .");			
		}
		else if (){
			
		}
		
		
		return Integer.toString(value) ;
	}
	
	
	
}
