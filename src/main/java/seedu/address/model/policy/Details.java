package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Details {
    public static final String MESSAGE_CONSTRAINTS = "Details should not be blank";

    public static final String VALIDATION_REGEX = "\\S.*";

    private final String value;

    public Details(String details) {
        requireNonNull(details);
        checkArgument(isValidDetails(details), MESSAGE_CONSTRAINTS);
        value = details;
    }

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
