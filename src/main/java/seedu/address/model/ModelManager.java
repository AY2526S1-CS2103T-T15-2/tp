package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.contract.Contract;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyId;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final SortedList<Person> sortedPersons;
    private final SortedList<Contract> sortedContracts;
    private final SortedList<Appointment> sortedAppointments;
    private final FilteredList<Policy> filteredPolicies;
    private final FilteredList<Contract> filteredContracts;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Appointment> filteredAppointments;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredAppointments = new FilteredList<>(this.addressBook.getAppointmentList());
        filteredContracts = new FilteredList<>(this.addressBook.getContractList());
        sortedPersons = new SortedList<>(filteredPersons);
        sortedContracts = new SortedList<>(filteredContracts);
        sortedAppointments = new SortedList<>(filteredAppointments);
        filteredPolicies = new FilteredList<>(this.addressBook.getPolicyList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public boolean hasPerson(Nric nric) {
        requireNonNull(nric);
        return addressBook.hasPerson(nric);
    }
    @Override
    public boolean hasSamePolicyId(Policy policy) {
        requireNonNull(policy);
        return addressBook.hasSamePolicyId(policy);
    }

    @Override
    public boolean hasSamePolicyFields(Policy policy) {
        requireNonNull(policy);
        return addressBook.hasSamePolicyFields(policy);
    }

    @Override
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return addressBook.hasAppointment(appointment);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void removePolicy(Policy policy) {
        addressBook.removePolicy(policy);
    }

    @Override
    public void removeAppointment(Appointment appointment) {
        addressBook.removeAppointment(appointment);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void addPolicy(Policy policy) {
        addressBook.addPolicy(policy);
        updateFilteredPolicyList(PREDICATE_SHOW_ALL_POLICIES);
    }

    @Override
    public void addPolicies(List<Policy> policies) {
        addressBook.addPolicies(policies);
    }

    @Override
    public void addAppointment(Appointment appointment) {
        addressBook.addAppointment(appointment);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public void setPolicy(Policy target, Policy editedPolicy) {
        requireAllNonNull(target, editedPolicy);

        addressBook.setPolicy(target, editedPolicy);
    }

    @Override
    public void setContract(Contract target, Contract editedContract) {
        requireAllNonNull(target, editedContract);

        addressBook.setContract(target, editedContract);
    }

    @Override
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireAllNonNull(target, editedAppointment);

        addressBook.setAppointment(target, editedAppointment);
    }

    @Override
    public boolean hasContract(Contract contract) {
        requireNonNull(contract);
        return addressBook.hasContract(contract);
    }

    @Override
    public void addContract(Contract contract) {
        addressBook.addContract(contract);
    }

    @Override
    public void addContractToPerson(Contract contract) {
        addressBook.addContractToPerson(contract);
    }

    @Override
    public void addContractToPolicy(Contract contract) {
        addressBook.addContractToPolicy(contract);
    }

    @Override
    public boolean personHasContract(Contract contract, Person person) {
        requireAllNonNull(contract, person);
        return addressBook.personHasContract(contract, person);
    }

    @Override
    public boolean policyHasContract(Contract contract, Policy policy) {
        requireAllNonNull(contract, policy);
        return addressBook.policyHasContract(contract, policy);
    }

    @Override
    public void removeContract(Contract contract) {
        addressBook.removeContract(contract);
    }

    @Override
    public void removeContractFromPerson(Contract contract) {
        requireAllNonNull(contract);
        addressBook.removeContractFromPerson(contract);
    }

    @Override
    public void removeContractFromPolicy(Contract contract) {
        requireAllNonNull(contract);
        addressBook.removeContractFromPolicy(contract);
    }

    @Override
    public PolicyId generateUniquePolicyId() {
        return addressBook.generateUniquePolicyId();
    }

    @Override
    public List<PolicyId> generateUniquePolicyIds(int length) {
        return addressBook.generateUniquePolicyIds(length);
    }

    //=========== Filtered List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    /**
     * Returns an unmodifiable view of the sorted list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getSortedPersonList() {
        return sortedPersons;
    }

    @Override
    public ObservableList<Contract> getSortedContractList() {
        return sortedContracts;
    }
    /**
     * Returns an unmodifiable view of the sorted list of {@code Appointment} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Appointment> getSortedAppointmentList() {
        return sortedAppointments;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Policy} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Policy> getFilteredPolicyList() {
        return filteredPolicies;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Contract} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Contract> getFilteredContractList() {
        return filteredContracts;
    }

    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return filteredAppointments;
    }


    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredPolicyList(Predicate<Policy> predicate) {
        requireNonNull(predicate);
        filteredPolicies.setPredicate(predicate);
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointments.setPredicate(predicate);
    }

    @Override
    public void updateFilteredContractList(Predicate<Contract> predicate) {
        requireNonNull(predicate);
        filteredContracts.setPredicate(predicate);
    }

    @Override
    public void sortPersons(Comparator<Person> comparator) {
        sortedPersons.setComparator(comparator);
    }

    @Override
    public void sortContracts(Comparator<Contract> comparator) {
        sortedContracts.setComparator(comparator);
    }

    @Override
    public void sortAppointments(Comparator<Appointment> comparator) {
        sortedAppointments.setComparator(comparator);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons)
                && filteredPolicies.equals(otherModelManager.filteredPolicies)
                && filteredContracts.equals(otherModelManager.filteredContracts)
                && filteredAppointments.equals(otherModelManager.filteredAppointments);
    }
}
