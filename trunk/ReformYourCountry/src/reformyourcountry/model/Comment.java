package reformyourcountry.model;


public class Comment {
	
	private String contentComment;
	private Action action;
		
	public Comment() {
	}	
	
	public Comment(String descript)
	{
		this.contentComment = descript;		
	}

	public String getContentComment() {
		return contentComment;
	}

	public void setContentComment(String contentComment) {
		this.contentComment = contentComment;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
	@Override
	public String toString() {
		return contentComment;
	}
	
	

}
