package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_ID_A;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.APPOINTMENT_A;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.ui.ListPanelType;


/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code RemoveAppointmentCommand}.
 */
public class RemoveAppointmentCommandTest {

    private static final String EXPECTED_PREFIX_STRING = "seedu.address.logic.commands.RemoveAppointmentCommand{aId=";

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validAppointmentIdUnfilteredList_success() {
        RemoveAppointmentCommand removeAppointmentCommand =
                new RemoveAppointmentCommand(new AppointmentId(VALID_APPOINTMENT_ID_A));

        String expectedMessage = String.format(RemoveAppointmentCommand.MESSAGE_REMOVE_APPOINTMENT_SUCCESS,
                APPOINTMENT_A.getAId());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeAppointment(APPOINTMENT_A);

        assertCommandSuccess(removeAppointmentCommand, model, expectedMessage,
                ListPanelType.APPOINTMENT, expectedModel);
    }

    @Test
    public void execute_invalidAppointmentIdUnfilteredList_throwsCommandException() {
        RemoveAppointmentCommand removeAppointmentCommand =
                new RemoveAppointmentCommand(new AppointmentId(VALID_APPOINTMENT_ID_A));

        // First removal should be successful
        try {
            removeAppointmentCommand.execute(model);
        } catch (Exception e) {
            // Ignore
        }

        // Second removal should fail as the appointment has already been removed
        assertCommandFailure(removeAppointmentCommand, model, Messages.MESSAGE_APPOINTMENT_NOT_FOUND);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        RemoveAppointmentCommand removeAppointmentCommand =
                new RemoveAppointmentCommand(new AppointmentId(VALID_APPOINTMENT_ID_A));
        assertThrows(NullPointerException.class, () -> removeAppointmentCommand.execute(null));
    }

    @Test
    public void equals() {
        RemoveAppointmentCommand removeFirstCommand =
                new RemoveAppointmentCommand(new AppointmentId(VALID_APPOINTMENT_ID_A));
        RemoveAppointmentCommand removeSecondCommand =
                new RemoveAppointmentCommand(new AppointmentId(VALID_APPOINTMENT_ID_A));

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
        RemoveAppointmentCommand removeAppointmentCommand =
                new RemoveAppointmentCommand(new AppointmentId(VALID_APPOINTMENT_ID_A));
        String expectedString = EXPECTED_PREFIX_STRING + VALID_APPOINTMENT_ID_A + "}";
        assertEquals(expectedString, removeAppointmentCommand.toString());
    }

}
