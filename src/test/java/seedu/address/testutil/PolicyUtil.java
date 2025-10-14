package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.AddPolicyCommand;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.UnassignedPolicy;

/**
 * A utility class for Policy.
 */
public class PolicyUtil {

    /**
     * Returns an add policy command string for adding the {@code policy}.
     */
    public static String getAddPolicyCommand(Policy policy) {
        return AddPolicyCommand.COMMAND_WORD + " " + getPolicyDetails(policy);
    }

    /**
     * Returns the part of command string for the given {@code policy}'s details.
     */
    public static String getPolicyDetails(Policy policy) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + policy.getName().value + " ");
        sb.append(PREFIX_DETAILS + policy.getDetails().value + " ");
        return sb.toString();
    }

    /**
     * Returns the same policy without the policy id.
     */
    public static UnassignedPolicy unassign(Policy policy) {
        return new UnassignedPolicy(policy.getName(), policy.getDetails());
    }
}
