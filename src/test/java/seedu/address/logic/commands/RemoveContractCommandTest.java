package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.CONTRACT_A;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;
import static seedu.address.testutil.TypicalId.CONTRACT_A_ID;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.ListPanelType;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code RemoveContractCommand}.
 */
public class RemoveContractCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validContractIdUnfilteredList_success() {
        RemoveContractCommand removeContractCommand = new RemoveContractCommand(CONTRACT_A_ID);

        String expectedMessage = String.format(RemoveContractCommand.MESSAGE_REMOVE_CONTRACT_SUCCESS, CONTRACT_A_ID);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeContract(CONTRACT_A);
        expectedModel.removeContractFromPerson(CONTRACT_A);
        expectedModel.removeContractFromPolicy(CONTRACT_A);

        assertCommandSuccess(removeContractCommand, model, expectedMessage, ListPanelType.CONTRACT, expectedModel);
    }

    @Test
    public void execute_invalidContractIdUnfilteredList_throwsCommandException() {
        RemoveContractCommand removeContractCommand = new RemoveContractCommand(CONTRACT_A_ID);

        // First removal should be successful
        try {
            removeContractCommand.execute(model);
        } catch (Exception e) {
            // Ignore
        }

        // Second removal should fail as the contract has already been removed
        assertCommandFailure(removeContractCommand, model, Messages.MESSAGE_CONTRACT_NOT_FOUND);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        RemoveContractCommand removeContractCommand = new RemoveContractCommand(CONTRACT_A_ID);
        assertThrows(NullPointerException.class, () -> removeContractCommand.execute(null));
    }

    @Test
    public void equals() {
        RemoveContractCommand removeFirstCommand = new RemoveContractCommand(CONTRACT_A_ID);
        RemoveContractCommand removeSecondCommand = new RemoveContractCommand(CONTRACT_A_ID);

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
        RemoveContractCommand removeContractCommand = new RemoveContractCommand(CONTRACT_A_ID);
        String expectedString = "seedu.address.logic.commands.RemoveContractCommand{cId=" + CONTRACT_A_ID + "}";
        assertEquals(expectedString, removeContractCommand.toString());
    }

}
