package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.PolicyCommandTestUtil.VALID_POLICY_ID_HOME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.PolicyUtil.unassign;
import static seedu.address.testutil.TypicalData.getLife;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.PolicyName;
import seedu.address.model.policy.UnassignedPolicy;
import seedu.address.testutil.PolicyBuilder;

public class AddPolicyCommandTest {

    private static final String STUB_POLICY_ID = VALID_POLICY_ID_HOME;

    @Test
    public void constructor_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPolicyCommand(null));
    }

    @Test
    public void execute_policyAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPolicyAdded modelStub = new ModelStubAcceptingPolicyAdded();
        Policy validPolicy = new PolicyBuilder().withId(STUB_POLICY_ID).build();

        CommandResult commandResult = new AddPolicyCommand(unassign(validPolicy)).execute(modelStub);

        assertEquals(String.format(AddPolicyCommand.MESSAGE_SUCCESS, Messages.format(validPolicy)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPolicy), modelStub.policiesAdded);
    }

    @Test
    public void execute_duplicatePolicy_throwsCommandException() {
        Policy validPolicy = new PolicyBuilder().build();
        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(unassign(validPolicy));
        ModelStub modelStub = new ModelStubWithPolicy(validPolicy);

        assertThrows(CommandException.class,
                AddPolicyCommand.MESSAGE_DUPLICATE_POLICY, ()
                 -> addPolicyCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        UnassignedPolicy unassignedPolicy = new UnassignedPolicy(
                new PolicyName("Life Insurance"), new PolicyDetails("details")
        );
        UnassignedPolicy otherUnassignedPolicy = new UnassignedPolicy(
                new PolicyName("Healthcare"), new PolicyDetails("details")
        );
        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(unassignedPolicy);
        AddPolicyCommand addPolicyCommandOther = new AddPolicyCommand(otherUnassignedPolicy);

        // same object -> returns true
        assertTrue(addPolicyCommand.equals(addPolicyCommand));

        // same values -> returns true
        AddPolicyCommand addPolicyCommandCopy = new AddPolicyCommand(unassignedPolicy);
        assertTrue(addPolicyCommand.equals(addPolicyCommandCopy));

        // different types -> returns false
        assertFalse(addPolicyCommand.equals(1));

        // null -> returns false
        assertFalse(addPolicyCommand.equals(null));

        // different policy -> returns false
        assertFalse(addPolicyCommand.equals(addPolicyCommandOther));
    }

    @Test
    public void toStringMethod() {
        UnassignedPolicy unassignedPolicy = unassign(getLife());
        AddPolicyCommand addPolicyCommand = new AddPolicyCommand(unassignedPolicy);
        String expected = AddPolicyCommand.class.getCanonicalName() + "{toAdd=" + unassignedPolicy + "}";
        assertEquals(expected, addPolicyCommand.toString());
    }

    /**
     * A Model stub that contains a single policy.
     */
    private class ModelStubWithPolicy extends ModelStub {
        private final Policy policy;

        ModelStubWithPolicy(Policy policy) {
            requireNonNull(policy);
            this.policy = policy;
        }

        @Override
        public boolean hasSamePolicyFields(Policy policy) {
            requireNonNull(policy);
            return this.policy.isSamePolicy(policy);
        }

        @Override
        public PolicyId generateUniquePolicyId() {
            return new PolicyId(STUB_POLICY_ID);
        }
    }

    /**
     * A Model stub that always accept the policy being added.
     */
    private class ModelStubAcceptingPolicyAdded extends ModelStub {
        final ArrayList<Policy> policiesAdded = new ArrayList<>();

        @Override
        public boolean hasSamePolicyFields(Policy policy) {
            requireNonNull(policy);
            return policiesAdded.stream().anyMatch(policy::isSamePolicy);
        }

        @Override
        public void addPolicy(Policy policy) {
            requireNonNull(policy);
            policiesAdded.add(policy);
        }

        @Override
        public PolicyId generateUniquePolicyId() {
            return new PolicyId(STUB_POLICY_ID);
        }
    }
}
