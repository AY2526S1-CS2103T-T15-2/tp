package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ALPHABETICAL_ORDER;
import static seedu.address.logic.parser.CliSyntax.FLAG_INSERTION_ORDER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortContactCommand;
import seedu.address.model.contact.ContactComparatorType;

public class SortContactCommandParserTest {

    private SortContactCommandParser parser = new SortContactCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsSortContactCommand() {
        // one flag
        assertParseSuccess(parser, FLAG_INSERTION_ORDER, new SortContactCommand(ContactComparatorType.UNORDERED));
        assertParseSuccess(parser, FLAG_ALPHABETICAL_ORDER, new SortContactCommand(ContactComparatorType.ALPHABETICAL));

        // multiple whitespaces around flag
        assertParseSuccess(parser, " \n\t" + FLAG_INSERTION_ORDER + " \t\n ",
                new SortContactCommand(ContactComparatorType.UNORDERED));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // more than one flag
        assertParseFailure(parser, FLAG_INSERTION_ORDER + " " + FLAG_ALPHABETICAL_ORDER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortContactCommand.MESSAGE_USAGE));

        // one flag with other arguments
        assertParseFailure(parser, FLAG_INSERTION_ORDER + " extra text",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortContactCommand.MESSAGE_USAGE));
    }

}
