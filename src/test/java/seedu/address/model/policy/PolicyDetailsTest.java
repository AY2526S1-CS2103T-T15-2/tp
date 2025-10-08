package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PolicyDetailsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PolicyDetails(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidDetails = "";
        assertThrows(IllegalArgumentException.class, () -> new PolicyDetails(invalidDetails));
    }

    @Test
    public void isValidDetails() {
        // null details
        assertThrows(NullPointerException.class, () -> PolicyDetails.isValidPolicyDetails(null));

        // invalid details
        assertFalse(PolicyDetails.isValidPolicyDetails("")); // empty string
        assertFalse(PolicyDetails.isValidPolicyDetails(" ")); // spaces only

        // valid details
        assertTrue(PolicyDetails.isValidPolicyDetails("This policy"));
        assertTrue(PolicyDetails.isValidPolicyDetails("a")); // one character
        assertTrue(PolicyDetails.isValidPolicyDetails("This policy provides coverage for the insured and immediate family "
                + "members against medical emergencies, hospitalization, and accidental injuries.")); // long details
    }

    @Test
    public void equals() {
        PolicyDetails policyDetails = new PolicyDetails("Valid Details");

        // same values -> returns true
        assertTrue(policyDetails.equals(new PolicyDetails("Valid Details")));

        // same object -> returns true
        assertTrue(policyDetails.equals(policyDetails));

        // null -> returns false
        assertFalse(policyDetails.equals(null));

        // different types -> returns false
        assertFalse(policyDetails.equals(5.0f));

        // different values -> returns false
        assertFalse(policyDetails.equals(new PolicyDetails("Other Valid Details")));
    }
}
