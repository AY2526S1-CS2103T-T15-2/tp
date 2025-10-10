package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalId.VALID_CONTRACT_ID;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemoveContractCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside the RemoveContractCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the RemoveContractCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RemoveContractCommandParserTest {

    private RemoveContractCommandParser parser = new RemoveContractCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveContractCommand() {
        assertParseSuccess(parser, "C1234A", new RemoveContractCommand(VALID_CONTRACT_ID));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveContractCommand.MESSAGE_USAGE));
    }
}
