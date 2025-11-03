package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showContactAtIndex;
import static seedu.address.logic.commands.RemoveContactCommand.MESSAGE_DELETE_CONTACT_FAILURE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CONTACT;
import static seedu.address.testutil.TypicalNricPredicates.PREDICATE_FIRST;
import static seedu.address.testutil.TypicalNricPredicates.PREDICATE_SECOND;
import static seedu.address.testutil.TypicalNricPredicates.PREDICATE_THIRD;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.NricContainsKeywordsPredicate;
import seedu.address.model.contract.Contract;
import seedu.address.ui.ListPanelType;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code RemoveContactCommand}.
 */
public class RemoveContactCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validNricUnfilteredList_success() {
        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());
        RemoveContactCommand removeContactCommand = new RemoveContactCommand(PREDICATE_FIRST);

        // remove the contact's contracts
        Set<Contract> contractToDelete = contactToDelete.getContracts();
        for (Contract contract : contractToDelete) {
            model.removeContract(contract);
            contactToDelete.removeContract(contract);
        }
        //remove the contact's appointments
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        List<Appointment> appointmentsToRemove = model.getFilteredAppointmentList().stream()
                .filter(a -> a.getNric().equals(contactToDelete.getNric())).toList();
        for (Appointment appointment : appointmentsToRemove) {
            model.removeAppointment(appointment);
        }

        // Success message is updated to reflect the contact deleted (or count if multiple are possible)
        String expectedMessage = String.format(RemoveContactCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                contactToDelete.getName() + " " + contactToDelete.getNric());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);

        assertCommandSuccess(removeContactCommand, model, expectedMessage, ListPanelType.CONTACT, expectedModel);
    }

    @Test
    public void execute_validNricFilteredList_success() {
        // Filter the list to show only the contact we are about to delete
        showContactAtIndex(model, INDEX_FIRST_CONTACT);

        // Use the NRIC of the contact who is currently at index 0 of the filtered list
        RemoveContactCommand removeContactCommand = new RemoveContactCommand(PREDICATE_FIRST);

        Contact contactToDelete = model.getFilteredContactList().get(INDEX_FIRST_CONTACT.getZeroBased());

        // remove the contact's contracts
        Set<Contract> contractToDelete = contactToDelete.getContracts();
        for (Contract contract : contractToDelete) {
            model.removeContract(contract);
            contactToDelete.removeContract(contract);
        }
        // remove the contact's appointments
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        List<Appointment> appointmentsToRemove = model.getFilteredAppointmentList().stream()
                .filter(a -> a.getNric().equals(contactToDelete.getNric())).toList();
        for (Appointment appointment : appointmentsToRemove) {
            model.removeAppointment(appointment);
        }

        String expectedMessage = String.format(String.format(RemoveContactCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                contactToDelete.getName() + " " + contactToDelete.getNric()));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteContact(contactToDelete);

        expectedModel.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);

        assertCommandSuccess(removeContactCommand, model, expectedMessage, ListPanelType.CONTACT, expectedModel);
    }

    @Test
    public void execute_nricNotFoundUnfilteredList_throwsCommandException() {
        // Create a predicate for an NRIC that is not in the typical address book
        NricContainsKeywordsPredicate notFoundPredicate =
                new NricContainsKeywordsPredicate(List.of("S0000000Z"));
        RemoveContactCommand removeContactCommand = new RemoveContactCommand(notFoundPredicate);

        // Assuming RemoveContactCommand throws a specific error if no contact matches the predicate
        assertCommandFailure(removeContactCommand, model, MESSAGE_DELETE_CONTACT_FAILURE);
    }

    @Test
    public void execute_nricNotFoundFilteredList_throwsCommandException() {
        // Create a predicate for a contact NOT currently in the filtered view
        RemoveContactCommand removeContactCommand = new RemoveContactCommand(PREDICATE_THIRD);

        // remove contract for contact in third index
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        Contact contact = model.getFilteredContactList().get(INDEX_THIRD_CONTACT.getZeroBased());
        Contract contractsToRemove = (Contract) contact.getContracts().toArray()[0];
        contact.removeContract(contractsToRemove);

        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        List<Appointment> appointmentsToRemove = model.getFilteredAppointmentList().stream()
                .filter(a -> a.getNric().equals(contact.getNric())).toList();
        for (Appointment appointment : appointmentsToRemove) {
            model.removeAppointment(appointment);
        }

        showContactAtIndex(model, INDEX_FIRST_CONTACT); // Filter list to one contact
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String expectedMessage = String.format(RemoveContactCommand.MESSAGE_DELETE_CONTACT_SUCCESS,
                contact.getName() + " " + contact.getNric());
        expectedModel.deleteContact(expectedModel.getFilteredContactList().get(INDEX_THIRD_CONTACT.getZeroBased()));
        assertCommandSuccess(removeContactCommand, model, expectedMessage, ListPanelType.CONTACT, expectedModel);

        // Create a predicate for a contact truly not in the view
        NricContainsKeywordsPredicate trulyNotFoundPredicate = new NricContainsKeywordsPredicate(List.of("S0000000Z"));
        RemoveContactCommand deleteTrulyNotFoundCommand = new RemoveContactCommand(trulyNotFoundPredicate);

        assertCommandFailure(deleteTrulyNotFoundCommand, model, MESSAGE_DELETE_CONTACT_FAILURE);
    }

    @Test
    public void equals() {
        RemoveContactCommand deleteFirstCommand = new RemoveContactCommand(PREDICATE_FIRST);
        RemoveContactCommand deleteSecondCommand = new RemoveContactCommand(PREDICATE_SECOND);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same predicate (same values) -> returns true
        RemoveContactCommand deleteFirstCommandCopy = new RemoveContactCommand(PREDICATE_FIRST);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different predicate (different NRIC) -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        RemoveContactCommand removeContactCommand = new RemoveContactCommand(PREDICATE_FIRST);

        // Note: Predicates don't have a clean toString, so this is an approximation.
        // You would typically override the toString in RemoveContactCommand to show the predicate.
        String expected = RemoveContactCommand.class.getCanonicalName()
                + "{predicate=" + PREDICATE_FIRST.toString() + "}";
        assertEquals(expected, removeContactCommand.toString());
    }

    /**
     * Helper method used in original tests, no longer needed but kept for completeness.
     */
    private void showNoContact(Model model) {
        model.updateFilteredContactList(p -> false);

        assertTrue(model.getFilteredContactList().isEmpty());
    }
}
