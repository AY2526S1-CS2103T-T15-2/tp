package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AID;

import java.util.stream.Stream;

import seedu.address.logic.commands.RemoveAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentId;

/**
 * Parses input arguments and creates a new RemoveContractCommand object
 */
public class RemoveAppointmentCommandParser implements Parser<RemoveAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemoveAppointmentCommand
     * and returns a RemoveAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap arguMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AID);

        if (!arePrefixesPresent(arguMultimap, PREFIX_AID)
                || !arguMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveAppointmentCommand.MESSAGE_USAGE));
        }

        arguMultimap.verifyNoDuplicatePrefixesFor(PREFIX_AID);
        AppointmentId aId = ParserUtil.parseAppointmentId(arguMultimap.getValue(PREFIX_AID).get());
        return new RemoveAppointmentCommand(aId);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
