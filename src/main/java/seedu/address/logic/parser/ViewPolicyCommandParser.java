package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.ViewPolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.IdContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ViewPolicyCommand object
 */
public class ViewPolicyCommandParser implements Parser<ViewPolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewPolicyCommand
     * and returns a ViewPolicyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewPolicyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PID);
        String preamble = argMultimap.getPreamble().trim();

        // Check for view all command
        if (preamble.startsWith(FLAG_ALL)) {
            return new ViewPolicyCommand();
        }

        // Check for view specific policy id command
        if (arePrefixesPresent(argMultimap, PREFIX_PID) && preamble.isEmpty()) {
            String trimmedArgs = argMultimap.getValue(PREFIX_PID).get().trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                        ViewPolicyCommand.MESSAGE_USAGE));
            }
            String[] idKeyWords = trimmedArgs.split("\\s+");
            return new ViewPolicyCommand(new IdContainsKeywordsPredicate(Arrays.asList(idKeyWords)));
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewPolicyCommand.MESSAGE_USAGE));
    }

    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
