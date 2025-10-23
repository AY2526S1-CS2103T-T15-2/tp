package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n:");
    public static final Prefix PREFIX_PHONE = new Prefix("p:");
    public static final Prefix PREFIX_NRIC = new Prefix("ic:");
    public static final Prefix PREFIX_EMAIL = new Prefix("e:");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a:");
    public static final Prefix PREFIX_AID = new Prefix("a:");
    public static final Prefix PREFIX_TAG = new Prefix("t:");
    public static final Prefix PREFIX_DETAILS = new Prefix("d:");
    public static final Prefix PREFIX_FILE = new Prefix("f:");
    public static final Prefix PREFIX_PID = new Prefix("p:");
    public static final Prefix PREFIX_CID = new Prefix("c:");
    public static final Prefix PREFIX_DATE = new Prefix("dt:");
    public static final Prefix PREFIX_EXPIRY = new Prefix("e:");
    public static final Prefix PREFIX_PREMIUM = new Prefix("pr:");

    /* Flag definitions */
    public static final String INVALID_FLAG = "-";
    public static final String FLAG_LIST_ALL = "-a";
    public static final String FLAG_ALPHABETICAL_ORDER = "-a";
    public static final String FLAG_INSERTION_ORDER = "-i";
    public static final String FLAG_DATE_ORDER_ASCENDING = "-da";
    public static final String FLAG_DATE_ORDER_DESCENDING = "-dd";
    public static final String FLAG_EXPIRY_ORDER_ASCENDING = "-ea";
}
