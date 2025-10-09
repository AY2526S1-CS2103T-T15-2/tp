package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.PolicyName;

public class AddPolicyCommandParserTest {

    private static final String VALID_NAME_1 = "Healthcare - A";
    private static final String VALID_NAME_2 = "Life Insurance";
    private static final String INVALID_NAME = "Medical*";

    private static final String VALID_DETAILS_1 = "This policy coverage...";
    private static final String VALID_DETAILS_2 = "Other policy details";

    private static final String NAME_DESC_1 = " " + PREFIX_NAME + VALID_NAME_1;
    private static final String NAME_DESC_2 = " " + PREFIX_NAME + VALID_NAME_2;
    private static final String INVALID_NAME_DESC = " " + PREFIX_NAME + INVALID_NAME;

    private static final String DETAILS_DESC_1 = " " + PREFIX_DETAILS + VALID_DETAILS_1;
    private static final String DETAILS_DESC_2 = " " + PREFIX_DETAILS + VALID_DETAILS_2;
    private static final String INVALID_DETAILS_DESC = " " + PREFIX_DETAILS;

    private AddPolicyCommandParser parser = new AddPolicyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Policy expectedPolicy = new Policy(
                new PolicyName(VALID_NAME_1), new PolicyDetails(VALID_DETAILS_1), new PolicyId("AAAAAA"));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_1 + DETAILS_DESC_1,
                new AddPolicyCommand(expectedPolicy));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPolicyString = NAME_DESC_1 + DETAILS_DESC_1;

        // multiple names
        assertParseFailure(parser, NAME_DESC_2 + validExpectedPolicyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple details
        assertParseFailure(parser, DETAILS_DESC_2 + validExpectedPolicyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DETAILS));

        // multiple fields repeated
        assertParseFailure(parser, validExpectedPolicyString + NAME_DESC_2 + DETAILS_DESC_2
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
        assertParseFailure(parser, validExpectedPolicyString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid details
        assertParseFailure(parser, validExpectedPolicyString + INVALID_DETAILS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DETAILS));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, NAME_DESC_1, expectedMessage);

        // missing details prefix
        assertParseFailure(parser, DETAILS_DESC_1, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + DETAILS_DESC_1, PolicyName.MESSAGE_CONSTRAINTS);

        // invalid details
        assertParseFailure(parser, INVALID_DETAILS_DESC + NAME_DESC_1 , PolicyDetails.MESSAGE_CONSTRAINTS);

        // two invalid values, only the first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_DETAILS_DESC, PolicyName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_1 + DETAILS_DESC_1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPolicyCommand.MESSAGE_USAGE));
    }
}
