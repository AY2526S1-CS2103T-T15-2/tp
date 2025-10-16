package seedu.address.logic.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.parser.exceptions.InvalidLineException;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.UnassignedPolicy;

/**
 * Contains utility methods for reading and parsing policies from text files.
 */
public class PolicyFileParser {

    private static final String DELIMITER = "`";
    private static final String GROUP_NAME = "name";
    private static final String GROUP_DETAILS = "details";
    private static final Pattern PATTERN = Pattern.compile(
            "^\\s*(?<" + GROUP_NAME + ">" + PolicyName.VALIDATION_REGEX + ")"
                    + DELIMITER
                    + "(?<" + GROUP_DETAILS + ">" + PolicyDetails.VALIDATION_REGEX + ")\\s*$");

    private static final String MESSAGE_INVALID_LINE = "Error when reading line %d. "
            + "Each line should contain a valid policy name and details, separated by " + DELIMITER;

    /**
     * Reads and parses policies from the given text file.
     */
    public static List<UnassignedPolicy> readFile(Path filePath) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            ArrayList<UnassignedPolicy> policies = new ArrayList<>();
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                policies.add(parseLine(lineNumber, line));
            }

            return policies;
        }
    }

    private static UnassignedPolicy parseLine(int lineNumber, String line) {
        Matcher m = PATTERN.matcher(line);
        if (m.find()) {
            return new UnassignedPolicy(
                    new PolicyName(m.group(GROUP_NAME).trim()),
                    new PolicyDetails(m.group(GROUP_DETAILS).trim())
            );
        } else {
            throw new InvalidLineException(String.format(MESSAGE_INVALID_LINE, lineNumber));
        }
    }
}
