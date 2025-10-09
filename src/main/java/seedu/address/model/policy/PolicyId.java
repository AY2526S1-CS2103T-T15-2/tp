package seedu.address.model.policy;

import java.util.Random;

/**
 * Represents a Policy's id that a user can reference.
 * Guarantees: immutable.
 */
public class PolicyId {

    public static final String MESSAGE_CONSTRAINTS =
            "Policy Id should only contain alphanumeric characters, and should be 6 characters long";
    private static final String ID_CHARACTERS = "abcdefghijklmnopqrstuvwxyz"
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789";
    private static final int ID_LENGTH = 6;

    private static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String value;

    public PolicyId(String id) {
        value = id;
    }

    /**
     * Generates a random alphanumeric id to assign to a Policy.
     */
    public static PolicyId generate() {
        Random random = new Random();
        String id = random.ints(0, ID_CHARACTERS.length())
                .map(ID_CHARACTERS::charAt)
                .limit(ID_LENGTH)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        return new PolicyId(id);
    }

    /**
     * Return true if a given string is a valid policy id
     */
    public static boolean isValidPolicyId(String test) {
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
