package reformyourcountry.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class VoteAction extends BaseEntity{
	

	private  int value = 0;  // -2 = against; 0 = neutral; +2 = pro.
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Action action;
	@ManyToOne
    @JoinColumn(nullable = false)
	private User user;  
		
	
	public VoteAction() {
	}
		
	public VoteAction(int value, Action action, User user) {
		

		this.value = value;
		this.action = action;
		this.user = user;
		
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
	
	@Override
	public String toString() 
	{
		return "VoteAction.value:" + value;
			
	}
		
		
	
	
	
	
}
