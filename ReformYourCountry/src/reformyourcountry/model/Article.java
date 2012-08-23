package reformyourcountry.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Article extends BaseEntity {

	@NotEmpty
	@Column(length = 100)
	private String title;

	private String url;

	@Lob
	private String summary;

	@Lob
	private String content;

	private Date releaseDate;

	private Date publishDate;
	@NotNull
	private boolean publicView;
	@ManyToMany
	@JoinTable(name = "ARTICLE_ACTION", joinColumns = @JoinColumn(name = "ARTICLE_ID"), inverseJoinColumns = @JoinColumn(name="ACTION_ID"))
	private List<Action> actions = new ArrayList<Action>();

	@ManyToOne
	@JoinColumn
	private Article parent;

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
		this.publishDate = publishDate;
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
		boolean published;
		if(publishDate!=null){
			published =(publicView&&(!publishDate.after(new Date())));
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
