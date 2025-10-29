package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_DETAILS_HOME;
import static seedu.address.testutil.TypicalData.HEALTH;
import static seedu.address.testutil.TypicalData.HOME;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditPolicyCommand.EditPolicyDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyId;
import seedu.address.testutil.EditPolicyDescriptorBuilder;
import seedu.address.testutil.PolicyBuilder;
import seedu.address.ui.ListPanelType;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditPolicyCommand.
 */
public class EditPolicyCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        Policy policyToEdit = model.getFilteredPolicyList().get(0);
        PolicyId policyId = policyToEdit.getId();

        Policy editedPolicy = new PolicyBuilder(HOME).withId(policyId.value).build();
        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder(editedPolicy).build();
        EditPolicyCommand editPolicyCommand = new EditPolicyCommand(policyId, descriptor);

        String expectedMessage = String.format(EditPolicyCommand.MESSAGE_EDIT_POLICY_SUCCESS,
                editedPolicy.getId().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPolicy(policyToEdit, editedPolicy);

        assertCommandSuccess(editPolicyCommand, model, expectedMessage, ListPanelType.POLICY, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Policy policyToEdit = model.getFilteredPolicyList().get(0);
        PolicyId policyId = policyToEdit.getId();

        Policy editedPolicy = new PolicyBuilder(policyToEdit).withDetails(VALID_DETAILS_HOME).build();
        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder().withDetails(VALID_DETAILS_HOME).build();
        EditPolicyCommand editPolicyCommand = new EditPolicyCommand(policyId, descriptor);

        String expectedMessage = String.format(EditPolicyCommand.MESSAGE_EDIT_POLICY_SUCCESS,
                editedPolicy.getId().toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPolicy(policyToEdit, editedPolicy);

        assertCommandSuccess(editPolicyCommand, model, expectedMessage, ListPanelType.POLICY, expectedModel);
    }

    @Test
    public void execute_duplicatePolicy_failure() {
        Policy firstPolicy = model.getFilteredPolicyList().get(0);
        Policy secondPolicy = model.getFilteredPolicyList().get(1);

        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder(firstPolicy).build();
        EditPolicyCommand editPolicyCommand = new EditPolicyCommand(secondPolicy.getId(), descriptor);

        assertCommandFailure(editPolicyCommand, model, EditPolicyCommand.MESSAGE_DUPLICATE_POLICY);
    }

    @Test
    public void execute_policyIdNotInAddressBook_failure() {
        PolicyId policyId = HOME.getId();
        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder(HOME).build();
        EditPolicyCommand editPolicyCommand = new EditPolicyCommand(policyId, descriptor);

        assertCommandFailure(editPolicyCommand, model,
                String.format(EditPolicyCommand.MESSAGE_POLICY_ID_NOT_FOUND, policyId));
    }

    @Test
    public void equals() {
        EditPolicyDescriptor descriptor = new EditPolicyDescriptorBuilder(HOME).build();
        final EditPolicyCommand standardCommand = new EditPolicyCommand(HOME.getId(), descriptor);

        // same values -> returns true
        EditPolicyDescriptor copyDescriptor = new EditPolicyDescriptorBuilder(HOME).build();
        EditPolicyCommand commandWithSameValues = new EditPolicyCommand(HOME.getId(), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different policy id -> returns false
        assertFalse(standardCommand.equals(new EditPolicyCommand(HEALTH.getId(), descriptor)));

        // different descriptor -> returns false
        EditPolicyDescriptor otherDescriptor = new EditPolicyDescriptorBuilder(HEALTH).build();
        assertFalse(standardCommand.equals(new EditPolicyCommand(HOME.getId(), otherDescriptor)));
    }

    @Test
    public void toStringMethod() {
        PolicyId policyId = HOME.getId();
        EditPolicyDescriptor editPolicyDescriptor = new EditPolicyDescriptor();
        EditPolicyCommand editPolicyCommand = new EditPolicyCommand(policyId, editPolicyDescriptor);
        String expected = EditPolicyCommand.class.getCanonicalName() + "{policyId=" + policyId
                + ", editPolicyDescriptor=" + editPolicyDescriptor + "}";
        assertEquals(expected, editPolicyCommand.toString());
    }

}
