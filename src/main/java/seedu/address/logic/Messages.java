package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.contact.Contact;
import seedu.address.model.policy.Policy;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_CONTACT_DISPLAYED_INDEX = "The contact index provided is invalid";
    public static final String MESSAGE_POLICY_NOT_FOUND = "This policy ID does not exist in iCon";
    public static final String MESSAGE_CONTACTS_LISTED_OVERVIEW = "%1$d contacts listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_CONTRACT_NOT_FOUND = "This contract ID does not exist in iCon.";
    public static final String MESSAGE_CONTACT_NOT_FOUND = "This contact NRIC does not exist in iCon";
    public static final String MESSAGE_APPOINTMENT_NOT_FOUND = "This appointment ID does not exist in the iCOn.";
    public static final String MESSAGE_INVALID_DATE = "The date provided is invalid.\n"
            + "Please ensure that the date exists in the calendar.\n"
            + "Kind reminder that the format is yyyy-MM-dd. Check that the order is correct!";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Date should be in the format yyyy-MM-dd";
    public static final String MESSAGE_INVALID_EXPIRY_DATE = "Signing date comes after expiry date";
    public static final String MESSAGE_INVALID_CONTRACT_PERIOD = "Date signed must be before Expiry date";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code contact} for display to the user.
     */
    public static String format(Contact contact) {
        final StringBuilder builder = new StringBuilder();
        builder.append(contact.getName())
                .append("; Phone: ")
                .append(contact.getPhone())
                .append("; NRIC: ")
                .append(contact.getNric())
                .append("; Email: ")
                .append(contact.getEmail())
                .append("; Address: ")
                .append(contact.getAddress())
                .append("; Tags: ");
        contact.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code policy} for display to the user.
     */
    public static String format(Policy policy) {
        return policy.getName() + "; Details: " + policy.getDetails();
    }

    /**
     * Formats the {@code policy} for display to the user.
     */
    public static String format(Appointment appointment) {
        return appointment.getAId().value;
    }
}
