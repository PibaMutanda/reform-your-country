package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

public class Argument {
	
	private String content;
	private Action action;
	private List<VoteArguments> voteArguments = new ArrayList<VoteArguments>();
	
	public Argument (){
		
	}
	
	
	public Argument(String content, Action action, List<VoteArguments> voteArguments) {
		
		this.content = content;
		this.action = action;
		this.voteArguments = voteArguments;
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


	public List<VoteArguments> getVoteArguments() {
		return voteArguments;
	}


	@Override
	public String toString() {
		return content;
	}


}
