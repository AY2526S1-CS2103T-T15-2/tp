package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;

import java.util.stream.Stream;

import seedu.address.logic.commands.RemovePolicyCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.policy.PolicyId;

/**
 * Parses input arguments and creates a new RemovePolicyCommand object
 */
public class RemovePolicyCommandParser implements Parser<RemovePolicyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemovePolicyCommand
     * and returns a RemovePolicyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemovePolicyCommand parse(String args) throws ParseException {
        ArgumentMultimap arguMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PID);
        if (!arePrefixesPresent(arguMultimap, PREFIX_PID) || !arguMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemovePolicyCommand.MESSAGE_USAGE));
        }
        PolicyId id = ParserUtil.parsePolicyId(arguMultimap.getValue(PREFIX_PID).get());
        return new RemovePolicyCommand(id);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}

