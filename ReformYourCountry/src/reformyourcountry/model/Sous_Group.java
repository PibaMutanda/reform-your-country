package reformyourcountry.model;

public class Sous_Group {
	
	private String name;
	//private Group group; 
	
	public Sous_Group(){
		
	}

	public Sous_Group(String name) {
		
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString (){
		return name;
	}
	
	

}
