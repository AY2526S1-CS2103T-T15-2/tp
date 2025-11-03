package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.util.RandomUtil;

/**
 * Represents a Policy's id that a user can reference.
 * Guarantees: immutable; is valid as declared in {@link #isValidPolicyId(String)}
 */
public class PolicyId {

    public static final int ID_LENGTH = 6;

    public static final String MESSAGE_CONSTRAINTS =
            String.format("Id should be an alphanumeric string of length %d\n", ID_LENGTH)
                    + "Note that capital 'i'(I), 'o'(O) and lowercase 'l'(l) are not used to avoid confusion.";

    public static final String VALIDATION_REGEX = String.format("[a-zA-Z0-9]{%d}", ID_LENGTH);

    public final String value;

    /**
     * Constructs a {@code PolicyId}.
     *
     * @param id A valid policy id.
     */
    public PolicyId(String id) {
        requireNonNull(id);
        checkArgument(isValidPolicyId(id), MESSAGE_CONSTRAINTS);
        value = id;
    }

    /**
     * Returns true if a given string is a valid policy id.
     */
    public static boolean isValidPolicyId(String test) {
        return test.matches(VALIDATION_REGEX);
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
