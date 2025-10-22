package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_LIST_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CID;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.ViewContractCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contract.ContractIdContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ViewContractCommand object
 */
public class ViewContractCommandParser implements Parser<ViewContractCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewContractCommand
     * and returns a ViewContractCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewContractCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_CID);
        String preamble = argMultimap.getPreamble().trim();

        // Check for view all command
        if (preamble.startsWith(FLAG_LIST_ALL)) {
            return new ViewContractCommand();
        }

        // Check for view specific contract id command
        if (arePrefixesPresent(argMultimap, PREFIX_CID) && preamble.isEmpty()) {
            String trimmedArgs = argMultimap.getValue(PREFIX_CID).get().trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                        ViewContractCommand.MESSAGE_USAGE));
            }
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CID);
            String[] idKeyWords = trimmedArgs.split("\\s+");
            return new ViewContractCommand(new ContractIdContainsKeywordsPredicate(Arrays.asList(idKeyWords)));
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewContractCommand.MESSAGE_USAGE));
    }

    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
