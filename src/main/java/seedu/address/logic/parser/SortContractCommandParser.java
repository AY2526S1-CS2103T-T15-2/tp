package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_EXPIRY_ORDER_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.FLAG_INSERTION_ORDER;

import seedu.address.logic.commands.SortContractCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contract.ContractComparatorType;

/**
 * Parses input arguments and creates a new SortContractCommand object
 */
public class SortContractCommandParser implements Parser<SortContractCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortContractCommand
     * and returns a SortContractCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortContractCommand parse(String args) throws ParseException {
       String preamble = args.trim();

       return switch (preamble) {
           case FLAG_INSERTION_ORDER -> new SortContractCommand(ContractComparatorType.UNORDERED);
           case FLAG_EXPIRY_ORDER_ASCENDING -> new SortContractCommand(ContractComparatorType.EXPIRY_DATE);
           default -> throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                   SortContractCommand.MESSAGE_USAGE));
       };
    }
}
