package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.policy.PolicyId;


public class AddContractCommandTest {

    @Test
    public void constructor_nullContract_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddContractCommand(null));
    }

    @Test
    public void equals() {
        Contract contract = new Contract(new ContractId("C1234A"), new Name("Alice"), new Nric("S1234567D"),
                new PolicyId("P1234A"), LocalDate.parse("2023-01-01"));
        Contract otherContract = new Contract(new ContractId("C1234B"), new Name("Bob"), new Nric("S7654321D"),
                new PolicyId("P1234B"), LocalDate.parse("2023-02-01"));
        AddContractCommand addContractCommand = new AddContractCommand(contract);
        AddContractCommand addContractCommandOther = new AddContractCommand(otherContract);

        // same object -> returns true
        assertTrue(addContractCommand.equals(addContractCommand));

        // same values -> returns true
        AddContractCommand addContractCommandCopy = new AddContractCommand(contract);
        assertTrue(addContractCommand.equals(addContractCommandCopy));

        // different types -> returns false
        assertFalse(addContractCommand.equals(1));

        // null -> returns false
        assertFalse(addContractCommand.equals(null));

        // different contract -> returns false
        assertFalse(addContractCommand.equals(addContractCommandOther));
    }

    @Test
    public void toStringMethod() {
        Contract contract = new Contract(new ContractId("C1234A"), new Name("Alice"), new Nric("S1234567D"),
                new PolicyId("P1234A"), LocalDate.parse("2023-01-01"));
        AddContractCommand addContractCommand = new AddContractCommand(contract);
        String expected = AddContractCommand.class.getCanonicalName() + "{toAdd=" + contract + "}";
        assertEquals(expected, addContractCommand.toString());
    }

}
