package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.model.util.RandomUtil;

/**
 * Represents an Appointment's id that a user can reference.
 * Guarantees: immutable.
 */
public class AppointmentId {

    public static final int ID_LENGTH = 6;
    public static final String MESSAGE_CONSTRAINTS = "Appointment IDs should be alphanumeric";
    public static final String VALIDATION_REGEX = String.format("[a-zA-Z0-9]{%d}", ID_LENGTH);

    public final String value;

    /**
     * Constructs a {@code AppointmentId}.
     *
     * @param id A valid appointment id.
     */
    public AppointmentId(String id) {
        requireNonNull(id);
        checkArgument(isValidAppointmentId(id), MESSAGE_CONSTRAINTS);
        value = id;
    }

    /**
     * Generates a random alphanumeric id to assign to an appointment.
     */
    public static AppointmentId generate() {
        return new AppointmentId(RandomUtil.generateAlphanum(ID_LENGTH));
    }

    /**
     * Returns true if a given string is a valid Appointment ID.
     */
    public static boolean isValidAppointmentId(String test) {
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
        if (!(other instanceof AppointmentId)) {
            return false;
        }

        AppointmentId otherAppointmentId = (AppointmentId) other;
        return value.equals(otherAppointmentId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
