package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contract.Contract;

/**
 * Adds a contract to iCon.
 * Links a contact to aa policy.
 */
public class AddContract extends Command {

    public static final String COMMAND_WORD = "add_contract";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a contract to iCon. "
            + "Parameters: "
            + PREFIX_PID + "POLICY_ID "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_DATE + "DATE_SIGNED "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PID + "68g354f7"
            + PREFIX_NRIC + "T0123456A "
            + PREFIX_DATE + "2025-01-13 ";

    public static final String MESSAGE_SUCCESS = "New contract added: %1$s";
    public static final String MESSAGE_DUPLICATE_CONTRACT = "This contract already exists in iCon";

    private final Contract toAdd;

    /**
     * Creates an AddContract to add the specified {@code Contract}
     */
    public AddContract(Contract contract) {
        requireNonNull(contract);
        toAdd = contract;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasContract(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTRACT);
        }

        model.addContract(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddContract)) {
            return false;
        }

        AddContract otherAddContract = (AddContract) other;
        return toAdd.equals(otherAddContract.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
