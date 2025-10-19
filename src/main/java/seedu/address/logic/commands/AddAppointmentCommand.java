package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDetails;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Adds an appointment to iCon.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "add_appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to iCon. "
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_DATE + "DATE"
            + PREFIX_DETAILS + "DETAILS"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "T0123456A "
            + PREFIX_DATE + "2025-01-13 "
            + PREFIX_DETAILS + "Discuss coverage";

    public static final String MESSAGE_SUCCESS = "New appointment added with the following ID: %s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in iCon";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person does not exist in iCon";

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

        // get nric from preloaded appointment and load person to get name
        Nric nric = toAdd.getNric();
        FilteredList<Person> personFilteredList = model.getFilteredPersonList()
                .filtered(new NricContainsKeywordsPredicate(List.of(nric.toString())));
        if (personFilteredList.isEmpty()) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }
        Person person = personFilteredList.get(0);
        if (person == null || !person.getNric().equals(nric)) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        // fill in necessary fields

        AppointmentId appointmentId = toAdd.getAId();
        LocalDate date = toAdd.getDate();
        AppointmentDetails details = toAdd.getDetails();

        // initialise new appointment
        toAdd = new Appointment(appointmentId, nric, date, details);

        if (model.hasAppointment(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.addAppointment(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getAId().toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
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
