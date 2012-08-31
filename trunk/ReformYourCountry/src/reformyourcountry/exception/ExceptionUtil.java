package reformyourcountry.exception;

import java.io.PrintStream;
import java.sql.BatchUpdateException;

public class ExceptionUtil {

	/** Look for a BatchUpdateException in the causes, in order to display the real cause of that exception. */		
	public static void printBatchUpdateException(Throwable throwable, PrintStream out) {
		Throwable cause = getCauseException(throwable);
		while (cause != null) {
			if (cause instanceof BatchUpdateException) {
				BatchUpdateException bue = (BatchUpdateException)cause;
				out.println();
				out.println("XXXXXXXXXXXXXXXXX NEXT from BatchUpdateException");
				bue.getNextException().printStackTrace(out);
			}
			cause = getCauseException(cause);
		}				

	}
	public static String printBatchUpdateException(Throwable throwable) {
        Throwable cause = getCauseException(throwable);
        String response ="";
        while (cause != null) {
            if (cause instanceof BatchUpdateException) {
                BatchUpdateException bue = (BatchUpdateException)cause;
                response +="<br>";
                response +=("XXXXXXXXXXXXXXXXX NEXT from BatchUpdateException");
                response +=bue.getNextException().getMessage();
            }
            cause = getCauseException(cause);
        }               
        return response;
    }
	protected static Throwable getCauseException(Throwable t) {
		if (t instanceof Exception) {
			return t.getCause();
		} else {
			return null;
		}
	}
}
