package seedu.address.model.appointment;

import java.util.List;
import java.util.Optional;

/**
 * Creates abstraction for appointment list commands
 */
public class AppointmentListUtil {

    /**
     * Finds an appointment in a list by its ID.
     * @param list The list to search in.
     * @param aId The Appointment ID to search for.
     * @return An Optional containing the found item, or an empty Optional if not found.
     */
    public static Optional<Appointment> findById(List<Appointment> list, AppointmentId aId) {
        return list.stream()
                .filter(item -> item.getAId().equals(aId))
                .findFirst();
    }
}
