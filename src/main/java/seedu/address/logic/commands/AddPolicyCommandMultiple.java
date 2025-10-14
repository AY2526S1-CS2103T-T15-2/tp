package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Adds multiple policies from a file.
 */
public non-sealed class AddPolicyCommandMultiple extends AddPolicyCommandType {

    public static final String MESSAGE_SUCCESS = "New policies added from file: %1$s";

    private final Path toAdd;

    /**
     * Creates an AddPolicyCommandMultiple to add policies from a {@code Path}
     */
    public AddPolicyCommandMultiple(Path filePath) {
        requireNonNull(filePath);
        toAdd = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        model.addPolicyFile(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
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
