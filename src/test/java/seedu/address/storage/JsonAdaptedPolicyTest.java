package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPolicy.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;

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

    @Test
    public void toModelType_validPolicyValues_returnsPolicy() throws Exception {
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

}
