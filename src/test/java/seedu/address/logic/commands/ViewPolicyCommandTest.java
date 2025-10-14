package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalData.HEALTH;
import static seedu.address.testutil.TypicalData.LIFE;
import static seedu.address.testutil.TypicalData.PROPERTY;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.IdContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewPolicyCommand.
 */
public class ViewPolicyCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_viewIsNotFiltered_showsSameView() {
        assertCommandSuccess(new ViewPolicyCommand(), model, ViewPolicyCommand.MESSAGE_SUCCESS_ALL, expectedModel);
    }

    @Test
    public void equals() {
        IdContainsKeywordsPredicate firstPredicate =
                new IdContainsKeywordsPredicate(Collections.singletonList("abcdef"));
        IdContainsKeywordsPredicate secondPredicate =
                new IdContainsKeywordsPredicate(Collections.singletonList("xyz123"));

        ViewPolicyCommand viewFirstPolicyCommand = new ViewPolicyCommand(firstPredicate);
        ViewPolicyCommand viewSecondPolicyCommand = new ViewPolicyCommand(secondPredicate);
        ViewPolicyCommand viewThirdPolicyCommand = new ViewPolicyCommand(null);

        // same object -> returns true
        assertTrue(viewFirstPolicyCommand.equals(viewFirstPolicyCommand));

        // same values -> returns true
        ViewPolicyCommand viewFirstPolicyCommandCopy = new ViewPolicyCommand(firstPredicate);
        assertTrue(viewFirstPolicyCommand.equals(viewFirstPolicyCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstPolicyCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstPolicyCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstPolicyCommand.equals(viewSecondPolicyCommand));

        // both null predicates
        ViewPolicyCommand viewThirdPolicyCommandCopy = new ViewPolicyCommand(null);
        assertTrue(viewThirdPolicyCommand.equals(viewThirdPolicyCommandCopy));

        // one null predicate
        assertFalse(viewFirstPolicyCommand.equals(viewThirdPolicyCommand));
        assertFalse(viewThirdPolicyCommand.equals(viewFirstPolicyCommand));
    }

    @Test
    public void execute_zeroKeywords_noPolicyFound() {
        String expectedMessage = ViewPolicyCommand.MESSAGE_NO_ID_MATCH;
        IdContainsKeywordsPredicate predicate = preparePredicate(" ");
        ViewPolicyCommand command = new ViewPolicyCommand(predicate);
        expectedModel.updateFilteredPolicyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPolicyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePoliciesFound() {
        IdContainsKeywordsPredicate predicate = preparePredicate("abcdef 123456 Abc123");
        String expectedMessage = String.format(ViewPolicyCommand.MESSAGE_SUCCESS_SPECIFIC,
                String.join(", ", predicate.getKeywords()));
        ViewPolicyCommand command = new ViewPolicyCommand(predicate);
        expectedModel.updateFilteredPolicyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(LIFE, HEALTH, PROPERTY), model.getFilteredPolicyList());
    }

    @Test
    public void toStringMethod() {
        IdContainsKeywordsPredicate predicate = new IdContainsKeywordsPredicate(Arrays.asList("abcdef"));
        ViewPolicyCommand viewPolicyCommand = new ViewPolicyCommand(predicate);
        String expected = ViewPolicyCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, viewPolicyCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code IdContainsKeywordsPredicate}.
     */
    private IdContainsKeywordsPredicate preparePredicate(String userInput) {
        return new IdContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
