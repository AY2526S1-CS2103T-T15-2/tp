package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.PolicyCommandTestUtil.DETAILS_DESC_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.DETAILS_DESC_HOME;
import static seedu.address.logic.commands.PolicyCommandTestUtil.INVALID_DETAILS_DESC;
import static seedu.address.logic.commands.PolicyCommandTestUtil.INVALID_POLICY_ID_DESC;
import static seedu.address.logic.commands.PolicyCommandTestUtil.INVALID_POLICY_NAME_DESC;
import static seedu.address.logic.commands.PolicyCommandTestUtil.POLICY_ID_DESC_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.POLICY_ID_DESC_HOME;
import static seedu.address.logic.commands.PolicyCommandTestUtil.POLICY_NAME_DESC_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.POLICY_NAME_DESC_HOME;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_DETAILS_HOME;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HOME;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_NAME_HOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditPolicyCommand;
import seedu.address.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.PolicyName;
import seedu.address.testutil.EditPolicyDescriptorBuilder;

public class EditPolicyCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditPolicyCommand.MESSAGE_USAGE);

    private EditPolicyCommandParser parser = new EditPolicyCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no policy id specified
        assertParseFailure(parser, POLICY_NAME_DESC_HOME, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, POLICY_ID_DESC_HOME, EditPolicyCommand.MESSAGE_NOT_EDITED);

        // no policy id and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        assertParseFailure(parser, "1" + POLICY_ID_DESC_HOME + POLICY_NAME_DESC_HOME, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid policy id
        assertParseFailure(parser, INVALID_POLICY_ID_DESC + POLICY_NAME_DESC_HOME, PolicyId.MESSAGE_CONSTRAINTS);
        // invalid policy name
        assertParseFailure(parser, POLICY_ID_DESC_HOME + INVALID_POLICY_NAME_DESC, PolicyName.MESSAGE_CONSTRAINTS);
        // invalid policy details
        assertParseFailure(parser, POLICY_ID_DESC_HOME + INVALID_DETAILS_DESC, PolicyDetails.MESSAGE_CONSTRAINTS);

        // invalid policy name followed by valid policy details
        assertParseFailure(parser, POLICY_ID_DESC_HOME + INVALID_POLICY_NAME_DESC + DETAILS_DESC_HOME,
                PolicyName.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, INVALID_POLICY_ID_DESC + INVALID_POLICY_NAME_DESC + INVALID_DETAILS_DESC,
                PolicyId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        PolicyId policyId = new PolicyId(VALID_POLICY_ID_HOME);
        String userInput = POLICY_ID_DESC_HOME + POLICY_NAME_DESC_HOME + DETAILS_DESC_HOME;
        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder()
                .withName(VALID_POLICY_NAME_HOME)
                .withDetails(VALID_DETAILS_HOME)
                .build();
        EditPolicyCommand expectedCommand = new EditPolicyCommand(policyId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        PolicyId policyId = new PolicyId(VALID_POLICY_ID_HOME);
        String userInput = POLICY_ID_DESC_HOME + DETAILS_DESC_HOME;
        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder().withDetails(VALID_DETAILS_HOME).build();
        EditPolicyCommand expectedCommand = new EditPolicyCommand(policyId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // policy name
        PolicyId policyId = new PolicyId(VALID_POLICY_ID_HOME);
        String userInput = POLICY_ID_DESC_HOME + POLICY_NAME_DESC_HOME;
        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder().withName(VALID_POLICY_NAME_HOME).build();
        EditPolicyCommand expectedCommand = new EditPolicyCommand(policyId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // policy details
        userInput = POLICY_ID_DESC_HOME + DETAILS_DESC_HOME;
        descriptor = new EditPolicyDescriptorBuilder().withDetails(VALID_DETAILS_HOME).build();
        expectedCommand = new EditPolicyCommand(policyId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        PolicyId policyId = new PolicyId(VALID_POLICY_ID_HOME);

        // valid followed by invalid
        String userInput = POLICY_ID_DESC_HOME + POLICY_NAME_DESC_HOME + INVALID_POLICY_NAME_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid followed by valid
        userInput = POLICY_ID_DESC_HOME + INVALID_POLICY_NAME_DESC + POLICY_NAME_DESC_HOME;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple valid fields repeated
        userInput = POLICY_ID_DESC_HOME + POLICY_NAME_DESC_HOME + DETAILS_DESC_HOME
                + POLICY_ID_DESC_HOME + POLICY_NAME_DESC_HOME + DETAILS_DESC_HOME
                + POLICY_ID_DESC_HEALTH_B + POLICY_NAME_DESC_HEALTH_B + DETAILS_DESC_HEALTH_B;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PID, PREFIX_NAME, PREFIX_DETAILS));

        // multiple invalid values
        userInput = INVALID_POLICY_ID_DESC + INVALID_POLICY_NAME_DESC + INVALID_DETAILS_DESC
                + INVALID_POLICY_ID_DESC + INVALID_POLICY_NAME_DESC + INVALID_DETAILS_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PID, PREFIX_NAME, PREFIX_DETAILS));
    }
}
