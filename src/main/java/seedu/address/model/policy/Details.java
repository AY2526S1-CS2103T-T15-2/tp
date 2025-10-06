package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;

public class Details {
    private final String value;

    public Details(String details) {
        requireNonNull(details);
        value = details;
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
