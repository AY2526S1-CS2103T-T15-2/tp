package seedu.address.model.policy;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contract.Contract;

/**
 * Represents a Policy of a Contract.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Policy {

    private final PolicyName policyName;
    private final PolicyDetails policyDetails;
    private final PolicyId policyId;
    private final ObservableSet<Contract> contracts = FXCollections.observableSet(new HashSet<>());

    /**
     * Fields must be present and not null.
     */
    public Policy(PolicyName policyName, PolicyDetails policyDetails, PolicyId policyId) {
        requireAllNonNull(policyName, policyDetails);
        this.policyName = policyName;
        this.policyDetails = policyDetails;
        this.policyId = policyId;
    }

    /**
     * Constructor when constructing Policy with contracts.
     */
    public Policy(PolicyName policyName, PolicyDetails policyDetails, PolicyId policyId,
                  Set<Contract> contracts) {
        requireAllNonNull(policyName, policyDetails);
        this.policyName = policyName;
        this.policyDetails = policyDetails;
        this.policyId = policyId;
        this.contracts.addAll(contracts);
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

    public ObservableSet<Contract> getContracts() {
        return FXCollections.unmodifiableObservableSet(contracts);
    }

    /**
     * Adds the given contract.
     * {@code contract} must not already exist in the policy.
     */
    public void addContract(Contract contract) {
        if (containsContract(contract)) {
            return;
        }
        contracts.add(contract);
    }

    /**
     * Removes the given contract.
     * {@code contract} must exist in the policy.
     */
    public void removeContract(Contract contract) {
        contracts.remove(contract);
    }

    /**
     * Returns true if the policy has the given contract.
     * @param contract The contract to be checked.
     * @return True if the policy has the given contract, false otherwise.
     */
    private boolean containsContract(Contract contract) {
        return contracts.stream().anyMatch(contract::isSameContract);
    }

    /**
     * Returns true if both policies have the same policy id.
     */
    public boolean hasSameId(Policy otherPolicy) {
        if (otherPolicy == this) {
            return true;
        }

        return otherPolicy != null
                && otherPolicy.getId().equals(getId());
    }

    /**
     * Tests for equality of policies excluding the policy id.
     * This defines a weaker notion of equality between two policies.
     */
    public boolean isSamePolicy(Object other) {
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

    /**
     * Returns true if {@code policies} contains only unique policies by {@link Policy#isSamePolicy}.
     */
    public static boolean policiesAreUnique(List<Policy> policies) {
        for (int i = 0; i < policies.size() - 1; i++) {
            for (int j = i + 1; j < policies.size(); j++) {
                if (policies.get(i).isSamePolicy(policies.get(j))) {
                    return false;
                }
            }
        }
        return true;
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
