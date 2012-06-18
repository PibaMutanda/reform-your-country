package reformyourcountry.model;


public class Comment {
	
	private String content;
	private Action action;
		
	public Comment() {
	}	
	
	public Comment(String descript)
	{
		this.content = descript;		
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
	
	@Override
	public String toString() {
		return content;
	}
	
	

}
