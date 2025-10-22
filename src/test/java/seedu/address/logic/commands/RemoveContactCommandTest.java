package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.RemoveContactCommand.MESSAGE_DELETE_PERSON_FAILURE;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalNricPredicates.PREDICATE_FIRST;
import static seedu.address.testutil.TypicalNricPredicates.PREDICATE_SECOND;
import static seedu.address.testutil.TypicalNricPredicates.PREDICATE_THIRD;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contract.Contract;
import seedu.address.model.person.NricContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.ui.ListPanelType;

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
    public void execute_validNricUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        RemoveContactCommand removeContactCommand = new RemoveContactCommand(PREDICATE_FIRST);

        // remove the person's contracts
        Set<Contract> contractToDelete = personToDelete.getContracts();
        for (Contract contract : contractToDelete) {
            model.removeContract(contract);
            personToDelete.removeContract(contract);
        }

        // Success message is updated to reflect the person deleted (or count if multiple are possible)
        String expectedMessage = String.format(RemoveContactCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                personToDelete.getName() + " " + personToDelete.getNric());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(removeContactCommand, model, expectedMessage, ListPanelType.CONTACT, expectedModel);
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
    public void execute_validNricFilteredList_success() {
        // Filter the list to show only the person we are about to delete
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // Use the NRIC of the person who is currently at index 0 of the filtered list
        RemoveContactCommand removeContactCommand = new RemoveContactCommand(PREDICATE_FIRST);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        // remove the person's contracts
        Set<Contract> contractToDelete = personToDelete.getContracts();
        for (Contract contract : contractToDelete) {
            model.removeContract(contract);
            personToDelete.removeContract(contract);
        }

        String expectedMessage = String.format(String.format(RemoveContactCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                personToDelete.getName() + " " + personToDelete.getNric()));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        assertCommandSuccess(removeContactCommand, model, expectedMessage, ListPanelType.CONTACT, expectedModel);
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
