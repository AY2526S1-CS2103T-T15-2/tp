package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.EditContractCommand.EditContractDescriptor;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalData.ALICE;
import static seedu.address.testutil.TypicalData.BENSON;
import static seedu.address.testutil.TypicalData.CONTRACT_A;
import static seedu.address.testutil.TypicalData.CONTRACT_B;
import static seedu.address.testutil.TypicalData.HEALTH;
import static seedu.address.testutil.TypicalData.LIFE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditContractCommand;
import seedu.address.testutil.EditContractDescriptorBuilder;

public class EditContractCommandParserTest {

    private static final String NAME_A = ALICE.getName().toString();
    private static final String NRIC_A = ALICE.getNric().toString();
    private static final String NAME_B = BENSON.getName().toString();
    private static final String NRIC_B = BENSON.getNric().toString();
    private static final String POLICY_ID_LIFE = LIFE.getId().toString();
    private static final String POLICY_ID_HEALTH = HEALTH.getId().toString();
    private static final String CONTRACT_ID_A = CONTRACT_A.getCId().toString();
    private static final String CONTRACT_ID_B = CONTRACT_B.getCId().toString();
    private static final String CONTRACT_DATE_A = CONTRACT_A.getDate().toString();
    private static final String CONTRACT_EXPIRY_A = CONTRACT_A.getExpiryDate().toString();
    private static final String CONTRACT_DATE_B = CONTRACT_B.getDate().toString();
    private static final String CONTRACT_EXPIRY_B = CONTRACT_B.getExpiryDate().toString();

    private static final String VALID_NAME_DESC_A = PREFIX_NAME + NAME_A + " ";
    private static final String VALID_NAME_DESC_B = PREFIX_NAME + NAME_B + " ";
    private static final String VALID_NRIC_DESC_A = PREFIX_NRIC + NRIC_A + " ";
    private static final String VALID_NRIC_DESC_B = PREFIX_NRIC + NRIC_B + " ";
    private static final String VALID_PID_DESC_A = PREFIX_PID + POLICY_ID_LIFE + " ";
    private static final String VALID_PID_DESC_B = PREFIX_PID + POLICY_ID_HEALTH + " ";
    private static final String VALID_CID_DESC_A = PREFIX_CID + CONTRACT_ID_A + " ";
    private static final String VALID_CID_DESC_B = PREFIX_CID + CONTRACT_ID_B + " ";
    private static final String VALID_DATE_DESC_A = PREFIX_DATE + CONTRACT_DATE_A + " ";
    private static final String VALID_DATE_DESC_B = PREFIX_DATE + CONTRACT_DATE_B + " ";
    private static final String VALID_EXPIRY_DESC_A = PREFIX_EXPIRY + CONTRACT_EXPIRY_A + " ";
    private static final String VALID_EXPIRY_DESC_B = PREFIX_EXPIRY + CONTRACT_EXPIRY_B + " ";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditContractCommand.MESSAGE_USAGE);

    private EditContractCommandParser parser = new EditContractCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = EditContractCommand.COMMAND_WORD + " " + VALID_CID_DESC_A + VALID_PID_DESC_B
                + VALID_NRIC_DESC_B + VALID_DATE_DESC_B
                + VALID_EXPIRY_DESC_B;
        EditContractDescriptor descriptor = new EditContractDescriptorBuilder()
                .withCId(CONTRACT_ID_A)
                .withPId(POLICY_ID_HEALTH)
                .withNric(NRIC_B)
                .withDate(CONTRACT_DATE_B)
                .withExpiryDate(CONTRACT_EXPIRY_B)
                .build();
        EditContractCommand expectedCommand = new EditContractCommand(CONTRACT_A.getCId(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsPresent_success() {
        String userInput = EditContractCommand.COMMAND_WORD + " " + VALID_CID_DESC_A + VALID_NRIC_DESC_B
                + VALID_DATE_DESC_B;
        EditContractDescriptor descriptor = new EditContractDescriptorBuilder()
                .withCId(CONTRACT_ID_A)
                .withNric(NRIC_B)
                .withDate(CONTRACT_DATE_B)
                .build();
        EditContractCommand expectedCommand = new EditContractCommand(CONTRACT_A.getCId(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() {
        String userInput = EditContractCommand.COMMAND_WORD + " " + VALID_CID_DESC_A;
        assertParseFailure(parser, userInput, EditContractCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_missingContractId_failure() {
        String userInput = EditContractCommand.COMMAND_WORD + " " + VALID_NRIC_DESC_B + VALID_DATE_DESC_B;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        String userInput = EditContractCommand.COMMAND_WORD + " " + VALID_CID_DESC_A
                + VALID_NRIC_DESC_B + VALID_NRIC_DESC_A
                + VALID_DATE_DESC_B + VALID_DATE_DESC_A;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC, PREFIX_DATE));
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        String userInput;
        EditContractDescriptor descriptor;
        EditContractCommand expectedCommand;

        // nric
        userInput = EditContractCommand.COMMAND_WORD + " " + VALID_CID_DESC_A + VALID_NRIC_DESC_B;
        descriptor = new EditContractDescriptorBuilder()
                .withCId(CONTRACT_ID_A)
                .withNric(NRIC_B)
                .build();
        expectedCommand = new EditContractCommand(CONTRACT_A.getCId(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // policy id
        userInput = EditContractCommand.COMMAND_WORD + " " + VALID_CID_DESC_A + VALID_PID_DESC_B;
        descriptor = new EditContractDescriptorBuilder()
                .withCId(CONTRACT_ID_A)
                .withPId(POLICY_ID_HEALTH)
                .build();
        expectedCommand = new EditContractCommand(CONTRACT_A.getCId(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date signed
        userInput = EditContractCommand.COMMAND_WORD + " " + VALID_CID_DESC_A + VALID_DATE_DESC_B;
        descriptor = new EditContractDescriptorBuilder()
                .withCId(CONTRACT_ID_A)
                .withDate(CONTRACT_DATE_B)
                .build();
        expectedCommand = new EditContractCommand(CONTRACT_A.getCId(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // expiry date
        userInput = EditContractCommand.COMMAND_WORD + " " + VALID_CID_DESC_A + VALID_EXPIRY_DESC_B;
        descriptor = new EditContractDescriptorBuilder()
                .withCId(CONTRACT_ID_A)
                .withExpiryDate(CONTRACT_EXPIRY_B)
                .build();
        expectedCommand = new EditContractCommand(CONTRACT_A.getCId(), descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
