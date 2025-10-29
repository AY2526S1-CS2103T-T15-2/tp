package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.getAlice;
import static seedu.address.testutil.TypicalData.getAppointmentA;
import static seedu.address.testutil.TypicalData.getAppointmentE;
import static seedu.address.testutil.TypicalData.getLife;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.exceptions.DuplicateContactException;
import seedu.address.model.contract.Contract;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.PolicyName;
import seedu.address.testutil.ContactBuilder;
import seedu.address.testutil.TypicalData;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getContactList());
        assertEquals(Collections.emptyList(), addressBook.getPolicyList());
        assertEquals(Collections.emptyList(), addressBook.getContractList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicateContacts_throwsDuplicateContactException() {
        // Two contacts with the same identity fields
        Contact editedAlice = new ContactBuilder(getAlice()).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Contact> newContacts = Arrays.asList(getAlice(), editedAlice);
        AddressBookStub newData = new AddressBookStub(newContacts);

        assertThrows(DuplicateContactException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasContact((Contact) null));
    }

    @Test
    public void hasContact_contactNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasContact(getAlice()));
    }

    @Test
    public void hasContact_contactInAddressBook_returnsTrue() {
        addressBook.addContact(getAlice());
        assertTrue(addressBook.hasContact(getAlice()));
    }

    @Test
    public void hasContact_contactWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addContact(getAlice());
        Contact editedAlice = new ContactBuilder(getAlice()).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasContact(editedAlice));
    }

    @Test
    public void getContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getContactList().remove(0));
    }

    @Test
    public void getPolicyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPolicyList().remove(0));
    }

    @Test
    public void setPolicy_hasPolicyInAddressBook_returnsTrue() {
        assertThrows(NullPointerException.class, () -> addressBook.setPolicy(null, null));
        Policy policy = TypicalData.getLife();
        addressBook.addPolicy(policy);
        Policy editedPolicy = new Policy(new PolicyName("Life Plus"), new PolicyDetails("Covers life and more"),
                new PolicyId("123456"));
        assertThrows(NullPointerException.class, () -> addressBook.setPolicy(policy, null));
        assertThrows(NullPointerException.class, () -> addressBook.setPolicy(null, editedPolicy));
        addressBook.setPolicy(policy, editedPolicy);
        assertTrue(addressBook.hasSamePolicyId(editedPolicy));
        assertFalse(addressBook.hasSamePolicyId(policy));
    }

    @Test
    public void hasPolicy_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPolicy((PolicyId) null));
    }

    @Test
    public void hasPolicy_policyNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPolicy(new PolicyId("Lkf0sw")));
    }

    @Test
    public void hasPolicy_policyInAddressBook_returnsTrue() {
        addressBook.addPolicy(getLife());
        assertTrue(addressBook.hasPolicy(getLife().getId()));
    }

    @Test
    public void hasAppointment_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasAppointment((AppointmentId) null));
    }

    @Test
    public void hasAppointment_appointmentNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasAppointment(new AppointmentId("Lkf0sw")));
    }

    @Test
    public void hasAppointment_appointmentInAddressBook_returnsTrue() {
        addressBook.addAppointment(getAppointmentA());
        assertTrue(addressBook.hasAppointment(getAppointmentA().getAId()));
    }

    @Test
    public void getAppointment_appointmentInAddressBook_returnsTrue() {
        addressBook.addAppointment(getAppointmentA());
        assertEquals(addressBook.getAppointment(getAppointmentA().getAId()), getAppointmentA());
    }

    @Test
    public void getAppointment_appointmentNotInAddressBook_returnsNull() {
        assertEquals(addressBook.getAppointment(getAppointmentE().getAId()), null);
    }

    @Test
    public void addContract_hasContractInAddressBook_returnsTrue() {
        Contract contract = TypicalData.getContractA();
        addressBook.addContract(contract);
        assertTrue(addressBook.getContractList().contains(contract));
        addressBook.removeContract(contract);
        assertFalse(addressBook.getContractList().contains(contract));
    }

    @Test
    public void contractMethods_nullContract_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.addContract(null));
        assertThrows(NullPointerException.class, () -> addressBook.removeContract(null));
        assertThrows(NullPointerException.class, () -> addressBook.hasContract(null));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName() + "{contacts=" + addressBook.getContactList()
                + ", policies=" + addressBook.getPolicyList() + ", contracts=" + addressBook.getContractList()
                + ", appointments=" + addressBook.getAppointmentList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBook();
        AddressBook differentAddressBook = new AddressBook();
        differentAddressBook.addContact(getAlice());

        // same values -> returns true
        AddressBook addressBookCopy = new AddressBook(addressBook);
        assertTrue(addressBook.equals(addressBookCopy));

        // same object -> returns true
        assertTrue(addressBook.equals(addressBook));

        // null -> returns false
        assertFalse(addressBook.equals(null));

        // different type -> returns false
        assertFalse(addressBook.equals(5));

        // different contact -> returns false
        assertFalse(addressBook.equals(differentAddressBook));
    }

    @Test
    public void hashCode_sameObject_sameHashcode() {
        AddressBook addressBook = new AddressBook();
        assertEquals(addressBook.hashCode(), addressBook.hashCode());
    }

    /**
     * A stub ReadOnlyAddressBook whose contacts list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
        private final ObservableList<Policy> policies = FXCollections.observableArrayList();
        private final ObservableList<Contract> contracts = FXCollections.observableArrayList();
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        AddressBookStub(Collection<Contact> contacts) {
            this.contacts.setAll(contacts);
        }

        @Override
        public ObservableList<Contact> getContactList() {
            return contacts;
        }

        @Override
        public ObservableList<Policy> getPolicyList() {
            return policies;
        }

        @Override
        public ObservableList<Contract> getContractList() {
            return contracts;
        }

        @Override
        public ObservableList<Appointment> getAppointmentList() {
            return appointments;
        }
    }

}
