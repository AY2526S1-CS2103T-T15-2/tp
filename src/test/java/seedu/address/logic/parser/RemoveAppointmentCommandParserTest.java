package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AppointmentCommandTestUtil.VALID_APPOINTMENT_ID_A;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemoveAppointmentCommand;
import seedu.address.model.appointment.AppointmentId;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside the RemoveAppointmentCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the RemoveAppointmentCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RemoveAppointmentCommandParserTest {

    private RemoveAppointmentCommandParser parser = new RemoveAppointmentCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveContractCommand() {
        assertParseSuccess(parser, " " + PREFIX_AID + VALID_APPOINTMENT_ID_A,
                new RemoveAppointmentCommand(new AppointmentId(VALID_APPOINTMENT_ID_A)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveAppointmentCommand.MESSAGE_USAGE));
    }
}
