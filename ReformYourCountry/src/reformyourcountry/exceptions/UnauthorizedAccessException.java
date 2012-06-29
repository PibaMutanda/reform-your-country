package reformyourcountry.exceptions;

import reformyourcountry.security.Privilege;

@SuppressWarnings("serial")
public class UnauthorizedAccessException extends com.sun.servicetag.UnauthorizedAccessException{
                   
       public UnauthorizedAccessException() {
                super();
             }
            
       public UnauthorizedAccessException(String message) {
                super();
                this.message = message;
            }
       
       public UnauthorizedAccessException(Privilege privilege) {
                super();
                this.privilege = privilege;
            }
            
 private Privilege privilege;
 private String message;   
}
