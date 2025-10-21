package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddContractCommand;
import seedu.address.model.contract.ContractPremium;
import seedu.address.model.person.Nric;
import seedu.address.model.policy.PolicyId;

public class AddContractCommandParserTest {

    private static final String VALID_NRIC_1 = "S1234567A";
    private static final String VALID_NRIC_2 = "S7654321B";
    private static final String INVALID_NRIC = "S1234!67D";

    private static final String VALID_PID_1 = "P12345";
    private static final String VALID_PID_2 = "P54321";
    private static final String INVALID_PID = "P1234!";

    private static final String VALID_DATE_1 = "2023-10-01";
    private static final String VALID_DATE_2 = "2024-05-15";
    private static final String INVALID_DATE = "01-10-2023";

    private static final String VALID_EXPIRY_1 = "2025-10-01";
    private static final String VALID_EXPIRY_2 = "2026-05-15";
    private static final String INVALID_EXPIRY_DATE_INVALID_FORMAT = "01-10-2025";
    private static final String INVALID_EXPIRY_INVALID_PERIOD = "2000-01-01";

    private static final String VALID_PREMIUM_1 = "1000.00";
    private static final String VALID_PREMIUM_2 = "1500.00";
    private static final String INVALID_PREMIUM = "-500.00";

    private static final String PID_DESC = " " + PREFIX_PID + VALID_PID_1;
    private static final String NRIC_DESC = " " + PREFIX_NRIC + VALID_NRIC_1;
    private static final String DATE_DESC = " " + PREFIX_DATE + VALID_DATE_1;
    private static final String EXPIRY_DESC = " " + PREFIX_EXPIRY + VALID_EXPIRY_1;
    private static final String PREMIUM_DESC = " " + PREFIX_PREMIUM + VALID_PREMIUM_1;
    private static final String INVALID_PID_DESC = " " + PREFIX_PID + INVALID_PID;
    private static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + INVALID_NRIC;
    private static final String INVALID_DATE_DESC = " " + PREFIX_DATE + INVALID_DATE;
    private static final String INVALID_EXPIRY_DESC_1 = " " + PREFIX_EXPIRY + INVALID_EXPIRY_DATE_INVALID_FORMAT;
    private static final String INVALID_EXPIRY_DESC_2 = " " + PREFIX_EXPIRY + INVALID_EXPIRY_INVALID_PERIOD;
    private static final String INVALID_PREMIUM_DESC = " " + PREFIX_PREMIUM + INVALID_PREMIUM;

    private AddContractCommandParser parser = new AddContractCommandParser();

    @Test
    public void testMethod() {
        String validExpectedContractString = " " + PREFIX_PID + VALID_PID_1
                + " " + PREFIX_NRIC + VALID_NRIC_1
                + " " + PREFIX_DATE + VALID_DATE_1
                + " " + PREFIX_EXPIRY + VALID_EXPIRY_1
                + " " + PREFIX_PREMIUM + VALID_PREMIUM_1;

        assertParseFailure(parser, " " + PREFIX_EXPIRY + VALID_EXPIRY_2 + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EXPIRY));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedContractString = " " + PREFIX_PID + VALID_PID_1
                + " " + PREFIX_NRIC + VALID_NRIC_1
                + " " + PREFIX_DATE + VALID_DATE_1
                + " " + PREFIX_EXPIRY + VALID_EXPIRY_1
                + " " + PREFIX_PREMIUM + VALID_PREMIUM_1;

        // multiple nrics
        assertParseFailure(parser, " " + PREFIX_NRIC + VALID_NRIC_2 + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // multiple pids
        assertParseFailure(parser, " " + PREFIX_PID + VALID_PID_2 + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PID));

        // multiple dates
        assertParseFailure(parser, " " + PREFIX_DATE + VALID_DATE_2 + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple expiries
        assertParseFailure(parser, " " + PREFIX_EXPIRY + VALID_EXPIRY_2 + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EXPIRY));

        // multiple fields repeated
        assertParseFailure(parser, validExpectedContractString
                        + " " + PREFIX_NRIC + VALID_NRIC_2
                        + " " + PREFIX_PID + VALID_PID_2
                        + " " + PREFIX_DATE + VALID_DATE_2
                        + " " + PREFIX_EXPIRY + VALID_EXPIRY_2
                        + " " + PREFIX_PREMIUM + VALID_PREMIUM_2,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC, PREFIX_PID, PREFIX_DATE,
                        PREFIX_EXPIRY, PREFIX_PREMIUM));

        // invalid value followed by valid value

        // invalid nric
        assertParseFailure(parser, " " + PREFIX_NRIC + INVALID_NRIC + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // invalid pid
        assertParseFailure(parser, " " + PREFIX_PID + INVALID_PID + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PID));

        // invalid date
        assertParseFailure(parser, " " + PREFIX_DATE + INVALID_DATE + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid expiry(date format)
        assertParseFailure(parser, " " + PREFIX_EXPIRY + INVALID_EXPIRY_DATE_INVALID_FORMAT
                        + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EXPIRY));

        // invalid expiry(invalid period)
        assertParseFailure(parser, " " + PREFIX_EXPIRY + INVALID_EXPIRY_INVALID_PERIOD
                        + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EXPIRY));

        // invalid premium(negative value)
        assertParseFailure(parser, " " + PREFIX_PREMIUM + INVALID_PREMIUM + validExpectedContractString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PREMIUM));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContractCommand.MESSAGE_USAGE);

        // missing pId prefix
        assertParseFailure(parser, NRIC_DESC + DATE_DESC + EXPIRY_DESC + PREMIUM_DESC, expectedMessage);

        // missing Nric prefix
        assertParseFailure(parser, PID_DESC + DATE_DESC + EXPIRY_DESC + PREMIUM_DESC, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, PID_DESC + NRIC_DESC + EXPIRY_DESC + PREMIUM_DESC, expectedMessage);

        // missing expiry prefix
        assertParseFailure(parser, PID_DESC + NRIC_DESC + DATE_DESC + PREMIUM_DESC, expectedMessage);

        // missing premium prefix
        assertParseFailure(parser, PID_DESC + NRIC_DESC + DATE_DESC + EXPIRY_DESC, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid pId
        assertParseFailure(parser, INVALID_PID_DESC + NRIC_DESC + DATE_DESC + EXPIRY_DESC + PREMIUM_DESC,
                PolicyId.MESSAGE_CONSTRAINTS);

        // invalid Nric
        assertParseFailure(parser, PID_DESC + INVALID_NRIC_DESC + DATE_DESC + EXPIRY_DESC + PREMIUM_DESC,
                Nric.MESSAGE_CONSTRAINTS);

        // invalid Date
        assertParseFailure(parser, PID_DESC + NRIC_DESC + INVALID_DATE_DESC + EXPIRY_DESC + PREMIUM_DESC,
                Messages.MESSAGE_INVALID_DATE_FORMAT);

        // invalid Expiry(Invalid date format)
        assertParseFailure(parser, PID_DESC + NRIC_DESC + INVALID_DATE_DESC
                        + INVALID_EXPIRY_DESC_1 + PREMIUM_DESC,
                Messages.MESSAGE_INVALID_DATE_FORMAT);

        // invalid Expiry(Invalid period)
        assertParseFailure(parser, PID_DESC + NRIC_DESC + DATE_DESC + INVALID_EXPIRY_DESC_2 + PREMIUM_DESC,
                Messages.MESSAGE_INVALID_EXPIRY_DATE);

        // invalid Premium(negative value)
        assertParseFailure(parser, PID_DESC + NRIC_DESC + DATE_DESC + EXPIRY_DESC + INVALID_PREMIUM_DESC,
                ContractPremium.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + PID_DESC + NRIC_DESC + DATE_DESC + EXPIRY_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContractCommand.MESSAGE_USAGE));
    }

}
