package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Contains additional helper methods for testing policy commands.
 */
public class PolicyCommandTestUtil {

    public static final String VALID_POLICY_NAME_HOME = "Home Insurance - B";
    public static final String VALID_POLICY_NAME_HEALTH_B = "Healthcare - B";
    public static final String VALID_DETAILS_HOME = "Placeholder policy details";
    public static final String VALID_DETAILS_HEALTH_B = "Policy details 123";
    public static final String VALID_POLICY_ID_HOME = "X2EsiB";
    public static final String VALID_POLICY_ID_HEALTH_B = "CzvuS3";

    public static final String POLICY_NAME_DESC_HOME = " " + PREFIX_NAME + VALID_POLICY_NAME_HOME;
    public static final String POLICY_NAME_DESC_HEALTH_B = " " + PREFIX_NAME + VALID_POLICY_NAME_HEALTH_B;
    public static final String DETAILS_DESC_HOME = " " + PREFIX_DETAILS + VALID_DETAILS_HOME;
    public static final String DETAILS_DESC_HEALTH_B = " " + PREFIX_DETAILS + VALID_DETAILS_HEALTH_B;

    public static final String INVALID_POLICY_NAME = "Medical*";
    public static final String INVALID_POLICY_NAME_DESC = " " + PREFIX_NAME + INVALID_POLICY_NAME;
    public static final String INVALID_DETAILS_DESC = " " + PREFIX_DETAILS;

}
