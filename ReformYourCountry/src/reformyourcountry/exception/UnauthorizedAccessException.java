package reformyourcountry.exception;

import reformyourcountry.security.Privilege;


public class UnauthorizedAccessException extends RuntimeException {
    
    
    private static final long serialVersionUID = 556690995736470233L;
    
    private Privilege privileges[];

    public UnauthorizedAccessException(){
        super();
    }
    
    public UnauthorizedAccessException(String message){
        super(message);
    }
    
    public UnauthorizedAccessException(String message, Throwable root){
        super(message, root);
    }

    public UnauthorizedAccessException(Privilege privilege) {
        privileges = new Privilege[]{privilege};
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Privilege[] getPrivileges() {
        return privileges;
    }
    public String getErrorMessage(){
        if (privileges!= null && privileges.length>0){
            return "You need the privilege: "+privileges[0].getAssociatedRole().toString();
        }
        return "";
    }
}
//public class UnauthorizedAccessException extends com.sun.servicetag.UnauthorizedAccessException{
//                   
//       public UnauthorizedAccessException() {
//                super();
//                this.message = "You don't have the rights to access this part of the website";
//             }
//            
//       public UnauthorizedAccessException(String message) {
//                super();
//                this.message = message;
//            }
//       
//       public UnauthorizedAccessException(Privilege privilege) {
//                super();
//                this.message = "You don't have the rights to access this part of the website";
//                this.privilege = privilege;
//            }
//       @Override
//       public String toString(){
//           return this.message;
//       }
// private Privilege privilege;
// private String message;   
//}
