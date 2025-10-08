package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.Policy;


/**
 * Jackson-friendly version of {@link Policy}.
 */
class JsonAdaptedPolicy {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Policy's %s field is missing!";

    private final String id;
    private final String name;
    private final String detail;

    /**
     * Constructs a {@code JsonAdaptedPolicy} with the given policy details.
     */
    @JsonCreator
    public JsonAdaptedPolicy(@JsonProperty("name") String name,
                             @JsonProperty("detail") String detail,
                             @JsonProperty("id") String id) {
        this.name = name;
        this.detail = detail;
        this.id = id;
    }

    /**
     * Converts a given {@code Policy} into this class for Jackson use.
     */
    public JsonAdaptedPolicy(Policy source) {
        name = source.getName().value;
        detail = source.getDetails().value;
        id = source.getId().value;
    }

    /**
     * Converts this Jackson-friendly adapted policy object into the model's {@code Policy} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted policy.
     */
    public Policy toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, PolicyName.class.getSimpleName()));
        }
        if (!PolicyName.isValidPolicyName(name)) {
            throw new IllegalValueException(PolicyName.MESSAGE_CONSTRAINTS);
        }
        final PolicyName modelPolicyName = new PolicyName(name);

        if (detail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, PolicyDetails.class.getSimpleName()));
        }
        if (!PolicyDetails.isValidPolicyDetails(detail)) {
            throw new IllegalValueException(PolicyDetails.MESSAGE_CONSTRAINTS);
        }
        final PolicyDetails modelPolicyDetails = new PolicyDetails(detail);

        final PolicyId modelPolicyId = new PolicyId(id);

        return new Policy(modelPolicyName, modelPolicyDetails, modelPolicyId);
    }

}