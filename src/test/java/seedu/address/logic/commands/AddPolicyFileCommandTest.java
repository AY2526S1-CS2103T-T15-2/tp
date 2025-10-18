package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.PolicyFileParser;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AddPolicyFileCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "PolicyFileParserTest");

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPolicyFileCommand(null));
    }

    @Test
    public void execute_missingFile_throwsCommandException() {
        Path path = TEST_DATA_FOLDER.resolve("nonexistentPolicyFile.txt");
        AddPolicyFileCommand addPolicyCommand = new AddPolicyFileCommand(path);

        Exception exception = null;
        try {
            PolicyFileParser.readFile(path);
            fail();
        } catch (Exception e) {
            exception = e;
        }

        String expectedMessage = String.format(AddPolicyFileCommand.MESSAGE_IOEXCEPTION, exception.getMessage());

        assertCommandFailure(addPolicyCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        Path path = Path.of("example");
        Path otherPath = Path.of("different");
        AddPolicyFileCommand addPolicyCommand = new AddPolicyFileCommand(path);
        AddPolicyFileCommand addPolicyCommandOther = new AddPolicyFileCommand(otherPath);

        // same object -> returns true
        assertTrue(addPolicyCommand.equals(addPolicyCommand));

        // same values -> returns true
        AddPolicyFileCommand addPolicyCommandCopy = new AddPolicyFileCommand(path);
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
        AddPolicyFileCommand addPolicyCommand = new AddPolicyFileCommand(path);
        String expected = AddPolicyFileCommand.class.getCanonicalName() + "{toAdd=" + path + "}";
        assertEquals(expected, addPolicyCommand.toString());
    }
}
