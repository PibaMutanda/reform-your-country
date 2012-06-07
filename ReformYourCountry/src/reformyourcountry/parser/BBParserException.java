package reformyourcountry.parser;

public class BBParserException extends RuntimeException {
    public BBParserException() {
    }

    public BBParserException(Throwable cause) {
        super(cause);
    }

    public BBParserException(String message) {
        super(message);
    }

    public BBParserException(String message, Throwable cause) {
        super(message, cause);
    }
}