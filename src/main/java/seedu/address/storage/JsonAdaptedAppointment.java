package seedu.address.storage;


import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_FORMAT;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDetails;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.contact.Nric;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
public class JsonAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    private final String aId;
    private final String nric;
    private final String appDate;
    private final String details;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given appointment details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("aId") String aId, @JsonProperty("nric") String nric,
                                  @JsonProperty("appDate") String appDate, @JsonProperty("details") String details) {
        this.aId = aId;
        this.nric = nric;
        this.appDate = appDate;
        this.details = details;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        aId = source.getAId().toString();
        nric = source.getNric().toString();
        appDate = source.getDate().toString();
        details = source.getDetails().toString();
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment.
     */
    public Appointment toModelType() throws IllegalValueException {
        if (aId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppointmentId.class.getSimpleName()));
        }
        if (!AppointmentId.isValidAppointmentId(aId)) {
            throw new IllegalValueException(AppointmentId.MESSAGE_CONSTRAINTS);
        }
        final AppointmentId modelAId = new AppointmentId(aId);

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        if (appDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDate.class.getSimpleName()));
        }
        final LocalDate modelDate;
        try {
            modelDate = LocalDate.parse(appDate);
        } catch (Exception e) {
            throw new IllegalValueException(MESSAGE_INVALID_DATE_FORMAT);
        }

        if (details == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    AppointmentDetails.class.getSimpleName()));
        }
        if (!AppointmentDetails.isValidAppointmentDetails(details)) {
            throw new IllegalValueException(AppointmentDetails.MESSAGE_CONSTRAINTS);
        }
        final AppointmentDetails modelDetails = new AppointmentDetails(details);

        return new Appointment(modelAId, modelNric, modelDate, modelDetails);
    }
}
