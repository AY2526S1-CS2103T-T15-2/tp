package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Nric;
import seedu.address.model.contact.UniqueContactList;
import seedu.address.model.contact.exceptions.ContactNotFoundException;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.UniqueContractList;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.UniquePolicyList;
import seedu.address.model.policy.exceptions.PolicyNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameContact, .isSameContract, .isSamePolicy, .isSameAppointment comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueContactList contacts;
    private final UniqueContractList contracts;
    private final UniquePolicyList policies;
    private final UniqueAppointmentList appointments;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        contacts = new UniqueContactList();
        contracts = new UniqueContractList();
        policies = new UniquePolicyList();
        appointments = new UniqueAppointmentList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Contacts in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the contact list with {@code contacts}.
     * {@code contacts} must not contain duplicate contacts.
     */
    public void setContacts(List<Contact> contacts) {
        this.contacts.setContacts(contacts);
    }

    /**
     * Replaces the contents of the policy list with {@code policies}.
     * {@code policies} must not contain duplicate policies.
     */
    public void setPolicies(List<Policy> policies) {
        this.policies.setPolicies(policies);
    }

    /**
     * Replaces the contents of the appointment list with {@code appointments}.
     * {@code appointments} must not contain duplicate appointments.
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments.setAppointments(appointments);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setContacts(newData.getContactList());
        setPolicies(newData.getPolicyList());
        setContracts(newData.getContractList());
        setAppointments(newData.getAppointmentList());
    }

    //// contact-level operations

    /**
     * Returns true if a contact with the same identity as {@code contact} exists in the address book.
     */
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return contacts.contains(contact);
    }

    /**
     * Returns true if a contact with the same NRIC as {@code nric} exists in the address book.
     */
    public boolean hasContact(Nric nric) {
        return contacts.contains(nric);
    }

    //@@author Joshua-Seah
    public Name getName(Nric nric) {
        requireNonNull(nric);
        for (Contact contact : contacts) {
            if (contact.getNric().equals(nric)) {
                return contact.getName();
            }
        }
        return null;
    }
    //@@author

    /**
     * Adds a contact to the address book.
     * The contact must not already exist in the address book.
     */
    public void addContact(Contact p) {
        contacts.add(p);
    }

    /**
     * Replaces the given contact {@code target} in the list with {@code editedContact}.
     * {@code target} must exist in the address book.
     * The contact identity of {@code editedContact} must not be the same as another
     * existing contact in the address book.
     */
    public void setContact(Contact target, Contact editedContact) {
        requireNonNull(editedContact);

        contacts.setContact(target, editedContact);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeContact(Contact key) {
        contacts.remove(key);
    }

    //// policy-level operations

    /**
     * Returns true if a policy with the same id as {@code policy} exists in the address book.
     */
    public boolean hasSamePolicyId(Policy policy) {
        requireNonNull(policy);
        return policies.containsSameId(policy);
    }

    /**
     * Returns true if a policy with the same fields as {@code policy} exists in the address book.
     */
    public boolean hasSamePolicyFields(Policy policy) {
        requireNonNull(policy);
        return policies.containsSamePolicy(policy);
    }

    /**
     * Returns true if the address book contains a policy with {@code policyId}
     * @param policyId identifier for policy in addressbook
     * @return true if policy with policyId exists in address book
     */
    public boolean hasPolicy(PolicyId policyId) {
        requireNonNull(policyId);
        return policies.hasPolicy(policyId);
    }

    /**
     * Returns a Policy from the addressbook with the policyId
     * @param policyId identifier for policy in addressbook
     * @return Policy from the addressbook
     */
    public Policy getPolicy(PolicyId policyId) {
        requireNonNull(policyId);
        for (Policy policy : policies) {
            if (policy.getId().equals(policyId)) {
                return policy;
            }
        }
        return null;
    }

    /**
     * Adds a policy to the address book.
     * The policy must not already exist in the address book.
     */
    public void addPolicy(Policy p) {
        policies.add(p);
    }

    /**
     * Adds all policies from the list to the address book.
     * Every policy must not already exist in the address book or have duplicates in the list.
     */
    public void addPolicies(List<Policy> policyList) {
        policies.addAll(policyList);
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
     * Generates a list of pairwise unique policy ids that are not present in the address book.
     * @param length Must be a nonnegative integer.
     */
    public List<PolicyId> generateUniquePolicyIds(int length) {
        assert length >= 0;
        ArrayList<PolicyId> policyIds = new ArrayList<>(length);

        while (policyIds.size() < length) {
            PolicyId policyId = PolicyId.generate();
            if (!policies.containsId(policyId) && !policyIds.contains(policyId)) {
                policyIds.add(policyId);
            }
        }

        return policyIds;
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
     * Returns true if an appointment with the same id as {@code appointment} exists in the address book.
     */
    public boolean hasAppointment(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.contains(appointment);
    }

    /**
     * Returns true if the address book contains an appointment with {@code appointmentId}
     * @param appointmentId identifier for appointment in addressbook
     * @return true if appointment with appointmentId exists in address book
     */
    public boolean hasAppointment(AppointmentId appointmentId) {
        requireNonNull(appointmentId);
        return appointments.hasAppointment(appointmentId);
    }

    /**
     * Adds an appointment to the address book.
     * The appointment must not already exist in the address book.
     */
    public void addAppointment(Appointment a) {
        appointments.add(a);
    }


    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the address book.
     * The appointment id of {@code editedAppointment} must not be the same as another existing appointment
     * in the address book.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireNonNull(editedAppointment);

        appointments.setAppointment(target, editedAppointment);
    }

    /**
     * Returns an Appointment from the addressbook with the appointmentId
     * @param appointmentId identifier for appointment in addressbook
     * @return Appointment from the addressbook
     */
    public Appointment getAppointment(AppointmentId appointmentId) {
        requireNonNull(appointmentId);
        for (Appointment appointment : appointments) {
            if (appointment.getAId().equals(appointmentId)) {
                return appointment;
            }
        }
        return null;
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeAppointment(Appointment key) {
        appointments.remove(key);
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

    //@@author Joshua-Seah
    /**
     * Adds the given contract to the corresponding contact in the address book.
     * The contact must exist in the address book.
     */
    public void addContractToContact(Contract contract) throws ContactNotFoundException {
        requireNonNull(contract);
        for (Contact contact : contacts) {
            if (contact.getNric().equals(contract.getNric())) {
                contact.addContract(contract);
                setContact(contact, contact);
                return;
            }
        }
        throw new ContactNotFoundException();
    }
    //@@author

    /**
     * Adds the given contract to the corresponding policy in the address book.
     * The policy must exist in the address book.
     */
    public void addContractToPolicy(Contract contract) throws PolicyNotFoundException {
        requireNonNull(contract);
        for (Policy policy : policies) {
            if (policy.getId().equals(contract.getPId())) {
                policy.addContract(contract);
                setPolicy(policy, policy);
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

    //@@author Joshua-Seah
    /**
     * Removes the given contract from the corresponding contact in the address book.
     */
    public void removeContractFromContact(Contract contract) throws ContactNotFoundException {
        requireNonNull(contract);
        for (Contact contact : contacts) {
            if (contact.getNric().equals(contract.getNric())) {
                contact.removeContract(contract);
                return;
            }
        }
        throw new ContactNotFoundException();
    }
    //@@author

    /**
     * Removes the given contract from the corresponding policy in the address book.
     */
    public void removeContractFromPolicy(Contract contract) throws PolicyNotFoundException {
        requireNonNull(contract);
        for (Policy policy : policies) {
            if (policy.getId().equals(contract.getPId())) {
                policy.removeContract(contract);
                //update policyview method
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
     * Replaces the given contract {@code target} in the list with {@code editedContract}.
     * {@code target} must exist in the address book.
     * The contract identity of {@code editedContract} must not be the same as another existing contract
     * in the address book.
     */
    public void setContract(Contract target, Contract editedContract) {
        requireNonNull(editedContract);
        contracts.setContract(target, editedContract);
    }

    //@@author Joshua-Seah
    /**
     * Returns true if a contact has the given contract.
     */
    public boolean contactHasContract(Contract contract, Contact contact) {
        requireNonNull(contract);
        requireNonNull(contact);
        for (Contact p : contacts) {
            if (p.equals(contact)) {
                return p.getContracts().contains(contract);
            }
        }
        return false;
    }
    //@@author

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
                .add("contacts", contacts)
                .add("policies", policies)
                .add("contracts", contracts)
                .add("appointments", appointments)
                .toString();
    }

    @Override
    public ObservableList<Contact> getContactList() {
        return contacts.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Policy> getPolicyList() {
        return policies.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asUnmodifiableObservableList();
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
        return contacts.equals(otherAddressBook.contacts);
    }

    @Override
    public int hashCode() {
        return contacts.hashCode();
    }
}
