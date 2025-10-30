package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.contact.NricContainsKeywordsPredicate;
import seedu.address.ui.ListPanelType;

/**
 * Finds and lists all contacts in address book whose name contains any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class ViewContactCommand extends Command {

    public static final String COMMAND_WORD = "view_contact";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all contacts whose nrics matches any of "
            + "the specified nrics (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC1 [NRIC2] [NRIC3]...\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_NRIC + " T1234567A S1234892B T0549223e";

    private final NricContainsKeywordsPredicate predicate;
    private final boolean showAllContacts;

    /**
     * Initialises find command with NRICs to search
     * @param predicate
     */
    public ViewContactCommand(NricContainsKeywordsPredicate predicate) {
        this.showAllContacts = false;
        this.predicate = predicate;
    }

    /**
     * Initialises find command to find all contacts
     * @param showAllContacts
     */
    public ViewContactCommand(boolean showAllContacts) {
        this.showAllContacts = showAllContacts;
        this.predicate = null;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (showAllContacts) {
            model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
            return new CommandResult(
                    String.format(Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW, model.getFilteredContactList().size()),
                    ListPanelType.CONTACT);
        }
        model.updateFilteredContactList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_CONTACTS_LISTED_OVERVIEW, model.getFilteredContactList().size()),
                ListPanelType.CONTACT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewContactCommand)) {
            return false;
        }

        ViewContactCommand otherViewContactCommand = (ViewContactCommand) other;
        if (this.predicate == null && otherViewContactCommand.predicate == null) {
            return true;
        }

        if (this.predicate == null || otherViewContactCommand.predicate == null) {
            return false;
        }

        return predicate.equals(otherViewContactCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
