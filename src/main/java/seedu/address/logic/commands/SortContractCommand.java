package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.FLAG_EXPIRY_ORDER_ASCENDING;
import static seedu.address.logic.parser.CliSyntax.FLAG_INSERTION_ORDER;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.contract.ContractComparatorType;
import seedu.address.ui.ListPanelType;

/**
 * Sorts all contracts by their expiry date in ascending order.
 */
public class SortContractCommand extends Command {

    public static final String COMMAND_WORD = "sort_contract";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts all contracts by their premium amount in ascending order "
            + "or by their insertion order.\n"
            + "Parameters: [SORT_FLAG = " + FLAG_EXPIRY_ORDER_ASCENDING
            + " or " + FLAG_INSERTION_ORDER + "]\n"
            + "Example: " + COMMAND_WORD + " " + FLAG_EXPIRY_ORDER_ASCENDING;

    public static final String MESSAGE_SUCCESS_UNORDERED = "Showing contracts by insertion order";

    public static final String MESSAGE_SUCCESS_EXPIRY_DATE = "Contracts sorted by expiry date in ascending order.";

    private final ContractComparatorType comparatorType;

    /**
     * Creates a SortContractCommand to sort contracts using the specified comparator type.
     *
     * @param comparatorType The type of comparator to use for sorting.
     */
    public SortContractCommand(ContractComparatorType comparatorType) {
        requireNonNull(comparatorType);
        this.comparatorType = comparatorType;
    }

    /**
     * Executes the sort contract command.
     *
     * @param model The model containing the contracts to be sorted.
     * @return A CommandResult indicating the result of the command execution.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortContracts(comparatorType.comparator);

        return switch(comparatorType) {
        case UNORDERED -> new CommandResult(MESSAGE_SUCCESS_UNORDERED, ListPanelType.CONTRACT);
        case EXPIRY_DATE -> new CommandResult(MESSAGE_SUCCESS_EXPIRY_DATE, ListPanelType.CONTRACT);
        };
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortContractCommand)) {
            return false;
        }

        SortContractCommand otherCommand = (SortContractCommand) other;
        return comparatorType.equals(otherCommand.comparatorType);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("comparatorType", comparatorType)
                .toString();
    }
}
