package seedu.address.testutil;

import java.time.LocalDate;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDetails;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.person.Nric;

/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {

    public static final String DEFAULT_AID = "A1234A";
    public static final String DEFAULT_NRIC = "S1234567A";
    public static final LocalDate DEFAULT_DATE = LocalDate.parse("2023-01-01");
    public static final String DEFAULT_DETAIL = "Details";

    private AppointmentId aId;
    private Nric nric;
    private LocalDate appDate;
    private AppointmentDetails details;

    /**
     * Creates an {@code AppointmentBuilder} with the default details.
     */
    public AppointmentBuilder() {
        aId = new AppointmentId(DEFAULT_AID);
        nric = new Nric(DEFAULT_NRIC);
        appDate = DEFAULT_DATE;
        details = new AppointmentDetails(DEFAULT_DETAIL);
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        aId = appointmentToCopy.getAId();
        nric = appointmentToCopy.getNric();
        appDate = appointmentToCopy.getDate();
        details = appointmentToCopy.getDetails();
    }

    /**
     * Sets the {@code AppointmentId} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withId(String appointmentId) {
        this.aId = new AppointmentId(appointmentId);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code LocalDate} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDate(LocalDate date) {
        this.appDate = date;
        return this;
    }

    /**
     * Sets the {@code AppointmentDetails} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDetails(String details) {
        this.details = new AppointmentDetails(details);
        return this;
    }

    public Appointment build() {
        return new Appointment(aId, nric, appDate, details);
    }
}
