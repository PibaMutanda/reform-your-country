package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

public class Argument {
	
	String contentArgument;
	private List<Action> actions = new ArrayList<Action>();
	
	public Argument (){
		
	}
	
	public Argument(String descript)
	{
		this.contentArgument = descript;		
	}

	public String getContentArgument() {
		return contentArgument;
	}

	public void setContentArgument(String contentArgument) {
		this.contentArgument = contentArgument;
	}

	public List<Action> getActions() {
		return actions;
	}

	
	// TODO: toString


}
