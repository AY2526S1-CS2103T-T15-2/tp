package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.contract.Contract;
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

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Returns true if a policy with the same id as {@code policy} exists in the address book.
     */
    boolean hasPolicy(Policy id);

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
     * Adds policies from the given file.
     * Every {@code policy} must not already exist in the address book.
     */
    void addPolicyFile(Path filePath);

    /**
     * Replaces the given policy {@code target} with {@code editedPolicy}.
     * {@code target} must exist in the address book.
     * The policy id of {@code editedPolicy} must not be the same as another existing policy in the address book.
     */
    void setPolicy(Policy target, Policy editedPolicy);

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

    /** Returns an unmodifiable view of the filtered contract list */
    ObservableList<Contract> getFilteredContractList();

    /**
     * Updates the filter of the filtered contract list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredContractList(Predicate<Contract> predicate);

    /**
     * Removes the given contract.
     * The contract must exist in the address book.
     */
    void removeContract(Contract contract);

    /**
     * Generates a policy id not present in the currentaddress book.
     */
    PolicyId generateUniquePolicyId();
}
