package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contract.Contract;

/**
 * Removes a contract from iCon.
 */
public class RemoveContract extends Command {

    public static final String COMMAND_WORD = "remove_contract";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a contract from iCon. "
            + "Parameters: "
            + "CID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMOVE_CONTRACT_SUCCESS = "Removed Contract: %1$s";

    private final Index targetIndex;

    public RemoveContract(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contract> lastShownList = model.getFilteredContractList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CONTRACT_DISPLAYED_INDEX);
        }

        Contract contractToRemove = lastShownList.get(targetIndex.getZeroBased());
        model.removeContract(contractToRemove);
        return new CommandResult(String.format(MESSAGE_REMOVE_CONTRACT_SUCCESS, contractToRemove));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveContract)) {
            return false;
        }

        RemoveContract otherRemoveContract = (RemoveContract) other;
        return targetIndex.equals(otherRemoveContract.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
