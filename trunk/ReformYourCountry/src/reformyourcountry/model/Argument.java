package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

public class Argument {
	
	private String content;
	private Action action;
	private List<VoteArgument> voteArguments = new ArrayList<VoteArgument>();
	private User user;
	
	public Argument (){
		
	}
	
	
	public Argument(String content, Action action, User user ) {
		
		this.content = content;
		this.action = action;
		this.user = user;
		
	}
	
	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Action getAction() {
		return action;
	}


	public void setAction(Action action) {
		this.action = action;
	}


	public List<VoteArgument> getVoteArguments() {
		return voteArguments;
	}
	
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return content;
	}


}
