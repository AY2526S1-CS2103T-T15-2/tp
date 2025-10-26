package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HEALTH_B;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HOME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.ALICE;
import static seedu.address.testutil.TypicalData.APPOINTMENT_A;
import static seedu.address.testutil.TypicalData.APPOINTMENT_B;
import static seedu.address.testutil.TypicalData.BENSON;
import static seedu.address.testutil.TypicalData.CARL;
import static seedu.address.testutil.TypicalData.CONTRACT_A;
import static seedu.address.testutil.TypicalData.CONTRACT_B;
import static seedu.address.testutil.TypicalData.CONTRACT_D;
import static seedu.address.testutil.TypicalData.HEALTH_B;
import static seedu.address.testutil.TypicalData.HOME;
import static seedu.address.testutil.TypicalData.LIFE;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractComparatorType;
import seedu.address.model.person.NricContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonComparatorType;
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
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPerson((Person) null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    @Test
    public void hasSamePolicyId_policyIdNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasSamePolicyId(HOME));
    }

    @Test
    public void hasSamePolicyId_policyIdInAddressBook_returnsTrue() {
        modelManager.addPolicy(new PolicyBuilder(HEALTH_B).withId(VALID_POLICY_ID_HOME).build());
        assertTrue(modelManager.hasSamePolicyId(HOME));
    }

    @Test
    public void hasSamePolicyFields_policyNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasSamePolicyFields(HOME));
    }

    @Test
    public void hasSamePolicyFields_policyInAddressBook_returnsTrue() {
        modelManager.addPolicy(new PolicyBuilder(HOME).withId(VALID_POLICY_ID_HEALTH_B).build());
        assertTrue(modelManager.hasSamePolicyFields(HOME));
    }

    @Test
    public void removePolicy_policyInAddressBook_returnsFalse() {
        modelManager.addPolicy(LIFE);
        modelManager.removePolicy(LIFE);
        assertFalse(modelManager.hasSamePolicyId(LIFE));
    }

    @Test
    public void setPolicy_policyInAddressBook_returnsTrue() {
        modelManager.addPolicy(LIFE);
        modelManager.setPolicy(LIFE, LIFE);
        assertTrue(modelManager.hasSamePolicyId(LIFE));
    }

    @Test
    public void addContract_policyInAddressBook_returnsTrue() {
        modelManager.addPolicy(LIFE);
        modelManager.addContract(CONTRACT_A);
        assertTrue(modelManager.hasContract(CONTRACT_A));
    }

    @Test
    public void addContractToPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        modelManager.addPolicy(LIFE);
        modelManager.addContract(CONTRACT_A);
        modelManager.addContractToPerson(CONTRACT_A);
        assertTrue(modelManager.personHasContract(CONTRACT_A, ALICE));
    }

    @Test
    public void addContractToPolicy_policyInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        modelManager.addPolicy(LIFE);
        modelManager.addContract(CONTRACT_A);
        modelManager.addContractToPolicy(CONTRACT_A);
        assertTrue(modelManager.policyHasContract(CONTRACT_A, LIFE));
    }

    @Test
    public void removeContract_policyInAddressBook_returnsFalse() {
        modelManager.addContract(CONTRACT_A);
        modelManager.removeContract(CONTRACT_A);
        assertFalse(modelManager.hasContract(CONTRACT_A));
    }

    @Test
    public void hasAppointment_appointmentNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasAppointment(APPOINTMENT_A));
    }

    @Test
    public void hasAppointment_appointmentInAddressBook_returnsTrue() {
        modelManager.addAppointment(APPOINTMENT_A);
        assertTrue(modelManager.hasAppointment(APPOINTMENT_A));
    }

    @Test
    public void removeAppointment_appointmentInAddressBook_returnsFalse() {
        modelManager.addAppointment(APPOINTMENT_A);
        modelManager.removeAppointment(APPOINTMENT_A);
        assertFalse(modelManager.hasAppointment(APPOINTMENT_A));
    }

    @Test
    public void setAppointment_appointmentInAddressBook_returnsTrue() {
        modelManager.addAppointment(APPOINTMENT_A);
        modelManager.setAppointment(APPOINTMENT_A, APPOINTMENT_A);
        assertTrue(modelManager.hasAppointment(APPOINTMENT_A));
    }

    @Test
    public void hasContract_policyNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasContract(CONTRACT_D));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
    }

    @Test
    public void getSortedPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPersonList().remove(0));
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
        modelManager.addContract(CONTRACT_A);
        modelManager.addContract(CONTRACT_B);
        modelManager.updateFilteredContractList(x -> x.equals(CONTRACT_A));
        assertEquals(1, modelManager.getFilteredContractList().size());
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredContractList().remove(0));
    }

    @Test
    public void updatedFilteredContractList_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.updateFilteredContractList(null));
    }

    @Test
    public void sortPersons() {
        ObservableList<Person> persons = FXCollections.observableArrayList(BENSON, ALICE, CARL);
        persons.forEach(modelManager::addPerson);

        // Make sure the internal filtered person list has the correct persons
        assertEquals(persons, modelManager.getFilteredPersonList());

        // Sort alphabetically
        ObservableList<Person> expectedPersons = FXCollections.observableArrayList(ALICE, BENSON, CARL);
        modelManager.sortPersons(PersonComparatorType.ALPHABETICAL.comparator);
        assertEquals(expectedPersons, modelManager.getSortedPersonList());

        // Sort by insertion order
        modelManager.sortPersons(PersonComparatorType.UNORDERED.comparator);
        assertEquals(persons, modelManager.getSortedPersonList());
    }

    @Test
    public void sortContracts() {
        ObservableList<Contract> contracts = FXCollections.observableArrayList(CONTRACT_B, CONTRACT_D, CONTRACT_A);
        contracts.forEach(modelManager::addContract);

        // Make sure the internal filtered contract list has the correct contracts
        assertEquals(contracts, modelManager.getFilteredContractList());

        // Sort by expiry date
        ObservableList<Contract> expectedContracts =
                FXCollections.observableArrayList(CONTRACT_A, CONTRACT_B, CONTRACT_D);
        modelManager.sortContracts(ContractComparatorType.EXPIRY_DATE.comparator);
        assertEquals(expectedContracts, modelManager.getSortedContractList());

        // Sort by insertion order
        modelManager.sortContracts(ContractComparatorType.UNORDERED.comparator);
        assertEquals(contracts, modelManager.getSortedContractList());
    }

    @Test
    public void updateFilteredAppointmentList_modifyList_throwsUnsupportedOperationException() {
        modelManager.addAppointment(APPOINTMENT_A);
        modelManager.addAppointment(APPOINTMENT_B);
        modelManager.updateFilteredAppointmentList(x -> x.equals(APPOINTMENT_A));
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
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON).build();
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

        // different filteredPersonList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NricContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // different filteredPolicyList -> returns false
        modelManager.updateFilteredPolicyList(x -> x.equals(LIFE));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // different filteredContractList -> returns false
        modelManager.updateFilteredContractList(x -> x.equals(CONTRACT_A));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // different filteredAppointmentList -> returns false
        modelManager.updateFilteredAppointmentList(x -> x.equals(APPOINTMENT_A));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
