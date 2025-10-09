package seedu.address.model.contract;

import seedu.address.model.util.RandomUtil;

/**
 * Represents a Policy's id that a user can reference.
 * Guarantees: immutable.
 */
public class ContractId {

    private static final int ID_LENGTH = 6;

    public final String value;

    private ContractId(String id) {
        value = id;
    }

    /**
     * Generates a random alphanumeric id to assign to a Policy.
     */
    public static ContractId generate() {
        return new ContractId(RandomUtil.generateAlphanum(ID_LENGTH));
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
