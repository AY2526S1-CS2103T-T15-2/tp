package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.AppointmentListUtil;
import seedu.address.ui.ListPanelType;

/**
 * Removes an appointment from iCon.
 */
public class RemoveAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "remove_appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes an appointment from iCon. "
            + "Parameters: "
            + "Appointment ID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " gVVGzn";

    public static final String MESSAGE_REMOVE_APPOINTMENT_SUCCESS = "Removed Appointment: %1$s";

    private final AppointmentId aId;

    public RemoveAppointmentCommand(AppointmentId targetIndex) {
        this.aId = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        Appointment appointmentToRemove = AppointmentListUtil.findById(lastShownList, aId)
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_APPOINTMENT_NOT_FOUND));
        model.removeAppointment(appointmentToRemove);
        return new CommandResult(String.format(MESSAGE_REMOVE_APPOINTMENT_SUCCESS, appointmentToRemove.getAId()),
                ListPanelType.APPOINTMENT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveAppointmentCommand)) {
            return false;
        }

        RemoveAppointmentCommand otherRemoveAppointment = (RemoveAppointmentCommand) other;
        return aId.equals(otherRemoveAppointment.aId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("aId", aId)
                .toString();
    }
}
