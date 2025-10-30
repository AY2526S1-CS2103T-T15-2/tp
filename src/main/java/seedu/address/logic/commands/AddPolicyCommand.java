package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.UnassignedPolicy;
import seedu.address.ui.ListPanelType;

/**
 * Adds a policy to iCon.
 */
public non-sealed class AddPolicyCommand extends AddPolicyCommandType {

    public static final String MESSAGE_SUCCESS = "New policy added: %1$s";
    public static final String MESSAGE_DUPLICATE_POLICY = "This policy already exists in iCon";

    private final UnassignedPolicy toAdd;

    /**
     * Creates an AddPolicyCommand to add the specified {@code UnassignedPolicy}
     */
    public AddPolicyCommand(UnassignedPolicy unassignedPolicy) {
        requireNonNull(unassignedPolicy);
        toAdd = unassignedPolicy;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Policy policy = toAdd.assignId(model.generateUniquePolicyId());

        if (model.hasSamePolicyFields(policy)) {
            throw new CommandException(MESSAGE_DUPLICATE_POLICY);
        }

        model.addPolicy(policy);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(policy)), ListPanelType.POLICY);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPolicyCommand)) {
            return false;
        }

        AddPolicyCommand otherAddPolicyCommand = (AddPolicyCommand) other;
        return toAdd.equals(otherAddPolicyCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
