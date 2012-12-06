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
import javax.persistence.OrderBy;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;

import reformyourcountry.search.Searchable;
import reformyourcountry.security.SecurityContext;
@Entity
public class GoodExample extends BaseEntity implements IVote ,Searchable{

	@NotBlank(message="entrer un titre")
	@Column(length = 100, unique = true, nullable = false)// need nullable= false for schemaupdate
	private String title;
	
    @Lob
    /*Forcing type definition to have text type column in postgresql instead of automatic indirect storage of large object (postgresql store lob in a separate table named pg_largeobject and store his id in the "content" column).
     *Without forcing, JDBC driver use write() method of the BlobOutputStream to store Clob into the database;
     * this method take an int as parameter an convert it into a byte causing lose of 3 byte information so character are render as ASCII instead of UTF-8 expected .
     * @see http://stackoverflow.com/questions/9993701/cannot-store-euro-sign-into-lob-string-property-with-hibernate-postgresql
     * @see http://stackoverflow.com/questions/5043992/postgres-utf-8-clobs-with-jdbc
     */
    @Type(type="org.hibernate.type.StringClobType")
	@Column(nullable = false)
	@NotBlank(message="entrer une description")
	private String content;
	
	@ManyToMany(mappedBy = "goodExamples")
	private List<Article> articles = new ArrayList <Article>();
	
	@OneToMany(mappedBy = "goodExample")
	@OrderBy("createdOn ASC")
	private List<Comment> commentList = new ArrayList<Comment>();
	
	@OneToMany(mappedBy = "goodExample")
	private List<VoteGoodExample> voteGoodExample = new ArrayList<>();
	
	private int voteCount;

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

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
    
    public void addArticle(Article article){
		articles.add(article);
	}
    
    public List<Comment> getCommentList() {
        return commentList;
    }


    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    
	public List<VoteGoodExample> getVoteGoodExample() {
		return voteGoodExample;
	}

	public void setVoteGoodExample(List<VoteGoodExample> voteGoodExample) {
		this.voteGoodExample = voteGoodExample;
	}

	@Override
	public int getTotal() {
		return voteCount;
	}

	@Override
	public int getVoteValueByUser(User user) {
		for(VoteGoodExample vote:voteGoodExample){
            if (vote.getUser()==user){
               return 1;//There is only one value for a GoodExample.
            }
        }
        return 0;
	}

    @Override
    public Map<String, String> getCriterias() {
        Map<String,String> criterias = new HashMap<String,String>();
        criterias.put("title",StringUtils.defaultIfEmpty(title,""));
        criterias.put("description", StringUtils.defaultIfEmpty(content,""));
        
        return criterias;
    }

    @Override
    public String getBoostedCriteriaName() {
        
        return "title";
    }

   
    public boolean isEditable(){  // Placed in the entity because used in JSPs (EL).
        return SecurityContext.canCurrentUserEditGoodExample(this);
    }
    public boolean isDeletable(){
        return SecurityContext.canCurrentUserDeleteGoodExample(this);
    }
	
}
