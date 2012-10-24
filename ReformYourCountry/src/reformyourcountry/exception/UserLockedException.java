package reformyourcountry.exception;

import reformyourcountry.model.User;

public class UserLockedException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private User user ;
    public UserLockedException(User user){
        
        this.user = user ;
    }
    public User getUser() {
        return user;
    }
    
    
    

}
