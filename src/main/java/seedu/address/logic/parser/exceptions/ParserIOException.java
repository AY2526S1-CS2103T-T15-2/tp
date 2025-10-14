package seedu.address.logic.parser.exceptions;

/**
 * Signals that the parser raised an IOException.
 */
public class ParserIOException extends RuntimeException {
    public ParserIOException(String message) {
        super(message);
    }
}
