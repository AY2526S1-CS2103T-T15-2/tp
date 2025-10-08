package seedu.address.model.contract.exceptions;

/**
 * Signals that the operation will result in duplicate Contracts (Contracts are considered duplicates if they have the
 * same NRIC, policy ID and sign date).
 */
public class DuplicateContractException extends RuntimeException {
    public DuplicateContractException() {
        super("Operation would result in duplicate contracts");
    }
}
