package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewContactCommand;
import seedu.address.model.person.NricContainsKeywordsPredicate;

public class ViewContactCommandParserTest {

    private ViewContactCommandParser parser = new ViewContactCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewContactCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewContactCommand() {
        // no leading and trailing whitespaces
        ViewContactCommand expectedViewContactCommand =
                new ViewContactCommand(new NricContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedViewContactCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedViewContactCommand);
    }

}
