package seedu.address.model;

import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.contract.Contract;
import seedu.address.model.person.Person;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyId;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getAddressBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasPerson(Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deletePerson(Person target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Person> getSortedPersonList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortPersons(Comparator<Person> comparator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasSamePolicyId(Policy id) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasSamePolicyFields(Policy policy) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removePolicy(Policy target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPolicy(Policy policy) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addPolicies(List<Policy> policies) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setPolicy(Policy target, Policy editedPolicy) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Policy> getFilteredPolicyList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredPolicyList(Predicate<Policy> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    public boolean hasContract(Contract contract) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addContract(Contract contract) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addContractToPerson(Contract contract) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addContractToPolicy(Contract contract) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Contract> getFilteredContractList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public PolicyId generateUniquePolicyId() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public List<PolicyId> generateUniquePolicyIds(int length) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasAppointment(Appointment appointment) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeAppointment(Appointment target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addAppointment(Appointment appointment) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void sortAppointments(Comparator<Appointment> comparator) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ObservableList<Appointment> getSortedAppointmentList() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void updateFilteredContractList(Predicate<Contract> predicate) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeContract(Contract contract) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean personHasContract(Contract contract, Person person) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean policyHasContract(Contract contract, Policy policy) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeContractFromPerson(Contract contract) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void removeContractFromPolicy(Contract contract) {
        throw new AssertionError("This method should not be called.");
    }
}
