package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ALPHABETICAL_ORDER;
import static seedu.address.logic.parser.CliSyntax.FLAG_INSERTION_ORDER;

import seedu.address.logic.commands.SortContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.contact.ContactComparatorType;

//@@author AndrescuIII-too
/**
 * Parses input arguments and creates a new SortContactCommand object
 */
public class SortContactCommandParser implements Parser<SortContactCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortContactCommand
     * and returns a SortContactCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortContactCommand parse(String args) throws ParseException {
        String preamble = args.trim();

        return switch (preamble) {
        case FLAG_INSERTION_ORDER -> new SortContactCommand(ContactComparatorType.UNORDERED);
        case FLAG_ALPHABETICAL_ORDER -> new SortContactCommand(ContactComparatorType.ALPHABETICAL);
        default -> throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortContactCommand.MESSAGE_USAGE));
        };
    }
}
