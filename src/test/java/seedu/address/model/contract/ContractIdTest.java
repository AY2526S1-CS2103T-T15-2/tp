package seedu.address.model.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ContractIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ContractId(null));
    }

    @Test
    public void constructor_invalidContractId_throwsIllegalArgumentException() {
        String invalidContractId = "";
        assertThrows(IllegalArgumentException.class, () -> new ContractId(invalidContractId));
    }

    @Test
    public void isValidContractId() {
        // null id
        assertThrows(NullPointerException.class, () -> ContractId.isValidContractId(null));

        // invalid name
        assertFalse(ContractId.isValidContractId("")); // empty string
        assertFalse(ContractId.isValidContractId("      ")); // spaces only
        assertFalse(ContractId.isValidContractId("^&*+-$")); // only non-alphanumeric characters
        assertFalse(ContractId.isValidContractId("abcde*")); // contains non-alphanumeric characters
        assertFalse(ContractId.isValidContractId("abcd")); // incorrect length

        // valid name
        assertTrue(ContractId.isValidContractId("abcdef")); // alphabets only
        assertTrue(ContractId.isValidContractId("123456")); // numbers only
        assertTrue(ContractId.isValidContractId("String")); // with capital letters
        assertTrue(ContractId.isValidContractId("Abc123")); // alphanumeric
    }

    @Test
    public void generate() {
        assertEquals(ContractId.ID_LENGTH, ContractId.generate().value.length());
    }

    @Test
    public void equals() {
        ContractId contractId = new ContractId("abcdef");

        // same object -> returns true
        assertTrue(contractId.equals(contractId));

        // null -> returns false
        assertFalse(contractId.equals(null));

        // different types -> returns false
        assertFalse(contractId.equals(5.0f));

        // different values -> returns false
        // note: randomly generated
        assertFalse(contractId.equals(new ContractId("123456")));
    }
}
