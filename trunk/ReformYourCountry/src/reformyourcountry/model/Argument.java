package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

public class Argument {
	
	String content;
	private List<Action> actions = new ArrayList<Action>();
	
	public Argument (){
		
	}
	
	public Argument(String descript)
	{
		this.content = descript;		
	}

	public String getContentArgument() {
		return content;
	}

	public void setContentArgument(String contentArgument) {
		this.content = contentArgument;
	}

	public List<Action> getActions() {
		return actions;
	}

	
	public String toString() {
		return content;
	}


}
