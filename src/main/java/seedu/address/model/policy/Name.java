package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Name {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters, spaces, and dashes, and it should not be blank";

    public static final String VALIDATION_REGEX = "[\\p{Alnum}-][\\p{Alnum}- ]*";

    public final String value;

    public Name(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        value = name;
    }

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
        if (!(other instanceof Name)) {
            return false;
        }

        Name otherName = (Name) other;
        return value.equals(otherName.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
