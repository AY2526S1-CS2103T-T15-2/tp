package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyIdTest {

    @Test
    public void equals() {
        PolicyId policyId = PolicyId.generate();

        // same object -> returns true
        assertTrue(policyId.equals(policyId));

        // null -> returns false
        assertFalse(policyId.equals(null));

        // different types -> returns false
        assertFalse(policyId.equals(5.0f));

        // different values -> returns false
        // note: randomly generated
        assertFalse(policyId.equals(PolicyId.generate()));
    }
}
