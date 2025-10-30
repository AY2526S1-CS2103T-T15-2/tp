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
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Nric;
import seedu.address.model.contract.Contract;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyId;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final SortedList<Contact> sortedContacts;
    private final SortedList<Contract> sortedContracts;
    private final SortedList<Appointment> sortedAppointments;
    private final FilteredList<Policy> filteredPolicies;
    private final FilteredList<Contract> filteredContracts;
    private final FilteredList<Contact> filteredContacts;
    private final FilteredList<Appointment> filteredAppointments;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredContacts = new FilteredList<>(this.addressBook.getContactList());
        filteredAppointments = new FilteredList<>(this.addressBook.getAppointmentList());
        filteredContracts = new FilteredList<>(this.addressBook.getContractList());
        sortedContacts = new SortedList<>(filteredContacts);
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
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return addressBook.hasContact(contact);
    }

    @Override
    public boolean hasContact(Nric nric) {
        requireNonNull(nric);
        return addressBook.hasContact(nric);
    }

    @Override
    public Name getName(Nric nric) {
        requireNonNull(nric);
        return addressBook.getName(nric);
    }

    @Override
    public boolean hasSamePolicyId(Policy policy) {
        requireNonNull(policy);
        return addressBook.hasSamePolicyId(policy);
    }

    @Override
    public boolean hasPolicy(PolicyId policyId) {
        requireNonNull(policyId);
        return addressBook.hasPolicy(policyId);
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
    public boolean hasAppointment(AppointmentId appointmentId) {
        requireNonNull(appointmentId);
        return addressBook.hasAppointment(appointmentId);
    }

    @Override
    public void deleteContact(Contact target) {
        addressBook.removeContact(target);
    }

    @Override
    public void removePolicy(Policy policy) {
        addressBook.removePolicy(policy);
    }

    @Override
    public Policy getPolicy(PolicyId policyId) {
        return addressBook.getPolicy(policyId);
    }

    @Override
    public void removeAppointment(Appointment appointment) {
        addressBook.removeAppointment(appointment);
    }

    @Override
    public void addContact(Contact contact) {
        addressBook.addContact(contact);
        updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
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
    public void setContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);

        addressBook.setContact(target, editedContact);
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
    public Appointment getAppointment(AppointmentId appointmentId) {
        return addressBook.getAppointment(appointmentId);
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
    public void addContractToContact(Contract contract) {
        addressBook.addContractToContact(contract);
    }

    @Override
    public void addContractToPolicy(Contract contract) {
        addressBook.addContractToPolicy(contract);
    }

    @Override
    public boolean contactHasContract(Contract contract, Contact contact) {
        requireAllNonNull(contract, contact);
        return addressBook.contactHasContract(contract, contact);
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
    public void removeContractFromContact(Contract contract) {
        requireAllNonNull(contract);
        addressBook.removeContractFromContact(contract);
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
     * Returns an unmodifiable view of the list of {@code Contact} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return filteredContacts;
    }

    /**
     * Returns an unmodifiable view of the sorted list of {@code Contact} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Contact> getSortedContactList() {
        return sortedContacts;
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
    public void updateFilteredContactList(Predicate<Contact> predicate) {
        requireNonNull(predicate);
        filteredContacts.setPredicate(predicate);
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
    public void sortContacts(Comparator<Contact> comparator) {
        sortedContacts.setComparator(comparator);
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
    public ObservableList<Contract> getUniqueContractList() {
        return addressBook.getContractList();
    }

    @Override
    public ObservableList<Contact> getUniqueContactList() {
        return addressBook.getContactList();
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
                && filteredContacts.equals(otherModelManager.filteredContacts)
                && filteredPolicies.equals(otherModelManager.filteredPolicies)
                && filteredContracts.equals(otherModelManager.filteredContracts)
                && filteredAppointments.equals(otherModelManager.filteredAppointments);
    }
}
