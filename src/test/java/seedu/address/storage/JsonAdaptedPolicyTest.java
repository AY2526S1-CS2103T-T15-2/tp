package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPolicy.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.getTypicalPolicies;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.PolicyName;

public class JsonAdaptedPolicyTest {
    private static final String INVALID_NAME = "Medical*";
    private static final String INVALID_DETAILS = " ";
    private static final String INVALID_ID = "abcd";

    private static final String VALID_NAME = "Life Insurance";
    private static final String VALID_DETAILS = "This policy coverage for family...";
    private static final String VALID_ID = "abcdef";

    private static final Policy SAMPLE_POLICY = new Policy(
            new PolicyName(VALID_NAME), new PolicyDetails(VALID_DETAILS), new PolicyId(VALID_ID));

    private static final List<Policy> policiesWithContracts = getTypicalPolicies();
    private static final Policy LIFE_POLICY_WITH_CONTRACT = policiesWithContracts.get(0);
    private static final List<JsonAdaptedContract> VALID_CONTRACTS = LIFE_POLICY_WITH_CONTRACT.getContracts().stream()
            .map(JsonAdaptedContract::new)
            .collect(Collectors.toUnmodifiableList());
    private static final JsonAdaptedContract INVALID_CONTRACT = new JsonAdaptedContract(
            "#C123A",
            "Alice Pauline",
            "S1234567A",
            "abcdef",
            "2023-01-01"
    );

    @Test
    public void toModelType_validPolicyValues_returnsPolicy() throws Exception {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy(SAMPLE_POLICY);
        assertEquals(SAMPLE_POLICY, policy.toModelType());
    }

    @Test
    public void toModelType_validPolicyValuesWithContract_returnsPolicy() throws Exception {
        SAMPLE_POLICY.addContract(VALID_CONTRACTS.get(0).toModelType());
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy(SAMPLE_POLICY);
        assertEquals(SAMPLE_POLICY, policy.toModelType());
    }

    @Test
    public void toModelType_invalidPolicyName_throwsIllegalValueException() {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy(INVALID_NAME, VALID_DETAILS, VALID_ID);
        String expectedMessage = PolicyName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, policy::toModelType);
    }

    @Test
    public void toModelType_nullPolicyName_throwsIllegalValueException() {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy(null, VALID_DETAILS, VALID_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PolicyName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, policy::toModelType);
    }

    @Test
    public void toModelType_invalidPolicyDetails_throwsIllegalValueException() {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy(VALID_NAME, INVALID_DETAILS, VALID_ID);
        String expectedMessage = PolicyDetails.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, policy::toModelType);
    }

    @Test
    public void toModelType_nullPolicyDetails_throwsIllegalValueException() {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy(VALID_NAME, null, VALID_ID);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PolicyDetails.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, policy::toModelType);
    }

    @Test
    public void toModelType_invalidPolicyId_throwsIllegalValueException() {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy(VALID_NAME, VALID_DETAILS, INVALID_ID);
        String expectedMessage = PolicyId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, policy::toModelType);
    }

    @Test
    public void toModelType_nullPolicyId_throwsIllegalValueException() {
        JsonAdaptedPolicy policy = new JsonAdaptedPolicy(VALID_NAME, VALID_DETAILS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PolicyId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, policy::toModelType);
    }

    @Test
    public void toModelType_invalidContract_throwsIllegalValueException() {
        List<JsonAdaptedContract> invalidContracts = new ArrayList<>(VALID_CONTRACTS);
        invalidContracts.add(INVALID_CONTRACT);
        JsonAdaptedPolicy policy =
                new JsonAdaptedPolicy(VALID_NAME, VALID_DETAILS, VALID_ID, invalidContracts);
        assertThrows(IllegalValueException.class, policy::toModelType);
    }

}
