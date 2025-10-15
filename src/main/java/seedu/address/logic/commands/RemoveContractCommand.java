package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractId;

/**
 * Removes a contract from iCon.
 */
public class RemoveContractCommand extends Command {

    public static final String COMMAND_WORD = "remove_contract";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a contract from iCon. "
            + "Parameters: "
            + "CID (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " C1234A";

    public static final String MESSAGE_REMOVE_CONTRACT_SUCCESS = "Removed Contract: %1$s";

    private final ContractId cId;

    public RemoveContractCommand(ContractId targetIndex) {
        this.cId = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contract> lastShownList = model.getFilteredContractList();

        if (lastShownList.stream().anyMatch(x -> x.getCId().equals(cId))) {
            Contract contractToRemove = lastShownList.stream()
                    .filter(x -> x.getCId().equals(cId))
                    .findFirst()
                    .get();
            model.removeContract(contractToRemove);
            model.removeContractFromPerson(contractToRemove);
            model.removeContractFromPolicy(contractToRemove);
            return new CommandResult(String.format(MESSAGE_REMOVE_CONTRACT_SUCCESS, contractToRemove.getCId()));
        } else {
            throw new CommandException(Messages.MESSAGE_CONTRACT_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveContractCommand)) {
            return false;
        }

        RemoveContractCommand otherRemoveContract = (RemoveContractCommand) other;
        return cId.equals(otherRemoveContract.cId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("cId", cId)
                .toString();
    }
}
