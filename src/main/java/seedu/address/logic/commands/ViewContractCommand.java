package seedu.address.logic.commands;


import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTRACTS;

import seedu.address.model.Model;


/**
 * Lists all contracts in the address book to the user.
 */
public class ViewContractCommand extends Command {

    public static final String COMMAND_WORD = "view c: -a";

    public static final String MESSAGE_SUCCESS = "Viewing all contracts ";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredContractList(PREDICATE_SHOW_ALL_CONTRACTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

