package seedu.address.model.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.policy.PolicyId;

public class ContractTest {

    @Test
    public void hasSameId() {
        Contract contract = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"));

        // same object -> returns true
        assertTrue(contract.isSameContract(contract));

        // null -> returns false
        assertFalse(contract.isSameContract(null));

        // same id, all other attributes different -> returns true
        Contract editedContract = new Contract(
                new ContractId("abcdef"),
                new Name("TestDiff"),
                new Nric("T1234567A"),
                new PolicyId("abceef"),
                LocalDate.parse("2023-01-02"));
        assertEquals(contract.getCId(), editedContract.getCId());

        // different id, all other attributes same -> returns false
        editedContract = new Contract(
                new ContractId("bcdefg"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"));
        assertFalse(contract.getCId().equals(editedContract.getCId()));

        // id differs in case, all other attributes same -> returns false
        editedContract = new Contract(
                new ContractId("ABCDEF"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"));
        assertFalse(contract.getCId().equals(editedContract.getCId()));
    }

    @Test
    public void equals() {
        Contract contract = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"));

        // same values -> returns true
        Contract contractCopy = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"));
        assertTrue(contract.isSameContract(contractCopy));
        assertTrue(contract.equals(contractCopy));

        // same object -> returns true
        assertTrue(contract.equals(contract));

        // null -> returns false
        assertFalse(contract.equals(null));

        // different type -> returns false
        assertFalse(contract.equals(5));

        // different id -> returns false
        Contract differentContract = new Contract(
                new ContractId("123456"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"));
        assertFalse(contract.equals(differentContract));

        // different name -> returns false
        Contract editedContract = new Contract(
                new ContractId("abcdef"),
                new Name("TestDiff"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"));
        assertFalse(contract.equals(editedContract));

        // different NRIC -> returns false
        editedContract = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("T1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"));
        assertFalse(contract.equals(editedContract));

        // different PolicyId -> returns false
        editedContract = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("bcdefg"),
                LocalDate.parse("2023-01-01"));
        assertFalse(contract.equals(editedContract));

        // different LocalDate -> returns false
        editedContract = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-02"));
        assertFalse(contract.equals(editedContract));

        // hashcode same for same object
        assertEquals(contract.hashCode(), contract.hashCode());
    }

    @Test
    public void toStringMethod() {
        Contract contract = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"));
        String expected = Contract.class.getCanonicalName()
                + "{cId=" + contract.getCId()
                + ", name=" + contract.getName()
                + ", nric=" + contract.getNric()
                + ", pId=" + contract.getPId()
                + ", dateSigned=" + contract.getDate() + "}";
        assertEquals(expected, contract.toString());
    }

    @Test
    public void differentContractsNotEqual() {
        Contract contract1 = new Contract(
                new Nric("T1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-02")
        );
        Contract contract2 = new Contract(
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-02")
        );
        assertNotEquals(contract1, contract2);
    }

}
