package seedu.address.testutil;

import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.PolicyName;

/**
 * A utility class to help with building Policy objects.
 */
public class PolicyBuilder {

    public static final String DEFAULT_NAME = "Life Insurance - A";
    public static final String DEFAULT_DETAILS = "Policy details placeholder";
    public static final String DEFAULT_ID = "F3WsAp";

    private PolicyName name;
    private PolicyDetails details;
    private PolicyId id;

    /**
     * Creates a {@code PolicyBuilder} with the default details.
     */
    public PolicyBuilder() {
        name = new PolicyName(DEFAULT_NAME);
        details = new PolicyDetails(DEFAULT_DETAILS);
        id = new PolicyId(DEFAULT_ID);
    }

    /**
     * Initializes the PolicyBuilder with the data of {@code policyToCopy}.
     */
    public PolicyBuilder(Policy policyToCopy) {
        name = policyToCopy.getName();
        details = policyToCopy.getDetails();
        id = policyToCopy.getId();
    }

    /**
     * Sets the {@code PolicyName} of the {@code Policy} that we are building.
     */
    public PolicyBuilder withName(String name) {
        this.name = new PolicyName(name);
        return this;
    }

    /**
     * Sets the {@code PolicyDetails} of the {@code Policy} that we are building.
     */
    public PolicyBuilder withDetails(String address) {
        this.details = new PolicyDetails(address);
        return this;
    }

    /**
     * Sets the {@code PolicyId} of the {@code Policy} that we are building.
     */
    public PolicyBuilder withId(String phone) {
        this.id = new PolicyId(phone);
        return this;
    }

    public Policy build() {
        return new Policy(name, details, id);
    }

}
