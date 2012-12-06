package reformyourcountry.model;

public enum BadgeTypeLevel {

    GOLD    ("Or","Les gommettes d'or sont rares. Vous aurez à travailler activemment pour ça. Ils sont en quelque sorte un exploit!"),
	SILVER	("Argent","Les gommettes d'argent sont attribuées pour des objectifs à plus long terme. Les gomettes d'argent sont rares, mais certainement réalisables si vous êtes intéressé"),
	BRONZE  ("Verte","Les gommettes vertes sont décernées pour une utilisation basique. Ils sont faciles à gagner.");

	String name;		
	String description;
	
	private BadgeTypeLevel(String name, String description) {
		this.name = name;
		this.description=description;
	}

	
	
	

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}