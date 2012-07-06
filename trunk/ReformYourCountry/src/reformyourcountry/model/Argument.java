package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class Argument extends BaseEntity{
    
    @Column(length = 100)
	private String title; 
    @Lob
	private String content;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Action action;
	@OneToMany(mappedBy = "argument")
	private List<VoteArgument> voteArguments = new ArrayList<VoteArgument>();
	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;
	private int voteCountPro;
	private int voteCountAgainst;
	
	public Argument (){
		
	}
	
	
	public Argument(String title,String content, Action action, User user ) {
		
	    this.title = title;
		this.content = content;
		this.action = action;
		this.user = user;
		
	}
	
	public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
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


	public int getVoteCountPro() {
        return voteCountPro;
    }


    public void incrementVoteCountPro() {
        voteCountPro++;
    }


    public int getVoteCountAgainst() {
        return voteCountAgainst;
    }


    public void incrementVoteCountAgainst() {
        voteCountAgainst++;
    }


    @Override
	public String toString() {
		return content;
	}


}
