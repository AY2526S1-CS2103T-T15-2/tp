package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.RemoveContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new RemoveContactCommand object
 */
public class RemoveContactCommandParser implements Parser<RemoveContactCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveContactCommand
     * and returns a RemoveContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveContactCommand parse(String args) throws ParseException {
        ArgumentMultimap arguMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NRIC);
        if (!arePrefixesPresent(arguMultimap, PREFIX_NRIC) || !arguMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveContactCommand.MESSAGE_USAGE));
        }
        arguMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NRIC);
        Nric nric = ParserUtil.parseNric(arguMultimap.getValue(PREFIX_NRIC).get());
        return new RemoveContactCommand(new NricContainsKeywordsPredicate(Arrays.asList(nric.toString())));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
