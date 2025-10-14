package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.io.File;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.policy.Policy;

/**
 * Adds multiple policies from a file.
 */
public non-sealed class AddPolicyCommandMultiple extends AddPolicyCommandType {

    public static final String MESSAGE_SUCCESS = "New policies added from file: %1$s";

    private final File toAdd;

    /**
     * Creates an AddPolicyCommandMultiple to add policies from a {@code File}
     */
    public AddPolicyCommandMultiple(File file) {
        requireNonNull(file);
        toAdd = file;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addPolicyFile(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getPath()));
    }

    /**
     * Tests for equality of commands.
     * Mainly used for testing the correctness of the parser.
     */
    public boolean isSameCommand(Object other) {
        return equals(other);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPolicyCommandMultiple)) {
            return false;
        }

        AddPolicyCommandMultiple otherAddPolicyCommand = (AddPolicyCommandMultiple) other;
        return toAdd.equals(otherAddPolicyCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
