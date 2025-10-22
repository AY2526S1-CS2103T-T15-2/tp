package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_ALPHABETICAL_ORDER;
import static seedu.address.logic.parser.CliSyntax.FLAG_DATE_ORDER_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.FLAG_DATE_ORDER_DESCENDING;
import static seedu.address.logic.parser.CliSyntax.FLAG_INSERTION_ORDER;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.appointment.AppointmentComparatorType;
import seedu.address.ui.ListPanelType;

/**
 * Sorts and lists all appointments in address book.
 */
public class SortAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "sort_appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts appointments by insertion or alphabetical name order\n"
            + "Alphabetical sorting is case-insensitive.\n"
            + "Parameters: [SORT_FLAG = " + FLAG_INSERTION_ORDER + " or " + FLAG_ALPHABETICAL_ORDER
            + " or " + FLAG_DATE_ORDER_ASCENDING + " or " + FLAG_DATE_ORDER_DESCENDING + "]\n"
            + "Example: " + COMMAND_WORD + " " + FLAG_INSERTION_ORDER;

    public static final String MESSAGE_SUCCESS_UNORDERED = "Showing appointments by insertion order.";

    public static final String MESSAGE_SUCCESS_ALPHABETICAL = "Showing appointments by alphabetical order.";

    public static final String MESSAGE_SUCCESS_DATE_ASCENDING = "Showing appointments by date ascending order.";

    public static final String MESSAGE_SUCCESS_DATE_DESCENDING = "Showing appointments by date descending order.";

    private final AppointmentComparatorType comparatorType;

    /**
     * Creates a SortAppointmentCommand with the given {@code comparatorType}
     */
    public SortAppointmentCommand(AppointmentComparatorType comparatorType) {
        requireNonNull(comparatorType);
        this.comparatorType = comparatorType;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortAppointments(comparatorType.comparator);

        return switch (comparatorType) {
        case UNORDERED -> new CommandResult(MESSAGE_SUCCESS_UNORDERED, ListPanelType.APPOINTMENT);
        case ALPHABETICAL -> new CommandResult(MESSAGE_SUCCESS_ALPHABETICAL, ListPanelType.APPOINTMENT);
        case DATE_ASCENDING -> new CommandResult(MESSAGE_SUCCESS_DATE_ASCENDING, ListPanelType.APPOINTMENT);
        case DATE_DESCENDING -> new CommandResult(MESSAGE_SUCCESS_DATE_DESCENDING, ListPanelType.APPOINTMENT);
        };
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortAppointmentCommand)) {
            return false;
        }

        SortAppointmentCommand otherSortAppointmentCommand = (SortAppointmentCommand) other;
        return comparatorType.equals(otherSortAppointmentCommand.comparatorType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("comparatorType", comparatorType)
                .toString();
    }
}
