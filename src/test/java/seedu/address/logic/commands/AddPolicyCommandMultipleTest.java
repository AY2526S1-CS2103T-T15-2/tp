package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;
import static seedu.address.testutil.TypicalId.CONTRACT_A_ID;
import static seedu.address.testutil.TypicalId.VALID_POLICY_ID_2;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.PolicyFileParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AddPolicyCommandMultipleTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "PolicyFileParserTest");

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPolicyCommandMultiple(null));
    }

    @Test
    public void execute_missingFile_throwsCommandException() {
        Path path = TEST_DATA_FOLDER.resolve("nonexistentPolicyFile.txt");
        AddPolicyCommandMultiple addPolicyCommand = new AddPolicyCommandMultiple(path);

        IOException exception = null;
        try {
            PolicyFileParser.readFile(path);
            fail();
        } catch (IOException e) {
            exception = e;
        }

        String expectedMessage = String.format(AddPolicyCommandMultiple.MESSAGE_IOEXCEPTION, exception.getMessage());

        assertCommandFailure(addPolicyCommand, model, expectedMessage);
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
