package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PolicyTest {

    @Test
    public void hasSameId() {
        Policy policy = new Policy(new PolicyName("Life"), new PolicyDetails("details"), new PolicyId("abcdef"));

        // same object -> returns true
        assertTrue(policy.hasSameId(policy));

        // null -> returns false
        assertFalse(policy.hasSameId(null));

        // same id, all other attributes different -> returns true
        Policy editedPolicy = new Policy(new PolicyName("Health"), new PolicyDetails("others"), new PolicyId("abcdef"));
        assertTrue(policy.hasSameId(editedPolicy));

        // different id, all other attributes same -> returns false
        editedPolicy = new Policy(new PolicyName("Life"), new PolicyDetails("details"), new PolicyId("123456"));
        assertFalse(policy.hasSameId(editedPolicy));

        // id differs in case, all other attributes same -> returns false
        editedPolicy = new Policy(new PolicyName("Life"), new PolicyDetails("details"), new PolicyId("ABCDEF"));
        assertFalse(policy.hasSameId(editedPolicy));
    }

    @Test
    public void equals() {
        Policy policy = new Policy(new PolicyName("Healthcare - A"), new PolicyDetails("policy details"),
                new PolicyId("abcdef"));

        // same values -> returns true
        Policy policyCopy = new Policy(new PolicyName("Healthcare - A"), new PolicyDetails("policy details"),
                new PolicyId("abcdef"));
        assertTrue(policy.equals(policyCopy));

        // same object -> returns true
        assertTrue(policy.equals(policy));

        // null -> returns false
        assertFalse(policy.equals(null));

        // different type -> returns false
        assertFalse(policy.equals(5));

        // different person -> returns false
        Policy differentPolicy = new Policy(new PolicyName("Life"), new PolicyDetails("other details"),
                new PolicyId("abcdef"));
        assertFalse(policy.equals(differentPolicy));

        // different name -> returns false
        Policy editedPolicy = new Policy(new PolicyName("Medical - A"), new PolicyDetails("policy details"),
                new PolicyId("abcdef"));
        assertFalse(policy.equals(editedPolicy));

        // different details -> returns false
        editedPolicy = new Policy(new PolicyName("Healthcare - A"), new PolicyDetails("other details"),
                new PolicyId("abcdef"));
        assertFalse(policy.equals(editedPolicy));

        // different id -> returns false
        editedPolicy = new Policy(new PolicyName("Healthcare - A"), new PolicyDetails("policy details"),
                new PolicyId("123456"));
        assertFalse(policy.equals(editedPolicy));
    }

    @Test
    public void toStringMethod() {
        Policy policy = new Policy(new PolicyName("Property - A"), new PolicyDetails("policy details"),
                PolicyId.generate());
        String expected = Policy.class.getCanonicalName() + "{name=" + policy.getName()
                + ", details=" + policy.getDetails() + ", id=" + policy.getId() + "}";
        assertEquals(expected, policy.toString());
    }

}
