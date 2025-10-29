package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalData.getAppointmentA;
import static seedu.address.testutil.TypicalData.getAppointmentB;
import static seedu.address.testutil.TypicalData.getAppointmentC;
import static seedu.address.testutil.TypicalData.getAppointmentE;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;
import seedu.address.ui.ListPanelType;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditAppointmentCommand.
 */
public class EditAppointmentCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        Appointment appointmentToEdit = model.getFilteredAppointmentList().get(0);
        AppointmentId appointmentId = appointmentToEdit.getAId();

        Appointment editedAppointment = new AppointmentBuilder(getAppointmentA()).withId(appointmentId.value).build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(appointmentId, descriptor);

        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS,
                Messages.format(editedAppointment));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setAppointment(appointmentToEdit, editedAppointment);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, ListPanelType.APPOINTMENT, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Appointment appointmentToEdit = model.getFilteredAppointmentList().get(0);
        AppointmentId appointmentId = appointmentToEdit.getAId();

        Appointment editedAppointment = new AppointmentBuilder(appointmentToEdit).withDetails("test").build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder().withDetails("test").build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(appointmentId, descriptor);

        String expectedMessage = String.format(EditAppointmentCommand.MESSAGE_EDIT_APPOINTMENT_SUCCESS,
                Messages.format(editedAppointment));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setAppointment(appointmentToEdit, editedAppointment);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, ListPanelType.APPOINTMENT, expectedModel);
    }

    @Test
    public void execute_duplicateAppointment_failure() {
        Appointment firstAppointment = model.getFilteredAppointmentList().get(0);
        Appointment secondAppointment = model.getFilteredAppointmentList().get(1);

        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(firstAppointment).build();
        EditAppointmentCommand editAppointmentCommand =
                new EditAppointmentCommand(secondAppointment.getAId(), descriptor);

        assertCommandFailure(editAppointmentCommand, model, EditAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT);
    }

    @Test
    public void execute_appointmentIdNotInAddressBook_failure() {
        AppointmentId appointmentId = getAppointmentE().getAId();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(getAppointmentE()).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(appointmentId, descriptor);

        assertCommandFailure(editAppointmentCommand, model,
                String.format(Messages.MESSAGE_APPOINTMENT_NOT_FOUND, appointmentId));
    }

    @Test
    public void equals() {
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(getAppointmentA()).build();
        final EditAppointmentCommand standardCommand = new EditAppointmentCommand(getAppointmentA().getAId(),
                descriptor);

        // same values -> returns true
        EditAppointmentDescriptor copyDescriptor = new EditAppointmentDescriptorBuilder(getAppointmentA()).build();
        EditAppointmentCommand commandWithSameValues =
                new EditAppointmentCommand(getAppointmentA().getAId(), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different appointment id -> returns false
        assertFalse(standardCommand.equals(new EditAppointmentCommand(getAppointmentB().getAId(), descriptor)));

        // different descriptor -> returns false
        EditAppointmentDescriptor otherDescriptor = new EditAppointmentDescriptorBuilder(getAppointmentC()).build();
        assertFalse(standardCommand.equals(new EditAppointmentCommand(getAppointmentA().getAId(), otherDescriptor)));
    }

    @Test
    public void toStringMethod() {
        AppointmentId appointmentId = getAppointmentA().getAId();
        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();
        EditAppointmentCommand editAppointmentCommand =
                new EditAppointmentCommand(appointmentId, editAppointmentDescriptor);
        String expected = EditAppointmentCommand.class.getCanonicalName() + "{aId=" + appointmentId
                + ", editAppointmentDescriptor=" + editAppointmentDescriptor + "}";
        assertEquals(expected, editAppointmentCommand.toString());
    }

}
