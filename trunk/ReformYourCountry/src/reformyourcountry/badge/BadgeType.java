package reformyourcountry.badge;

public enum BadgeType {
	
	AUTOBIOGRAPHER		("Autobiographe", "a complété tous les champs de son profil", BadgeTypeLevel.BRONZE),
	FILMMAKER			("Cinéaste", "a produit une vidéo", BadgeTypeLevel.GOLD),
	CITIZEN				("Citoyen", "a voté sur toutes les actions", BadgeTypeLevel.SILVER),
	STATISTICIAN 	    ("Statisticien"," a complété son appartenance à un ou plusieurs groupes", BadgeTypeLevel.BRONZE),
	RUBBERNECK          ("Badaud","a voté sur une action",BadgeTypeLevel.BRONZE),
	ELECTOR             ("Electeur","a voté sur 10 actions",BadgeTypeLevel.BRONZE);
	
	
	private BadgeType(String name, String description, BadgeTypeLevel badgeTypeLevel) {
		this.name = name;
		this.description = description;
		this.badgeTypeLevel = badgeTypeLevel;
	}

	
	String name;
	String description;
	BadgeTypeLevel badgeTypeLevel;
	
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
	public BadgeTypeLevel getBadgeTypeLevel() {
		return badgeTypeLevel;
	}
	public void setBadgeTypeLevel(BadgeTypeLevel badgeTypeLevel) {
		this.badgeTypeLevel = badgeTypeLevel;
	}
	
		
}
