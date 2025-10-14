package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contract.Contract;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.PolicyName;

/**
 * Jackson-friendly version of {@link Policy}.
 */
class JsonAdaptedPolicy {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Policy's %s field is missing!";

    private final String name;
    private final String details;
    private final String id;
    private final List<JsonAdaptedContract> contracts = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPolicy} with the given policy details.
     */
    @JsonCreator
    public JsonAdaptedPolicy(@JsonProperty("name") String name,
                             @JsonProperty("details") String details,
                             @JsonProperty("id") String id,
                             @JsonProperty("contracts") List<JsonAdaptedContract> contracts) {
        this.name = name;
        this.details = details;
        this.id = id;
        if (contracts != null) {
            this.contracts.addAll(contracts);
        }
    }

    /**
     * Converts a given {@code Policy} into this class for Jackson use.
     */
    public JsonAdaptedPolicy(Policy source) {
        name = source.getName().value;
        details = source.getDetails().value;
        id = source.getId().value;
    }

    /**
     * Converts this Jackson-friendly adapted policy object into the model's {@code Policy} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted policy.
     */
    public Policy toModelType() throws IllegalValueException {
        final List<Contract> policyContracts = new ArrayList<>();
        for (JsonAdaptedContract contract : contracts) {
            policyContracts.add(contract.toModelType());
        }
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PolicyName.class.getSimpleName()));
        }
        if (!PolicyName.isValidPolicyName(name)) {
            throw new IllegalValueException(PolicyName.MESSAGE_CONSTRAINTS);
        }
        final PolicyName modelPolicyName = new PolicyName(name);

        if (details == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PolicyDetails.class.getSimpleName()));
        }
        if (!PolicyDetails.isValidPolicyDetails(details)) {
            throw new IllegalValueException(PolicyDetails.MESSAGE_CONSTRAINTS);
        }
        final PolicyDetails modelPolicyDetails = new PolicyDetails(details);

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PolicyId.class.getSimpleName()));
        }
        if (!PolicyId.isValidPolicyId(id)) {
            throw new IllegalValueException(PolicyId.MESSAGE_CONSTRAINTS);
        }
        final PolicyId modelPolicyId = new PolicyId(id);

        final Set<Contract> modelContracts = new HashSet<>(policyContracts);
        return new Policy(modelPolicyName, modelPolicyDetails, modelPolicyId, modelContracts);
    }

}
