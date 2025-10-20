package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREAMBLE_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewContractCommand;
import seedu.address.model.contract.ContractIdContainsKeywordsPredicate;

public class ViewContractCommandParserTest {

    private ViewContractCommandParser parser = new ViewContractCommandParser();

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewContractCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyTrimmedArgs_throwsParseException() {
        assertParseFailure(parser, " c:   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewContractCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewContractCommand() {
        // no leading and trailing whitespaces
        ViewContractCommand expectedViewContractcommand =
                new ViewContractCommand(new ContractIdContainsKeywordsPredicate(Arrays.asList("C1234A", "C1234B")));
        assertParseSuccess(parser, " " + PREFIX_CID + "C1234A C1234B", expectedViewContractcommand);

        // mutliple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_CID + "\n C1234A \n \t C1234B  \t",
                expectedViewContractcommand);
    }

    @Test
    public void parse_anyArgs_returnsViewContractCommand() {
        ViewContractCommand expectedViewContractcommand = new ViewContractCommand();
        assertParseSuccess(parser, " " + PREAMBLE_ALL, expectedViewContractcommand);
        assertParseSuccess(parser, PREAMBLE_ALL + " nonsense", expectedViewContractcommand);
    }
}
