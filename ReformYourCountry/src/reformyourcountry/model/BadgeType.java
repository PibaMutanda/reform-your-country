package reformyourcountry.model;


public enum BadgeType {
	
	AUTOBIOGRAPHER		("Autobiographe", "a complété tous les champs de son profil", BadgeTypeLevel.BRONZE,true),
	FILMMAKER			("Cinéaste", "a produit une vidéo", BadgeTypeLevel.GOLD,false),
	CITIZEN				("Citoyen", "a voté sur toutes les actions", BadgeTypeLevel.SILVER,true),
	STATISTICIAN 	    ("Statisticien"," a complété son appartenance à un ou plusieurs groupes", BadgeTypeLevel.BRONZE,false),
	RUBBERNECK          ("Badaud","a voté sur une action",BadgeTypeLevel.BRONZE,false),
	ELECTOR             ("Electeur","a voté sur 10 actions",BadgeTypeLevel.BRONZE,true),
	REFEREE             ("Arbitre","a voté sur 10 arguments",BadgeTypeLevel.BRONZE,true),
	JUDGE               ("Juge","a voté sur 50 arguments",BadgeTypeLevel.SILVER,true),
	INQUISITOR          ("Inquisiteur","a voté sur 200 arguments",BadgeTypeLevel.GOLD,true),
	LAWYER              ("Avocat","A écrit 1 argument qui a recueilli un score de 10",BadgeTypeLevel.BRONZE,false),
	TENOR               ("Ténor","A écrit 5 argument qui ont recueilli un score de 20",BadgeTypeLevel.SILVER,true),
	LUMINARY            ("Sommité","A écrit 20 argument qui ont recueilli un score de 100",BadgeTypeLevel.GOLD,true);
	
	private BadgeType(String name, String description, BadgeTypeLevel badgeTypeLevel,boolean mailConfirm) {
		this.name = name;
		this.description = description;
		this.badgeTypeLevel = badgeTypeLevel;
		this.mailConfirm = mailConfirm;
	}

	
	String name;
	String description;
	BadgeTypeLevel badgeTypeLevel;
	boolean mailConfirm;
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
	
	public boolean isMailConfirm(){
	    return mailConfirm;
	}
	
		
}
