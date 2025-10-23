package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.APPOINTMENT_DATE_DESC_A;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.APPOINTMENT_DATE_DESC_B;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.APPOINTMENT_DETAILS_DESC_A;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.APPOINTMENT_NRIC_DESC_A;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.INVALID_APPOINTMENT_DATE_DESC;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.INVALID_APPOINTMENT_DETAILS_DESC;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.INVALID_APPOINTMENT_ID;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.INVALID_APPOINTMENT_NRIC_DESC;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_DATE_A;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_DETAILS_A;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_ID_A;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_NRIC_1;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditAppointmentCommand;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.model.appointment.AppointmentDetails;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.person.Nric;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;

public class EditAppointmentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAppointmentCommand.MESSAGE_USAGE);

    private EditAppointmentCommandParser parser = new EditAppointmentCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, " " + PREFIX_AID + "abcdef", EditAppointmentCommand.MESSAGE_NOT_EDITED);

        // no appointment id and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid appointment id
        assertParseFailure(parser, " " + PREFIX_AID + INVALID_APPOINTMENT_ID
                + APPOINTMENT_DATE_DESC_B, AppointmentId.MESSAGE_CONSTRAINTS);
        // invalid appointment nric
        assertParseFailure(parser, " " + PREFIX_AID + VALID_APPOINTMENT_ID_A
                + INVALID_APPOINTMENT_NRIC_DESC, Nric.MESSAGE_CONSTRAINTS);
        // invalid appointment date
        assertParseFailure(parser, " " + PREFIX_AID + VALID_APPOINTMENT_ID_A
                + INVALID_APPOINTMENT_DATE_DESC, MESSAGE_INVALID_DATE_FORMAT);
        // invalid appointment details
        assertParseFailure(parser, " " + PREFIX_AID + VALID_APPOINTMENT_ID_A
                +  INVALID_APPOINTMENT_DETAILS_DESC, PolicyDetails.MESSAGE_CONSTRAINTS);

        // invalid appointment nric followed by valid appointment details
        assertParseFailure(parser, " " + PREFIX_AID + VALID_APPOINTMENT_ID_A
                        + INVALID_APPOINTMENT_NRIC_DESC + INVALID_APPOINTMENT_DETAILS_DESC, Nric.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        AppointmentId appointmentId = new AppointmentId(VALID_APPOINTMENT_ID_A);
        String userInput = " " + PREFIX_AID + VALID_APPOINTMENT_ID_A + APPOINTMENT_NRIC_DESC_A
                + APPOINTMENT_DATE_DESC_A + APPOINTMENT_DETAILS_DESC_A;
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder()
                .withAId(VALID_APPOINTMENT_ID_A)
                .withNric(VALID_APPOINTMENT_NRIC_1)
                .withDate(VALID_APPOINTMENT_DATE_A)
                .withDetails(VALID_APPOINTMENT_DETAILS_A)
                .build();
        EditAppointmentCommand expectedCommand = new EditAppointmentCommand(appointmentId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        AppointmentId appointmentId = new AppointmentId(VALID_APPOINTMENT_ID_A);
        String userInput = " " + PREFIX_AID + VALID_APPOINTMENT_ID_A + APPOINTMENT_DETAILS_DESC_A;
        EditAppointmentDescriptor descriptor =
                new EditAppointmentDescriptorBuilder().withAId(VALID_APPOINTMENT_ID_A)
                        .withDetails(VALID_APPOINTMENT_DETAILS_A).build();
        EditAppointmentCommand expectedCommand = new EditAppointmentCommand(appointmentId, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // appointment nric
        AppointmentId appointmentId = new AppointmentId(VALID_APPOINTMENT_ID_A);
        String userInput = " " + PREFIX_AID + VALID_APPOINTMENT_ID_A + APPOINTMENT_NRIC_DESC_A;
        EditAppointmentDescriptor descriptor =
                new EditAppointmentDescriptorBuilder().withAId(VALID_APPOINTMENT_ID_A)
                        .withNric(VALID_APPOINTMENT_NRIC_1).build();
        EditAppointmentCommand expectedCommand = new EditAppointmentCommand(appointmentId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // appointment date
        userInput = " " + PREFIX_AID + VALID_APPOINTMENT_ID_A + APPOINTMENT_DATE_DESC_A;
        descriptor = new EditAppointmentDescriptorBuilder().withAId(VALID_APPOINTMENT_ID_A)
                .withDate(VALID_APPOINTMENT_DATE_A).build();
        expectedCommand = new EditAppointmentCommand(appointmentId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // appointment details
        userInput = " " + PREFIX_AID + VALID_APPOINTMENT_ID_A + APPOINTMENT_DETAILS_DESC_A;
        descriptor = new EditAppointmentDescriptorBuilder().withAId(VALID_APPOINTMENT_ID_A)
                .withDetails(VALID_APPOINTMENT_DETAILS_A).build();
        expectedCommand = new EditAppointmentCommand(appointmentId, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        AppointmentId appointmentId = new AppointmentId(VALID_APPOINTMENT_ID_A);

        // valid followed by invalid
        String userInput = " " + PREFIX_AID + VALID_APPOINTMENT_ID_A
                + APPOINTMENT_NRIC_DESC_A
                + APPOINTMENT_DATE_DESC_A
                + INVALID_APPOINTMENT_DETAILS_DESC;

        assertParseFailure(parser, userInput, AppointmentDetails.MESSAGE_CONSTRAINTS);

        // invalid followed by valid
        userInput = " " + PREFIX_AID + VALID_APPOINTMENT_ID_A
                + INVALID_APPOINTMENT_NRIC_DESC
                + APPOINTMENT_DATE_DESC_A
                + APPOINTMENT_DETAILS_DESC_A;

        assertParseFailure(parser, userInput, Nric.MESSAGE_CONSTRAINTS);

        // multiple valid fields repeated
        userInput = " " + PREFIX_AID + VALID_APPOINTMENT_ID_A
                + APPOINTMENT_NRIC_DESC_A + APPOINTMENT_DATE_DESC_A + APPOINTMENT_DETAILS_DESC_A
                + " " + PREFIX_AID + VALID_APPOINTMENT_ID_A
                + APPOINTMENT_NRIC_DESC_A + APPOINTMENT_DATE_DESC_A + APPOINTMENT_DETAILS_DESC_A;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_AID, PREFIX_NRIC, PREFIX_DATE, PREFIX_DETAILS));

        // multiple invalid values
        userInput = " " + PREFIX_AID + VALID_APPOINTMENT_ID_A
                + INVALID_APPOINTMENT_NRIC_DESC + INVALID_APPOINTMENT_DATE_DESC + INVALID_APPOINTMENT_DETAILS_DESC
                + INVALID_APPOINTMENT_NRIC_DESC + INVALID_APPOINTMENT_DATE_DESC + INVALID_APPOINTMENT_DETAILS_DESC;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NRIC, PREFIX_DATE, PREFIX_DETAILS));
    }
}