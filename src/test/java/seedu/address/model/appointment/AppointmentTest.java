package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Nric;

public class AppointmentTest {

    @Test
    public void hasSameId() {
        Appointment appointment = new Appointment(
                new AppointmentId("abcdef"),
                new Nric("S1234567A"),
                LocalDate.parse("2023-01-01"),
                new AppointmentDetails("Details"));

        // same object -> returns true
        assertTrue(appointment.isSameAppointment(appointment));

        // null -> returns false
        assertFalse(appointment.isSameAppointment(null));

        // same id, all other attributes different -> returns true
        Appointment editedAppointment = new Appointment(
                new AppointmentId("abcdef"),
                new Nric("T1234567A"),
                LocalDate.parse("2023-01-02"),
                new AppointmentDetails("Details 2.0"));
        assertEquals(appointment.getAId(), editedAppointment.getAId());

        // different id, all other attributes same -> returns false
        editedAppointment = new Appointment(
                new AppointmentId("bcdefg"),
                new Nric("S1234567A"),
                LocalDate.parse("2023-01-01"),
                new AppointmentDetails("Details"));
        assertFalse(appointment.getAId().equals(editedAppointment.getAId()));

        // id differs in case, all other attributes same -> returns false
        editedAppointment = new Appointment(
                new AppointmentId("ABCDEF"),
                new Nric("S1234567A"),
                LocalDate.parse("2023-01-01"),
                new AppointmentDetails("Details"));
        assertFalse(appointment.getAId().equals(editedAppointment.getAId()));
    }

    @Test
    public void equals() {
        Appointment appointment = new Appointment(
                new AppointmentId("abcdef"),
                new Nric("S1234567A"),
                LocalDate.parse("2023-01-01"),
                new AppointmentDetails("Details"));

        // same values -> returns true
        Appointment appointmentToCopy = new Appointment(
                new AppointmentId("abcdef"),
                new Nric("S1234567A"),
                LocalDate.parse("2023-01-01"),
                new AppointmentDetails("Details"));

        assertTrue(appointment.isSameAppointment(appointmentToCopy));
        assertTrue(appointment.equals(appointmentToCopy));

        // same object -> returns true
        assertTrue(appointment.equals(appointment));

        // null -> returns false
        assertFalse(appointment.equals(null));

        // different type -> returns false
        assertFalse(appointment.equals(5));

        // different id -> returns false
        Appointment differentAppointment = new Appointment(
                new AppointmentId("123456"),
                new Nric("S1234567A"),
                LocalDate.parse("2023-01-01"),
                new AppointmentDetails("Details"));
        assertFalse(appointment.equals(differentAppointment));

        // different NRIC -> returns false
        Appointment editedAppointment = new Appointment(
                new AppointmentId("abcdef"),
                new Nric("T1234567A"),
                LocalDate.parse("2023-01-01"),
                new AppointmentDetails("Details"));
        assertFalse(appointment.equals(editedAppointment));

        // different LocalDate -> returns false
        editedAppointment = new Appointment(
                new AppointmentId("abcdef"),
                new Nric("S1234567A"),
                LocalDate.parse("2023-01-02"),
                new AppointmentDetails("Details"));
        assertFalse(appointment.equals(editedAppointment));

        // different details -> returns false
        editedAppointment = new Appointment(
                new AppointmentId("abcdef"),
                new Nric("S1234567A"),
                LocalDate.parse("2023-01-01"),
                new AppointmentDetails("Details 2.0"));
        assertFalse(appointment.equals(editedAppointment));

        // hashcode same for same object
        assertEquals(appointment.hashCode(), appointment.hashCode());
    }

    @Test
    public void toStringMethod() {
        Appointment appointment = new Appointment(
                new AppointmentId("abcdef"),
                new Nric("S1234567A"),
                LocalDate.parse("2023-01-01"),
                new AppointmentDetails("Details"));
        String expected = Appointment.class.getCanonicalName()
                + "{aId=" + appointment.getAId()
                + ", nric=" + appointment.getNric()
                + ", appDate=" + appointment.getDate()
                + ", details=" + appointment.getDetails() + "}";
        assertEquals(expected, appointment.toString());
    }

    @Test
    public void differentAppointmentsNotEqual() {
        Appointment appointment1 = new Appointment(
                new Nric("T1234567A"),
                LocalDate.parse("2023-01-02"),
                new AppointmentDetails("Details")
        );
        Appointment appointment2 = new Appointment(
                new Nric("S1234567A"),
                LocalDate.parse("2023-01-02"),
                new AppointmentDetails("Details")
        );
        assertNotEquals(appointment1, appointment2);
    }

}
