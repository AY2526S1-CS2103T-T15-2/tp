package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.policy.Policy;

/**
 * Adds a policy.
 */
public class AddPolicyCommand extends Command {

    public static final String COMMAND_WORD = "add_policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a policy. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DETAILS + "DETAILS "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Life Insurance "
            + PREFIX_DETAILS + "This policy... ";

    public static final String MESSAGE_SUCCESS = "New policy added: %1$s";

    private final Policy toAdd;

    /**
     * Creates an AddPolicyCommand to add the specified {@code Policy}
     */
    public AddPolicyCommand(Policy policy) {
        requireNonNull(policy);
        toAdd = policy;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addPolicy(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    /**
     * Tests for equality of commands excluding the policy id.
     * Mainly used for testing the correctness of the parser.
     */
    public boolean weakEquals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPolicyCommand)) {
            return false;
        }

        AddPolicyCommand otherAddPolicyCommand = (AddPolicyCommand) other;
        return toAdd.weakEquals(otherAddPolicyCommand.toAdd);
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
