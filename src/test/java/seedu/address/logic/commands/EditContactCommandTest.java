package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalData.getAlice;
import static seedu.address.testutil.TypicalData.getBob;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;
import static seedu.address.testutil.TypicalData.getTypicalAlice;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditContactCommand.EditContactDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Nric;
import seedu.address.testutil.ContactBuilder;
import seedu.address.testutil.EditContactDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditContactCommand.
 */
public class EditContactCommandTest {

    private static final Nric NRIC_ALICE = getAlice().getNric();
    private static final Nric NRIC_BOB = getBob().getNric();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_success() {
        Contact editedContact = new ContactBuilder(getTypicalAlice()).withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder(editedContact).build();
        EditContactCommand editContactCommand = new EditContactCommand(NRIC_ALICE, descriptor);
        try {
            editContactCommand.execute(model);
        } catch (CommandException ce) {
            fail();
        }

        Contact editedContactFromModel = model.getUniqueContactList().stream()
                .filter(contact -> contact.getNric().equals(NRIC_ALICE))
                .findFirst().get();
        assertEquals(editedContact, editedContactFromModel);
    }

    @Test
    public void equals() {
        final EditContactCommand standardCommand = new EditContactCommand(NRIC_ALICE, DESC_AMY);

        // same values -> returns true
        EditContactCommand.EditContactDescriptor copyDescriptor = new EditContactDescriptor(DESC_AMY);
        EditContactCommand commandWithSameValues = new EditContactCommand(NRIC_ALICE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different nric -> returns false
        assertFalse(standardCommand.equals(new EditContactCommand(NRIC_BOB, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditContactCommand(NRIC_ALICE, DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Nric nric = NRIC_ALICE;
        EditContactCommand.EditContactDescriptor editContactDescriptor = new EditContactCommand.EditContactDescriptor();
        EditContactCommand editContactCommand = new EditContactCommand(nric, editContactDescriptor);
        String expected = EditContactCommand.class.getCanonicalName() + "{nric=" + nric + ", editContactDescriptor="
                + editContactDescriptor + "}";
        assertEquals(expected, editContactCommand.toString());
    }

}
