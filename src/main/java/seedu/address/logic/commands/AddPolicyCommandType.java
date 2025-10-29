package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Represents commands invoked by {@code add_policy}.
 */
public abstract sealed class AddPolicyCommandType
        extends Command
        permits AddPolicyCommand, AddPolicyFileCommand {

    public static final String COMMAND_WORD = "add_policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a policy or multiple"
            + " policies from a file.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DETAILS + "DETAILS (Adds one policy) or "
            + PREFIX_FILE + "FILE_PATH (Adds policies from a file) \n"
            + "Note: Policies loaded from a txt file should have text be formatted as lines of NAME`DETAILS."
            + " See help for examples.\n"
            + "Examples: \n"
            + COMMAND_WORD + " "
            + PREFIX_NAME + "Life Insurance "
            + PREFIX_DETAILS + "This policy...\n"
            + COMMAND_WORD + " "
            + PREFIX_FILE + "policies.txt";
}
