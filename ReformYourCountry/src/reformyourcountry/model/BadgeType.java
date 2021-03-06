package reformyourcountry.model;


public enum BadgeType {
	
	AUTOBIOGRAPHER		("Autobiographe", "a complété tous les champs de son profil", BadgeTypeLevel.BRONZE,true),
	FILMMAKER			("Cinéaste", "a produit une vidéo", BadgeTypeLevel.GOLD,false),
	STATISTICIAN 	    ("Statisticien"," a complété son appartenance à un ou plusieurs groupes", BadgeTypeLevel.BRONZE,false),
	RUBBERNECK          ("Badaud","a voté sur une action", BadgeTypeLevel.BRONZE,false),
	ELECTOR             ("Electeur","a voté sur 10 actions", BadgeTypeLevel.BRONZE,true),
	CITIZEN				("Citoyen", "a voté sur toutes les actions", BadgeTypeLevel.SILVER,true),
	REFEREE             ("Arbitre","a voté sur 10 arguments", BadgeTypeLevel.BRONZE,true),
	JUDGE               ("Juge","a voté sur 50 arguments", BadgeTypeLevel.SILVER,true),
	INQUISITOR          ("Inquisiteur","a voté sur 200 arguments", BadgeTypeLevel.GOLD,true),
	LAWYER              ("Avocat","A écrit 1 argument qui a recueilli un score de 10", BadgeTypeLevel.BRONZE, true),
	TENOR               ("Ténor","A écrit 5 arguments qui ont chacun recueilli un score de 20", BadgeTypeLevel.SILVER,true),
	LUMINARY            ("Sommité","A écrit 20 arguments qui ont chacun recueilli un score de 100", BadgeTypeLevel.GOLD,true),
	COMMENTATOR         ("Commentateur","A ajouté 10 commentaires à des arguments ou de bons exemples", BadgeTypeLevel.BRONZE,false),
	BLABBERMOUTH        ("Pipelette","A ajouté 100 commentaires à des arguments ou de bons exemples", BadgeTypeLevel.SILVER,false),
	EXAMPLE             ("Exemple", "A posté un premier bon exemple",BadgeTypeLevel.BRONZE,false),
	GOODEXAMPLE         ("Bon exemple", "A recueilli 10 votes sur l'ensemble de ses bons exemples", BadgeTypeLevel.SILVER,true),  
	MODEL               ("Modèle", "A recueilli 100 votes sur l'ensemble de ses bons exemples", BadgeTypeLevel.SILVER,true);
	
	private BadgeType(String name, String description, BadgeTypeLevel badgeTypeLevel,boolean mailConfirm) {
		this.name = name;
		this.description = description;
		this.badgeTypeLevel = badgeTypeLevel;
		this.mailConfirm = mailConfirm;
	}

	
	String name;
	String description;
	BadgeTypeLevel badgeTypeLevel;
	boolean mailConfirm;   // Needed for badges that are not delivered to the user who triggers the action (for example John votes on an argument or Cindy, and Cindy gets a badge and a mail)
	
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
