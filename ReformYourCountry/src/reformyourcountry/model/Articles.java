package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

public class Articles {
	
	private String titleArticle;
	private String contentArticle;
	private List <Action> action = new ArrayList <Action>();
	
	public Articles() {
	}
	
	public Articles(String titre, String descript)
	{
		this.titleArticle = titre;
		this.contentArticle = descript;
		
	}
	
	public String getTitle ()
	{
		return titleArticle;
		
	}
	
	public String getdescription ()
	{
		return contentArticle;
	}
	
	public void setTitle(String t)
	{
		titleArticle = t;
	}
	
	public void setdescription (String d)
	{
		contentArticle = d;
	}

	public List <Action> getActions() {
		return action;
	}

	public void setAction(ArrayList <Action> action) {
		this.action = action;
	}
	
	public void addAction (Action ac){
		action.add(ac);
		
	}
	
	public void displayAction(){
		
		for (Action a : this.getActions()){
			System.out.println(a.getTitle() + a.getdescription());
		}
		
	}
	

}
