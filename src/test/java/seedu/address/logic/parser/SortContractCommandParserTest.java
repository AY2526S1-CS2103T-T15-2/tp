package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_EXPIRY_ORDER_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.FLAG_INSERTION_ORDER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortContractCommand;
import seedu.address.model.contract.ContractComparatorType;

public class SortContractCommandParserTest {

    private SortContractCommandParser parser = new SortContractCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortContractCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArg_returnsSortContractCommand() {
        // one flag
        assertParseSuccess(parser, FLAG_INSERTION_ORDER, new SortContractCommand(ContractComparatorType.UNORDERED));
        assertParseSuccess(parser, FLAG_EXPIRY_ORDER_ASCENDING,
                new SortContractCommand(ContractComparatorType.EXPIRY_DATE));

        // multiple whitespaces around flag
        assertParseSuccess(parser, " \n\t" + FLAG_INSERTION_ORDER + " \t\n ",
                new SortContractCommand(ContractComparatorType.UNORDERED));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // more than one flag
        assertParseFailure(parser, FLAG_INSERTION_ORDER + " " + FLAG_EXPIRY_ORDER_ASCENDING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortContractCommand.MESSAGE_USAGE));

        // one flag with other arguments
        assertParseFailure(parser, FLAG_INSERTION_ORDER + " extra text",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortContractCommand.MESSAGE_USAGE));
    }
}
