package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PolicyNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PolicyName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new PolicyName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> PolicyName.isValidPolicyName(null));

        // invalid name
        assertFalse(PolicyName.isValidPolicyName("")); // empty string
        assertFalse(PolicyName.isValidPolicyName(" ")); // spaces only
        assertFalse(PolicyName.isValidPolicyName("^")); // only non-alphanumeric characters
        assertFalse(PolicyName.isValidPolicyName("medical*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(PolicyName.isValidPolicyName("property")); // alphabets only
        assertTrue(PolicyName.isValidPolicyName("12345")); // numbers only
        assertTrue(PolicyName.isValidPolicyName("Life")); // with capital letters
        assertTrue(PolicyName.isValidPolicyName("Healthcare - A1")); // alphanumeric with dashes
    }

    @Test
    public void equals() {
        PolicyName policyName = new PolicyName("Valid Name");

        // same values -> returns true
        assertTrue(policyName.equals(new PolicyName("Valid Name")));

        // same object -> returns true
        assertTrue(policyName.equals(policyName));

        // null -> returns false
        assertFalse(policyName.equals(null));

        // different types -> returns false
        assertFalse(policyName.equals(5.0f));

        // different values -> returns false
        assertFalse(policyName.equals(new PolicyName("Other Valid Name")));
    }
}
