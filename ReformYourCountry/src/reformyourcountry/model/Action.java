package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

public class Action {
	
	private String title;
	private String content;
	private List<Article> articles = new ArrayList <Article>();
	private List<Comment> comments = new ArrayList <Comment>();
	private List<VoteAction> voteActions = new ArrayList <VoteAction>();
	private List<Argument> arguments = new ArrayList <Argument>();
	
	
	//constructeur sans arguments
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
	
	

