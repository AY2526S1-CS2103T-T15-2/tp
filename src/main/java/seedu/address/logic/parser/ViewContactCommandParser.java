package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_LIST_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.ViewContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NricContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ViewContactCommand object
 */
public class ViewContactCommandParser implements Parser<ViewContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewContactCommand
     * and returns a ViewContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewContactCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC);
        String preamble = argMultimap.getPreamble().trim();

        // Check for view all command
        if (preamble.startsWith(FLAG_LIST_ALL)) {
            return new ViewContactCommand(true);
        }

        // Check for view specific nric command
        if (arePrefixesPresent(argMultimap, PREFIX_NRIC) && preamble.isEmpty()) {
            String trimmedArgs = argMultimap.getValue(PREFIX_NRIC).get().trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ViewContactCommand.MESSAGE_USAGE));
            }
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NRIC);
            String[] idKeyWords = trimmedArgs.split("\\s+");
            return new ViewContactCommand(new NricContainsKeywordsPredicate(Arrays.asList(idKeyWords)));
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewContactCommand.MESSAGE_USAGE));
    }

    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
