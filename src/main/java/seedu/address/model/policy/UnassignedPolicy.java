package seedu.address.model.policy;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Policy of a Contract without an id.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class UnassignedPolicy {

    private final PolicyName policyName;
    private final PolicyDetails policyDetails;

    /**
     * Fields must be present and not null.
     */
    public UnassignedPolicy(PolicyName policyName, PolicyDetails policyDetails) {
        requireAllNonNull(policyName, policyDetails);
        this.policyName = policyName;
        this.policyDetails = policyDetails;
    }

    public PolicyName getName() {
        return policyName;
    }

    public PolicyDetails getDetails() {
        return policyDetails;
    }

    public Policy assignId(PolicyId policyId) {
        return new Policy(policyName, policyDetails, policyId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnassignedPolicy)) {
            return false;
        }

        UnassignedPolicy otherUnassignedPolicy = (UnassignedPolicy) other;
        return policyName.equals(otherUnassignedPolicy.policyName)
                && policyDetails.equals(otherUnassignedPolicy.policyDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(policyName, policyDetails);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", policyName)
                .add("details", policyDetails)
                .toString();
    }
}
