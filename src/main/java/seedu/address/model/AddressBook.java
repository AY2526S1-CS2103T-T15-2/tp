package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.parser.PolicyFileParser;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.UniqueContractList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.UnassignedPolicy;
import seedu.address.model.policy.UniquePolicyList;
import seedu.address.model.policy.exceptions.PolicyNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueContractList contracts;
    private final UniquePolicyList policies;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        contracts = new UniqueContractList();
    }

    /*
    * Non-static init block for Policy list
    */
    {
        policies = new UniquePolicyList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the policy list with {@code policies}.
     * {@code policies} must not contain duplicate policies.
     */
    public void setPolicies(List<Policy> policies) {
        this.policies.setPolicies(policies);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setPolicies(newData.getPolicyList());
        setContracts(newData.getContractList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// policy-level operations

    /**
     * Returns true if a policy with the same id as {@code policy} exists in the address book.
     */
    public boolean hasPolicy(Policy policy) {
        requireNonNull(policy);
        return policies.containsSameId(policy);
    }

    /**
     * Adds a policy to the address book.
     * The policy must not already exist in the address book.
     */
    public void addPolicy(Policy p) {
        policies.add(p);
    }

    /**
     * Adds policies from a file to the address book.
     * Policies are assigned unique ids upon insertion.
     */
    public void addPolicyFile(Path filePath) {
        List<UnassignedPolicy> unassignedPolicies = PolicyFileParser.readFile(filePath);
        for (UnassignedPolicy unassignedPolicy: unassignedPolicies) {
            PolicyId policyId = generateUniquePolicyId();
            policies.add(unassignedPolicy.assignId(policyId));
        }
    }

    /**
     * Generates a policy id not present in the address book.
     */
    public PolicyId generateUniquePolicyId() {
        PolicyId policyId;
        do {
            policyId = PolicyId.generate();
        } while (policies.containsId(policyId));
        return policyId;
    }

    /**
     * Replaces the given policy {@code target} in the list with {@code editedPolicy}.
     * {@code target} must exist in the address book.
     * The policy id of {@code editedPolicy} must not be the same as another existing policy in the address book.
     */
    public void setPolicy(Policy target, Policy editedPolicy) {
        requireNonNull(editedPolicy);

        policies.setPolicy(target, editedPolicy);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePolicy(Policy key) {
        policies.remove(key);
    }

    /**
     * Returns true if a contract with the same details as {@code contract} exists in the address book.
     */
    public boolean hasContract(Contract contract) {
        requireNonNull(contract);
        return contracts.contains(contract);
    }

    /**
     * Adds the given contract.
     * {@code contract} must not already exist in the address book.
     */
    public void addContract(Contract contract) {
        requireNonNull(contract);
        contracts.add(contract);
    }

    /**
     * Adds the given contract to the corresponding person in the address book.
     * The person must exist in the address book.
     */
    public void addContractToPerson(Contract contract) throws PersonNotFoundException {
        requireNonNull(contract);
        for (Person person : persons) {
            if (person.getNric().equals(contract.getNric())) {
                person.addContract(contract);
                return;
            }
        }
        throw new PersonNotFoundException();
    }

    /**
     * Adds the given contract to the corresponding policy in the address book.
     * The policy must exist in the address book.
     */
    public void addContractToPolicy(Contract contract) throws PolicyNotFoundException {
        requireNonNull(contract);
        for (Policy policy : policies) {
            if (policy.getId().equals(contract.getPId())) {
                policy.addContract(contract);
                return;
            }
        }
        throw new PolicyNotFoundException();
    }

    /**
     * Removes the given contract.
     * The contract must exist in the address book.
     */
    public void removeContract(Contract contract) {
        requireNonNull(contract);
        contracts.remove(contract);
    }

    /**
     * Removes the given contract from the corresponding person in the address book.
     */
    public void removeContractFromPerson(Contract contract) throws PersonNotFoundException {
        requireNonNull(contract);
        for (Person person : persons) {
            if (person.getNric().equals(contract.getNric())) {
                person.removeContract(contract);
                return;
            }
        }
        throw new PersonNotFoundException();
    }

    /**
     * Removes the given contract from the corresponding policy in the address book.
     */
    public void removeContractFromPolicy(Contract contract) throws PolicyNotFoundException {
        requireNonNull(contract);
        for (Policy policy : policies) {
            if (policy.getId().equals(contract.getPId())) {
                policy.removeContract(contract);
                return;
            }
        }
        throw new PolicyNotFoundException();
    }

    /**
     * Replaces the contents of the contract list with {@code contracts}.
     * {@code contracts} must not contain duplicate contracts.
     */
    public void setContracts(List<Contract> contracts) {
        this.contracts.setContracts(contracts);
    }

    /**
     * Returns true if a person has the given contract.
     */
    public boolean personHasContract(Contract contract, Person person) {
        requireNonNull(contract);
        requireNonNull(person);
        for (Person p : persons) {
            if (p.equals(person)) {
                return p.getContracts().contains(contract);
            }
        }
        return false;
    }

    /**
     * Returns true if a policy has the given contract.
     */
    public boolean policyHasContract(Contract contract, Policy policy) {
        requireNonNull(contract);
        requireNonNull(policy);
        for (Policy p : policies) {
            if (p.equals(policy)) {
                return p.getContracts().contains(contract);
            }
        }
        return false;
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("policies", policies)
                .add("contracts", contracts)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Policy> getPolicyList() {
        return policies.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Contract> getContractList() {
        return contracts.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons);
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
