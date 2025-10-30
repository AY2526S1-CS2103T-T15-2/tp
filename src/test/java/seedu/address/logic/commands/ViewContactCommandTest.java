package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalData.getElle;
import static seedu.address.testutil.TypicalData.getFiona;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;
import static seedu.address.testutil.TypicalData.getTypicalCarl;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contact.NricContainsKeywordsPredicate;
import seedu.address.ui.ListPanelType;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewContactCommand}.
 */
public class ViewContactCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        NricContainsKeywordsPredicate firstPredicate =
                new NricContainsKeywordsPredicate(Collections.singletonList("first"));
        NricContainsKeywordsPredicate secondPredicate =
                new NricContainsKeywordsPredicate(Collections.singletonList("second"));

        ViewContactCommand findFirstCommand = new ViewContactCommand(firstPredicate);
        ViewContactCommand findSecondCommand = new ViewContactCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        ViewContactCommand findFirstCommandCopy = new ViewContactCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noContactFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 0);
        NricContainsKeywordsPredicate predicate = preparePredicate(" ");
        ViewContactCommand command = new ViewContactCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, ListPanelType.CONTACT, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredContactList());
    }

    @Test
    public void execute_multipleKeywords_multipleContactsFound() {
        String expectedMessage = String.format(MESSAGE_CONTACTS_LISTED_OVERVIEW, 3);
        NricContainsKeywordsPredicate predicate = preparePredicate("S1234567C T1234567A T1234567B");
        ViewContactCommand command = new ViewContactCommand(predicate);
        expectedModel.updateFilteredContactList(predicate);
        assertCommandSuccess(command, model, expectedMessage, ListPanelType.CONTACT, expectedModel);
        assertEquals(Arrays.asList(getTypicalCarl(), getElle(), getFiona()), model.getFilteredContactList());
    }

    @Test
    public void toStringMethod() {
        NricContainsKeywordsPredicate predicate = new NricContainsKeywordsPredicate(Arrays.asList("keyword"));
        ViewContactCommand viewContactCommand = new ViewContactCommand(predicate);
        String expected = ViewContactCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, viewContactCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code NricContainsKeywordPredicate}.
     */
    private NricContainsKeywordsPredicate preparePredicate(String userInput) {
        return new NricContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
