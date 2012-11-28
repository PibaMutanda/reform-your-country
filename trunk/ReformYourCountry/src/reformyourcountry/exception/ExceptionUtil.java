package reformyourcountry.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.BatchUpdateException;

public class ExceptionUtil {

	/** Look for a BatchUpdateException in the causes, in order to display the real cause of that exception. */		
	public static void printBatchUpdateException(Throwable throwable, PrintStream out) {
		out.print(getStringBatchUpdateExceptionStackTrace(throwable,false));
	}
	public static String getStringBatchUpdateExceptionStackTrace(Throwable throwable, boolean htmlVersion) {
        String response ="";
        while (throwable != null) {
            response += htmlVersion ? "<br/>" : "\n";
            response += getStackTrace(throwable);
            if (throwable instanceof BatchUpdateException) {
                BatchUpdateException bue = (BatchUpdateException)throwable;
                
                response += "XXXXXXXXXXXXXXXXX NEXT from BatchUpdateException";
                response += bue.getNextException().getMessage();
            }
            throwable = getCauseException(throwable);
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
	 protected static String getStackTrace(Throwable aThrowable) {
	     final Writer result = new StringWriter();
	     final PrintWriter printWriter = new PrintWriter(result);
	     aThrowable.printStackTrace(printWriter);
	     return result.toString();
	   }
}
