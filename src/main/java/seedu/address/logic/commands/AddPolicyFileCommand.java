package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.policy.Policy.policiesAreUnique;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.PolicyFileParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.policy.Policy;
import seedu.address.model.policy.PolicyId;
import seedu.address.model.policy.UnassignedPolicy;

/**
 * Adds multiple policies from a file.
 */
public non-sealed class AddPolicyFileCommand extends AddPolicyCommandType {

    public static final String MESSAGE_SUCCESS = "New policies added from file: %1$s";
    public static final String MESSAGE_IOEXCEPTION = "Error encountered when reading file: %1$s";
    public static final String MESSAGE_DUPLICATE_POLICY = "A policy in the file already exists in the address book";
    public static final String MESSAGE_DUPLICATE_POLICY_IN_FILE = "There exist duplicate policies in the file";

    private final Path toAdd;

    /**
     * Creates an AddPolicyFileCommand to add policies from a {@code Path}
     */
    public AddPolicyFileCommand(Path filePath) {
        requireNonNull(filePath);
        toAdd = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<UnassignedPolicy> unassignedPolicies;

        try {
            unassignedPolicies = PolicyFileParser.readFile(toAdd);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_IOEXCEPTION, e.getMessage()), e);
        } catch (ParseException e) {
            throw new CommandException(e.getMessage(), e);
        }

        int policyCount = unassignedPolicies.size();
        List<PolicyId> policyIds = model.generateUniquePolicyIds(policyCount);
        List<Policy> policies = IntStream.range(0, policyCount)
                .mapToObj(i -> unassignedPolicies.get(i).assignId(policyIds.get(i)))
                .collect(Collectors.toList());

        if (policies.stream().anyMatch(model::hasSamePolicyFields)) {
            throw new CommandException(MESSAGE_DUPLICATE_POLICY);
        }
        if (!policiesAreUnique(policies)) {
            throw new CommandException(MESSAGE_DUPLICATE_POLICY_IN_FILE);
        }

        model.addPolicies(policies);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddPolicyFileCommand)) {
            return false;
        }

        AddPolicyFileCommand otherAddPolicyCommand = (AddPolicyFileCommand) other;
        return toAdd.equals(otherAddPolicyCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
