package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

public class Article {
	private String title;
	private String content;
	private Article parent;
	private List<Action> actions = new ArrayList<Action>();
	private List<Article> children = new ArrayList <Article>();
	
	
	public Article() {
	}
	

	public Article(String title, String content) {
		//super();
		this.title = title;
		this.content = content;
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

	public List<Article> getChildren(){
		return children;
	}
	
	public void setParent(Article parent){
		this.parent = parent;
	}	


	@Override
	public String toString() {
		return title;
	}
}
