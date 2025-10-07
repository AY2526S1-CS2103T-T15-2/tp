package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.policy.Policy;

/**
 * Removes a policy identified using policy id from the address book.
 */
public class RemovePolicyCommand extends Command {

    public static final String COMMAND_WORD = "remove policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the policy identified by the id used in the policy list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_REMOVE_POLICY_SUCCESS = "Removed Policy: %1$s";

    private final Index targetIndex;

    public RemovePolicyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Policy> lastShownList = model.getFilteredPolicyList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_POLICY_ID);
        }

        Policy policyToRemove = lastShownList.get(targetIndex.getZeroBased());
        model.removePolicy(policyToRemove);
        return new CommandResult(String.format(MESSAGE_REMOVE_POLICY_SUCCESS, Messages.format(policyToRemove)));
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
        return targetIndex.equals(otherRemovePolicyCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
