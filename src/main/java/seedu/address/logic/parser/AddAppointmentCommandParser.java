package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.time.LocalDate;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDetails;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointment
     * and returns an AddAppointment object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap arguMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC, PREFIX_DATE, PREFIX_DETAILS);

        if (!arePrefixesPresent(arguMultimap, PREFIX_NRIC, PREFIX_DATE, PREFIX_DETAILS)
                || !arguMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
        }

        arguMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NRIC, PREFIX_DATE, PREFIX_DETAILS);
        Nric nric = ParserUtil.parseNric(arguMultimap.getValue(PREFIX_NRIC).get());
        LocalDate date = ParserUtil.parseDate(arguMultimap.getValue(PREFIX_DATE).get());
        AppointmentDetails details = ParserUtil.parseAppointmentDetails(arguMultimap.getValue(PREFIX_DETAILS).get());

        Appointment appointment = new Appointment(nric, date, details);

        return new AddAppointmentCommand(appointment);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
