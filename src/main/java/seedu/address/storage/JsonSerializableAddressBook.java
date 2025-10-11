package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.contract.Contract;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_POLICY = "Policies list contains duplicate policy(s).";
    public static final String MESSAGE_DUPLICATE_CONTRACT = "Contracts list contains duplicate contract(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedPolicy> policies = new ArrayList<>();
    private final List<JsonAdaptedContract> contracts = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("policies") List<JsonAdaptedPolicy> policies,
                                       @JsonProperty("contracts") List<JsonAdaptedContract> contracts) {
        this.persons.addAll(persons);
        this.policies.addAll(policies);
        this.contracts.addAll(contracts);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        policies.addAll(source.getPolicyList().stream().map(JsonAdaptedPolicy::new).collect(Collectors.toList()));
        contracts.addAll(source.getContractList().stream().map(JsonAdaptedContract::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        /* Convert persons */
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
        }
        /* Convert policies */
        for (JsonAdaptedPolicy jsonAdaptedPolicy : policies) {
            Policy policy = jsonAdaptedPolicy.toModelType();
            if (addressBook.hasPolicy(policy)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_POLICY);
            }
            addressBook.addPolicy(policy);
        }
        /* Convert contracts */
        for (JsonAdaptedContract jsonAdaptedContract : contracts) {
            Contract contract = jsonAdaptedContract.toModelType();
            if (addressBook.hasContract(contract)) {
                throw new IllegalValueException("Contracts list contains duplicate contract(s).");
            }
            addressBook.addContract(contract);
        }
        return addressBook;
    }

}
