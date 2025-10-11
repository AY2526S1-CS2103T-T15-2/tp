package seedu.address.testutil;

import seedu.address.model.contract.ContractId;
import seedu.address.model.policy.PolicyId;

/**
 * A utility class containing a list of {@code ContractId} and {@code PolicyId} objects to be used in tests.
 */
public class TypicalId {
    public static final ContractId VALID_CONTRACT_ID = new ContractId("C1234A");
    public static final PolicyId VALID_POLICY_ID = new PolicyId("P1234A");
    public static final PolicyId VALID_POLICY_ID_2 = new PolicyId("abcdef");
}
