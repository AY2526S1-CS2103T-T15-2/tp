package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DetailsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Details(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidDetails = "";
        assertThrows(IllegalArgumentException.class, () -> new Details(invalidDetails));
    }

    @Test
    public void isValidDetails() {
        // null details
        assertThrows(NullPointerException.class, () -> Details.isValidDetails(null));

        // invalid details
        assertFalse(Details.isValidDetails("")); // empty string
        assertFalse(Details.isValidDetails(" ")); // spaces only

        // valid details
        assertTrue(Details.isValidDetails("This policy"));
        assertTrue(Details.isValidDetails("a")); // one character
        assertTrue(Details.isValidDetails("This policy provides coverage for the insured and immediate family members against medical emergencies, hospitalization, and accidental injuries.")); // long details
    }

    @Test
    public void equals() {
        Details details = new Details("Valid Details");

        // same values -> returns true
        assertTrue(details.equals(new Details("Valid Details")));

        // same object -> returns true
        assertTrue(details.equals(details));

        // null -> returns false
        assertFalse(details.equals(null));

        // different types -> returns false
        assertFalse(details.equals(5.0f));

        // different values -> returns false
        assertFalse(details.equals(new Details("Other Valid Details")));
    }
}
