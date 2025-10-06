package seedu.address.model.policy;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

public class Policy {

    private final Name name;
    private final Details details;

    public Policy(Name name, Details details) {
        requireAllNonNull(name, details);
        this.name = name;
        this.details = details;
    }

    public Name getName() {
        return name;
    }

    public Details getDetails() {
        return details;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Policy)) {
            return false;
        }

        Policy otherPolicy = (Policy) other;
        return name.equals(otherPolicy.name) && details.equals(otherPolicy.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, details);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("details", details)
                .toString();
    }
}
