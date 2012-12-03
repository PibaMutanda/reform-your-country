package reformyourcountry.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import reformyourcountry.security.SecurityContext;

@Entity
public class Comment extends BaseEntity{
   
    @Type(type="org.hibernate.type.StringClobType")
	private String content;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Argument argument;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private GoodExample goodExample;
	
	@ManyToOne
	private User user;
	
	private boolean hidden;
		
	public Comment() {
	}	
	
	public Comment(String content, Argument argument, User user)
	{
		this.content = content;
		this.argument = argument;
		this.user = user;
	}
		
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Argument getArgument() {
		return argument;
	}

	public void setArgument(Argument argument) {
		this.argument = argument;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

    public GoodExample getGoodExample() {
		return goodExample;
	}

	public void setGoodExample(GoodExample goodExample) {
		this.goodExample = goodExample;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean isHidden) {
		this.hidden = isHidden;
	}
	////// security methods //////
    public boolean isEditable(){
        return SecurityContext.canCurrentUserEditComment(this);
    }
    
    public boolean isHidable(){
        return SecurityContext.canCurrentUserHideComment(this);
    }

    ///// custom methods /////
	@Override
	public String toString() {
		return content;
	}

}
