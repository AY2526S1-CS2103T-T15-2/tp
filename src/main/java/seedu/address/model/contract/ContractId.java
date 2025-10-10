package seedu.address.model.contract;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.util.RandomUtil;

/**
 * Represents a Contract's id that a user can reference.
 * Guarantees: immutable.
 */
public class ContractId {

    public static final int ID_LENGTH = 6;
    public static final String MESSAGE_CONSTRAINTS = "Contract IDs should be alphanumeric";
    public static final String VALIDATION_REGEX = String.format("[a-zA-Z0-9]{%d}", ID_LENGTH);

    public final String value;

    /**
     * Constructs a {@code ContractId}.
     *
     * @param id A valid contract id.
     */
    public ContractId(String id) {
        requireNonNull(id);
        checkArgument(isValidContractId(id), MESSAGE_CONSTRAINTS);
        value = id;
    }

    /**
     * Generates a random alphanumeric id to assign to a Policy.
     */
    public static ContractId generate() {
        return new ContractId(RandomUtil.generateAlphanum(ID_LENGTH));
    }

    /**
     * Returns true if a given string is a valid Contract ID.
     */
    public static boolean isValidContractId(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContractId)) {
            return false;
        }

        ContractId otherPolicyId = (ContractId) other;
        return value.equals(otherPolicyId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
