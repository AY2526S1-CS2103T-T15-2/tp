package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalNricPredicates.PREDICATE_FIRST;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemoveContactCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the RemoveContactCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the RemoveContactCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RemoveContactCommandParserTest {

    private RemoveContactCommandParser parser = new RemoveContactCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveContactCommand() {
        assertParseSuccess(parser, "S1234567A", new RemoveContactCommand(PREDICATE_FIRST));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveContactCommand.MESSAGE_USAGE));
    }
}
