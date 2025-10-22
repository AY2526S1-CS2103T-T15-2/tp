package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ALPHABETICAL_ORDER;
import static seedu.address.logic.parser.CliSyntax.FLAG_INSERTION_ORDER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortAppointmentCommand;
import seedu.address.model.appointment.AppointmentComparatorType;

public class SortAppointmentCommandParserTest {

    private SortAppointmentCommandParser parser = new SortAppointmentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortAppointmentCommand() {
        // one flag
        assertParseSuccess(parser, FLAG_INSERTION_ORDER,
                new SortAppointmentCommand(AppointmentComparatorType.UNORDERED));
        assertParseSuccess(parser, FLAG_ALPHABETICAL_ORDER,
                new SortAppointmentCommand(AppointmentComparatorType.ALPHABETICAL));

        // multiple whitespaces around flag
        assertParseSuccess(parser, " \n\t" + FLAG_INSERTION_ORDER + " \t\n ",
                new SortAppointmentCommand(AppointmentComparatorType.UNORDERED));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // more than one flag
        assertParseFailure(parser, FLAG_INSERTION_ORDER + " " + FLAG_ALPHABETICAL_ORDER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortAppointmentCommand.MESSAGE_USAGE));

        // one flag with other arguments
        assertParseFailure(parser, FLAG_INSERTION_ORDER + " extra text",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortAppointmentCommand.MESSAGE_USAGE));
    }

}
