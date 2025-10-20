package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PolicyFileParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelStub;
import seedu.address.model.UserPrefs;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.UnassignedPolicy;

public class AddPolicyFileCommandTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "PolicyFileParserTest");
    private static final Path VALID_POLICY_FILE_PATH = TEST_DATA_FOLDER.resolve("validPolicyFile.txt");
    private static final List<UnassignedPolicy> VALID_UNASSIGNED_POLICIES;
    private static final List<Policy> VALID_POLICIES;
    private static final List<PolicyId> STUB_POLICY_IDS;

    static {
        try {
            VALID_UNASSIGNED_POLICIES = PolicyFileParser.readFile(VALID_POLICY_FILE_PATH);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
        VALID_POLICIES = VALID_UNASSIGNED_POLICIES.stream()
                .map(unassignedPolicy -> unassignedPolicy.assignId(PolicyId.generate()))
                .collect(Collectors.toList());
        STUB_POLICY_IDS = VALID_POLICIES.stream()
                .map(Policy::getId)
                .collect(Collectors.toList());
    }

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullPolicy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPolicyFileCommand(null));
    }

    @Test
    public void execute_missingFile_throwsCommandException() {
        Path path = TEST_DATA_FOLDER.resolve("nonexistentPolicyFile.txt");
        AddPolicyFileCommand addPolicyCommand = new AddPolicyFileCommand(path);

        IOException exception = null;
        try {
            PolicyFileParser.readFile(path);
            fail();
        } catch (IOException e) {
            exception = e;
        } catch (ParseException e) {
            fail();
        }

        String expectedMessage = String.format(AddPolicyFileCommand.MESSAGE_IOEXCEPTION, exception.getMessage());

        assertCommandFailure(addPolicyCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidPolicyFile_throwsCommandException() {
        Path path = TEST_DATA_FOLDER.resolve("invalidPolicyFile.txt");
        AddPolicyFileCommand addPolicyCommand = new AddPolicyFileCommand(path);

        ParseException exception = null;
        try {
            PolicyFileParser.readFile(path);
            fail();
        } catch (IOException e) {
            fail();
        } catch (ParseException e) {
            exception = e;
        }

        assertCommandFailure(addPolicyCommand, model, exception.getMessage());
    }

    @Test
    public void execute_policyFileAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPolicyFileAdded modelStub = new ModelStubAcceptingPolicyFileAdded();

        CommandResult commandResult = new AddPolicyFileCommand(VALID_POLICY_FILE_PATH).execute(modelStub);

        assertEquals(String.format(AddPolicyFileCommand.MESSAGE_SUCCESS, VALID_POLICY_FILE_PATH),
                commandResult.getFeedbackToUser());
        assertEquals(VALID_POLICIES, modelStub.policiesAdded);
    }

    @Test
    public void execute_duplicatePolicy_throwsCommandException() {
        AddPolicyFileCommand addPolicyFileCommand = new AddPolicyFileCommand(VALID_POLICY_FILE_PATH);
        ModelStub modelStub = new ModelStubWithPolicy(VALID_POLICIES.get(0));

        assertThrows(CommandException.class,
                AddPolicyFileCommand.MESSAGE_DUPLICATE_POLICY, ()
                 -> addPolicyFileCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicatePolicyInFile_throwsCommandException() {
        Path path = TEST_DATA_FOLDER.resolve("duplicateValidPolicyFile.txt");
        AddPolicyFileCommand addPolicyFileCommand = new AddPolicyFileCommand(path);
        ModelStubAcceptingPolicyFileAdded modelStub = new ModelStubAcceptingPolicyFileAdded();

        assertThrows(CommandException.class,
                AddPolicyFileCommand.MESSAGE_DUPLICATE_POLICY_IN_FILE, ()
                        -> addPolicyFileCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Path path = Path.of("example");
        Path otherPath = Path.of("different");
        AddPolicyFileCommand addPolicyCommand = new AddPolicyFileCommand(path);
        AddPolicyFileCommand addPolicyCommandOther = new AddPolicyFileCommand(otherPath);

        // same object -> returns true
        assertTrue(addPolicyCommand.equals(addPolicyCommand));

        // same values -> returns true
        AddPolicyFileCommand addPolicyCommandCopy = new AddPolicyFileCommand(path);
        assertTrue(addPolicyCommand.equals(addPolicyCommandCopy));

        // different types -> returns false
        assertFalse(addPolicyCommand.equals(1));

        // null -> returns false
        assertFalse(addPolicyCommand.equals(null));

        // different path -> returns false
        assertFalse(addPolicyCommand.equals(addPolicyCommandOther));
    }

    @Test
    public void toStringMethod() {
        Path path = Path.of("example");
        AddPolicyFileCommand addPolicyCommand = new AddPolicyFileCommand(path);
        String expected = AddPolicyFileCommand.class.getCanonicalName() + "{toAdd=" + path + "}";
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
        public List<PolicyId> generateUniquePolicyIds(int length) {
            return STUB_POLICY_IDS;
        }
    }

    /**
     * A Model stub that always accept the policies being added.
     */
    private class ModelStubAcceptingPolicyFileAdded extends ModelStub {
        final ArrayList<Policy> policiesAdded = new ArrayList<>();

        @Override
        public boolean hasSamePolicyFields(Policy policy) {
            requireNonNull(policy);
            return policiesAdded.stream().anyMatch(policy::isSamePolicy);
        }

        @Override
        public void addPolicies(List<Policy> policies) {
            requireNonNull(policies);
            policiesAdded.addAll(policies);
        }

        @Override
        public List<PolicyId> generateUniquePolicyIds(int length) {
            return STUB_POLICY_IDS;
        }
    }
}
