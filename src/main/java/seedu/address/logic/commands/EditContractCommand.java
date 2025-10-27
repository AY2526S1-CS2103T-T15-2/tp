package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_CONTRACT_PERIOD;
import static seedu.address.logic.Messages.MESSAGE_PERSON_NOT_FOUND;
import static seedu.address.logic.Messages.MESSAGE_POLICY_NOT_FOUND;
import static seedu.address.logic.commands.CommandUtil.isValidDateSignedAndExpiry;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PREMIUM;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractId;
import seedu.address.model.contract.ContractPremium;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.policy.PolicyId;
import seedu.address.ui.ListPanelType;

/**
 * Edits the details of an existing contract in iCon.
 */
public class EditContractCommand extends Command {

    public static final String COMMAND_WORD = "edit_contract";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the contract identified "
            + "by the contract id used in the displayed contract list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_CID + "CONTRACT_ID (must be a 6 character-length alphanumeric string) "
            + "[" + PREFIX_PID + "POLICY_ID] "
            + "[" + PREFIX_NRIC + "NRIC] "
            + "[" + PREFIX_DATE + "DATE SIGNED] "
            + "[" + PREFIX_EXPIRY + "EXPIRY DATE] "
            + "[" + PREFIX_PREMIUM + "PREMIUM] "
            + "Example: "
            + COMMAND_WORD + PREFIX_CID + " C1234a "
            + PREFIX_NRIC + "T1234567B "
            + PREFIX_EXPIRY + "2026-01-02";

    public static final String MESSAGE_EDIT_CONTRACT_SUCCESS = "Edited Contract: %s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CONTRACT = "This contract already exists in the address book.";

    private final ContractId cId;
    private final EditContractDescriptor editContractDescriptor;

    /**
     * @param contractId of the contract in the filtered contract list to edit
     * @param editContractDescriptor details to edit the contract with
     */
    public EditContractCommand(ContractId contractId, EditContractDescriptor editContractDescriptor) {
        this.cId = contractId;
        this.editContractDescriptor = editContractDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(cId);
        List<Contract> lastShownList = model.getFilteredContractList();

        if (lastShownList.stream().anyMatch(x -> x.getCId().equals(cId))) {
            Contract contractToEdit = lastShownList.stream()
                    .filter(x -> x.getCId().equals(cId))
                    .findFirst()
                    .get();
            Contract editedContract = createEditedContract(contractToEdit, editContractDescriptor, model);

            if (!contractToEdit.isSameContract(editedContract) && model.hasContract(editedContract)) {
                throw new CommandException(MESSAGE_DUPLICATE_CONTRACT);
            }

            model.setContract(contractToEdit, editedContract);
            model.removeContractFromPerson(contractToEdit);
            model.removeContractFromPolicy(contractToEdit);
            model.addContractToPerson(editedContract);
            model.addContractToPolicy(editedContract);
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            model.updateFilteredPolicyList(Model.PREDICATE_SHOW_ALL_POLICIES);
            model.updateFilteredContractList(Model.PREDICATE_SHOW_ALL_CONTRACTS);
            return new CommandResult(String.format(MESSAGE_EDIT_CONTRACT_SUCCESS, editedContract.getCId().toString()),
                    ListPanelType.CONTRACT);

        } else {
            throw new CommandException(Messages.MESSAGE_CONTRACT_NOT_FOUND);
        }
    }

    /**
     * Creates and returns a {@code Contract} with the details of {@code contractToEdit}
     * edited with {@code editContractDescriptor}.
     */
    private static Contract createEditedContract(Contract contractToEdit,
                                                 EditContractDescriptor editContractDescriptor,
                                                 Model model) throws CommandException {
        assert contractToEdit != null;

        ContractId updatedCId = contractToEdit.getCId();
        Name updatedName = editContractDescriptor.getName(model).orElse(contractToEdit.getName());
        PolicyId updatedPid = editContractDescriptor.getPId().orElse(contractToEdit.getPId());
        Nric updatedNric = editContractDescriptor.getNric().orElse(contractToEdit.getNric());
        LocalDate updatedDateSigned = editContractDescriptor.getDate().orElse(contractToEdit.getDate());
        LocalDate updatedExpiryDate = editContractDescriptor.getExpiryDate().orElse(contractToEdit.getExpiryDate());
        ContractPremium updatedPremium = editContractDescriptor.getPremium().orElse(contractToEdit.getPremium());
        if (!model.hasPerson(updatedNric)) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }
        if (!model.hasPolicy(updatedPid)) {
            throw new CommandException(MESSAGE_POLICY_NOT_FOUND);
        }
        if (!isValidDateSignedAndExpiry(updatedDateSigned, updatedExpiryDate)) {
            throw new CommandException(MESSAGE_INVALID_CONTRACT_PERIOD);
        }
        System.out.println("Person: " + updatedNric);
        System.out.println("Policy: " + updatedPid);

        return new Contract(updatedCId, updatedName, updatedNric, updatedPid, updatedDateSigned, updatedExpiryDate,
                updatedPremium);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditContractCommand)) {
            return false;
        }

        EditContractCommand otherEditContractCommand = (EditContractCommand) other;
        return cId.equals(otherEditContractCommand.cId)
                && editContractDescriptor.equals(otherEditContractCommand.editContractDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("cId", cId)
                .toString();
    }

    /**
     * Stores the details to edit the contract with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditContractDescriptor {
        private ContractId cId;
        private PolicyId pId;
        private Nric nric;
        private Name name;
        private LocalDate dateSigned;
        private LocalDate expiryDate;
        private ContractPremium premium;

        public EditContractDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code toCopy} is used internally.
         */
        public EditContractDescriptor(EditContractDescriptor toCopy) {
            setCId(toCopy.cId);
            setPId(toCopy.pId);
            setNric(toCopy.nric);
            setName(toCopy.name);
            setDate(toCopy.dateSigned);
            setExpiryDate(toCopy.expiryDate);
            setPremium(toCopy.premium);
        }

        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(pId, nric, name, dateSigned, expiryDate, premium);
        }

        public void setCId(ContractId cId) {
            this.cId = cId;
        }

        public Optional<ContractId> getCId() {
            return Optional.ofNullable(cId);
        }

        public void setPId(PolicyId pId) {
            this.pId = pId;
        }

        public Optional<PolicyId> getPId() {
            return Optional.ofNullable(pId);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        /**
         * Standard setName method for copying values.
         * @param name
         */
        public void setName(Name name) {
            this.name = name;
        }

        /**
         * Overloaded setName method to set name based on nric from model.
         * Mainly used for when nric is edited.
         * @param model
         * @throws CommandException
         */
        public void setName(Model model) {
            this.name = model.getName(nric);
        }

        public Optional<Name> getName(Model model) throws CommandException {
            if (getNric().isPresent()) {
                setName(model);
            }
            return Optional.ofNullable(name);
        }

        public void setDate(LocalDate dateSigned) {
            this.dateSigned = dateSigned;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(dateSigned);
        }

        public void setExpiryDate(LocalDate expiryDate) {
            this.expiryDate = expiryDate;
        }

        public Optional<LocalDate> getExpiryDate() {
            return Optional.ofNullable(expiryDate);
        }

        public void setPremium(ContractPremium premium) {
            this.premium = premium;
        }

        public Optional<ContractPremium> getPremium() {
            return Optional.ofNullable(premium);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditContractDescriptor)) {
                return false;
            }

            EditContractDescriptor otherDesciptor = (EditContractDescriptor) other;

            return getCId().equals(otherDesciptor.getCId())
                    && getPId().equals(otherDesciptor.getPId())
                    && getNric().equals(otherDesciptor.getNric())
                    && getDate().equals(otherDesciptor.getDate())
                    && getExpiryDate().equals(otherDesciptor.getExpiryDate())
                    && getPremium().equals(otherDesciptor.getPremium());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("cId", cId)
                    .add("pId", pId)
                    .add("nric", nric)
                    .add("name", name)
                    .add("dateSigned", dateSigned)
                    .add("expiryDate", expiryDate)
                    .add("premium", premium)
                    .toString();
        }
    }
}
