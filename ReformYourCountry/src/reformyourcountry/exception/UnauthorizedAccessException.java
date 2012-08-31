package reformyourcountry.exception;

import reformyourcountry.security.Privilege;

@SuppressWarnings("serial")
public class UnauthorizedAccessException extends com.sun.servicetag.UnauthorizedAccessException{
                   
       public UnauthorizedAccessException() {
                super();
                this.message = "You don't have the rights to access this part of the website";
             }
            
       public UnauthorizedAccessException(String message) {
                super();
                this.message = message;
            }
       
       public UnauthorizedAccessException(Privilege privilege) {
                super();
                this.message = "You don't have the rights to access this part of the website";
                this.privilege = privilege;
            }
       @Override
       public String toString(){
           return this.message;
       }
 private Privilege privilege;
 private String message;   
}
