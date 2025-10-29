package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_LIST_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewContactCommand;
import seedu.address.model.contact.NricContainsKeywordsPredicate;

public class ViewContactCommandParserTest {

    private ViewContactCommandParser parser = new ViewContactCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTrimmedArg_throwsParseException() {
        assertParseFailure(
                parser,
                " " + PREFIX_NRIC + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewContactCommand() {
        // no leading and trailing whitespaces
        ViewContactCommand expectedViewContactCommand =
                new ViewContactCommand(new NricContainsKeywordsPredicate(Arrays.asList("abcdef", "xyz123")));
        assertParseSuccess(parser, " " + PREFIX_NRIC + "abcdef xyz123", expectedViewContactCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_NRIC
                + "\n abcdef \n \t xyz123  \t", expectedViewContactCommand);
    }

    @Test
    public void parse_anyArgs_returnsViewContactCommand() {
        ViewContactCommand expectedViewContactCommand =
                new ViewContactCommand(true);
        assertParseSuccess(parser, FLAG_LIST_ALL, expectedViewContactCommand);
        assertParseSuccess(parser, FLAG_LIST_ALL + " nonsense", expectedViewContactCommand);
    }
}
