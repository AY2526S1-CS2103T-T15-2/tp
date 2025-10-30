package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.time.LocalDate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDetails;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.contact.Nric;
import seedu.address.ui.ListPanelType;

/**
 * Adds an appointment to iCon.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "add_appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to iCon.\n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_DATE + "DATE "
            + PREFIX_DETAILS + "DETAILS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "T0123456A "
            + PREFIX_DATE + "2025-01-13 "
            + PREFIX_DETAILS + "Discuss coverage";

    public static final String MESSAGE_SUCCESS = "New appointment added with the following ID: %s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in iCon";

    private Appointment toAdd;

    /**
     * Creates an AddAppointment to add the specified {@code Appointment}
     */
    public AddAppointmentCommand(Appointment appointment) {
        requireNonNull(appointment);
        toAdd = appointment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Nric nric = toAdd.getNric();
        if (!model.hasContact(nric)) {
            throw new CommandException(Messages.MESSAGE_CONTACT_NOT_FOUND);
        }

        AppointmentId appointmentId = toAdd.getAId();
        LocalDate date = toAdd.getDate();
        AppointmentDetails details = toAdd.getDetails();

        toAdd = new Appointment(appointmentId, nric, date, details);

        if (model.hasAppointment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.addAppointment(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getAId().toString()), ListPanelType.APPOINTMENT);
    }

    public Appointment getAppointment() {
        return toAdd;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddAppointmentCommand)) {
            return false;
        }

        AddAppointmentCommand otherAddAppointment = (AddAppointmentCommand) other;
        return toAdd.equals(otherAddAppointment.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
