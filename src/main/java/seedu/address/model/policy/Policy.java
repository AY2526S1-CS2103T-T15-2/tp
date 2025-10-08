package seedu.address.model.policy;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Policy of a Contract.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Policy {

    private final PolicyName policyName;
    private final PolicyDetails policyDetails;
    private final PolicyId policyId;

    /**
     * Fields must be present and not null.
     */
    public Policy(PolicyName policyName, PolicyDetails policyDetails, PolicyId policyId) {
        requireAllNonNull(policyName, policyDetails);
        this.policyName = policyName;
        this.policyDetails = policyDetails;
        this.policyId = policyId;
    }

    public PolicyName getName() {
        return policyName;
    }

    public PolicyDetails getDetails() {
        return policyDetails;
    }

    public PolicyId getId() {
        return policyId;
    }

    /**
     * Test for equality of fields other than policyId.
     * Mainly used for unit testing.
     */
    public boolean partialEquals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Policy)) {
            return false;
        }

        Policy otherPolicy = (Policy) other;
        return policyName.equals(otherPolicy.policyName) && policyDetails.equals(otherPolicy.policyDetails);
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
        return policyName.equals(otherPolicy.policyName) && policyDetails.equals(otherPolicy.policyDetails)
                && policyId.equals(otherPolicy.policyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(policyName, policyDetails, policyId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", policyName)
                .add("details", policyDetails)
                .add("id", policyId)
                .toString();
    }
}
