package seedu.address.model.appointment;

import java.time.LocalDate;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Nric;

/**
 * Represents an Appointment in the iCon.
 */
public class Appointment {

    private final AppointmentId aId;
    private final Nric nric;
    private final LocalDate appDate;
    private final AppointmentDetails details;

    /**
     * Constructor for a Creating a new Appointment object.
     * @param nric NRIC of contact set for appointment
     * @param appDate Date scheduled for appointment
     * @param details Details of what the appointment covers
     */
    public Appointment(Nric nric, LocalDate appDate, AppointmentDetails details) {
        this.aId = AppointmentId.generate();
        this.nric = nric;
        this.appDate = appDate;
        this.details = details;
    }

    /**
     * Constructor for an Appointment object with all fields specified for JSON.
     * @param aId Appointment ID
     * @param nric NRIC of appointment contact
     * @param appDate Date scheduled for appointment
     * @param details Details of what the appointment covers
     */
    public Appointment(AppointmentId aId, Nric nric, LocalDate appDate, AppointmentDetails details) {
        this.aId = aId;
        this.nric = nric;
        this.appDate = appDate;
        this.details = details;
    }

    public AppointmentId getAId() {
        return aId;
    }

    public Nric getNric() {
        return nric;
    }

    public LocalDate getDate() {
        return appDate;
    }

    public AppointmentDetails getDetails() {
        return details;
    }

    /**
     * Checks if the given appointment is the same as this appointment.
     */
    public boolean isSameAppointment(Appointment otherAppointment) {
        if (otherAppointment == this) {
            return true;
        }

        return otherAppointment != null
                && otherAppointment.getNric().equals(getNric())
                && otherAppointment.getDate().equals(getDate())
                && otherAppointment.getDetails().equals(getDetails());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getAId().equals(getAId())
                && otherAppointment.getNric().equals(getNric())
                && otherAppointment.getDate().equals(getDate())
                && otherAppointment.getDetails().equals(getDetails());
    }

    @Override
    public int hashCode() {
        return Objects.hash(aId, nric, appDate, details);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("aId", aId)
                .add("nric", nric)
                .add("appDate", appDate)
                .add("details", details)
                .toString();
    }

    /**
     * Compares two appointments' IDs alphabetically.
     * Used as a comparator to sort appointments.
     */
    public static int compareNameAlphabetical(Appointment appointment, Appointment otherAppointment) {
        return String.CASE_INSENSITIVE_ORDER.compare(
                appointment.aId.toString(),
                otherAppointment.aId.toString()
        );
    }
}
