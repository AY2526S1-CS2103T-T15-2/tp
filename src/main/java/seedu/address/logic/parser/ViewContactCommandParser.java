package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewContactCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        if (nameKeywords[0].equals("-a")) {
            return new ViewContactCommand(true);
        }

        return new ViewContactCommand(new NricContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
