package reformyourcountry.badge;

public enum BadgeType {
	
	AUTOBIOGRAPHER		("autobiographe", "a compléter tous les champs de son profile", BadgeTypeLevel.BRONZE),
	FILMMAKER			("cinéaste", "a produit une vidéo", BadgeTypeLevel.GOLD),
	CITIZEN				("citoyen", "a voté sur toutes les actions", BadgeTypeLevel.SILVER);
	
	
	
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
