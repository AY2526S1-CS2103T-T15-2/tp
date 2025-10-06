package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Policy's details.
 * Guarantees: immutable; is valid as declared in {@link #isValidDetails(String)}
 */
public class Details {

    public static final String MESSAGE_CONSTRAINTS = "Details should not be blank";

    public static final String VALIDATION_REGEX = "\\S.*";

    public final String value;

    /**
     * Constructs a {@code Details}.
     *
     * @param details Valid details.
     */
    public Details(String details) {
        requireNonNull(details);
        checkArgument(isValidDetails(details), MESSAGE_CONSTRAINTS);
        value = details;
    }

    /**
     * Returns true if a given string is valid details.
     */
    public static boolean isValidDetails(String test) {
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
        if (!(other instanceof Details)) {
            return false;
        }

        Details otherDetails = (Details) other;
        return value.equals(otherDetails.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
