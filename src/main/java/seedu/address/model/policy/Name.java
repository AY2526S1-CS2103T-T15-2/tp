package seedu.address.model.policy;

import static java.util.Objects.requireNonNull;

public class Name {
    private final String value;

    public Name(String name) {
        requireNonNull(name);
        value = name;
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
