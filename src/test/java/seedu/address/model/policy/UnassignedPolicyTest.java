package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HOME;
import static seedu.address.testutil.PolicyUtil.unassign;
import static seedu.address.testutil.TypicalData.getHealthB;
import static seedu.address.testutil.TypicalData.getHome;

import org.junit.jupiter.api.Test;

public class UnassignedPolicyTest {

    @Test
    public void assignId() {
        PolicyId policyId = new PolicyId(VALID_POLICY_ID_HOME);
        UnassignedPolicy unassignedPolicy = unassign(getHome());
        Policy policy = unassignedPolicy.assignId(policyId);

        assertTrue(policy.getName().equals(unassignedPolicy.getName())
                && policy.getDetails().equals(unassignedPolicy.getDetails())
                && policy.getId().equals(policyId)
        );
    }

    @Test
    public void equals() {
        UnassignedPolicy unassignedPolicy = unassign(getHome());

        // same values -> returns true
        UnassignedPolicy unassignedPolicyCopy = unassign(getHome());
        assertTrue(unassignedPolicy.equals(unassignedPolicyCopy));

        // same object -> returns true
        assertTrue(unassignedPolicy.equals(unassignedPolicy));

        // null -> returns false
        assertFalse(unassignedPolicy.equals(null));

        // different type -> returns false
        assertFalse(unassignedPolicy.equals(5));

        // different unassigned policy -> returns false
        assertFalse(unassignedPolicy.equals(unassign(getHealthB())));

        // different name -> returns false
        UnassignedPolicy editedUnassignedPolicy = new UnassignedPolicy(getHome().getName(), getHealthB().getDetails());
        assertFalse(unassignedPolicy.equals(editedUnassignedPolicy));

        // different details -> returns false
        editedUnassignedPolicy = new UnassignedPolicy(getHealthB().getName(), getHome().getDetails());
        assertFalse(unassignedPolicy.equals(editedUnassignedPolicy));
    }

    @Test
    public void toStringMethod() {
        String expected = UnassignedPolicy.class.getCanonicalName() + "{name=" + getHome().getName()
                + ", details=" + getHome().getDetails() + "}";
        assertEquals(expected, unassign(getHome()).toString());
    }

}
