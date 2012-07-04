package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

public class Group {
	
	private String name; // a group has a lot of users
	private List <VoteAction> voteActions = new ArrayList <VoteAction>();
	private Group parent;
	private List <Group> children = new ArrayList <Group>();
	private String url;
	private String description;
		
	public Group(){
		
	}

	public Group(String name) {
		
		this.name = name;
		
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/*public List<GroupReg> getGroupRegs() {
		return groupRegs;
	}*/
	
	public List<VoteAction> getVoteActions() {
		return voteActions;
	}
	public List<Group> getChildren(){
		return children;
	}
	
	public void setParent(Group parent){
		this.parent = parent;
	}
  
    public Group getParent(){
        
        return parent;
    }
	public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
	public String toString(){
		return name;
	}

}
