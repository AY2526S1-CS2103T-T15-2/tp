package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.appointment.AppointmentIdContainsKeywordsPredicate;
import seedu.address.ui.ListPanelType;


/**
 * Lists all appointments in the address book to the user.
 */
public class ViewAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "view_appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Viewing appointments. "
            + "Parameters: "
            + "-a (View all appointments) or "
            + "a:APPOINTMENT_ID (View specific appointments by ID)\n"
            + "Examples: "
            + "view_appointment -a or "
            + "view_appointment a:ABCDEF";

    public static final String MESSAGE_SUCCESS_ALL = "Viewing all appointments";
    public static final String MESSAGE_SUCCESS_SPECIFIC = "Viewing appointments with ID : %1$s";
    public static final String MESSAGE_NO_ID_MATCH = "Failed to find any appointments";

    private final boolean viewAll;
    private final AppointmentIdContainsKeywordsPredicate predicate;

    /**
     * Constructor method to view all appointments
     */
    public ViewAppointmentCommand() {
        this.viewAll = true;
        this.predicate = null;
    }

    /**
     * Constructor method to view appointments matching predicate
     */
    public ViewAppointmentCommand(AppointmentIdContainsKeywordsPredicate predicate) {
        this.viewAll = false;
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (viewAll) {
            model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
            return new CommandResult(MESSAGE_SUCCESS_ALL, ListPanelType.APPOINTMENT);
        } else {
            model.updateFilteredAppointmentList((predicate));
            if (model.getFilteredAppointmentList().isEmpty()) {
                return new CommandResult(String.format(MESSAGE_NO_ID_MATCH), ListPanelType.APPOINTMENT);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS_SPECIFIC,
                    String.join(", ", predicate.getKeywords())), ListPanelType.APPOINTMENT);

        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ViewAppointmentCommand)) {
            return false;
        }

        ViewAppointmentCommand otherViewAppointmentCommand = (ViewAppointmentCommand) other;

        if (this.predicate == null && otherViewAppointmentCommand.predicate == null) {
            return true;
        }

        if (this.predicate == null || otherViewAppointmentCommand.predicate == null) {
            return false;
        }

        return predicate.equals(otherViewAppointmentCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
