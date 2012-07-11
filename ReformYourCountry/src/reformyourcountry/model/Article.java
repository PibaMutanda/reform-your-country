package reformyourcountry.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
@Entity
public class Article extends BaseEntity {
    @Column(length = 100)
	private String title;
	private String url;
	@Lob
	private String summary;
	@Lob
	private String content;
	private Date releaseDate;
	@ManyToOne
	@JoinColumn
	private Article parent;
	@ManyToMany
	@JoinTable(name = "ARTICLE_ACTION", joinColumns = @JoinColumn(name = "ARTICLE_ID"), inverseJoinColumns = @JoinColumn(name="ACTION_ID"))
	private List<Action> actions = new ArrayList<Action>();
	@OneToMany(mappedBy = "parent")
	private List<Article> children = new ArrayList <Article>();
	
	
	public Article() {
	}
	

	public Article(String title, String content) {

		this.title = title;
		this.content = content;
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
	
	public String getSummary(){
		return summary;
	}
	
	public void setSummary(String summary){
		this.summary=summary;
	}
	
	public Date getReleaseDate(){
		return releaseDate;
	}
	
	public void setReleaseDate(Date releaseDate){
		this.releaseDate=releaseDate;
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

	public void addAction(Action action){
	    
	    actions.add(action);
	}

	@Override
	public String toString() {
		return title;
	}
}
