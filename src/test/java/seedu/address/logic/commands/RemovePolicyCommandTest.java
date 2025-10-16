package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.LIFE;
import static seedu.address.testutil.TypicalData.TRAVEL;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;
import static seedu.address.testutil.TypicalId.VALID_POLICY_ID_2;
import static seedu.address.testutil.TypicalId.VALID_POLICY_ID_3;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyId;
import seedu.address.testutil.TypicalData;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code RemovePolicyCommand}.
 */
public class RemovePolicyCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_noExistingContractsUnderPolicy_removeSuccess() {
        RemovePolicyCommand removePolicyCommand = new RemovePolicyCommand(VALID_POLICY_ID_3);

        String expectedMessage = String.format(RemovePolicyCommand.MESSAGE_REMOVE_POLICY_SUCCESS, VALID_POLICY_ID_3);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removePolicy(TRAVEL);

        assertCommandSuccess(removePolicyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_existingContractsUnderPolicy_throwsCommandException() {
        RemovePolicyCommand removePolicyCommand = new RemovePolicyCommand(VALID_POLICY_ID_2);
        assertThrows(CommandException.class, () -> removePolicyCommand.execute(model));
    }

    @Test
    public void execute_invalidPolicyIdUnfilteredList_throwsCommandException() {
        RemovePolicyCommand removePolicyCommand = new RemovePolicyCommand(VALID_POLICY_ID_3);

        // First removal should be successful
        try {
            removePolicyCommand.execute(model);
        } catch (Exception e) {
            // Ignore
        }

        // Second removal should fail as the policy has already been removed
        assertCommandFailure(removePolicyCommand, model, Messages.MESSAGE_INVALID_POLICY_ID);
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        RemovePolicyCommand removePolicyCommand = new RemovePolicyCommand(VALID_POLICY_ID_2);
        assertThrows(NullPointerException.class, () -> removePolicyCommand.execute(null));
    }

    @Test
    public void equals() {
        RemovePolicyCommand removeFirstCommand = new RemovePolicyCommand(VALID_POLICY_ID_2);
        RemovePolicyCommand removeSecondCommand = new RemovePolicyCommand(VALID_POLICY_ID_2);

        // same object -> returns true
        assertTrue(removeFirstCommand.equals(removeFirstCommand));

        // same values -> returns true
        assertTrue(removeFirstCommand.equals(removeSecondCommand));

        // different types -> returns false
        assertFalse(removeFirstCommand.equals(5.0f));

        // null -> returns false
        assertFalse(removeFirstCommand.equals(null));
    }

    @Test
    public void toString_test() {
        RemovePolicyCommand removePolicyCommand = new RemovePolicyCommand(VALID_POLICY_ID_2);
        String expectedString = "seedu.address.logic.commands.RemovePolicyCommand{targetId=" + VALID_POLICY_ID_2 + "}";
        assertEquals(expectedString, removePolicyCommand.toString());
    }

    @Test
    public void hasPolicy_existsInList() {
        List<Policy> policyList = TypicalData.getTypicalPolicies();
        boolean hasPolicy = new RemovePolicyCommand(VALID_POLICY_ID_2).hasPolicyInList(policyList, VALID_POLICY_ID_2);
        assertTrue(hasPolicy);
    }

    @Test
    public void hasPolicy_notExistsInList() {
        List<Policy> policyList = TypicalData.getTypicalPolicies();
        PolicyId invalidId = new PolicyId("Inval1");
        boolean hasPolicy = new RemovePolicyCommand(VALID_POLICY_ID_2).hasPolicyInList(policyList, invalidId);
        assertFalse(hasPolicy);
    }

    @Test
    public void getPolicy_existsInList() {
        List<Policy> policyList = TypicalData.getTypicalPolicies();
        try {
            Policy expectedPolicy = new RemovePolicyCommand(VALID_POLICY_ID_2)
                    .getPolicyInList(policyList, VALID_POLICY_ID_2);
            assertEquals(expectedPolicy, LIFE);
        } catch (Exception e) {
            // Ignore
        }
    }

    @Test
    public void getPolicy_notExistsInList_throwsCommandException() {
        List<Policy> policyList = TypicalData.getTypicalPolicies();
        PolicyId invalidId = new PolicyId("Inval2");
        RemovePolicyCommand removePolicyCommand = new RemovePolicyCommand(VALID_POLICY_ID_2);
        assertThrows(CommandException.class, () -> removePolicyCommand.getPolicyInList(policyList, invalidId));
    }
}
