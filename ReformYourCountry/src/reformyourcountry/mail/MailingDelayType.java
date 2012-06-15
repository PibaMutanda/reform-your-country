package reformyourcountry.mail;

public enum MailingDelayType {
	
    IMMEDIATELY("Immediately", "A mail is sent as soon as an event occur (new belt, someone edits one of your question ...)."),
	DAILY("Daily", "The notification mails are grouped and sent once a day in a single mail, except for urgent mail."),
	WEEKLY("Weekly", "The notification mails are grouped and sent once a week in a single mail, except for urgent mail.");

    
	private MailingDelayType(String name, String description){
	    this.name = name;
	    this.description = description;
	}

	
	private String name;
    private String description;
    
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
	
}
