package seedu.address.model.policy;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Policy of a Contract.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Policy {

    private final Name name;
    private final Details details;
    private final Id id;

    /**
     * Fields must be present and not null.
     */
    public Policy(Name name, Details details, Id id) {
        requireAllNonNull(name, details);
        this.name = name;
        this.details = details;
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public Details getDetails() {
        return details;
    }

    public Id getId() {
        return id;
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
        return name.equals(otherPolicy.name) && details.equals(otherPolicy.details)
                && id.equals(otherPolicy.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, details, id);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("details", details)
                .add("id", id)
                .toString();
    }
}
