package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Represents commands invoked by {@code add_policy}.
 */
public abstract sealed class AddPolicyCommandType
        extends Command
        permits AddPolicyCommand, AddPolicyFileCommand {

    public static final String COMMAND_WORD = "add_policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a policy.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DETAILS + "DETAILS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Life Insurance "
            + PREFIX_DETAILS + "This policy... ";
}
