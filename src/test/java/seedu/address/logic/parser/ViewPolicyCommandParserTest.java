package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREAMBLE_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewPolicyCommand;
import seedu.address.logic.commands.ViewPolicyCommandTest;
import seedu.address.model.policy.IdContainsKeywordsPredicate;

public class ViewPolicyCommandParserTest {

    private ViewPolicyCommandParser parser = new ViewPolicyCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewPolicyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTrimmedArg_throwsParseException() {
        assertParseFailure(
                parser,
                " " + PREFIX_PID + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewPolicyCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewPolicyCommand() {
        // no leading and trailing whitespaces
        ViewPolicyCommand expectedViewPolicyCommand =
                new ViewPolicyCommand(new IdContainsKeywordsPredicate(Arrays.asList("abcdef", "xyz123")));
        assertParseSuccess(parser, " " + PREFIX_PID + "abcdef xyz123", expectedViewPolicyCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_PID + "\n abcdef \n \t xyz123  \t", expectedViewPolicyCommand);
    }

    @Test
    public void parse_anyArgs_returnsViewPolicyCommand() {
        ViewPolicyCommand expectedViewPolicyCommand =
                new ViewPolicyCommand();
        assertParseSuccess(parser, " " + PREAMBLE_ALL, expectedViewPolicyCommand);
        assertParseSuccess(parser, " " + PREAMBLE_ALL + " nonsense", expectedViewPolicyCommand);
    }
}
