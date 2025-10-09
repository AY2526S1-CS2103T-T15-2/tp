package seedu.address.logic.commands;

import seedu.address.commons.util.ToStringBuilder;

import static java.util.Objects.requireNonNull;

public class ViewContractCommand extends Command {

    public static final String COMMAND_WORD = "view c: -a";

    public static final String MESSAGE_SUCCESS = "Viewing all contracts ";


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPolicyList(PREDICATE_SHOW_ALL_CONTRACTS)
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

