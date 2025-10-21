package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_LIST_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewAppointmentCommand;
import seedu.address.model.appointment.AppointmentIdContainsKeywordsPredicate;

public class ViewAppointmentCommandParserTest {

    private ViewAppointmentCommandParser parser = new ViewAppointmentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTrimmedArg_throwsParseException() {
        assertParseFailure(
                parser,
                " " + PREFIX_AID + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAppointmentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewAppointmentCommand() {
        // no leading and trailing whitespaces
        ViewAppointmentCommand expectedViewAppointmentCommand =
                new ViewAppointmentCommand(
                        new AppointmentIdContainsKeywordsPredicate(Arrays.asList("abcdef", "xyz123")));
        assertParseSuccess(parser, " " + PREFIX_AID + "abcdef xyz123", expectedViewAppointmentCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_AID
                + "\n abcdef \n \t xyz123  \t", expectedViewAppointmentCommand);
    }

    @Test
    public void parse_anyArgs_returnsViewAppointmentCommand() {
        ViewAppointmentCommand expectedViewAppointmentCommand =
                new ViewAppointmentCommand();
        assertParseSuccess(parser, FLAG_LIST_ALL, expectedViewAppointmentCommand);
        assertParseSuccess(parser, FLAG_LIST_ALL + " nonsense", expectedViewAppointmentCommand);
    }
}
