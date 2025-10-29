package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_DETAILS_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_NAME_HEALTH_B;
import static seedu.address.testutil.TypicalData.getHealthB;
import static seedu.address.testutil.TypicalData.getHome;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import seedu.address.testutil.EditPolicyDescriptorBuilder;

public class EditPolicyDescriptorTest {

    @Test
    public void equals() {
        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder(getHome()).build();

        // same values -> returns true
        EditPolicyDescriptor descriptorWithSameValues = new EditPolicyDescriptorBuilder(getHome()).build();
        assertTrue(descriptor.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(descriptor.equals(descriptor));

        // null -> returns false
        assertFalse(descriptor.equals(null));

        // different types -> returns false
        assertFalse(descriptor.equals(5));

        // different values -> returns false
        EditPolicyDescriptor otherDescriptor = new EditPolicyDescriptorBuilder(getHealthB()).build();
        assertFalse(descriptor.equals(otherDescriptor));

        // different name -> returns false
        EditPolicyDescriptor editedDescriptor = new EditPolicyDescriptorBuilder(getHome())
                .withName(VALID_POLICY_NAME_HEALTH_B).build();
        assertFalse(descriptor.equals(editedDescriptor));

        // different details -> returns false
        editedDescriptor = new EditPolicyDescriptorBuilder(getHome()).withName(VALID_DETAILS_HEALTH_B).build();
        assertFalse(descriptor.equals(editedDescriptor));
    }

    @Test
    public void toStringMethod() {
        EditPolicyDescriptor editPolicyDescriptor = new EditPolicyDescriptor();
        String expected = EditPolicyDescriptor.class.getCanonicalName() + "{name="
                + editPolicyDescriptor.getName().orElse(null) + ", details="
                + editPolicyDescriptor.getDetails().orElse(null) + "}";
        assertEquals(expected, editPolicyDescriptor.toString());
    }
}
