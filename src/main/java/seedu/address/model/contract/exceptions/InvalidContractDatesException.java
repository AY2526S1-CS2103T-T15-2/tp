package seedu.address.model.contract.exceptions;

/**
 * Signals that the input dates create an invalid contract period
 */
public class InvalidContractDatesException extends RuntimeException {
    public InvalidContractDatesException() {
        super("Signing date comes after expiry date");
    }
}
