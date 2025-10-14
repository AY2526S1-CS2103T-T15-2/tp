package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.PolicyUtil.unassign;
import static seedu.address.testutil.TypicalData.LIFE;

import org.junit.jupiter.api.Test;

import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.UnassignedPolicy;

public class AddPolicyCommandTest {

    @Test
    public void constructor_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPolicyCommand(null));
    }

    @Test
    public void equals() {
        UnassignedPolicy unassignedPolicy = new UnassignedPolicy(
                new PolicyName("Life Insurance"), new PolicyDetails("details")
        );
        UnassignedPolicy otherUnassignedPolicy = new UnassignedPolicy(
                new PolicyName("Healthcare"), new PolicyDetails("details")
        );
        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(unassignedPolicy);
        AddPolicyCommand addPolicyCommandOther = new AddPolicyCommand(otherUnassignedPolicy);

        // same object -> returns true
        assertTrue(addPolicyCommand.equals(addPolicyCommand));

        // same values -> returns true
        AddPolicyCommand addPolicyCommandCopy = new AddPolicyCommand(unassignedPolicy);
        assertTrue(addPolicyCommand.equals(addPolicyCommandCopy));

        // different types -> returns false
        assertFalse(addPolicyCommand.equals(1));

        // null -> returns false
        assertFalse(addPolicyCommand.equals(null));

        // different policy -> returns false
        assertFalse(addPolicyCommand.equals(addPolicyCommandOther));
    }

    @Test
    public void toStringMethod() {
        UnassignedPolicy unassignedPolicy = unassign(LIFE);
        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(unassignedPolicy);
        String expected = AddPolicyCommand.class.getCanonicalName() + "{toAdd=" + unassignedPolicy + "}";
        assertEquals(expected, addPolicyCommand.toString());
    }
}
