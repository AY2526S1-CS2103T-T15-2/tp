package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.PolicyName;

public class AddPolicyCommandTest {

    @Test
    public void constructor_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPolicyCommand(null));
    }

    @Test
    public void equals() {
        Policy policy = new Policy(new PolicyName("Life Insurance"), new PolicyDetails("policy details"),
                PolicyId.generate());
        Policy otherPolicy = new Policy(new PolicyName("Healthcare"), new PolicyDetails("other policy details"),
                PolicyId.generate());
        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(policy);
        AddPolicyCommand addPolicyCommandOther = new AddPolicyCommand(otherPolicy);

        // same object -> returns true
        assertTrue(addPolicyCommand.equals(addPolicyCommand));

        // same values -> returns true
        AddPolicyCommand addPolicyCommandCopy = new AddPolicyCommand(policy);
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
        Policy policy = new Policy(new PolicyName("Life Insurance"), new PolicyDetails("policy details"),
                PolicyId.generate());
        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(policy);
        String expected = AddPolicyCommand.class.getCanonicalName() + "{toAdd=" + policy + "}";
        assertEquals(expected, addPolicyCommand.toString());
    }
}
