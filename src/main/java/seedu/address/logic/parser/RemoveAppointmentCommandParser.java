package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
        try {
            AppointmentId aId = ParserUtil.parseAppointmentId(args);
            return new RemoveAppointmentCommand(aId);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveAppointmentCommand.MESSAGE_USAGE), pe);
        }
    }

}
