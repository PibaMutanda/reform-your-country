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
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Article extends BaseEntity {

	@NotBlank//Hibernate constraint, improves upon @NotNull
	@Column(length = 100, unique = true, nullable=false)// need nullable= false for schemaupdate
	private String title;
	
	@NotBlank
	@Column(length=20)
	private String shortName;
	
	@Column(unique = true, nullable=false)
	@NotBlank(message="entrer une Url")
	@Pattern(message="l'Url ne peut contenir des caractères spéciaux", regexp="[A-Za-z0-9_-]{2,256}")
	private String url; //Used to create a more readable URL; derived from the title (ie: if the title is "Le Web 2.0", url will be "le-Web-2-0") 

	@Lob
    /*Forcing type definition to have text type column in postgresql instead of automatic indirect storage of large object (postgresql store lob in a separate table named pg_largeobject and store his id in the "content" column).
     *Without forcing, JDBC driver use write() method of the BlobOutputStream to store Clob into the database;
     * this method take an int as parameter an convert it into a byte causing lose of 3 byte information so character are render as ASCII instead of UTF-8 expected .
     * @see http://stackoverflow.com/questions/9993701/cannot-store-euro-sign-into-lob-string-property-with-hibernate-postgresql
     * @see http://stackoverflow.com/questions/5043992/postgres-utf-8-clobs-with-jdbc
     */
    @Type(type="org.hibernate.type.TextType")
	private String summary;  // Longer than Article.description, probably 1 page long.
	
	@Lob
    /*Forcing type definition to have text type column in postgresql instead of automatic indirect storage of large object (postgresql store lob in a separate table named pg_largeobject and store his id in the "content" column).
     *Without forcing, JDBC driver use write() method of the BlobOutputStream to store Clob into the database;
     * this method take an int as parameter an convert it into a byte causing lose of 3 byte information so character are render as ASCII instead of UTF-8 expected .
     * @see http://stackoverflow.com/questions/9993701/cannot-store-euro-sign-into-lob-string-property-with-hibernate-postgresql
     * @see http://stackoverflow.com/questions/5043992/postgres-utf-8-clobs-with-jdbc
     */
    @Type(type="org.hibernate.type.TextType")
	private String toClassify; // Section of text used to store a draft, or quotes that will be added.
	
	@Lob
    /*Forcing type definition to have text type column in postgresql instead of automatic indirect storage of large object (postgresql store lob in a separate table named pg_largeobject and store his id in the "content" column).
     *Without forcing, JDBC driver use write() method of the BlobOutputStream to store Clob into the database;
     * this method take an int as parameter an convert it into a byte causing lose of 3 byte information so character are render as ASCII instead of UTF-8 expected .
     * @see http://stackoverflow.com/questions/9993701/cannot-store-euro-sign-into-lob-string-property-with-hibernate-postgresql
     * @see http://stackoverflow.com/questions/5043992/postgres-utf-8-clobs-with-jdbc
     */
    @Type(type="org.hibernate.type.TextType")
	private String description; // Used by meta tag for search engines.
	
	@Lob
	/*Forcing type definition to have text type column in postgresql instead of automatic indirect storage of large object (postgresql store lob in a separate table named pg_largeobject and store his id in the "content" column).
	 *Without forcing, JDBC driver use write() method of the BlobOutputStream to store Clob into the database;
	 * this method take an int as parameter an convert it into a byte causing lose of 3 byte information so character are render as ASCII instead of UTF-8 expected .
	 * @see http://stackoverflow.com/questions/9993701/cannot-store-euro-sign-into-lob-string-property-with-hibernate-postgresql
	 * @see http://stackoverflow.com/questions/5043992/postgres-utf-8-clobs-with-jdbc
	 */
	@Type(type="org.hibernate.type.TextType")
	private String content;

	private Date publishDate;
	
	@NotNull
	private boolean publicView = false;
	
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

	public String getShortName() {
		return shortName;
	}


	public void setShortName(String shortName) {
		this.shortName = shortName;
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
	

	public String getToClassify() {
		return toClassify;
	}


	public void setToClassify(String toClassify) {
		this.toClassify = toClassify;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
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
