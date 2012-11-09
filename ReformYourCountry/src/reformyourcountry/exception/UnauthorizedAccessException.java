package reformyourcountry.exception;

import reformyourcountry.security.Privilege;
import reformyourcountry.security.SecurityContext;

public class UnauthorizedAccessException extends RuntimeException {

    private static final long serialVersionUID = 556690995736470233L;

    private Privilege privileges[];

    public UnauthorizedAccessException() {
        super();
    }

    private static String getCurrentUserString() {
        if (SecurityContext.getUser() != null) {
            return "Current user " +  SecurityContext.getUser().getUserName() + ", ";  
        } else {
            return "Unconnected visitor, ";  
        }
    }

    public UnauthorizedAccessException(String message) {
        super(getCurrentUserString() + message);
    }

    public UnauthorizedAccessException(String message, Throwable root) {
        super(getCurrentUserString() + message, root);
    }

    public UnauthorizedAccessException(Privilege privilege) {
        super(getCurrentUserString());
        privileges = new Privilege[] { privilege };
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Privilege[] getPrivileges() {
        return privileges;
    }

    public String getErrorMessage() {
        String result = "You need the following privilege(s): ";
        for (Privilege p : privileges) {
            result += p.getName() + " ";
        }
        return result;
    }
}
// public class UnauthorizedAccessException extends
// com.sun.servicetag.UnauthorizedAccessException{
//
// public UnauthorizedAccessException() {
// super();
// this.message =
// "You don't have the rights to access this part of the website";
// }
//
// public UnauthorizedAccessException(String message) {
// super();
// this.message = message;
// }
//
// public UnauthorizedAccessException(Privilege privilege) {
// super();
// this.message =
// "You don't have the rights to access this part of the website";
// this.privilege = privilege;
// }
// @Override
// public String toString(){
// return this.message;
// }
// private Privilege privilege;
// private String message;
// }
