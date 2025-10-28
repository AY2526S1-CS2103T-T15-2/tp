package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;

import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractId;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyId;
import seedu.address.ui.ListPanelType;

/**
 * Removes a policy identified using policy id from the address book.
 */
public class RemovePolicyCommand extends Command {

    public static final String COMMAND_WORD = "remove_policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the policy identified by the id used in the policy list. \n"
            + "Parameters: ID (must be 6 alphanumeric characters)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PID + " A7f1Px";

    public static final String MESSAGE_REMOVE_POLICY_SUCCESS = "Removed Policy: %1$s";
    public static final String MESSAGE_REMOVE_POLICY_PENDING = "Contracts exists under this policy. "
                + "Please remove before proceeding: %1$s";

    private final PolicyId targetId;

    /**
     * Creates a RemovePolicyCommand to remove the specified policy by id
     * @param id the ID of policy to remove
     */
    public RemovePolicyCommand(PolicyId id) {
        requireAllNonNull(id);
        this.targetId = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPolicy(targetId)) {
            throw new CommandException(Messages.MESSAGE_POLICY_NOT_FOUND);
        }
        Policy policyToRemove = model.getPolicy(targetId);
        Set<Contract> existingContracts = policyToRemove.getContracts();
        if (!existingContracts.isEmpty()) {
            String existingContractIds = existingContracts.stream()
                    .map(Contract::getCId)
                    .map(ContractId::toString)
                    .collect(Collectors.joining(", "));
            throw new CommandException(String.format(MESSAGE_REMOVE_POLICY_PENDING, existingContractIds));
        }
        model.removePolicy(policyToRemove);
        return new CommandResult(String.format(MESSAGE_REMOVE_POLICY_SUCCESS, policyToRemove.getId()),
                    ListPanelType.POLICY);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemovePolicyCommand)) {
            return false;
        }

        RemovePolicyCommand otherRemovePolicyCommand = (RemovePolicyCommand) other;
        return targetId.equals(otherRemovePolicyCommand.targetId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetId", targetId)
                .toString();
    }
}
