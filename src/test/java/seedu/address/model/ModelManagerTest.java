package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HOME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.getAlice;
import static seedu.address.testutil.TypicalData.getAppointmentA;
import static seedu.address.testutil.TypicalData.getAppointmentB;
import static seedu.address.testutil.TypicalData.getBenson;
import static seedu.address.testutil.TypicalData.getCarl;
import static seedu.address.testutil.TypicalData.getContractA;
import static seedu.address.testutil.TypicalData.getContractB;
import static seedu.address.testutil.TypicalData.getContractD;
import static seedu.address.testutil.TypicalData.getHealthB;
import static seedu.address.testutil.TypicalData.getHome;
import static seedu.address.testutil.TypicalData.getLife;
import static seedu.address.testutil.TypicalData.getTypicalAlice;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.ContactComparatorType;
import seedu.address.model.contact.NricContainsKeywordsPredicate;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractComparatorType;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PolicyBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AddressBook(), new AddressBook(modelManager.getAddressBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasContact_nullContact_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasContact((Contact) null));
    }

    @Test
    public void hasContact_contactNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasContact(getAlice()));
    }

    @Test
    public void hasContact_contactInAddressBook_returnsTrue() {
        modelManager.addContact(getAlice());
        assertTrue(modelManager.hasContact(getAlice()));
    }

    @Test
    public void hasSamePolicyId_policyIdNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasSamePolicyId(getHome()));
    }

    @Test
    public void hasSamePolicyId_policyIdInAddressBook_returnsTrue() {
        modelManager.addPolicy(new PolicyBuilder(getHealthB()).withId(VALID_POLICY_ID_HOME).build());
        assertTrue(modelManager.hasSamePolicyId(getHome()));
    }

    @Test
    public void hasSamePolicyFields_policyNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasSamePolicyFields(getHome()));
    }

    @Test
    public void hasSamePolicyFields_policyInAddressBook_returnsTrue() {
        modelManager.addPolicy(new PolicyBuilder(getHome()).withId(VALID_POLICY_ID_HEALTH_B).build());
        assertTrue(modelManager.hasSamePolicyFields(getHome()));
    }

    @Test
    public void removePolicy_policyInAddressBook_returnsFalse() {
        modelManager.addPolicy(getLife());
        modelManager.removePolicy(getLife());
        assertFalse(modelManager.hasSamePolicyId(getLife()));
    }

    @Test
    public void setPolicy_policyInAddressBook_returnsTrue() {
        modelManager.addPolicy(getLife());
        modelManager.setPolicy(getLife(), getLife());
        assertTrue(modelManager.hasSamePolicyId(getLife()));
    }

    @Test
    public void addContract_policyInAddressBook_returnsTrue() {
        modelManager.addPolicy(getLife());
        modelManager.addContract(getContractA());
        assertTrue(modelManager.hasContract(getContractA()));
    }

    @Test
    public void addContractToContact_contactInAddressBook_returnsTrue() {
        modelManager.addContact(getAlice());
        modelManager.addPolicy(getLife());
        modelManager.addContract(getContractA());
        modelManager.addContractToContact(getContractA());
        assertTrue(modelManager.contactHasContract(getContractA(), getTypicalAlice()));
    }

    @Test
    public void addContractToPolicy_policyInAddressBook_returnsTrue() {
        modelManager.addContact(getAlice());
        modelManager.addPolicy(getLife());
        modelManager.addContract(getContractA());
        modelManager.addContractToPolicy(getContractA());
        assertTrue(modelManager.policyHasContract(getContractA(), getLife()));
    }

    @Test
    public void removeContract_policyInAddressBook_returnsFalse() {
        modelManager.addContract(getContractA());
        modelManager.removeContract(getContractA());
        assertFalse(modelManager.hasContract(getContractA()));
    }

    @Test
    public void hasAppointment_appointmentNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasAppointment(getAppointmentA()));
    }

    @Test
    public void hasAppointment_appointmentInAddressBook_returnsTrue() {
        modelManager.addAppointment(getAppointmentA());
        assertTrue(modelManager.hasAppointment(getAppointmentA()));
    }

    @Test
    public void removeAppointment_appointmentInAddressBook_returnsFalse() {
        modelManager.addAppointment(getAppointmentA());
        modelManager.removeAppointment(getAppointmentA());
        assertFalse(modelManager.hasAppointment(getAppointmentA()));
    }

    @Test
    public void setAppointment_appointmentInAddressBook_returnsTrue() {
        modelManager.addAppointment(getAppointmentA());
        modelManager.setAppointment(getAppointmentA(), getAppointmentA());
        assertTrue(modelManager.hasAppointment(getAppointmentA()));
    }

    @Test
    public void hasContract_policyNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasContract(getContractD()));
    }

    @Test
    public void getFilteredContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredContactList().remove(0));
    }

    @Test
    public void getSortedContactList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredContactList().remove(0));
    }

    @Test
    public void getFilteredPolicyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPolicyList().remove(0));
    }

    @Test
    public void getFilteredContractList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredContractList().remove(0));
    }

    @Test
    public void getFilteredAppointmentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                modelManager.getFilteredAppointmentList().remove(0));
    }

    @Test
    public void updateFilteredContractList_modifyList_throwsUnsupportedOperationException() {
        modelManager.addContract(getContractA());
        modelManager.addContract(getContractB());
        modelManager.updateFilteredContractList(x -> x.equals(getContractA()));
        assertEquals(1, modelManager.getFilteredContractList().size());
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredContractList().remove(0));
    }

    @Test
    public void updatedFilteredContractList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredContractList(null));
    }

    @Test
    public void sortContacts() {
        ObservableList<Contact> contacts = FXCollections.observableArrayList(getBenson(), getAlice(), getCarl());
        contacts.forEach(modelManager::addContact);

        // Make sure the internal filtered contact list has the correct contacts
        assertEquals(contacts, modelManager.getFilteredContactList());

        // Sort alphabetically
        ObservableList<Contact> expectedContacts =
                FXCollections.observableArrayList(getAlice(), getBenson(), getCarl());
        modelManager.sortContacts(ContactComparatorType.ALPHABETICAL.comparator);
        assertEquals(expectedContacts, modelManager.getSortedContactList());

        // Sort by insertion order
        modelManager.sortContacts(ContactComparatorType.UNORDERED.comparator);
        assertEquals(contacts, modelManager.getSortedContactList());
    }

    @Test
    public void sortContracts() {
        ObservableList<Contract> contracts = FXCollections.observableArrayList(getContractB(), getContractD(),
                getContractA());
        contracts.forEach(modelManager::addContract);

        // Make sure the internal filtered contract list has the correct contracts
        assertEquals(contracts, modelManager.getFilteredContractList());

        // Sort by expiry date
        ObservableList<Contract> expectedContracts =
                FXCollections.observableArrayList(getContractA(), getContractB(), getContractD());
        modelManager.sortContracts(ContractComparatorType.EXPIRY_DATE.comparator);
        assertEquals(expectedContracts, modelManager.getSortedContractList());

        // Sort by insertion order
        modelManager.sortContracts(ContractComparatorType.UNORDERED.comparator);
        assertEquals(contracts, modelManager.getSortedContractList());
    }

    @Test
    public void updateFilteredAppointmentList_modifyList_throwsUnsupportedOperationException() {
        modelManager.addAppointment(getAppointmentA());
        modelManager.addAppointment(getAppointmentB());
        modelManager.updateFilteredAppointmentList(x -> x.equals(getAppointmentA()));
        assertEquals(1, modelManager.getFilteredAppointmentList().size());
        assertThrows(UnsupportedOperationException.class, () ->
                modelManager.getFilteredAppointmentList().remove(0));
    }

    @Test
    public void updatedFilteredAppointmentList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                modelManager.updateFilteredAppointmentList(null));
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withContact(getAlice()).withContact(getBenson()).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredContactList -> returns false
        String[] keywords = getAlice().getName().fullName.split("\\s+");
        modelManager.updateFilteredContactList(new NricContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // different filteredPolicyList -> returns false
        modelManager.updateFilteredPolicyList(x -> x.equals(getLife()));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // different filteredContractList -> returns false
        modelManager.updateFilteredContractList(x -> x.equals(getContractA()));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // different filteredAppointmentList -> returns false
        modelManager.updateFilteredAppointmentList(x -> x.equals(getAppointmentA()));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
