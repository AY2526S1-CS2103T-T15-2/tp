package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.FLAG_ALPHABETICAL_ORDER;
import static seedu.address.logic.parser.CliSyntax.FLAG_DATE_ORDER_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.FLAG_DATE_ORDER_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.FLAG_INSERTION_ORDER;

import seedu.address.logic.commands.SortAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentComparatorType;

/**
 * Parses input arguments and creates a new SortAppointmentCommand object
 */
public class SortAppointmentCommandParser implements Parser<SortAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortAppointmentCommand
     * and returns a SortAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SortAppointmentCommand parse(String args) throws ParseException {
        String preamble = args.trim();

        return switch (preamble) {
        case FLAG_INSERTION_ORDER -> new SortAppointmentCommand(AppointmentComparatorType.UNORDERED);
        case FLAG_ALPHABETICAL_ORDER -> new SortAppointmentCommand(AppointmentComparatorType.ALPHABETICAL);
        case FLAG_DATE_ORDER_ASCENDING -> new SortAppointmentCommand(AppointmentComparatorType.DATE_ASCENDING);
        case FLAG_DATE_ORDER_DESCENDING -> new SortAppointmentCommand(AppointmentComparatorType.DATE_DESCENDING);
        default -> throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortAppointmentCommand.MESSAGE_USAGE));
        };
    }
}
