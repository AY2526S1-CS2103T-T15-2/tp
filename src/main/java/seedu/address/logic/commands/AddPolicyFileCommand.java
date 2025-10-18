package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Adds multiple policies from a file.
 */
public non-sealed class AddPolicyFileCommand extends AddPolicyCommandType {

    public static final String MESSAGE_SUCCESS = "New policies added from file: %1$s";

    public static final String MESSAGE_IOEXCEPTION = "Error encountered when reading file: %1$s";

    private final Path toAdd;

    /**
     * Creates an AddPolicyFileCommand to add policies from a {@code Path}
     */
    public AddPolicyFileCommand(Path filePath) {
        requireNonNull(filePath);
        toAdd = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            model.addPolicyFile(toAdd);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_IOEXCEPTION, e.getMessage()), e);
        } catch (ParseException e) {
            throw new CommandException(e.getMessage(), e);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPolicyFileCommand)) {
            return false;
        }

        AddPolicyFileCommand otherAddPolicyCommand = (AddPolicyFileCommand) other;
        return toAdd.equals(otherAddPolicyCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
