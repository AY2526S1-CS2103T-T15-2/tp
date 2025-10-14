package seedu.address.logic.parser.exceptions;

/**
 * Signals that the parser encountered an invalidly formatted input line.
 */
public class InvalidLineException extends RuntimeException {
    public InvalidLineException(String message) {
        super(message);
    }
}
