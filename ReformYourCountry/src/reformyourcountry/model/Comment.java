package reformyourcountry.model;


public class Comment {
	
	private String content;
	private Action action;
	private User user;
		
	public Comment() {
	}	
	
	public Comment(String descript, Action action, User user)
	{
		this.content = descript;
		this.action = action;
		this.user = user;
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

	@Override
	public String toString() {
		return content;
	}
	
	

}
