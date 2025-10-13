package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.LIFE;

import org.junit.jupiter.api.Test;

import seedu.address.model.policy.Policy;
import seedu.address.testutil.PolicyBuilder;

public class AddPolicyCommandTest {

    @Test
    public void constructor_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPolicyCommand(null));
    }

    @Test
    public void equals() {
        Policy policy = new PolicyBuilder().withName("Life Insurance").build();
        Policy otherPolicy = new PolicyBuilder().withName("Healthcare").build();
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
        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(LIFE);
        String expected = AddPolicyCommand.class.getCanonicalName() + "{toAdd=" + LIFE + "}";
        assertEquals(expected, addPolicyCommand.toString());
    }
}
