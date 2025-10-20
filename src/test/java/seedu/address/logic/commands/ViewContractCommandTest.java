package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalData.CONTRACT_A;
import static seedu.address.testutil.TypicalData.CONTRACT_B;
import static seedu.address.testutil.TypicalData.CONTRACT_C;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.contract.ContractIdContainsKeywordsPredicate;
import seedu.address.ui.ListPanelType;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ViewContractCommand.
 */
public class ViewContractCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_viewIsNotFiltered_showsSameView() {
        assertCommandSuccess(new ViewContractCommand(), model, ViewContractCommand.MESSAGE_SUCCESS_ALL,
                ListPanelType.CONTRACT, expectedModel);
    }

    @Test
    public void equals() {
        ContractIdContainsKeywordsPredicate firstPredicate = new ContractIdContainsKeywordsPredicate(
                Collections.singletonList("abcdef"));
        ContractIdContainsKeywordsPredicate secondPredicate = new ContractIdContainsKeywordsPredicate(
                Collections.singletonList("xyz123"));

        ViewContractCommand viewFirstContractCommand = new ViewContractCommand(firstPredicate);
        ViewContractCommand viewSecondContractCommand = new ViewContractCommand(secondPredicate);
        ViewContractCommand viewThirdContractCommand = new ViewContractCommand(null);

        // same object => returns true
        assertTrue(viewFirstContractCommand.equals(viewFirstContractCommand));

        // same values => returns true
        ViewContractCommand viewFirstContractCommandCopy = new ViewContractCommand(firstPredicate);
        assertTrue(viewFirstContractCommand.equals(viewFirstContractCommandCopy));

        // different types => returns false
        assertFalse(viewFirstContractCommand.equals(1));

        // null => returns false
        assertFalse(viewFirstContractCommand.equals(null));

        //different contract command => returns false
        assertFalse(viewFirstContractCommand.equals(viewSecondContractCommand));

        // both view all commands => returns true
        ViewContractCommand viewThirdContractCommandCopy = new ViewContractCommand(null);
        assertTrue(viewThirdContractCommand.equals(viewThirdContractCommandCopy));

        //one null predicate
        assertFalse(viewFirstContractCommand.equals(viewThirdContractCommand));
        assertFalse(viewThirdContractCommand.equals(viewFirstContractCommand));

    }

    @Test
    public void execute_zeroKeywords_noContractsFound() {
        String expectedMessage = ViewContractCommand.MESSAGE_NO_ID_MATCH;
        ContractIdContainsKeywordsPredicate predicate = preparePredicate(" ");
        ViewContractCommand command = new ViewContractCommand(predicate);
        expectedModel.updateFilteredContractList(predicate);
        assertCommandSuccess(command, model, expectedMessage, ListPanelType.CONTRACT, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredContractList());
    }

    @Test
    public void execute_multipleKeywords_multipleContractsFound() {
        ContractIdContainsKeywordsPredicate predicate = preparePredicate("C1234A C1234B C1234C");
        String expectedMessage = String.format(ViewContractCommand.MESSAGE_SUCCESS_SPECIFIC,
                String.join(", ", predicate.getKeywords()));
        ViewContractCommand command = new ViewContractCommand(predicate);
        expectedModel.updateFilteredContractList(predicate);
        assertCommandSuccess(command, model, expectedMessage, ListPanelType.CONTRACT, expectedModel);
        assertEquals(Arrays.asList(CONTRACT_A, CONTRACT_B, CONTRACT_C), model.getFilteredContractList());
    }

    private ContractIdContainsKeywordsPredicate preparePredicate(String userInput) {
        return new ContractIdContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
