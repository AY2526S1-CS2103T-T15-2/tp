package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.logic.commands.EditAppointmentCommand;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.AppointmentId;

/**
 * Parses input arguments and creates a new EditAppointmentCommand object
 */
public class EditAppointmentCommandParser implements Parser<EditAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditAppointmentCommand
     * and returns an EditAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditAppointmentCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultiMap =
                ArgumentTokenizer.tokenize(args, PREFIX_AID, PREFIX_NRIC, PREFIX_DATE, PREFIX_DETAILS);

        String appointmentId;
        AppointmentId aId;
        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();
        if (argMultiMap.getValue(PREFIX_AID).isPresent()) {
            appointmentId = argMultiMap.getValue(PREFIX_AID).get();
            editAppointmentDescriptor.setAId(ParserUtil.parseAppointmentId(argMultiMap.getValue(PREFIX_AID).get()));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAppointmentCommand.MESSAGE_USAGE));
        }
        try {
            aId = ParserUtil.parseAppointmentId(appointmentId);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAppointmentCommand.MESSAGE_USAGE), pe);
        }

        argMultiMap.verifyNoDuplicatePrefixesFor(PREFIX_AID, PREFIX_NRIC, PREFIX_DATE, PREFIX_DETAILS);

        if (argMultiMap.getValue(PREFIX_NRIC).isPresent()) {
            editAppointmentDescriptor.setNric(ParserUtil.parseNric(argMultiMap.getValue(PREFIX_NRIC).get()));
        }
        if (argMultiMap.getValue(PREFIX_DATE).isPresent()) {
            editAppointmentDescriptor.setDate(ParserUtil.parseDate(argMultiMap.getValue(PREFIX_DATE).get()));
        }
        if (argMultiMap.getValue(PREFIX_DETAILS).isPresent()) {
            editAppointmentDescriptor.setDetails(ParserUtil
                    .parseAppointmentDetails(argMultiMap.getValue(PREFIX_DETAILS).get()));
        }
        if (!editAppointmentDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAppointmentCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAppointmentCommand(aId, editAppointmentDescriptor);
    }
}
