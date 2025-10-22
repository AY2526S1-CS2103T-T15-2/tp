package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.APPOINTMENT_DATE_DESC_A;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.APPOINTMENT_DATE_DESC_B;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.APPOINTMENT_DETAILS_DESC_A;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.APPOINTMENT_DETAILS_DESC_B;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.APPOINTMENT_NRIC_DESC_A;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.APPOINTMENT_NRIC_DESC_B;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.INVALID_APPOINTMENT_DATE_DESC;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.INVALID_APPOINTMENT_DETAILS_DESC;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.INVALID_APPOINTMENT_NRIC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalData.APPOINTMENT_A;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDetails;
import seedu.address.model.person.Nric;
import seedu.address.testutil.AppointmentBuilder;

public class AddAppointmentCommandParserTest {

    private AddAppointmentCommandParser parser = new AddAppointmentCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Appointment appointment = new AppointmentBuilder(APPOINTMENT_A).build();

        // whitespace only preamble
        String userInputToTest = PREAMBLE_WHITESPACE + APPOINTMENT_NRIC_DESC_A + APPOINTMENT_DATE_DESC_A
                + APPOINTMENT_DETAILS_DESC_A;
        try {
            AddAppointmentCommand expectedCommand = new AddAppointmentCommandParser().parse(userInputToTest);
            Appointment expectedAppointment = expectedCommand.getAppointment();
            // Check all other fields (except AId) present and equal
            assertEquals(appointment.getNric(), expectedAppointment.getNric());
            assertEquals(appointment.getDate(), expectedAppointment.getDate());
            assertEquals(appointment.getDetails(), expectedAppointment.getDetails());
        } catch (ParseException e) {
            // Ignore, testing for success
        }
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedAppointmentString = APPOINTMENT_NRIC_DESC_A + APPOINTMENT_DATE_DESC_A
            + APPOINTMENT_DETAILS_DESC_A;

        // multiple nrics
        String userInputToTest = APPOINTMENT_NRIC_DESC_B + validExpectedAppointmentString;
        assertParseFailure(parser, userInputToTest,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        // multiple dates
        userInputToTest = APPOINTMENT_DATE_DESC_B + validExpectedAppointmentString;
        assertParseFailure(parser, userInputToTest,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple details
        userInputToTest = APPOINTMENT_DETAILS_DESC_B + validExpectedAppointmentString;
        assertParseFailure(parser, userInputToTest,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DETAILS));

        // multiple fields repeated
        userInputToTest = validExpectedAppointmentString + APPOINTMENT_NRIC_DESC_B
                + APPOINTMENT_DATE_DESC_B + APPOINTMENT_DETAILS_DESC_B + validExpectedAppointmentString;
        assertParseFailure(parser, userInputToTest,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC, PREFIX_DATE, PREFIX_DETAILS));

        // invalid value followed by valid value

        // invalid nric
        userInputToTest = INVALID_APPOINTMENT_NRIC_DESC + validExpectedAppointmentString;
        assertParseFailure(parser, userInputToTest,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        //invalid date
        userInputToTest = INVALID_APPOINTMENT_DATE_DESC + validExpectedAppointmentString;
        assertParseFailure(parser, userInputToTest,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid details
        userInputToTest = INVALID_APPOINTMENT_DETAILS_DESC + validExpectedAppointmentString;
        assertParseFailure(parser, userInputToTest,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DETAILS));

        // valid value followed by invalid value

        // invalid nric
        userInputToTest = validExpectedAppointmentString + INVALID_APPOINTMENT_NRIC_DESC;
        assertParseFailure(parser, userInputToTest,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC));

        //invalid date
        userInputToTest = validExpectedAppointmentString + INVALID_APPOINTMENT_DATE_DESC;
        assertParseFailure(parser, userInputToTest,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid details
        userInputToTest = validExpectedAppointmentString + INVALID_APPOINTMENT_DETAILS_DESC;
        assertParseFailure(parser, userInputToTest,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DETAILS));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE);

        // missing nric prefix
        assertParseFailure(parser, APPOINTMENT_NRIC_DESC_A, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, APPOINTMENT_DATE_DESC_A, expectedMessage);

        // missing details prefix
        assertParseFailure(parser, APPOINTMENT_DETAILS_DESC_A, expectedMessage);

        // no arguments
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid nric
        String userInputToTest = INVALID_APPOINTMENT_NRIC_DESC + APPOINTMENT_DATE_DESC_A + APPOINTMENT_DETAILS_DESC_A;
        assertParseFailure(parser, userInputToTest, Nric.MESSAGE_CONSTRAINTS);

        // invalid date
        userInputToTest = APPOINTMENT_NRIC_DESC_A + INVALID_APPOINTMENT_DATE_DESC + APPOINTMENT_DETAILS_DESC_A;
        assertParseFailure(parser, userInputToTest, MESSAGE_INVALID_DATE_FORMAT);

        // invalid details
        userInputToTest = APPOINTMENT_NRIC_DESC_A + APPOINTMENT_DATE_DESC_A + INVALID_APPOINTMENT_DETAILS_DESC;
        assertParseFailure(parser, userInputToTest, AppointmentDetails.MESSAGE_CONSTRAINTS);


        // two or more invalid values, only the first invalid value reported
        userInputToTest = INVALID_APPOINTMENT_NRIC_DESC + INVALID_APPOINTMENT_DATE_DESC
                + INVALID_APPOINTMENT_DETAILS_DESC;
        assertParseFailure(parser, userInputToTest, Nric.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        userInputToTest = PREAMBLE_NON_EMPTY + APPOINTMENT_NRIC_DESC_A + APPOINTMENT_DATE_DESC_A
                + APPOINTMENT_DETAILS_DESC_A;
        assertParseFailure(parser, userInputToTest,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
    }
}
