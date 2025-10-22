package seedu.address.model.appointment;

import java.util.Comparator;

/**
 * Enumeration for available comparators to be used by SortContactCommand.
 */
public enum AppointmentComparatorType {
    UNORDERED(null),
    ALPHABETICAL(Appointment::compareNameAlphabetical),
    DATE_ASCENDING(Appointment::compareDateAscending),
    DATE_DESCENDING(Appointment::compareDateDescending);

    public final Comparator<Appointment> comparator;

    AppointmentComparatorType(Comparator<Appointment> comparator) {
        this.comparator = comparator;
    }
}
