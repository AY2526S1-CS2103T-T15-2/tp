package seedu.address.model.policy;

import java.util.Random;

import seedu.address.model.util.RandomUtil;

/**
 * Represents a Policy's id that a user can reference.
 * Guarantees: immutable.
 */
public class PolicyId {

    private static final int ID_LENGTH = 6;

    public final String value;

    private PolicyId(String id) {
        value = id;
    }

    /**
     * Generates a random alphanumeric id to assign to a Policy.
     */
    public static PolicyId generate() {
        return new PolicyId(RandomUtil.generateAlphanum(ID_LENGTH));
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
        if (!(other instanceof PolicyId)) {
            return false;
        }

        PolicyId otherPolicyId = (PolicyId) other;
        return value.equals(otherPolicyId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
