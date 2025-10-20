package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModelStub;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonComparatorType;
import seedu.address.ui.ListPanelType;

public class SortContactCommandTest {

    @Test
    public void constructor_nullComparatorType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortContactCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() {
        ModelStubAcceptingComparator modelStub = new ModelStubAcceptingComparator();

        CommandResult commandResult = new SortContactCommand(PersonComparatorType.UNORDERED).execute(modelStub);
        assertEquals(commandResult,
                new CommandResult(SortContactCommand.MESSAGE_SUCCESS_UNORDERED, ListPanelType.CONTACT));

        commandResult = new SortContactCommand(PersonComparatorType.ALPHABETICAL).execute(modelStub);
        assertEquals(commandResult,
                new CommandResult(SortContactCommand.MESSAGE_SUCCESS_ALPHABETICAL, ListPanelType.CONTACT));
    }

    @Test
    public void equals() {
        SortContactCommand sortInsertion = new SortContactCommand(PersonComparatorType.UNORDERED);
        SortContactCommand sortAlphabetical = new SortContactCommand(PersonComparatorType.ALPHABETICAL);

        // same object -> returns true
        assertTrue(sortInsertion.equals(sortInsertion));

        // same values -> returns true
        SortContactCommand sortInsertionCopy = new SortContactCommand(PersonComparatorType.UNORDERED);
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
        PersonComparatorType comparatorType = PersonComparatorType.UNORDERED;
        SortContactCommand sortContactCommand = new SortContactCommand(comparatorType);
        String expected = SortContactCommand.class.getCanonicalName() + "{comparatorType=" + comparatorType + "}";
        assertEquals(expected, sortContactCommand.toString());
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingComparator extends ModelStub {
        @Override
        public void sortPersons(Comparator<Person> comparator) {

        }
    }
}
