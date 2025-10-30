package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalData.getHealth;
import static seedu.address.testutil.TypicalData.getLife;
import static seedu.address.testutil.TypicalData.getProperty;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.PolicyIdContainsKeywordsPredicate;
import seedu.address.ui.ListPanelType;

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
        assertCommandSuccess(new ViewPolicyCommand(), model, ViewPolicyCommand.MESSAGE_SUCCESS_ALL,
                ListPanelType.POLICY, expectedModel);
    }

    @Test
    public void equals() {
        PolicyIdContainsKeywordsPredicate firstPredicate =
                new PolicyIdContainsKeywordsPredicate(Collections.singletonList("abcdef"));
        PolicyIdContainsKeywordsPredicate secondPredicate =
                new PolicyIdContainsKeywordsPredicate(Collections.singletonList("xyz123"));

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

        // different view command -> returns false
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
        PolicyIdContainsKeywordsPredicate predicate = preparePredicate(" ");
        ViewPolicyCommand command = new ViewPolicyCommand(predicate);
        expectedModel.updateFilteredPolicyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, ListPanelType.POLICY, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPolicyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePoliciesFound() {
        PolicyIdContainsKeywordsPredicate predicate = preparePredicate("abcdef 123456 Abc123");
        String expectedMessage = String.format(ViewPolicyCommand.MESSAGE_SUCCESS_SPECIFIC,
                String.join(", ", predicate.getKeywords()));
        ViewPolicyCommand command = new ViewPolicyCommand(predicate);
        expectedModel.updateFilteredPolicyList(predicate);
        assertCommandSuccess(command, model, expectedMessage, ListPanelType.POLICY, expectedModel);
        assertEquals(Arrays.asList(getLife(), getHealth(), getProperty()), model.getFilteredPolicyList());
    }

    @Test
    public void toStringMethod() {
        PolicyIdContainsKeywordsPredicate predicate = new PolicyIdContainsKeywordsPredicate(Arrays.asList("abcdef"));
        ViewPolicyCommand viewPolicyCommand = new ViewPolicyCommand(predicate);
        String expected = ViewPolicyCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, viewPolicyCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code IdContainsKeywordsPredicate}.
     */
    private PolicyIdContainsKeywordsPredicate preparePredicate(String userInput) {
        return new PolicyIdContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
