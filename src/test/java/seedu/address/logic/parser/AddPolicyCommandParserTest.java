package seedu.address.logic.parser;

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
import static seedu.address.logic.commands.PolicyCommandTestUtil.POLICY_PATH_A;
import static seedu.address.logic.commands.PolicyCommandTestUtil.POLICY_PATH_A_DESC;
import static seedu.address.logic.commands.PolicyCommandTestUtil.POLICY_PATH_B_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.PolicyUtil.unassign;
import static seedu.address.testutil.TypicalData.getHome;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.logic.commands.AddPolicyCommandType;
import seedu.address.logic.commands.AddPolicyFileCommand;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyName;
import seedu.address.testutil.PolicyBuilder;

public class AddPolicyCommandParserTest {

    private AddPolicyCommandParser parser = new AddPolicyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Policy expectedPolicy = new PolicyBuilder(getHome()).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + POLICY_NAME_DESC_HOME + DETAILS_DESC_HOME,
                new AddPolicyCommand(unassign(expectedPolicy)));

        // file option
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + POLICY_PATH_A_DESC,
                new AddPolicyFileCommand(POLICY_PATH_A));
    }

    @Test
    public void parse_repeatedValue_failure() {
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

        // multiple files
        assertParseFailure(parser, POLICY_PATH_A_DESC + POLICY_PATH_B_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FILE));

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
    public void parse_mixedOptions_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommandType.MESSAGE_USAGE);

        // name, details, and file provided
        assertParseFailure(parser, POLICY_NAME_DESC_HOME + DETAILS_DESC_HOME + POLICY_PATH_A_DESC, expectedMessage);

        // name and file provided
        assertParseFailure(parser, POLICY_NAME_DESC_HOME + POLICY_PATH_A_DESC, expectedMessage);

        // details and file provided
        assertParseFailure(parser, DETAILS_DESC_HOME + POLICY_PATH_A_DESC, expectedMessage);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommandType.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, POLICY_NAME_DESC_HOME, expectedMessage);

        // missing details prefix
        assertParseFailure(parser, DETAILS_DESC_HOME, expectedMessage);

        // no arguments
        assertParseFailure(parser, "", expectedMessage);
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
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommandType.MESSAGE_USAGE));
    }
}
