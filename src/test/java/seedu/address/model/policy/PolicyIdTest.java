package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PolicyIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PolicyId(null));
    }

    @Test
    public void constructor_invalidPolicyId_throwsIllegalArgumentException() {
        String invalidPolicyId = "";
        assertThrows(IllegalArgumentException.class, () -> new PolicyId(invalidPolicyId));
    }

    @Test
    public void isValidPolicyId() {
        // null id
        assertThrows(NullPointerException.class, () -> PolicyId.isValidPolicyId(null));

        // invalid name
        assertFalse(PolicyId.isValidPolicyId("")); // empty string
        assertFalse(PolicyId.isValidPolicyId("      ")); // spaces only
        assertFalse(PolicyId.isValidPolicyId("^&*+-$")); // only non-alphanumeric characters
        assertFalse(PolicyId.isValidPolicyId("abcde*")); // contains non-alphanumeric characters
        assertFalse(PolicyId.isValidPolicyId("abcd")); // incorrect length

        // valid name
        assertTrue(PolicyId.isValidPolicyId("abcdef")); // alphabets only
        assertTrue(PolicyId.isValidPolicyId("123456")); // numbers only
        assertTrue(PolicyId.isValidPolicyId("String")); // with capital letters
        assertTrue(PolicyId.isValidPolicyId("Abc123")); // alphanumeric
    }

    @Test
    public void generate() {
        assertEquals(PolicyId.ID_LENGTH, PolicyId.generate().value.length());
    }

    @Test
    public void equals() {
        PolicyId policyId = new PolicyId("abcdef");

        // same object -> returns true
        assertTrue(policyId.equals(policyId));

        // null -> returns false
        assertFalse(policyId.equals(null));

        // different types -> returns false
        assertFalse(policyId.equals(5.0f));

        // different values -> returns false
        // note: randomly generated
        assertFalse(policyId.equals(new PolicyId("123456")));
    }
}
