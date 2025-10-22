package seedu.address.testutil;

import seedu.address.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyName;

/**
 * A utility class to help with building EditPolicyDescriptor objects.
 */
public class EditPolicyDescriptorBuilder {

    private EditPolicyDescriptor descriptor;

    public EditPolicyDescriptorBuilder() {
        descriptor = new EditPolicyDescriptor();
    }

    /**
     * Returns an {@code EditPolicyDescriptor} with fields containing {@code policy}'s details
     */
    public EditPolicyDescriptorBuilder(Policy policy) {
        descriptor = new EditPolicyDescriptor();
        descriptor.setName(policy.getName());
        descriptor.setDetails(policy.getDetails());
    }

    /**
     * Sets the {@code PolicyName} of the {@code EditPolicyDescriptor} that we are building.
     */
    public EditPolicyDescriptorBuilder withName(String policyName) {
        descriptor.setName(new PolicyName(policyName));
        return this;
    }

    /**
     * Sets the {@code PolicyDetails} of the {@code EditPolicyDescriptor} that we are building.
     */
    public EditPolicyDescriptorBuilder withDetails(String policyDetails) {
        descriptor.setDetails(new PolicyDetails(policyDetails));
        return this;
    }

    public EditPolicyDescriptor build() {
        return descriptor;
    }
}
