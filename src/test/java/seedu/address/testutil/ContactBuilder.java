package seedu.address.testutil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Nric;
import seedu.address.model.contact.Phone;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractId;
import seedu.address.model.contract.ContractPremium;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Contact objects.
 */
public class ContactBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_NRIC = "S1234567A";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final List<Contract> DEFAULT_CONTRACTS = List.of(new Contract(
            new ContractId("C1234A"),
            new Name("Alice Pauline"),
            new Nric("S1234567A"),
            new PolicyId("abcdef"),
            LocalDate.parse("2023-01-01"),
            LocalDate.parse("2025-01-01"),
            new ContractPremium("1000.00")
    ));

    private Name name;
    private Phone phone;
    private Nric nric;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Set<Contract> contracts;

    /**
     * Creates a {@code ContactBuilder} with the default details.
     */
    public ContactBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        nric = new Nric(DEFAULT_NRIC);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        contracts = new HashSet<>();
    }

    /**
     * Initializes the ContactBuilder with the data of {@code contactToCopy}.
     */
    public ContactBuilder(Contact contactToCopy) {
        name = contactToCopy.getName();
        phone = contactToCopy.getPhone();
        nric = contactToCopy.getNric();
        email = contactToCopy.getEmail();
        address = contactToCopy.getAddress();
        tags = new HashSet<>(contactToCopy.getTags());
        contracts = new HashSet<>(contactToCopy.getContracts());
    }

    /**
     * Sets the {@code Name} of the {@code Contact} that we are building.
     */
    public ContactBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Contact} that we are building.
     */
    public ContactBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Contact} that we are building.
     */
    public ContactBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Contact} that we are building.
     */
    public ContactBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Contact} that we are building.
     */
    public ContactBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Contact} that we are building.
     */
    public ContactBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code contracts} of the {@code Contact} that we are building to the DEFAULT_CONTRACTS.
     * @return
     */
    public ContactBuilder withDefaultContract() {
        this.contracts = new HashSet<>(DEFAULT_CONTRACTS);
        return this;
    }

    /**
     * Parses the {@code contracts} into a {@code Set<Contract>} and set it to the {@code Contact} that we are building.
     */
    public ContactBuilder withContracts(List<Contract> contracts) {
        this.contracts = new HashSet<>(contracts);
        return this;
    }

    public Contact build() {
        return new Contact(name, phone, nric, email, address, tags, contracts);
    }

}
