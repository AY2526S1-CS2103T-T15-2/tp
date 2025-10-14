package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

public class AddPolicyCommandMultipleTest {

    @Test
    public void constructor_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPolicyCommandMultiple(null));
    }

    @Test
    public void equals() {
        Path path = Path.of("example");
        Path otherPath = Path.of("different");
        AddPolicyCommandMultiple addPolicyCommand = new AddPolicyCommandMultiple(path);
        AddPolicyCommandMultiple addPolicyCommandOther = new AddPolicyCommandMultiple(otherPath);

        // same object -> returns true
        assertTrue(addPolicyCommand.equals(addPolicyCommand));

        // same values -> returns true
        AddPolicyCommandMultiple addPolicyCommandCopy = new AddPolicyCommandMultiple(path);
        assertTrue(addPolicyCommand.equals(addPolicyCommandCopy));

        // different types -> returns false
        assertFalse(addPolicyCommand.equals(1));

        // null -> returns false
        assertFalse(addPolicyCommand.equals(null));

        // different path -> returns false
        assertFalse(addPolicyCommand.equals(addPolicyCommandOther));
    }

    @Test
    public void toStringMethod() {
        Path path = Path.of("example");
        AddPolicyCommandMultiple addPolicyCommand = new AddPolicyCommandMultiple(path);
        String expected = AddPolicyCommandMultiple.class.getCanonicalName() + "{toAdd=" + path + "}";
        assertEquals(expected, addPolicyCommand.toString());
    }
}
