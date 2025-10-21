package seedu.address.model.contract;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class ContractPremiumTest {

    @Test
    public void isValidContractPremium() {
        // null premium
        assertFalse(ContractPremium.isValidContractPremium((String) null));
        assertFalse(ContractPremium.isValidContractPremium((BigDecimal) null));

        // invalid premiums
        assertFalse(ContractPremium.isValidContractPremium("")); // empty string
        assertFalse(ContractPremium.isValidContractPremium(" ")); // spaces only
        assertFalse(ContractPremium.isValidContractPremium("-50")); // negative value
        assertFalse(ContractPremium.isValidContractPremium("abc")); // non-numeric

        // valid premiums
        assertTrue(ContractPremium.isValidContractPremium("0")); // zero value
        assertTrue(ContractPremium.isValidContractPremium("100")); // integer value
        assertTrue(ContractPremium.isValidContractPremium("99.99")); // valid decimal value
        assertTrue(ContractPremium.isValidContractPremium("0.01")); // minimum positive value
        assertTrue(ContractPremium.isValidContractPremium("100.123")); // more than 2 decimal places
    }

    @Test
    public void equals() {
        ContractPremium premium1 = new ContractPremium("100.0");

        // same object -> returns true
        assertTrue(premium1.equals(premium1));

        // null -> returns false
        assertFalse(premium1.equals(null));

        // different type -> returns false
        assertFalse(premium1.equals("100.0"));

        // different value -> returns false
        ContractPremium premium2 = new ContractPremium("200.0");
        assertFalse(premium1.equals(premium2));
    }
}
