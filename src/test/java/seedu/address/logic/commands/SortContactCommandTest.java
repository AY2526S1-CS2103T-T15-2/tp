package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModelStub;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactComparatorType;
import seedu.address.ui.ListPanelType;

public class SortContactCommandTest {

    @Test
    public void constructor_nullComparatorType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortContactCommand(null));
    }

    @Test
    public void execute_sortByComparatorType_successful() {
        ModelStubAcceptingComparator modelStub = new ModelStubAcceptingComparator();

        CommandResult commandResult = new SortContactCommand(ContactComparatorType.UNORDERED).execute(modelStub);
        assertEquals(commandResult,
                new CommandResult(SortContactCommand.MESSAGE_SUCCESS_UNORDERED, ListPanelType.CONTACT));

        commandResult = new SortContactCommand(ContactComparatorType.ALPHABETICAL).execute(modelStub);
        assertEquals(commandResult,
                new CommandResult(SortContactCommand.MESSAGE_SUCCESS_ALPHABETICAL, ListPanelType.CONTACT));
    }

    @Test
    public void equals() {
        SortContactCommand sortInsertion = new SortContactCommand(ContactComparatorType.UNORDERED);
        SortContactCommand sortAlphabetical = new SortContactCommand(ContactComparatorType.ALPHABETICAL);

        // same object -> returns true
        assertTrue(sortInsertion.equals(sortInsertion));

        // same values -> returns true
        SortContactCommand sortInsertionCopy = new SortContactCommand(ContactComparatorType.UNORDERED);
        assertTrue(sortInsertion.equals(sortInsertionCopy));

        // different types -> returns false
        assertFalse(sortInsertion.equals(1));

        // null -> returns false
        assertFalse(sortInsertion.equals(null));

        // different comparator type -> returns false
        assertFalse(sortInsertion.equals(sortAlphabetical));
    }

    @Test
    public void toStringMethod() {
        ContactComparatorType comparatorType = ContactComparatorType.UNORDERED;
        SortContactCommand sortContactCommand = new SortContactCommand(comparatorType);
        String expected = SortContactCommand.class.getCanonicalName() + "{comparatorType=" + comparatorType + "}";
        assertEquals(expected, sortContactCommand.toString());
    }

    /**
     * A Model stub that always accept the contact being added.
     */
    private class ModelStubAcceptingComparator extends ModelStub {
        @Override
        public void sortContacts(Comparator<Contact> comparator) {

        }
    }
}
