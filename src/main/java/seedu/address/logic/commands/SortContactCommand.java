package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_ALPHABETICAL_ORDER;
import static seedu.address.logic.parser.CliSyntax.FLAG_INSERTION_ORDER;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.contact.ContactComparatorType;
import seedu.address.ui.ListPanelType;

/**
 * Sorts and lists currently viewed contacts in iCon.
 */
public class SortContactCommand extends Command {

    public static final String COMMAND_WORD = "sort_contact";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts contacts by insertion or alphabetical name order\n"
            + "Alphabetical sorting is case-insensitive.\n"
            + "Parameters: [SORT_FLAG = " + FLAG_INSERTION_ORDER + " or " + FLAG_ALPHABETICAL_ORDER + "]\n"
            + "Example: " + COMMAND_WORD + " " + FLAG_INSERTION_ORDER;

    public static final String MESSAGE_SUCCESS_UNORDERED = "Showing contacts by insertion order.";

    public static final String MESSAGE_SUCCESS_ALPHABETICAL = "Showing contacts by alphabetical order.";

    private final ContactComparatorType comparatorType;

    /**
     * Creates a SortContactCommand with the given {@code comparatorType}
     */
    public SortContactCommand(ContactComparatorType comparatorType) {
        requireNonNull(comparatorType);
        this.comparatorType = comparatorType;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortContacts(comparatorType.comparator);

        return switch (comparatorType) {
        case UNORDERED -> new CommandResult(MESSAGE_SUCCESS_UNORDERED, ListPanelType.CONTACT);
        case ALPHABETICAL -> new CommandResult(MESSAGE_SUCCESS_ALPHABETICAL, ListPanelType.CONTACT);
        };
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortContactCommand)) {
            return false;
        }

        SortContactCommand otherSortContactCommand = (SortContactCommand) other;
        return comparatorType.equals(otherSortContactCommand.comparatorType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("comparatorType", comparatorType)
                .toString();
    }
}
