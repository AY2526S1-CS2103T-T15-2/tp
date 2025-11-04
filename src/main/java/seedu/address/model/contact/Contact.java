package seedu.address.model.contact;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractId;
import seedu.address.model.tag.Tag;

/**
 * Represents a Contact in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Contact {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Nric nric;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Contract> contracts = new HashSet<>();

    //@@author Joshua-Seah
    /**
     * Constructor when constructing Contact with contracts.
     */
    public Contact(Name name, Phone phone, Nric nric, Email email, Address address, Set<Tag> tags,
                   Set<Contract> contracts) {
        requireAllNonNull(name, phone, nric);
        this.name = name;
        this.phone = phone;
        this.nric = nric;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.contracts.addAll(contracts);
    }

    //@@author porcupinezzz
    /**
     * Main constructor when constructing Contact
     * Constructor without contracts.
     */
    public Contact(Name name, Phone phone, Nric nric, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, nric);
        this.name = name;
        this.phone = phone;
        this.nric = nric;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }
    //@@author

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Nric getNric() {
        return nric;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable contract set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Contract> getContracts() {
        return Collections.unmodifiableSet(contracts);
    }

    public String getContractIdsAsString() {
        return contracts.stream()
                .map(Contract::getCId)
                .map(ContractId::toString)
                .collect(Collectors.joining(", "));
    }

    /**
     * Returns true if both contacts have the same nric.
     * This defines a weaker notion of equality between two contacts.
     */
    public boolean isSameContact(Contact otherContact) {
        if (otherContact == this) {
            return true;
        }

        return otherContact != null
                && otherContact.getNric().equals(getNric());
    }

    /**
     * Adds a contract to the contact's set of contracts.
     * @param contract The contract to be added.
     */
    public void addContract(Contract contract) {
        if (containsContract(contract)) {
            return;
        }
        contracts.add(contract);
    }

    /**
     * Removes a contract from the contact's set of contracts.
     * @param contract The contract to be removed.
     */
    public void removeContract(Contract contract) {
        contracts.remove(contract);
    }

    /**
     * Returns true if the contact has the given contract.
     * @param contract The contract to be checked.
     * @return True if the contact has the given contract, false otherwise.
     */
    private boolean containsContract(Contract contract) {
        return contracts.stream().anyMatch(contract::isSameContract);
    }

    /**
     * Compares two contacts' name alphabetically.
     * Used as a comparator to sort contacts.
     */
    public static int compareNameAlphabetical(Contact contact, Contact otherContact) {
        return String.CASE_INSENSITIVE_ORDER.compare(
                contact.name.fullName,
                otherContact.name.fullName
        );
    }

    /**
     * Returns true if both contacts have the same identity and data fields.
     * This defines a stronger notion of equality between two contacts.
     * This defines a stronger notion of equality between two contacts.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Contact)) {
            return false;
        }

        Contact otherContact = (Contact) other;
        return name.equals(otherContact.name)
                && phone.equals(otherContact.phone)
                && email.equals(otherContact.email)
                && nric.equals(otherContact.nric)
                && address.equals(otherContact.address)
                && tags.equals(otherContact.tags)
                && contracts.equals(otherContact.contracts);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, nric, email, address, tags, contracts);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("nric", nric)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("contracts", contracts)
                .toString();
    }

}
