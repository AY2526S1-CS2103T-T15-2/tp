package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddContractCommand;
import seedu.address.model.person.Nric;
import seedu.address.model.policy.PolicyId;

public class AddContractCommandParserTest {

    private static final String VALID_NAME_1 = "Rachel Green";
    private static final String VALID_NAME_2 = "Monica Geller";
    private static final String INVALID_NAME = "R@chel";

    private static final String VALID_NRIC_1 = "S1234567A";
    private static final String VALID_NRIC_2 = "S7654321B";
    private static final String INVALID_NRIC = "S1234!67D";

    private static final String VALID_PID_1 = "P12345";
    private static final String VALID_PID_2 = "P54321";
    private static final String INVALID_PID = "P1234!";

    private static final String VALID_DATE_1 = "2023-10-01";
    private static final String VALID_DATE_2 = "2024-05-15";
    private static final String INVALID_DATE = "01-10-2023";

    private static final String PID_DESC = " " + PREFIX_PID + VALID_PID_1;
    private static final String NRIC_DESC = " " + PREFIX_NRIC + VALID_NRIC_1;
    private static final String DATE_DESC = " " + PREFIX_DATE + VALID_DATE_1;
    private static final String INVALID_PID_DESC = " " + PREFIX_PID + INVALID_PID;
    private static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + INVALID_NRIC;
    private static final String INVALID_DATE_DESC = " " + PREFIX_DATE + INVALID_DATE;

    private AddContractCommandParser parser = new AddContractCommandParser();

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedContractString = " " + PREFIX_NAME + VALID_NAME_1
                + " " + PREFIX_NRIC + VALID_NRIC_1
                + " " + PREFIX_PID + VALID_PID_1
                + " " + PREFIX_DATE + VALID_DATE_1;

        // multiple names
        assertParseFailure(parser, " " + PREFIX_NAME + VALID_NAME_2 + validExpectedContractString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContractCommand.MESSAGE_USAGE));

        // multiple nrics
        assertParseFailure(parser, " " + PREFIX_NRIC + VALID_NRIC_2 + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // multiple pids
        assertParseFailure(parser, " " + PREFIX_PID + VALID_PID_2 + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PID));

        // multiple dates
        assertParseFailure(parser, " " + PREFIX_DATE + VALID_DATE_2 + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple fields repeated
        assertParseFailure(parser, validExpectedContractString
                        + " " + PREFIX_NAME + VALID_NAME_2
                        + " " + PREFIX_NRIC + VALID_NRIC_2
                        + " " + PREFIX_PID + VALID_PID_2
                        + " " + PREFIX_DATE + VALID_DATE_2,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContractCommand.MESSAGE_USAGE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, " " + PREFIX_NAME + INVALID_NAME + validExpectedContractString,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContractCommand.MESSAGE_USAGE));

        // invalid nric
        assertParseFailure(parser, " " + PREFIX_NRIC + INVALID_NRIC + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // invalid pid
        assertParseFailure(parser, " " + PREFIX_PID + INVALID_PID + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PID));

        // invalid date
        assertParseFailure(parser, " " + PREFIX_DATE + INVALID_DATE + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContractCommand.MESSAGE_USAGE);

        // missing pId prefix
        assertParseFailure(parser, NRIC_DESC + DATE_DESC, expectedMessage);

        // missing Nric prefix
        assertParseFailure(parser, PID_DESC + DATE_DESC, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, PID_DESC + NRIC_DESC, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid pId
        assertParseFailure(parser, INVALID_PID_DESC + NRIC_DESC + DATE_DESC, PolicyId.MESSAGE_CONSTRAINTS);

        // invalid Nric
        assertParseFailure(parser, PID_DESC + INVALID_NRIC_DESC + DATE_DESC, Nric.MESSAGE_CONSTRAINTS);

        // invalid Date
        assertParseFailure(parser, PID_DESC + NRIC_DESC + INVALID_DATE_DESC,
                "Date should be in the format yyyy-MM-dd");

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PID_DESC + NRIC_DESC + DATE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContractCommand.MESSAGE_USAGE));
    }

}
