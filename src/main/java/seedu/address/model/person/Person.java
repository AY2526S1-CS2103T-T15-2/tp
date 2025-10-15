package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contract.Contract;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Nric nric;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Set<Contract> contracts = new HashSet<>();

    /**
     * Constructor when constructing Person with contracts.
     */
    public Person(Name name, Phone phone, Nric nric, Email email, Address address, Set<Tag> tags,
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

    /**
     * Main constructor when constructing Person
     * Constructor without contracts.
     */
    public Person(Name name, Phone phone, Nric nric, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, phone, nric);
        this.name = name;
        this.phone = phone;
        this.nric = nric;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
    }

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

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Adds a contract to the person's set of contracts.
     * @param contract The contract to be added.
     */
    public void addContract(Contract contract) {
        contracts.add(contract);
    }

    /**
     * Removes a contract from the person's set of contracts.
     * @param contract The contract to be removed.
     */
    public void removeContract(Contract contract) {
        contracts.remove(contract);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && nric.equals(otherPerson.nric)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && contracts.equals(otherPerson.contracts);
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
