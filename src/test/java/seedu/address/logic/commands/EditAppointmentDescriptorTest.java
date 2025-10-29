package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalData.getAppointmentA;
import static seedu.address.testutil.TypicalData.getAppointmentB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;

public class EditAppointmentDescriptorTest {

    @Test
    public void equals() {
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(getAppointmentA()).build();

        // same values -> returns true
        EditAppointmentDescriptor descriptorWithSameValues =
                new EditAppointmentDescriptorBuilder(getAppointmentA()).build();
        assertTrue(descriptor.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(descriptor.equals(descriptor));

        // null -> returns false
        assertFalse(descriptor.equals(null));

        // different types -> returns false
        assertFalse(descriptor.equals(5));

        // different values -> returns false
        EditAppointmentDescriptor otherDescriptor = new EditAppointmentDescriptorBuilder(getAppointmentB()).build();
        assertFalse(descriptor.equals(otherDescriptor));

        // different nric -> returns false
        EditAppointmentDescriptor editedDescriptor = new EditAppointmentDescriptorBuilder(getAppointmentA())
                .withNric(getAppointmentB().getNric().nric).build();
        assertFalse(descriptor.equals(editedDescriptor));

        // different dates -> returns false
        editedDescriptor = new EditAppointmentDescriptorBuilder(getAppointmentA())
                .withDate(getAppointmentB().getDate().toString()).build();
        assertFalse(descriptor.equals(editedDescriptor));

        // different details -> returns false
        editedDescriptor = new EditAppointmentDescriptorBuilder(getAppointmentA())
                .withDetails(getAppointmentB().getDetails().value).build();
        assertFalse(descriptor.equals(editedDescriptor));
    }

    @Test
    public void toStringMethod() {
        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();
        String expected = EditAppointmentDescriptor.class.getCanonicalName() + "{nric="
                + editAppointmentDescriptor.getNric().orElse(null) + ", appDate="
                + editAppointmentDescriptor.getDate().orElse(null) + ", details="
                + editAppointmentDescriptor.getDetails().orElse(null) + "}";
        assertEquals(expected, editAppointmentDescriptor.toString());
    }
}
