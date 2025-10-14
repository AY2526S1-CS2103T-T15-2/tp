package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.RemoveContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveContactCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new RemoveContactCommand(new NricContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
