package reformyourcountry.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends BaseEntity{
	
    private String title;
   
	private String content;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Action action;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private User user;
		
	public Comment() {
	}	
	
	public Comment(String title,String descript, Action action, User user)
	{
		this.content = descript;
		this.action = action;
		this.user = user;
		this.title = title;
	}

	public String getContentComment() {
		return content;
	}

	public void setContentComment(String contentComment) {
		this.content = contentComment;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
		
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

 

    @Override
	public String toString() {
		return content;
	}
	

}
