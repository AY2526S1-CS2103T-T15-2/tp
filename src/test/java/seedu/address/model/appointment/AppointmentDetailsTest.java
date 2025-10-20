package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AppointmentDetailsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AppointmentDetails(null));
    }

    @Test
    public void constructor_invalidAppointmentDetails_throwsIllegalArgumentException() {
        String invalidAppointmentDetails = "";
        assertThrows(IllegalArgumentException.class, () -> new AppointmentDetails(invalidAppointmentDetails));
    }

    @Test
    public void isValidAppointmentDetails() {
        // null details
        assertThrows(NullPointerException.class, () -> AppointmentDetails.isValidAppointmentDetails(null));

        // invalid details
        assertFalse(AppointmentDetails.isValidAppointmentDetails("")); // empty string
        assertFalse(AppointmentDetails.isValidAppointmentDetails(" ")); // spaces only

        // valid details
        assertTrue(AppointmentDetails.isValidAppointmentDetails("This appointment"));
        assertTrue(AppointmentDetails.isValidAppointmentDetails("a")); // one character
        assertTrue(AppointmentDetails.isValidAppointmentDetails(
                "This appointment is to align which coverage the client "
                        + "require such that they are able to make an informed decision on purchase")); // long details
    }

    @Test
    public void equals() {
        AppointmentDetails appointmentDetails = new AppointmentDetails("Valid Details");

        // same values -> returns true
        assertTrue(appointmentDetails.equals(new AppointmentDetails("Valid Details")));

        // same object -> returns true
        assertTrue(appointmentDetails.equals(appointmentDetails));

        // null -> returns false
        assertFalse(appointmentDetails.equals(null));

        // different types -> returns false
        assertFalse(appointmentDetails.equals(5.0f));

        // different values -> returns false
        assertFalse(appointmentDetails.equals(new AppointmentDetails("Other Valid Details")));
    }
}
