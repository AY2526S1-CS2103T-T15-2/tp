package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDetails;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;
import seedu.address.ui.ListPanelType;


public class AddAppointmentCommandTest {

    private static final String INVALID_NRIC = "S0000000Z";
    private static final String VALID_NRIC = "S1234567A";
    private static final String VALID_DATE = "2099-01-01";
    private static final Person PERSON_WITH_VALID_ID = new PersonBuilder().build();

    @Test
    public void constructor_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddAppointmentCommand(null));
    }

    @Test
    public void equals() {
        Appointment appointment = new Appointment(new AppointmentId("A1234A"), new Nric("S1234567D"),
                LocalDate.parse("2023-01-01"), new AppointmentDetails("Details1"));
        Appointment otherAppointment = new Appointment(new AppointmentId("A1234B"), new Nric("S7654321D"),
                LocalDate.parse("2023-02-01"), new AppointmentDetails("Details2"));
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(appointment);
        AddAppointmentCommand addAppointmentCommandOther = new AddAppointmentCommand(otherAppointment);

        // same object -> returns true
        assertTrue(addAppointmentCommand.equals(addAppointmentCommand));

        // same values -> returns true
        AddAppointmentCommand addAppointmentCommandCopy = new AddAppointmentCommand(appointment);
        assertTrue(addAppointmentCommand.equals(addAppointmentCommandCopy));

        // different types -> returns false
        assertFalse(addAppointmentCommand.equals(1));

        // null -> returns false
        assertFalse(addAppointmentCommand.equals(null));

        // different appointment -> returns false
        assertFalse(addAppointmentCommand.equals(addAppointmentCommandOther));
    }

    @Test
    public void invalidNric_throwsCommandException() {
        Model modelStub = new ModelManager();

        Appointment appointmentWithInvalidNric = new Appointment(
                new Nric(INVALID_NRIC),
                LocalDate.parse(VALID_DATE),
                new AppointmentDetails("Details"));
        assertCommandFailure(new AddAppointmentCommand(appointmentWithInvalidNric),
                modelStub, Messages.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void validNric_success() {
        Model modelStub = new ModelManager();
        modelStub.addPerson(PERSON_WITH_VALID_ID);

        Appointment appointmentWithValidNric = new Appointment(
                new Nric(VALID_NRIC),
                LocalDate.parse(VALID_DATE),
                new AppointmentDetails("Details"));
        AppointmentId appointmentId = appointmentWithValidNric.getAId();
        // crude test for now
        assertCommandSuccess(new AddAppointmentCommand(appointmentWithValidNric),
                modelStub, String.format(AddAppointmentCommand.MESSAGE_SUCCESS, appointmentId.toString()),
                ListPanelType.APPOINTMENT,
                modelStub);
    }

    @Test
    public void toStringMethod() {
        Appointment appointment = new Appointment(new AppointmentId("A1234A"), new Nric("S1234567D"),
                LocalDate.parse("2023-01-01"), new AppointmentDetails("Details1"));
        AddAppointmentCommand addAppointmentCommand = new AddAppointmentCommand(appointment);
        String expected = AddAppointmentCommand.class.getCanonicalName() + "{toAdd=" + appointment + "}";
        assertEquals(expected, addAppointmentCommand.toString());
    }

}
