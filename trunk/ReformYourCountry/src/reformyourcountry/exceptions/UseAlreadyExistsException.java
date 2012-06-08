package reformyourcountry.exceptions;


public class UseAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 5049031236161497138L;

    public enum identifierType {
	MAIL("email"), USERNAME("username");
	private String type;

	identifierType(String type) {
	    this.type = type;
	}
	
	public String toString()
	{
	    return type;
	}
    }
    
    private identifierType type;
    private String identifier;
    
    public UseAlreadyExistsException(identifierType type, String identifier) {
	super("user already exist for the "+type.toString()+" identifer : "+identifier);
	this.type = type;
	this.identifier = identifier;
    }
    
    

}
