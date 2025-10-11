package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.LIFE;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;
import static seedu.address.testutil.TypicalId.VALID_POLICY_ID_2;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code RemovePolicyCommand}.
 */
public class RemovePolicyCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validPolicyIdUnfilteredList_success() {
        RemovePolicyCommand removePolicyCommand = new RemovePolicyCommand(VALID_POLICY_ID_2);

        String expectedMessage = String.format(RemovePolicyCommand.MESSAGE_REMOVE_POLICY_SUCCESS, VALID_POLICY_ID_2);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removePolicy(LIFE);

        assertCommandSuccess(removePolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPolicyIdUnfilteredList_throwsCommandException() {
        RemovePolicyCommand removePolicyCommand = new RemovePolicyCommand(VALID_POLICY_ID_2);

        // First removal should be successful
        try {
            removePolicyCommand.execute(model);
        } catch (Exception e) {
            // Ignore
        }

        // Second removal should fail as the policy has already been removed
        assertCommandFailure(removePolicyCommand, model, Messages.MESSAGE_INVALID_POLICY_ID);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        RemovePolicyCommand removePolicyCommand = new RemovePolicyCommand(VALID_POLICY_ID_2);
        assertThrows(NullPointerException.class, () -> removePolicyCommand.execute(null));
    }

    @Test
    public void equals() {
        RemovePolicyCommand removeFirstCommand = new RemovePolicyCommand(VALID_POLICY_ID_2);
        RemovePolicyCommand removeSecondCommand = new RemovePolicyCommand(VALID_POLICY_ID_2);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // same values -> returns true
        assertTrue(removeFirstCommand.equals(removeSecondCommand));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(5.0f));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));
    }

    @Test
    public void toString_test() {
        RemovePolicyCommand removePolicyCommand = new RemovePolicyCommand(VALID_POLICY_ID_2);
        String expectedString = "seedu.address.logic.commands.RemovePolicyCommand{targetId=" + VALID_POLICY_ID_2 + "}";
        assertEquals(expectedString, removePolicyCommand.toString());
    }
}
