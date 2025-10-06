package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyTest {

    @Test
    public void equals() {
        Policy policy = new Policy(new Name("Healthcare - A"), new Details("policy details"));

        // same values -> returns true
        Policy policyCopy = new Policy(new Name("Healthcare - A"), new Details("policy details"));
        assertTrue(policy.equals(policyCopy));

        // same object -> returns true
        assertTrue(policy.equals(policy));

        // null -> returns false
        assertFalse(policy.equals(null));

        // different type -> returns false
        assertFalse(policy.equals(5));

        // different person -> returns false
        Policy differentPolicy = new Policy(new Name("Life"), new Details("other details"));
        assertFalse(policy.equals(differentPolicy));

        // different name -> returns false
        Policy editedPolicy = new Policy(new Name("Medical - A"), new Details("policy details"));
        assertFalse(policy.equals(editedPolicy));

        // different details -> returns false
        editedPolicy = new Policy(new Name("Healthcare - A"), new Details("other details"));
        assertFalse(policy.equals(editedPolicy));
    }

    @Test
    public void toStringMethod() {
        Policy policy = new Policy(new Name("Property - A"), new Details("policy details"));
        String expected = Policy.class.getCanonicalName() + "{name=" + policy.getName() + ", details=" + policy.getDetails() + "}";
        assertEquals(expected, policy.toString());
    }

}
