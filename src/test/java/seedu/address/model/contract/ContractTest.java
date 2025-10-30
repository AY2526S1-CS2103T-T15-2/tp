package seedu.address.model.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.contact.Name;
import seedu.address.model.contact.Nric;
import seedu.address.model.contract.exceptions.InvalidContractDatesException;
import seedu.address.model.policy.PolicyId;

public class ContractTest {

    @Test
    public void hasSameId() {
        Contract contract = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2025-01-01"),
                new ContractPremium("1000.00"));

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
                LocalDate.parse("2023-01-02"),
                LocalDate.parse("2025-01-02"),
                new ContractPremium("2000.00"));
        assertEquals(contract.getCId(), editedContract.getCId());

        // different id, all other attributes same -> returns false
        editedContract = new Contract(
                new ContractId("bcdefg"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2025-01-01"),
                new ContractPremium("1000.00"));
        assertFalse(contract.getCId().equals(editedContract.getCId()));

        // id differs in case, all other attributes same -> returns false
        editedContract = new Contract(
                new ContractId("ABCDEF"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2025-01-01"),
                new ContractPremium("1000.00"));
        assertFalse(contract.getCId().equals(editedContract.getCId()));
    }

    @Test
    public void equals() {
        Contract contract = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2025-01-01"),
                new ContractPremium("1000.00"));

        // same values -> returns true
        Contract contractCopy = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2025-01-01"),
                new ContractPremium("1000.00"));
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
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2025-01-01"),
                new ContractPremium("1000.00"));
        assertFalse(contract.equals(differentContract));

        // different name -> returns false
        Contract editedContract = new Contract(
                new ContractId("abcdef"),
                new Name("TestDiff"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2025-01-01"),
                new ContractPremium("1000.00"));
        assertFalse(contract.equals(editedContract));

        // different NRIC -> returns false
        editedContract = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("T1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2025-01-01"),
                new ContractPremium("1000.00"));
        assertFalse(contract.equals(editedContract));

        // different PolicyId -> returns false
        editedContract = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("bcdefg"),
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2025-01-01"),
                new ContractPremium("1000.00"));
        assertFalse(contract.equals(editedContract));

        // different Date -> returns false
        editedContract = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-02"),
                LocalDate.parse("2025-01-01"),
                new ContractPremium("1000.00"));
        assertFalse(contract.equals(editedContract));

        // different Expiry -> returns false
        editedContract = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2025-01-02"),
                new ContractPremium("1000.00"));
        assertFalse(contract.equals(editedContract));

        // different Premium -> returns false
        // different Expiry -> returns false
        editedContract = new Contract(
                new ContractId("abcdef"),
                new Name("Test"),
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2025-01-02"),
                new ContractPremium("1234.00"));
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
                LocalDate.parse("2023-01-01"),
                LocalDate.parse("2025-01-01"),
                new ContractPremium("1000.00"));
        String expected = Contract.class.getCanonicalName()
                + "{cId=" + contract.getCId()
                + ", name=" + contract.getName()
                + ", nric=" + contract.getNric()
                + ", pId=" + contract.getPId()
                + ", dateSigned=" + contract.getDate()
                + ", expiryDate=" + contract.getExpiryDate()
                + ", premium=" + contract.getPremium() + "}";
        assertEquals(expected, contract.toString());
    }

    @Test
    public void differentContractsNotEqual() {
        Contract contract1 = new Contract(
                new Nric("T1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-02"),
                LocalDate.parse("2025-01-02"),
                new ContractPremium("1000.00")
        );
        Contract contract2 = new Contract(
                new Nric("S1234567A"),
                new PolicyId("abcdef"),
                LocalDate.parse("2023-01-02"),
                LocalDate.parse("2025-01-02"),
                new ContractPremium("1000.00")
        );
        assertNotEquals(contract1, contract2);
    }

    @Test
    public void invalidPeriodThrowsException() {
        Nric nric = new Nric("T1234567A");
        PolicyId policyId = new PolicyId("abcdef");
        LocalDate date = LocalDate.parse("2025-10-19");
        LocalDate expiry = LocalDate.parse("2000-01-01");
        ContractPremium premium = new ContractPremium("1000.00");
        assertThrows(InvalidContractDatesException.class, () -> new Contract(nric, policyId, date, expiry, premium));
    }

    @Test
    public void invalidPremiumThrowsException() {
        Nric nric = new Nric("T1234567A");
        PolicyId policyId = new PolicyId("abcdef");
        LocalDate date = LocalDate.parse("2025-10-19");
        LocalDate expiry = LocalDate.parse("2025-10-21");
        assertThrows(IllegalArgumentException.class, () -> new Contract(nric, policyId, date,
                expiry, new ContractPremium("-1000.00")));
    }
}
