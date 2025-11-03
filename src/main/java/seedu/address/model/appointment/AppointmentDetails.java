package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Appointment's details.
 * Guarantees: immutable; is valid as declared in {@link #isValidAppointmentDetails(String)}
 */
public class AppointmentDetails {

    public static final String MESSAGE_CONSTRAINTS =
            "Details should only contain printable ASCII characters, and it should not be blank\n"
                    + "For details, refer to: https://www.ascii-code.com/characters/printable-characters";

    public static final String VALIDATION_REGEX = "[\\p{Print}&&[\\S]]\\p{Print}*";

    public final String value;

    /**
     * Constructs a {@code AppointmentDetails}.
     *
     * @param details Valid appointment details.
     */
    public AppointmentDetails(String details) {
        requireNonNull(details);
        checkArgument(isValidAppointmentDetails(details), MESSAGE_CONSTRAINTS);
        value = details;
    }

    /**
     * Returns true if a given string is valid appointment details.
     */
    public static boolean isValidAppointmentDetails(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentDetails)) {
            return false;
        }

        AppointmentDetails otherAppointmentDetails = (AppointmentDetails) other;
        return value.equals(otherAppointmentDetails.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
