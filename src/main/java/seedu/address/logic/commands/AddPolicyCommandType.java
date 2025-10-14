package seedu.address.logic.commands;

public sealed abstract class AddPolicyCommandType
        extends Command
        permits AddPolicyCommand, AddPolicyCommandMultiple {
    public abstract boolean isSameCommand(Object other);
}
