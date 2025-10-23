package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModelStub;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractComparatorType;
import seedu.address.ui.ListPanelType;

public class SortContractCommandTest {

    @Test
    public void constructor_nullComparatorType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortContractCommand(null));
    }

    @Test
    public void execute_sortByComparatorType_success() throws Exception {
        ModelStubAcceptingComparatorType modelStub = new ModelStubAcceptingComparatorType();

        CommandResult commandResult = new SortContractCommand(ContractComparatorType.UNORDERED).execute(modelStub);
        assertEquals(commandResult,
                new CommandResult(SortContractCommand.MESSAGE_SUCCESS_UNORDERED, ListPanelType.CONTRACT));

        commandResult = new SortContractCommand(ContractComparatorType.EXPIRY_DATE).execute(modelStub);
        assertEquals(commandResult,
                new CommandResult(SortContractCommand.MESSAGE_SUCCESS_EXPIRY_DATE, ListPanelType.CONTRACT));
    }

    @Test
    public void equals() {
        SortContractCommand sortInsertion = new SortContractCommand(ContractComparatorType.UNORDERED);
        SortContractCommand sortExpiry = new SortContractCommand(ContractComparatorType.EXPIRY_DATE);

        // same object -> returns true
        assertTrue(sortInsertion.equals(sortInsertion));

        // same values -> returns true
        SortContractCommand sortInsertionCopy = new SortContractCommand(ContractComparatorType.UNORDERED);
        assertTrue(sortInsertion.equals(sortInsertionCopy));

        // different types -> returns false
        assertFalse(sortInsertion.equals(1));

        // null -> returns false
        assertFalse(sortInsertion.equals(null));

        // different comparator types -> returns false
        assertFalse(sortInsertion.equals(sortExpiry));
    }

    @Test
    public void toStringMethod() {
        ContractComparatorType comparatorType = ContractComparatorType.UNORDERED;
        SortContractCommand sortContractCommand = new SortContractCommand(comparatorType);
        String expected = SortContractCommand.class.getCanonicalName() + "{comparatorType=" + comparatorType + "}";
        assertEquals(expected, sortContractCommand.toString());
    }

    /**
     * A Model stub that always accepts the comparator type being set.
     */
    private class ModelStubAcceptingComparatorType extends ModelStub {
        @Override
        public void sortContracts(Comparator<Contract> comparator) {

        }
    }
}
