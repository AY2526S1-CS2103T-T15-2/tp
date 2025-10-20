package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTRACTS;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.contract.ContractIdContainsKeywordsPredicate;
import seedu.address.ui.ListPanelType;


/**
 * Lists all contracts in the address book to the user.
 */
public class ViewContractCommand extends Command {

    public static final String COMMAND_WORD = "view_contract";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Viewing contracts. "
            + "Parameters: "
            + "-a (View all contracts) or "
            + "c:CONTRACT_ID (View specific contracts by ID)\n"
            + "Examples: "
            + "view_contract -a or "
            + "view_contract c:ABCDEF";

    public static final String MESSAGE_SUCCESS_ALL = "Viewing all contracts";
    public static final String MESSAGE_SUCCESS_SPECIFIC = "Viewing contract with ID : %1$s";
    public static final String MESSAGE_NO_ID_MATCH = "Failed to find any contracts";

    private final boolean viewAll;
    private final ContractIdContainsKeywordsPredicate predicate;

    /**
     * Constructor method to view all contracts
     */
    public ViewContractCommand() {
        this.viewAll = true;
        this.predicate = null;
    }

    /**
     * Constructor method to view contracts matching predicate
     */
    public ViewContractCommand(ContractIdContainsKeywordsPredicate predicate) {
        this.viewAll = false;
        this.predicate = predicate;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        if (viewAll) {
            model.updateFilteredContractList(PREDICATE_SHOW_ALL_CONTRACTS);
            return new CommandResult(MESSAGE_SUCCESS_ALL, ListPanelType.CONTRACT);
        } else {
            model.updateFilteredContractList((predicate));
            if (model.getFilteredContractList().isEmpty()) {
                return new CommandResult(String.format(MESSAGE_NO_ID_MATCH), ListPanelType.CONTRACT);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS_SPECIFIC,
                    String.join(", ", predicate.getKeywords())), ListPanelType.CONTRACT);

        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ViewContractCommand)) {
            return false;
        }

        ViewContractCommand otherViewContractCommand = (ViewContractCommand) other;

        if (this.predicate == null && otherViewContractCommand.predicate == null) {
            return true;
        }

        if (this.predicate == null || otherViewContractCommand.predicate == null) {
            return false;
        }

        return predicate.equals(otherViewContractCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}

