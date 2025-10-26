package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.ALICE;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.contract.Contract;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.PolicyName;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalData;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
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
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson((Person) null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void getPolicyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPolicyList().remove(0));
    }

    @Test
    public void setPolicy_hasPolicyInAddressBook_returnsTrue() {
        assertThrows(NullPointerException.class, () -> addressBook.setPolicy(null, null));
        Policy policy = TypicalData.LIFE;
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
    public void addContract_hasContractInAddressBook_returnsTrue() {
        Contract contract = TypicalData.CONTRACT_A;
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
        String expected = AddressBook.class.getCanonicalName() + "{persons=" + addressBook.getPersonList()
                + ", policies=" + addressBook.getPolicyList() + ", contracts=" + addressBook.getContractList()
                + ", appointments=" + addressBook.getAppointmentList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBook();
        AddressBook differentAddressBook = new AddressBook();
        differentAddressBook.addPerson(ALICE);

        // same values -> returns true
        AddressBook addressBookCopy = new AddressBook(addressBook);
        assertTrue(addressBook.equals(addressBookCopy));

        // same object -> returns true
        assertTrue(addressBook.equals(addressBook));

        // null -> returns false
        assertFalse(addressBook.equals(null));

        // different type -> returns false
        assertFalse(addressBook.equals(5));

        // different person -> returns false
        assertFalse(addressBook.equals(differentAddressBook));
    }

    @Test
    public void hashCode_sameObject_sameHashcode() {
        AddressBook addressBook = new AddressBook();
        assertEquals(addressBook.hashCode(), addressBook.hashCode());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Policy> policies = FXCollections.observableArrayList();
        private final ObservableList<Contract> contracts = FXCollections.observableArrayList();
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
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
