package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Policy's name.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class PolicyName {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters, spaces, and dashes, and it should not be blank";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}-][\\p{Alnum}- ]*";

    public final String value;

    /**
     * Constructs a {@code Name}.
     *
     * @param name A valid name.
     */
    public PolicyName(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        value = name;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
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
        if (!(other instanceof PolicyName)) {
            return false;
        }

        PolicyName otherPolicyName = (PolicyName) other;
        return value.equals(otherPolicyName.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
