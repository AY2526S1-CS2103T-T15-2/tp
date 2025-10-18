package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.UnassignedPolicy;
import seedu.address.testutil.TypicalData;

public class PolicyFileParserTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "PolicyFileParserTest");

    private static boolean comparePolicies(List<UnassignedPolicy> unassignedPolicies, List<Policy> expectedPolicies) {
        return unassignedPolicies.size() == expectedPolicies.size()
                && IntStream.range(0, unassignedPolicies.size())
                .allMatch(i -> {
                    UnassignedPolicy unassignedPolicy = unassignedPolicies.get(i);
                    Policy expectedPolicy = expectedPolicies.get(i);
                    return unassignedPolicy.getName().equals(expectedPolicy.getName())
                            && unassignedPolicy.getDetails().equals(expectedPolicy.getDetails());
                });
    }

    @Test
    public void typicalPoliciesFile_success() {
        Path path = TEST_DATA_FOLDER.resolve("typicalPoliciesFile.txt");
        List<UnassignedPolicy> unassignedPolicies = null;
        try {
            unassignedPolicies = PolicyFileParser.readFile(path);
        } catch (Exception e) {
            fail();
        }
        assertTrue(comparePolicies(unassignedPolicies, TypicalData.getTypicalPolicies()));
    }

    @Test
    public void nullFilePath_throwNullPointerException() {
        assertThrows(NullPointerException.class, () -> PolicyFileParser.readFile(null));
    }

    @Test
    public void invalidPolicyFile_throwInvalidLineException() {
        Path path = TEST_DATA_FOLDER.resolve("invalidPolicyFile.txt");
        assertThrows(ParseException.class, () -> PolicyFileParser.readFile(path));
    }

    @Test
    public void invalidAndValidPolicyFile_throwInvalidLineException() {
        Path path = TEST_DATA_FOLDER.resolve("invalidAndValidPolicyFile.txt");
        assertThrows(ParseException.class, () -> PolicyFileParser.readFile(path));
    }

    @Test
    public void missingFile_throwIoException() {
        Path path = TEST_DATA_FOLDER.resolve("nonexistentPolicyFile.txt");
        assertThrows(IOException.class, () -> PolicyFileParser.readFile(path));
    }
}
