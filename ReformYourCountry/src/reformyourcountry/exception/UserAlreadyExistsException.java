package reformyourcountry.exception;


public class UserAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 5049031236161497138L;

    public enum IdentifierType {
        MAIL("email"), USERNAME("username");
        private String type;

        IdentifierType(String type) {
            this.type = type;
        }
        

        public String toString() {
            return type;
        }
    }

    private IdentifierType type;
    private String identifier;

    public UserAlreadyExistsException(IdentifierType type, String identifier) {
        super("user already exist for the "+type.toString()+" identifer : "+identifier);
        this.type = type;
        this.identifier = identifier;
    }

    public IdentifierType getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    

}
