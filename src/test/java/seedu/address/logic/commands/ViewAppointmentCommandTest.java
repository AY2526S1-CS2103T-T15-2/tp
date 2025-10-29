package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalData.getAppointmentA;
import static seedu.address.testutil.TypicalData.getAppointmentB;
import static seedu.address.testutil.TypicalData.getAppointmentC;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.AppointmentIdContainsKeywordsPredicate;
import seedu.address.ui.ListPanelType;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewAppointmentCommand.
 */
public class ViewAppointmentCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_viewIsNotFiltered_showsSameView() {
        assertCommandSuccess(new ViewAppointmentCommand(), model, ViewAppointmentCommand.MESSAGE_SUCCESS_ALL,
                ListPanelType.APPOINTMENT, expectedModel);
    }

    @Test
    public void equals() {
        AppointmentIdContainsKeywordsPredicate firstPredicate =
                new AppointmentIdContainsKeywordsPredicate(Collections.singletonList("abcdef"));
        AppointmentIdContainsKeywordsPredicate secondPredicate =
                new AppointmentIdContainsKeywordsPredicate(Collections.singletonList("xyz123"));

        ViewAppointmentCommand viewFirstAppointmentCommand = new ViewAppointmentCommand(firstPredicate);
        ViewAppointmentCommand viewSecondAppointmentCommand = new ViewAppointmentCommand(secondPredicate);
        ViewAppointmentCommand viewThirdAppointmentCommand = new ViewAppointmentCommand(null);

        // same object -> returns true
        assertTrue(viewFirstAppointmentCommand.equals(viewFirstAppointmentCommand));

        // same values -> returns true
        ViewAppointmentCommand viewFirstAppointmentCommandCopy = new ViewAppointmentCommand(firstPredicate);
        assertTrue(viewFirstAppointmentCommand.equals(viewFirstAppointmentCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstAppointmentCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstAppointmentCommand.equals(null));

        // different view command -> returns false
        assertFalse(viewFirstAppointmentCommand.equals(viewSecondAppointmentCommand));

        // both null predicates
        ViewAppointmentCommand viewThirdAppointmentCommandCopy = new ViewAppointmentCommand(null);
        assertTrue(viewThirdAppointmentCommand.equals(viewThirdAppointmentCommandCopy));

        // one null predicate
        assertFalse(viewFirstAppointmentCommand.equals(viewThirdAppointmentCommand));
        assertFalse(viewThirdAppointmentCommand.equals(viewFirstAppointmentCommand));
    }

    @Test
    public void execute_zeroKeywords_noAppointmentFound() {
        String expectedMessage = ViewAppointmentCommand.MESSAGE_NO_ID_MATCH;
        AppointmentIdContainsKeywordsPredicate predicate = preparePredicate(" ");
        ViewAppointmentCommand command = new ViewAppointmentCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, ListPanelType.APPOINTMENT, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_multipleKeywords_multiplePoliciesFound() {
        AppointmentIdContainsKeywordsPredicate predicate = preparePredicate("A1234A ABC123 123abc");
        String expectedMessage = String.format(ViewAppointmentCommand.MESSAGE_SUCCESS_SPECIFIC,
                String.join(", ", predicate.getKeywords()));
        ViewAppointmentCommand command = new ViewAppointmentCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, ListPanelType.APPOINTMENT, expectedModel);
        assertEquals(Arrays.asList(getAppointmentA(), getAppointmentB(), getAppointmentC()),
                model.getFilteredAppointmentList());
    }

    @Test
    public void toStringMethod() {
        AppointmentIdContainsKeywordsPredicate predicate =
                new AppointmentIdContainsKeywordsPredicate(Arrays.asList("abcdef"));
        ViewAppointmentCommand viewAppointmentCommand = new ViewAppointmentCommand(predicate);
        String expected = ViewAppointmentCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, viewAppointmentCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code AppointmentIdContainsKeywordsPredicate}.
     */
    private AppointmentIdContainsKeywordsPredicate preparePredicate(String userInput) {
        return new AppointmentIdContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
