package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AppointmentIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AppointmentId(null));
    }

    @Test
    public void constructor_invalidAppointmentId_throwsIllegalArgumentException() {
        String invalidAppointmentId = "******";
        assertThrows(IllegalArgumentException.class, () -> new AppointmentId(invalidAppointmentId));
    }

    @Test
    public void isValidAppointmentId() {
        // null id
        assertThrows(NullPointerException.class, () -> AppointmentId.isValidAppointmentId(null));

        // invalid name
        assertFalse(AppointmentId.isValidAppointmentId("")); // empty string
        assertFalse(AppointmentId.isValidAppointmentId("      ")); // spaces only
        assertFalse(AppointmentId.isValidAppointmentId("^&*+-$")); // only non-alphanumeric characters
        assertFalse(AppointmentId.isValidAppointmentId("abcde*")); // contains non-alphanumeric characters
        assertFalse(AppointmentId.isValidAppointmentId("abcd")); // incorrect length

        // valid name
        assertTrue(AppointmentId.isValidAppointmentId("abcdef")); // alphabets only
        assertTrue(AppointmentId.isValidAppointmentId("123456")); // numbers only
        assertTrue(AppointmentId.isValidAppointmentId("String")); // with capital letters
        assertTrue(AppointmentId.isValidAppointmentId("Abc123")); // alphanumeric
    }

    @Test
    public void generate() {
        assertEquals(AppointmentId.ID_LENGTH, AppointmentId.generate().value.length());
    }

    @Test
    public void equals() {
        AppointmentId appointmentId = new AppointmentId("abcdef");

        // same object -> returns true
        assertTrue(appointmentId.equals(appointmentId));

        // null -> returns false
        assertFalse(appointmentId.equals(null));

        // different types -> returns false
        assertFalse(appointmentId.equals(5.0f));

        // different values -> returns false
        // note: randomly generated
        assertFalse(appointmentId.equals(new AppointmentId("123456")));
    }

    @Test
    public void hashCode_sameObject_sameHashcode() {
        AppointmentId appointmentId = new AppointmentId("abcdef");
        assertEquals(appointmentId.hashCode(), appointmentId.hashCode());
    }
}
