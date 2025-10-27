package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_POLICIES;

import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyDetails;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.PolicyName;
import seedu.address.ui.ListPanelType;

/**
 * Edits the details of an existing policy in the address book.
 */
public class EditPolicyCommand extends Command {

    public static final String COMMAND_WORD = "edit_policy";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the policy identified "
            + "by its policyId. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: "
            + PREFIX_PID + "POLICY_ID "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DETAILS + "DETAILS]\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_PID + "a1b2c3 " + PREFIX_NAME + "Life Insurance";

    public static final String MESSAGE_EDIT_POLICY_SUCCESS = "Edited Policy: %1$s";
    public static final String MESSAGE_POLICY_ID_NOT_FOUND = "A policy with id %1$s was not found in the address book.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_POLICY = "This policy already exists in the address book.";

    private final PolicyId policyId;
    private final EditPolicyDescriptor editPolicyDescriptor;

    /**
     * @param policyId of the policy to edit
     * @param editPolicyDescriptor details to edit the policy with
     */
    public EditPolicyCommand(PolicyId policyId, EditPolicyDescriptor editPolicyDescriptor) {
        requireNonNull(policyId);
        requireNonNull(editPolicyDescriptor);

        this.policyId = policyId;
        this.editPolicyDescriptor = editPolicyDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Update filter to all policies first regardless of what is currently shown
        model.updateFilteredPolicyList(PREDICATE_SHOW_ALL_POLICIES);
        Policy policyToEdit = findMatchingPolicyId(model, policyId)
                .orElseThrow(() -> new CommandException(String.format(MESSAGE_POLICY_ID_NOT_FOUND, policyId)));

        Policy editedPolicy = createEditedPolicy(policyToEdit, editPolicyDescriptor);

        if (!policyToEdit.isSamePolicy(editedPolicy) && model.hasSamePolicyFields(editedPolicy)) {
            throw new CommandException(MESSAGE_DUPLICATE_POLICY);
        }

        model.setPolicy(policyToEdit, editedPolicy);
        return new CommandResult(String.format(MESSAGE_EDIT_POLICY_SUCCESS, Messages.format(editedPolicy)),
                ListPanelType.POLICY);
    }

    /**
     * Attempts to find a policy in the address book with the given policy id.
     */
    private static Optional<Policy> findMatchingPolicyId(Model model, PolicyId toFind) {
        return model.getFilteredPolicyList().stream()
                .filter(policy -> policy.getId().equals(toFind))
                .findFirst();
    }

    /**
     * Creates and returns a {@code Policy} with the details of {@code policyToEdit}
     * edited with {@code editPolicyDescriptor}.
     */
    private static Policy createEditedPolicy(Policy policyToEdit, EditPolicyDescriptor editPolicyDescriptor) {
        assert policyToEdit != null;

        PolicyName updatedName = editPolicyDescriptor.getName().orElse(policyToEdit.getName());
        PolicyDetails updatedDetails = editPolicyDescriptor.getDetails().orElse(policyToEdit.getDetails());

        return new Policy(updatedName, updatedDetails, policyToEdit.getId(), policyToEdit.getContracts());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPolicyCommand)) {
            return false;
        }

        EditPolicyCommand otherEditPolicyCommand = (EditPolicyCommand) other;
        return policyId.equals(otherEditPolicyCommand.policyId)
                && editPolicyDescriptor.equals(otherEditPolicyCommand.editPolicyDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("policyId", policyId)
                .add("editPolicyDescriptor", editPolicyDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the policy with. Each non-empty field value will replace the
     * corresponding field value of the policy.
     */
    public static class EditPolicyDescriptor {
        private PolicyName policyName;
        private PolicyDetails policyDetails;

        public EditPolicyDescriptor() {}

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(policyName, policyDetails);
        }

        public void setName(PolicyName policyName) {
            this.policyName = policyName;
        }

        public Optional<PolicyName> getName() {
            return Optional.ofNullable(policyName);
        }

        public void setDetails(PolicyDetails policyDetails) {
            this.policyDetails = policyDetails;
        }

        public Optional<PolicyDetails> getDetails() {
            return Optional.ofNullable(policyDetails);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPolicyDescriptor)) {
                return false;
            }

            EditPolicyDescriptor otherEditPolicyDescriptor = (EditPolicyDescriptor) other;
            return Objects.equals(policyName, otherEditPolicyDescriptor.policyName)
                    && Objects.equals(policyDetails, otherEditPolicyDescriptor.policyDetails);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("name", policyName)
                    .add("details", policyDetails)
                    .toString();
        }
    }
}
