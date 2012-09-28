package reformyourcountry.exception;

public class SocialAccountAlreadyExistException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String userName;
    
    
    public SocialAccountAlreadyExistException(String userName, String message) {
        super(message);
        this.userName = userName;
    }
    
    public String getUserName(){
        
        return userName;
    }

}
