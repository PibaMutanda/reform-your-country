package reformyourcountry.model;

public class Argument {
	
	String descriptionArgument;
	
	public Argument(String descript)
	{
		this.descriptionArgument = descript;
		
		
	}
	
	public String getcomment ()
	{
		return descriptionArgument;
	}
	
	public void setcomment (String d)
	{
		descriptionArgument = d;
	}
	


}
