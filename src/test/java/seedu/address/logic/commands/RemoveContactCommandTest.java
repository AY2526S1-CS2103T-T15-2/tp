package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.RemoveContactCommand.MESSAGE_DELETE_PERSON_FAILURE;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalNricPredicates.PREDICATE_FIRST;
import static seedu.address.testutil.TypicalNricPredicates.PREDICATE_SECOND;
import static seedu.address.testutil.TypicalNricPredicates.PREDICATE_THIRD;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NricContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code RemoveContactCommand}.
 */
public class RemoveContactCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_nricNotFoundUnfilteredList_throwsCommandException() {
        // Create a predicate for an NRIC that is not in the typical address book
        NricContainsKeywordsPredicate notFoundPredicate =
                new NricContainsKeywordsPredicate(List.of("S0000000Z"));
        RemoveContactCommand removeContactCommand = new RemoveContactCommand(notFoundPredicate);

        // Assuming RemoveContactCommand throws a specific error if no person matches the predicate
        assertCommandFailure(removeContactCommand, model, MESSAGE_DELETE_PERSON_FAILURE);
    }

    @Test
    public void execute_nricNotFoundFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON); // Filter list to one person

        // Create a predicate for a person NOT currently in the filtered view
        RemoveContactCommand removeContactCommand = new RemoveContactCommand(PREDICATE_THIRD);
        assertCommandFailure(removeContactCommand, model, MESSAGE_DELETE_PERSON_FAILURE);

        // Create a predicate for a person truly not in the view
        NricContainsKeywordsPredicate trulyNotFoundPredicate = new NricContainsKeywordsPredicate(List.of("S0000000Z"));
        RemoveContactCommand deleteTrulyNotFoundCommand = new RemoveContactCommand(trulyNotFoundPredicate);

        assertCommandFailure(deleteTrulyNotFoundCommand, model, MESSAGE_DELETE_PERSON_FAILURE);
    }

    @Test
    public void equals() {
        RemoveContactCommand deleteFirstCommand = new RemoveContactCommand(PREDICATE_FIRST);
        RemoveContactCommand deleteSecondCommand = new RemoveContactCommand(PREDICATE_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same predicate (same values) -> returns true
        RemoveContactCommand deleteFirstCommandCopy = new RemoveContactCommand(PREDICATE_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different predicate (different NRIC) -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        RemoveContactCommand removeContactCommand = new RemoveContactCommand(PREDICATE_FIRST);

        // Note: Predicates don't have a clean toString, so this is an approximation.
        // You would typically override the toString in RemoveContactCommand to show the predicate.
        String expected = RemoveContactCommand.class.getCanonicalName()
                + "{predicate=" + PREDICATE_FIRST.toString() + "}";
        assertEquals(expected, removeContactCommand.toString());
    }

    /**
     * Helper method used in original tests, no longer needed but kept for completeness.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
