package reformyourcountry.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Article extends BaseEntity {

	@NotBlank//Hibernate constraint, improves upon @NotNull
	@Column(length = 100)
	private String title;
	
	@Column(unique = true, nullable=false)
	private String url; //Used to create a more readable URL; derived from the title (ie: if the title is "Le Web 2.0", url will be "le-Web-2-0") 

	@Lob
	private String summary;
	
	@Lob
	private String content;

	private Date releaseDate;

	private Date publishDate;
	
	@NotNull
	private boolean publicView=true;
	
	@ManyToMany
	@JoinTable(name = "ARTICLE_ACTION", joinColumns = @JoinColumn(name = "ARTICLE_ID"), inverseJoinColumns = @JoinColumn(name="ACTION_ID"))
	private List<Action> actions = new ArrayList<Action>();

	@ManyToOne
	@JoinColumn
	private Article parent;

	@OneToMany(mappedBy = "parent")
	private List<Article> children = new ArrayList <Article>();


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public Article getParent() {
		return parent;
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


	public Date getPublishDate() {
		return publishDate;
	}


	public void setPublishDate(Date publishDate) {
		if (publishDate.after(new Date())) {
			this.publishDate = publishDate;
		}else{
			this.publishDate = null;
		}
	}


	public boolean isPublicView() {
		return publicView;
	}


	public void setPublicView(boolean publicView) {
		this.publicView = publicView;
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
	public boolean isPublished(){
		boolean published=false;
		if(publishDate!=null){
			published =(publicView && (!publishDate.after(new Date())));
		}else{
			published = publicView;
		}

		return published;
	}

	public boolean equalsOrIsParentOf(Article article) {
		while (article != null) {
			if (this.equals(article)) {
				return true;
			}
			article = article.getParent();
		}
		return false;
	}
}
