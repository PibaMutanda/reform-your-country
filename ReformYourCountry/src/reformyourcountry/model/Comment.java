package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

public class Comment {
	
	private String contentComment;
	
	public Comment() {
	}
	
	
	public Comment(String descript)
	{
		this.contentComment = descript;
		
		
	}
	
	public String getcontentComment ()
	{
		return contentComment;
	}
	
	public void setcomment (String d)
	{
		contentComment = d;
	}
	
	
	

}
