package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.contract.Contract;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyId;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;
    Predicate<Policy> PREDICATE_SHOW_ALL_POLICIES = unused -> true;
    Predicate<Contract> PREDICATE_SHOW_ALL_CONTRACTS = unused -> true;
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Returns true if a person with the same NRIC as {@code nric} exists in the address book.
     */
    boolean hasPerson(Nric nric);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered and sorted person list */
    ObservableList<Person> getSortedPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Updates the comparator of the sorted list to sort by the given {@code comparator}.
     * @param comparator {@code Comparator<Person>} or {@code null} for no sorting.
     */
    void sortPersons(Comparator<Person> comparator);

    /**
     * Returns true if a policy with the same id as {@code policy} exists in the address book.
     */
    boolean hasSamePolicyId(Policy policy);

    /**
     * Returns true if a policy with the same id as {@code policy} exists in the address book.
     */
    boolean hasSamePolicyFields(Policy policy);

    /**
     * Deletes the given policy.
     * The policy must exist in the address book.
     */
    void removePolicy(Policy target);

    /**
     * Adds the given policy.
     * {@code policy} must not already exist in the address book.
     */
    void addPolicy(Policy policy);

    /**
     * Adds all policies from the list.
     * Every {@code policy} must not already exist in the address book or have duplicates in the list.
     */
    void addPolicies(List<Policy> policies);

    /**
     * Replaces the given policy {@code target} with {@code editedPolicy}.
     * {@code target} must exist in the address book.
     * The policy id of {@code editedPolicy} must not be the same as another existing policy in the address book.
     */
    void setPolicy(Policy target, Policy editedPolicy);

    ObservableList<Appointment> getSortedAppointmentList();

    /** Returns an unmodifiable view of the filtered policy list */
    ObservableList<Policy> getFilteredPolicyList();

    /**
     * Updates the filter of the filtered policy list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPolicyList(Predicate<Policy> predicate);

    /**
     * Returns true if a contract with the same policy id and NRIC as {@code contract} exists in the address book.
     */
    boolean hasContract(Contract contract);

    /**
     * Adds the given contract.
     * {@code contract} must not already exist in the address book.
     */
    void addContract(Contract contract);

    /**
     * Links the given contract to the corresponding person in the address book.
     * The person must exist in the address book.
     */
    void addContractToPerson(Contract contract);

    /**
     * Links the given contract to the corresponding policy in the address book.
     * The policy must exist in the address book.
     */
    void addContractToPolicy(Contract contract);

    /** Returns an unmodifiable view of the filtered contract list */
    ObservableList<Contract> getFilteredContractList();

    /**
     * Updates the filter of the filtered contract list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredContractList(Predicate<Contract> predicate);

    /** Returns an unmodifiable view of the sorted contract list */
    ObservableList<Contract> getSortedContractList();

    /**
     * Removes the given contract.
     * The contract must exist in the address book.
     */
    void removeContract(Contract contract);

    /**
     * Updates the comparator of the sorted list to sort by the given {@code comparator}.
     * @param comparator {@code Comparator<Contract>} or {@code null} for no sorting.
     */
    void sortContracts(Comparator<Contract> comparator);

    /**
     * Returns true if a person has the given contract.
     * The person must exist in the address book.
     */
    boolean personHasContract(Contract contract, Person person);

    /**
     * Returns true if a policy has the given contract.
     * The policy must exist in the address book.
     */
    boolean policyHasContract(Contract contract, Policy policy);

    /**
     * Removes the given contract from the corresponding person in the address book.
     */
    void removeContractFromPerson(Contract contract);

    /**
     * Removes the given contract from the corresponding policy in the address book.
     */
    void removeContractFromPolicy(Contract contract);

    /**
     * Generates a policy id not present in the current address book.
     */
    PolicyId generateUniquePolicyId();

    /**
     * Generates a list of pairwise unique policy ids that are not present in the current address book.
     */
    List<PolicyId> generateUniquePolicyIds(int length);

    /**
     * Returns true if an Appointment with the same identity as {@code appointment} exists in the address book.
     */
    boolean hasAppointment(Appointment appointment);

    /**
     * Removes the given appointment.
     * The appointment must exist in the address book.
     */
    void removeAppointment(Appointment target);

    /**
     * Adds the given appointment.
     * {@code appointment} must not already exist in the address book.
     */
    void addAppointment(Appointment appointment);

    /**
     * Replaces the given contract {@code target} with {@code editedContract}.
     * {@code target} must exist in the address book.
     * The contract identity of {@code editedContract} must not be the same as another existing contract
     * in the address book.
     */
    void setContract(Contract target, Contract editedContract);

    /**
     * Replaces the given appointment {@code target} with {@code editedAppointment}.
     * {@code target} must exist in the address book.
     * The appointment identity of {@code editedAppointment} must not be the same as another existing appointment
     * in the address book.
     */
    void setAppointment(Appointment target, Appointment editedAppointment);

    /** Returns an unmodifiable view of the filtered appointment list */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);

    /**
     * Updates the comparator of the sorted list to sort by the given {@code comparator}.
     * @param comparator {@code Comparator<Appointment>} or {@code null} for no sorting.
     */
    void sortAppointments(Comparator<Appointment> comparator);

}
