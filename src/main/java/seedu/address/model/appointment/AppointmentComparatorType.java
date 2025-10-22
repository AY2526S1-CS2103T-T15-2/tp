package seedu.address.model.appointment;

import java.util.Comparator;

/**
 * Enumeration for available comparators to be used by SortContactCommand.
 */
public enum AppointmentComparatorType {
    UNORDERED(null),
    ALPHABETICAL(Appointment::compareNameAlphabetical);

    public final Comparator<Appointment> comparator;

    AppointmentComparatorType(Comparator<Appointment> comparator) {
        this.comparator = comparator;
    }
}
