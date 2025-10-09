package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

public class ViewContractCommand extends Command {

    public static final String COMMAND_WORD = "view c";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views a contract "
            + "Parameters: " + "<Contract id>";

    public static final String MESSAGE_SUCCESS = "Viewing contract: " + contract_id;

    private final Contract_id toView; //id to be viewed

    public ViewContractCommand(Contract_id id) {
        requireNonNull(id);
        toView = id;
    }

    @Override
    public Command
}

