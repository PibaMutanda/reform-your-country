package reformyourcountry.badge;

public enum BadgeTypeLevel {

	GOLD 	("Or"),
	SILVER	("Argent"),
	BRONZE	("Bronze");


	String name;		
	
	private BadgeTypeLevel(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	
	
	
}
