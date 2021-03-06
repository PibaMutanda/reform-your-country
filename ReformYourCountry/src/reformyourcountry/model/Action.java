package reformyourcountry.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import reformyourcountry.search.Searchable;
@Entity
public class Action extends BaseEntity implements Searchable{
	
    
    @Column(length = 100)
    @NotBlank(message="Il faut entrer un titre svp ")
	private String title;
    
    @Pattern(message ="ne peut contenir que des caractère alphanumériques, sans accents. Les 2 caractères \"-\" et \"_\" sont autorisés, mais pas les espaces.", regexp="[A-Za-z0-9_-]{2,256}")
	private String url;
	
	@Column(length = 100)
	private String shortDescription;
	
	
	@NotBlank
    @Column(length=20)
    private String shortName;
	
	@Lob
    /*Forcing type definition to have text type column in postgresql instead of automatic indirect storage of large object (postgresql store lob in a separate table named pg_largeobject and store his id in the "content" column).
     *Without forcing, JDBC driver use write() method of the BlobOutputStream to store Clob into the database;
     * this method take an int as parameter an convert it into a byte causing lose of 3 byte information so character are render as ASCII instead of UTF-8 expected .
     * @see http://stackoverflow.com/questions/9993701/cannot-store-euro-sign-into-lob-string-property-with-hibernate-postgresql
     * @see http://stackoverflow.com/questions/5043992/postgres-utf-8-clobs-with-jdbc
     */
    @Type(type="org.hibernate.type.StringClobType")
	private String content;
	
//	private int voteCountPro;
//	private int voteCountAgainst;
//	private int voteCountTotal;
	
	@ManyToMany(mappedBy = "actions")
	private List<Article> articles = new ArrayList <Article>();
	
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
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
//	public int getVoteCountPro(){
//		return voteCountPro;
//	}
//	
//	public void incrementVoteCountPro(){
//		voteCountPro++;
//	}
//	public int getVoteCountAgainst(){
//		return voteCountAgainst;
//	}
//	
//	public void incrementVoteCountAgainst(){
//		voteCountAgainst++;
//	}
//	
//	public int getVoteCountTotal(){
//		return voteCountTotal;
//	}
//	
//	public void incrementVoteCountTotal(){
//		voteCountTotal++;
//	}

	public List<Article> getArticles() {
		return articles;
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
	
	

	public void addArticle(Article article){
	    articles.add(article);
	}
	
	public void addArgument(Argument argument){
	    
	    arguments.add(argument);
	}

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public Map<String, String> getCriterias() {
        Map<String ,String>criterias = new HashMap<String,String>();
        criterias.put("title", StringUtils.defaultIfEmpty(title,""));
        criterias.put("shortName",StringUtils.defaultIfEmpty(shortName,""));
        criterias.put("description", StringUtils.defaultIfEmpty(shortDescription,""));
        criterias.put("content", StringUtils.defaultIfEmpty(content,""));
        
        return criterias;
    }

    @Override
    public String getBoostedCriteriaName() {
       
        return "title";
    }

   
}
	
	

