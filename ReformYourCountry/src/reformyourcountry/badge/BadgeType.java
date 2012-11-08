package reformyourcountry.badge;

public enum BadgeType {
	
	AUTOBIOGRAPHER		("autobiographe", "a compléter tous les champs de son profile", BadgeTypeLevel.BRONZE),
	STATISTICIAN		("statisticien", "a compléter son appartenance aux groupes", BadgeTypeLevel.BRONZE),
	ONLOOKER			("badaud", "à voter sur une action", BadgeTypeLevel.BRONZE),
	TOURIST				("touriste", "à voter sur 10 actions", BadgeTypeLevel.BRONZE),
	CONSULTANT			("consultant", "a écrit trois arguments", BadgeTypeLevel.BRONZE),
	LAWYER				("avocat", "a recueilli un score de 10 sur un argument", BadgeTypeLevel.SILVER),
	GOODEXAMPLE			("bon exemple", "a recueilli 10 votes sur un bon exemple", BadgeTypeLevel.BRONZE),
	FILMMAKER			("cinéaste", "a produit une vidéo", BadgeTypeLevel.SILVER ),
	POPULARFILMMAKER	("cinéaste populaire", "a produit une vidéo avec un score de 10", BadgeTypeLevel.SILVER ),
	SHERIFF				("shériff", "a rapporté 5 arguments ou exemples innapropriés", BadgeTypeLevel.SILVER ),
	CITIZEN				("citoyen", "a voté sur toutes les actions", BadgeTypeLevel.GOLD);
	
	
	
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
