package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.InvalidLineException;
import seedu.address.logic.parser.exceptions.ParserIOException;
import seedu.address.model.policy.Policy;
import seedu.address.testutil.TypicalData;

public class PolicyFileParserTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "PolicyFileParserTest");

    private static boolean comparePolicies(List<Policy> policies, List<Policy> otherPolicies) {
        return policies.size() == otherPolicies.size()
                && IntStream.range(0, policies.size())
                .allMatch(i -> policies.get(i).weakEquals(otherPolicies.get(i)));
    }

    @Test
    public void typicalPoliciesFile_success() {
        Path path = TEST_DATA_FOLDER.resolve("typicalPoliciesFile.txt");
        List<Policy> policies = PolicyFileParser.readFile(path);
        assertTrue(comparePolicies(policies, TypicalData.getTypicalPolicies()));
    }

    @Test
    public void nullFilePath_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> PolicyFileParser.readFile(null));
    }

    @Test
    public void invalidPolicyFile_throwInvalidLineException() {
        Path path = TEST_DATA_FOLDER.resolve("invalidPolicyFile.txt");
        assertThrows(InvalidLineException.class, () -> PolicyFileParser.readFile(path));
    }

    @Test
    public void invalidAndValidPolicyFile_throwInvalidLineException() {
        Path path = TEST_DATA_FOLDER.resolve("invalidAndValidPolicyFile.txt");
        assertThrows(InvalidLineException.class, () -> PolicyFileParser.readFile(path));
    }

    @Test
    public void missingFile_throwParserIOException() {
        Path path = TEST_DATA_FOLDER.resolve("nonexistentPolicyFile.txt");
        assertThrows(ParserIOException.class, () -> PolicyFileParser.readFile(path));
    }
}
