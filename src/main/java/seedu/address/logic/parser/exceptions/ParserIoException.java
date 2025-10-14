package seedu.address.logic.parser.exceptions;

/**
 * Signals that the parser raised an IOException.
 */
public class ParserIoException extends RuntimeException {
    public ParserIoException(String message) {
        super(message);
    }
}
