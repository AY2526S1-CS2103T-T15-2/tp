package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Policy's details.
 * Guarantees: immutable; is valid as declared in {@link #isValidPolicyDetails(String)}
 */
public class PolicyDetails {

    public static final String MESSAGE_CONSTRAINTS =
            "Details should only contain printable ASCII characters, and it should not be blank\n"
                    + "For details, refer to: https://www.ascii-code.com/characters/printable-characters";

    public static final String VALIDATION_REGEX = "[\\p{Print}&&[\\S]]\\p{Print}*";

    public final String value;

    /**
     * Constructs a {@code PolicyDetails}.
     *
     * @param details Valid policy details.
     */
    public PolicyDetails(String details) {
        requireNonNull(details);
        checkArgument(isValidPolicyDetails(details), MESSAGE_CONSTRAINTS);
        value = details;
    }

    /**
     * Returns true if a given string is valid policy details.
     */
    public static boolean isValidPolicyDetails(String test) {
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
        if (!(other instanceof PolicyDetails)) {
            return false;
        }

        PolicyDetails otherPolicyDetails = (PolicyDetails) other;
        return value.equals(otherPolicyDetails.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
