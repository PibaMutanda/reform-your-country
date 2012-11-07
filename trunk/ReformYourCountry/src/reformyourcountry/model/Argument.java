package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
@Entity
public class Argument extends BaseEntity{
    
    @Column(length = 100)
	private String title; 
    @Lob
    /*Forcing type definition to have text type column in postgresql instead of automatic indirect storage of large object (postgresql store lob in a separate table named pg_largeobject and store his id in the "content" column).
     *Without forcing, JDBC driver use write() method of the BlobOutputStream to store Clob into the database;
     * this method take an int as parameter an convert it into a byte causing lose of 3 byte information so character are render as ASCII instead of UTF-8 expected .
     * @see http://stackoverflow.com/questions/9993701/cannot-store-euro-sign-into-lob-string-property-with-hibernate-postgresql
     * @see http://stackoverflow.com/questions/5043992/postgres-utf-8-clobs-with-jdbc
     */
    @Type(type="org.hibernate.type.StringClobType")
	private String content;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Action action;
	@OneToMany(mappedBy = "argument")
	private List<VoteArgument> voteArguments = new ArrayList<VoteArgument>();
	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;
	private int voteCountPro;
	//use it as +1 not as -1
	private int voteCountAgainst;
	private boolean positiveArg;
	
	public boolean isPositiveArg() {
        return positiveArg;
    }


    public void setPositiveArg(boolean positiveArg) {
        this.positiveArg = positiveArg;
    }


    public Argument (){
		
	}
	
	
	public Argument(String title,String content, Action action, User user ) {
		
	    this.title = title;
		this.content = content;
		this.action = action;
		this.user = user;
		
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


	public Action getAction() {
		return action;
	}


	public void setAction(Action action) {
		this.action = action;
	}


	public List<VoteArgument> getVoteArguments() {
		return voteArguments;
	}
	
	
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public int getVoteCountPro() {
        return voteCountPro;
    }


    public void incrementVoteCountPro() {
        voteCountPro++;
    }


    public int getVoteCountAgainst() {
        return voteCountAgainst;
    }


    public void incrementVoteCountAgainst() {
        voteCountAgainst++;
    }

    public void addVoteArgument(VoteArgument voteArgument){
        
        voteArguments.add(voteArgument);
        
    }
    @Override
	public String toString() {
		return content;
	}
    public int getTotal(){
        return this.voteCountPro-this.voteCountAgainst;
    }
    public void recalculate(){
        
    }

}
