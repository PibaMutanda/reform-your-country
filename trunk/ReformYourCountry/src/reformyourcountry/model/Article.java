package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

public class Article {
	
	private String title;
	private String content;
	private List <Action>actions = new ArrayList <Action>();
	
	public Article() {
	}
	

	public Article(String title, String content, List<Action> actions) {
		
		this.title = title;
		this.content = content;
		this.actions = actions;
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


	public List<Action> getActions() {
		return actions;
	}


	@Override
	public String toString() {
		return title;
	}

}
