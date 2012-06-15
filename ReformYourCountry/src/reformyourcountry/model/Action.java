package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

public class Action {
	
	private String title;
	private String content;
	private List<Articles> article = new ArrayList <Articles> ();
	private List <Comment> comment = new ArrayList <Comment>();
	

	public Action() {
	}

	
	public Action(String titre, String descript) {
		this.title = titre;
		this.content = descript;
	}
	
	public String getTitle ()
	{
		return title;
		
	}
	
	public String getdescription ()
	{
		return content;
	}
	
	public void setTitle(String t)
	{
		this.title = t;
	}
	
	public void setDescription (String d)
	{
		this.content = d;
	}
	
	
	public List<Articles> getArticle() {
		return article;
	}
	
	public List <Comment> getComment() {
		return comment;
	}
	
	public void displayArticles(){
		
		for (Articles ar : this.getArticle()){
			System.out.println(ar.getTitle() + ar.getdescription());
		}
		
	}

	public void displayComment(){
	
		for (Comment c : this.getComment()){
			System.out.println( c.getcontentComment());
		}
	
}
	
	
	
	
	
	
	
	
	
	
	

}
