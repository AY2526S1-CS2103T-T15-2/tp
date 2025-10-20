package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_POLICIES;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.policy.IdContainsKeywordsPredicate;
import seedu.address.ui.ListPanelType;


/**
 * Lists all policies or by specific policy id in the address book to the user
 * Keyword matching is case sensitive
 */
public class ViewPolicyCommand extends Command {

    public static final String COMMAND_WORD = "view_policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views policies. "
            + "Parameters: "
            + "-a (View all policies) or "
            + "p:POLICY_ID  (View specific policies by ID)\n"
            + "Examples: "
            + "view_policy -a or "
            + "view_policy p:ABCDEF";

    public static final String MESSAGE_SUCCESS_ALL = "Viewing all policies";
    public static final String MESSAGE_SUCCESS_SPECIFIC = "Viewing policy with ID : %1$s";
    public static final String MESSAGE_NO_ID_MATCH = "Failed to find any policies";

    private final boolean viewAll;
    private final IdContainsKeywordsPredicate predicate;

    /**
     * Constructor method to view all policies
     */
    public ViewPolicyCommand() {
        this.viewAll = true;
        this.predicate = null;
    }

    /**
     * Constructor method to view policies matching predicate
     */
    public ViewPolicyCommand(IdContainsKeywordsPredicate predicate) {
        this.viewAll = false;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (viewAll) {
            model.updateFilteredPolicyList(PREDICATE_SHOW_ALL_POLICIES);
            return new CommandResult(MESSAGE_SUCCESS_ALL, ListPanelType.POLICY);
        } else {
            model.updateFilteredPolicyList((predicate));
            if (model.getFilteredPolicyList().isEmpty()) {
                return new CommandResult(String.format(MESSAGE_NO_ID_MATCH), ListPanelType.POLICY);
            }
            return new CommandResult(
                    String.format(MESSAGE_SUCCESS_SPECIFIC, String.join(", ", predicate.getKeywords())),
                    ListPanelType.POLICY
            );
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewPolicyCommand)) {
            return false;
        }

        ViewPolicyCommand otherViewPolicyCommand = (ViewPolicyCommand) other;

        if (this.predicate == null && otherViewPolicyCommand.predicate == null) {
            return true;
        }

        if (this.predicate == null || otherViewPolicyCommand.predicate == null) {
            return false;
        }

        return predicate.equals(otherViewPolicyCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
