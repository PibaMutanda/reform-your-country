package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
	private String name; // a group has a lot of users
	private List <Group_Reg> group_regs = new ArrayList <Group_Reg> ();
	private List <VoteArguments> voteArgument = new ArrayList <VoteArguments>();
	private List <VoteAction> voteActions = new ArrayList <VoteAction>();
	private List <Sous_Group> sous_group = new ArrayList <Sous_Group>();
		
	public Group(){
		
	}

	public Group(String name, User user) {
		
		this.name = name;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Group_Reg> getGroup_regs() {
		return group_regs;
	}

	public List<VoteArguments> getVoteArgument() {
		return voteArgument;
	}
	
	public List<VoteAction> getVoteActions() {
		return voteActions;
	}
	
	public List<Sous_Group> getSous_group() {
		return sous_group;
	}

	@Override
	public String toString(){
		return name;
	}
	

}
