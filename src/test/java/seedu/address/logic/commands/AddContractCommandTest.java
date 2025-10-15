package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.policy.PolicyId;
import seedu.address.testutil.PersonBuilder;


public class AddContractCommandTest {

    private static final String INVALID_NRIC = "S0000000Z";
    private static final String VALID_NRIC = "S1234567A";
    private static final String VALID_PID = "abcdef";
    private static final String VALID_DATE = "2099-01-01";
    private static final Person PERSON_WITH_VALID_ID = new PersonBuilder().build();

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
    public void invalidNric_throwsCommandException() {
        Model modelStub = new ModelManager();

        Contract contractWithInvalidNric = new Contract(
                new Nric(INVALID_NRIC),
                new PolicyId(VALID_PID),
                LocalDate.parse(VALID_DATE));
        assertCommandFailure(new AddContractCommand(contractWithInvalidNric),
                modelStub, AddContractCommand.MESSAGE_PERSON_NOT_FOUND);
    }

    @Test
    public void validNric_success() {
        Model modelStub = new ModelManager();
        modelStub.addPerson(PERSON_WITH_VALID_ID);

        Contract contractWithValidNric = new Contract(
                new Nric(VALID_NRIC),
                new PolicyId(VALID_PID),
                LocalDate.parse(VALID_DATE));
        // crude test for now
        assertCommandSuccess(new AddContractCommand(contractWithValidNric),
                modelStub, AddContractCommand.MESSAGE_SUCCESS, modelStub);
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
