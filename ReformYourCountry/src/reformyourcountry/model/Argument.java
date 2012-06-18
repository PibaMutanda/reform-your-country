package reformyourcountry.model;

import java.util.ArrayList;
import java.util.List;

public class Argument {
	
	private String content;
	private Action action;
	//private List<Action> actions = new ArrayList<Action>();
	
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

	public Action getAction() {
		return action;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}

	public String toString() {
		return content;
	}


}
