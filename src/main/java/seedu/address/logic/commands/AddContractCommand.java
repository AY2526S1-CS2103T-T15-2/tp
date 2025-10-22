package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.transformation.FilteredList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractId;
import seedu.address.model.contract.ContractPremium;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.policy.PolicyId;
import seedu.address.ui.ListPanelType;

/**
 * Adds a contract to iCon.
 * Links a contact to aa policy.
 */
public class AddContractCommand extends Command {

    public static final String COMMAND_WORD = "add_contract";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a contract to iCon. "
            + "Parameters: "
            + PREFIX_PID + "POLICY_ID "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_DATE + "DATE_SIGNED "
            + PREFIX_EXPIRY + "EXPIRY_DATE "
            + PREFIX_PREMIUM + "PREMIUM_AMOUNT "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PID + "68g354f7"
            + PREFIX_NRIC + "T0123456A "
            + PREFIX_DATE + "2025-01-13 "
            + PREFIX_EXPIRY + "2027-01-13 "
            + PREFIX_PREMIUM + "1200.50 ";

    public static final String MESSAGE_SUCCESS = "New contract added with the following ID: %s";
    public static final String MESSAGE_DUPLICATE_CONTRACT = "This contract already exists in iCon";
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person does not exist in iCon";

    private Contract toAdd;

    /**
     * Creates an AddContract to add the specified {@code Contract}
     */
    public AddContractCommand(Contract contract) {
        requireNonNull(contract);
        toAdd = contract;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // get nric from preloaded contract and load person to get name
        Nric nric = toAdd.getNric();
        FilteredList<Person> personFilteredList = model.getFilteredPersonList()
                .filtered(new NricContainsKeywordsPredicate(List.of(nric.toString())));
        if (personFilteredList.isEmpty()) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }
        Person person = personFilteredList.get(0);
        if (person == null || !person.getNric().equals(nric)) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        // fill in necessary fields
        Name name = person.getName();
        PolicyId policyId = toAdd.getPId();
        LocalDate date = toAdd.getDate();
        ContractId contractId = toAdd.getCId();
        LocalDate expiry = toAdd.getExpiryDate();
        ContractPremium premium = toAdd.getPremium();

        // initialise new contract
        toAdd = new Contract(contractId, name, nric, policyId, date, expiry, premium);

        if (model.hasContract(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTRACT);
        }

        model.addContract(toAdd);
        model.addContractToPerson(toAdd);
        model.addContractToPolicy(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.getCId().toString()), ListPanelType.CONTRACT);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddContractCommand)) {
            return false;
        }

        AddContractCommand otherAddContract = (AddContractCommand) other;
        return toAdd.equals(otherAddContract.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
