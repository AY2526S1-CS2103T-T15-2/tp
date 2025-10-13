package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.PolicyCommandTestUtil.DETAILS_DESC_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.DETAILS_DESC_HOME;
import static seedu.address.logic.commands.PolicyCommandTestUtil.INVALID_DETAILS_DESC;
import static seedu.address.logic.commands.PolicyCommandTestUtil.INVALID_POLICY_NAME_DESC;
import static seedu.address.logic.commands.PolicyCommandTestUtil.POLICY_NAME_DESC_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.POLICY_NAME_DESC_HOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalData.HOME;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyName;
import seedu.address.testutil.PolicyBuilder;

public class AddPolicyCommandParserTest {

    private AddPolicyCommandParser parser = new AddPolicyCommandParser();

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     * Modified for {@code AddPolicyCommandParser}.
     */
    public static void assertPolicyParseSuccess(AddPolicyCommandParser parser, String userInput,
            AddPolicyCommand expectedCommand) {
        try {
            AddPolicyCommand command = parser.parse(userInput);
            assertTrue(expectedCommand.weakEquals(command));
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Policy expectedPolicy = new PolicyBuilder(HOME).build();

        // whitespace only preamble
        assertPolicyParseSuccess(parser, PREAMBLE_WHITESPACE + POLICY_NAME_DESC_HOME + DETAILS_DESC_HOME,
                new AddPolicyCommand(expectedPolicy));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPolicyString = POLICY_NAME_DESC_HOME + DETAILS_DESC_HOME;

        // multiple names
        assertParseFailure(parser, POLICY_NAME_DESC_HEALTH_B + validExpectedPolicyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple details
        assertParseFailure(parser, DETAILS_DESC_HEALTH_B + validExpectedPolicyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DETAILS));

        // multiple fields repeated
        assertParseFailure(parser, validExpectedPolicyString + POLICY_NAME_DESC_HEALTH_B + DETAILS_DESC_HEALTH_B
                        + validExpectedPolicyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_DETAILS));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPolicyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid details
        assertParseFailure(parser, INVALID_DETAILS_DESC + validExpectedPolicyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DETAILS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPolicyString + INVALID_POLICY_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid details
        assertParseFailure(parser, validExpectedPolicyString + INVALID_DETAILS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DETAILS));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, POLICY_NAME_DESC_HOME, expectedMessage);

        // missing details prefix
        assertParseFailure(parser, DETAILS_DESC_HOME, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_POLICY_NAME_DESC + DETAILS_DESC_HOME, PolicyName.MESSAGE_CONSTRAINTS);

        // invalid details
        assertParseFailure(parser, INVALID_DETAILS_DESC + POLICY_NAME_DESC_HOME, PolicyDetails.MESSAGE_CONSTRAINTS);

        // two invalid values, only the first invalid value reported
        assertParseFailure(parser, INVALID_POLICY_NAME_DESC + INVALID_DETAILS_DESC, PolicyName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + POLICY_NAME_DESC_HOME + DETAILS_DESC_HOME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
    }
}
