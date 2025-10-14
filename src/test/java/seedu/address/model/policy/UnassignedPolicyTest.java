package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HOME;
import static seedu.address.testutil.PolicyUtil.unassign;
import static seedu.address.testutil.TypicalData.HEALTH_B;
import static seedu.address.testutil.TypicalData.HOME;

import org.junit.jupiter.api.Test;

public class UnassignedPolicyTest {

    @Test
    public void assignId() {
        PolicyId policyId = new PolicyId(VALID_POLICY_ID_HOME);
        UnassignedPolicy unassignedPolicy = unassign(HOME);
        Policy policy = unassignedPolicy.assignId(policyId);

        assertTrue(policy.getName().equals(unassignedPolicy.getName())
                && policy.getDetails().equals(unassignedPolicy.getDetails())
                && policy.getId().equals(policyId)
        );
    }
    
    @Test
    public void equals() {
        UnassignedPolicy unassignedPolicy = unassign(HOME);

        // same values -> returns true
        UnassignedPolicy unassignedPolicyCopy = unassign(HOME);
        assertTrue(unassignedPolicy.equals(unassignedPolicyCopy));

        // same object -> returns true
        assertTrue(unassignedPolicy.equals(unassignedPolicy));

        // null -> returns false
        assertFalse(unassignedPolicy.equals(null));

        // different type -> returns false
        assertFalse(unassignedPolicy.equals(5));

        // different unassigned policy -> returns false
        assertFalse(unassignedPolicy.equals(unassign(HEALTH_B)));

        // different name -> returns false
        UnassignedPolicy editedUnassignedPolicy = new UnassignedPolicy(HOME.getName(), HEALTH_B.getDetails());
        assertFalse(unassignedPolicy.equals(editedUnassignedPolicy));

        // different details -> returns false
        editedUnassignedPolicy = new UnassignedPolicy(HEALTH_B.getName(), HOME.getDetails());
        assertFalse(unassignedPolicy.equals(editedUnassignedPolicy));
    }

    @Test
    public void toStringMethod() {
        String expected = UnassignedPolicy.class.getCanonicalName() + "{name=" + HOME.getName()
                + ", details=" + HOME.getDetails() + "}";
        assertEquals(expected, unassign(HOME).toString());
    }

}
