package reformyourcountry.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;



public class VoteAction {
	
	private Long id;
	private  int value = 0;  // -2 = against; 0 = neutral; +2 = pro.
	
	@ManyToOne
	@JoinColumn
	private Action action;
	@ManyToOne
    @JoinColumn
	private User user;  // or group. Can be null if the vote is made by a group.
	@ManyToOne
    @JoinColumn
	private Group group; // or user. Can be null if the vote is made by a user.
	
	
	public VoteAction() {
	}
		
	public VoteAction(long id, int value, Action action, User user, Group group) {
		
		this.id = id;
		this.value = value;
		this.action = action;
		this.user = user;
		this.group = group;
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	@Override
	public String toString() 
	{
		if(user == null && group == null){
			throw new NullPointerException("Bug: an action should either have a user or a group. This action has none. id = "+ id);	
		}
		return "VoteAction.value:" + value;
			
	}
		
		
	
	
	
	
}
