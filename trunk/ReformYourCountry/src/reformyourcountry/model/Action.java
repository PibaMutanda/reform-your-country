package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OneToMany;

import javax.persistence.*;
@Entity
public class Action extends BaseEntity{
	
	private String title;
	private String url;
	private String shortDescription;
	private String longDescription;
	private String content;
	

	private int voteCountPro;
	private int voteCountAgainst;
	private int voteCountTotal;
	
	@ManyToMany(mappedBy = "actions")
	private List<Article> articles = new ArrayList <Article>();
	
	@OneToMany(mappedBy = "action")
	private List<Comment> comments = new ArrayList <Comment>();
	
	@OneToMany(mappedBy = "action")
	private List<VoteAction> voteActions = new ArrayList <VoteAction>();
	@OneToMany(mappedBy = "action")
	private List<Argument> arguments = new ArrayList <Argument>();
	
	

	public Action() {
	}
	
	public Action(String titre, String descript) {
		this.title = titre;
		this.content = descript;
	}
	


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setUrl(String url){
		this.url=url;
	}
	
	public String getShortDescription(){
		return shortDescription;
	}
	
	public void setShortDescription(String shortDescription){
		this.shortDescription=shortDescription;
	}
	
	public String getLongDescription(){
		return longDescription;
	}
	
	public void setLongDescription(String longDescription){
		this.longDescription=longDescription;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public int getVoteCountPro(){
		return voteCountPro;
	}
	
	public void incrementVoteCountPro(){
		voteCountPro++;
	}
	public int getVoteCountAgainst(){
		return voteCountAgainst;
	}
	
	public void incrementVoteCountAgainst(){
		voteCountAgainst++;
	}
	
	public int getVoteCountTotal(){
		return voteCountTotal;
	}
	
	public void incrementVoteCountTotal(){
		voteCountTotal++;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public List<VoteAction> getVoteActions() {
		return voteActions;
	}
	
	public List<Argument> getArguments() {
		return arguments;
	}

	@Override
	public String toString() {
		return title;
	}
	
}
	
	

