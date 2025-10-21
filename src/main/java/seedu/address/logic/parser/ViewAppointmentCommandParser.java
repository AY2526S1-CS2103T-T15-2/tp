package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREAMBLE_ALL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AID;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.ViewAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentIdContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new ViewAppointmentCommand object
 */
public class ViewAppointmentCommandParser implements Parser<ViewAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewAppointmentCommand
     * and returns a ViewAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_AID);
        String preamble = argMultimap.getPreamble().trim();

        // Check for view all command
        if (preamble.startsWith(PREAMBLE_ALL)) {
            return new ViewAppointmentCommand();
        }

        // Check for view specific appointment id command
        if (arePrefixesPresent(argMultimap, PREFIX_AID) && preamble.isEmpty()) {
            String trimmedArgs = argMultimap.getValue(PREFIX_AID).get().trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        ViewAppointmentCommand.MESSAGE_USAGE));
            }
            String[] idKeyWords = trimmedArgs.split("\\s+");
            return new ViewAppointmentCommand(new AppointmentIdContainsKeywordsPredicate(Arrays.asList(idKeyWords)));
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewAppointmentCommand.MESSAGE_USAGE));
    }

    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
