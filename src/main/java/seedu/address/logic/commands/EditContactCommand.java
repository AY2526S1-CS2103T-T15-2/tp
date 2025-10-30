package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Nric;
import seedu.address.model.contact.Phone;
import seedu.address.model.contract.Contract;
import seedu.address.model.tag.Tag;
import seedu.address.ui.ListPanelType;

/**
 * Edits the details of an existing contact in the address book.
 */
public class EditContactCommand extends Command {

    public static final String COMMAND_WORD = "edit_contact";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the contact identified "
            + "by the Nric specified in iCon. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_NRIC + "NRIC" + " "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_CONTACT_SUCCESS = "Edited Contact: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the address book.";

    private final Nric nric;
    private final EditContactDescriptor editContactDescriptor;

    /**
     * @param nric of the contact in the filtered contact list to edit
     * @param editContactDescriptor details to edit the contact with
     */
    public EditContactCommand(Nric nric, EditContactDescriptor editContactDescriptor) {
        requireNonNull(nric);
        requireNonNull(editContactDescriptor);

        this.nric = nric;
        this.editContactDescriptor = new EditContactDescriptor(editContactDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Contact> list = model.getUniqueContactList();

        if (list.stream().anyMatch(x -> x.getNric().equals(nric))) {
            Contact contactToEdit = list.stream()
                    .filter(x -> x.getNric().equals(nric))
                    .findFirst()
                    .get();
            Contact editedContact = createEditedContact(contactToEdit, editContactDescriptor);

            if (model.hasContact(editedContact)) {
                throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
            }

            model.setContact(contactToEdit, editedContact);
            model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
            return new CommandResult(String.format(MESSAGE_EDIT_CONTACT_SUCCESS, Messages.format(editedContact)),
                    ListPanelType.CONTACT);
        } else {
            throw new CommandException(String.format(Messages.MESSAGE_CONTACT_NOT_FOUND, nric));
        }
    }

    /**
     * Creates and returns a {@code Contact} with the details of {@code contactToEdit}
     * edited with {@code editContactDescriptor}.
     */
    private static Contact createEditedContact(Contact contactToEdit, EditContactDescriptor editContactDescriptor) {
        assert contactToEdit != null;

        Name updatedName = editContactDescriptor.getName().orElse(contactToEdit.getName());
        Phone updatedPhone = editContactDescriptor.getPhone().orElse(contactToEdit.getPhone());
        Nric updatedNric = contactToEdit.getNric();
        Email updatedEmail = editContactDescriptor.getEmail().orElse(contactToEdit.getEmail());
        Address updatedAddress = editContactDescriptor.getAddress().orElse(contactToEdit.getAddress());
        Set<Tag> updatedTags = editContactDescriptor.getTags().orElse(contactToEdit.getTags());
        Set<Contract> contracts = contactToEdit.getContracts();

        return new Contact(updatedName, updatedPhone,
                updatedNric, updatedEmail, updatedAddress, updatedTags, contracts);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditContactCommand)) {
            return false;
        }

        EditContactCommand otherEditContactCommand = (EditContactCommand) other;
        return nric.equals(otherEditContactCommand.nric)
                && editContactDescriptor.equals(otherEditContactCommand.editContactDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("nric", nric)
                .add("editContactDescriptor", editContactDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the contact with. Each non-empty field value will replace the
     * corresponding field value of the contact.
     */
    public static class EditContactDescriptor {
        private Nric nric;
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Set<Contract> contracts;

        public EditContactDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditContactDescriptor(EditContactDescriptor toCopy) {
            setNric(toCopy.nric);
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setContracts(toCopy.contracts);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code contracts} to this object's {@code contracts}.
         * A defensive copy of {@code contracts} is used internally
         */
        public void setContracts(Set<Contract> contracts) {
            this.contracts = (contracts != null) ? new HashSet<>(contracts) : null;
        }

        /**
         * Returns an unmodifiable contract set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code contracts} is null.
         */
        public Optional<Set<Contract>> getContracts() {
            return (contracts != null) ? Optional.of(Collections.unmodifiableSet(contracts)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditContactDescriptor)) {
                return false;
            }

            EditContactDescriptor otherEditContactDescriptor = (EditContactDescriptor) other;
            return Objects.equals(nric, otherEditContactDescriptor.nric)
                    && Objects.equals(name, otherEditContactDescriptor.name)
                    && Objects.equals(phone, otherEditContactDescriptor.phone)
                    && Objects.equals(email, otherEditContactDescriptor.email)
                    && Objects.equals(address, otherEditContactDescriptor.address)
                    && Objects.equals(tags, otherEditContactDescriptor.tags);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("nric", nric)
                    .add("name", name)
                    .add("phone", phone)
                    .add("email", email)
                    .add("address", address)
                    .add("tags", tags)
                    .toString();
        }
    }
}
