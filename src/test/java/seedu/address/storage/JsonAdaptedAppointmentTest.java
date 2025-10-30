package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.getAppointmentA;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.AppointmentDetails;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.contact.Nric;

public class JsonAdaptedAppointmentTest {

    private static final String INVALID_AID = "!1234A";
    private static final String INVALID_NRIC = "!123D";
    private static final String INVALID_DATE = "NOT A DATE";
    private static final String INVALID_DETAIL = "";

    private static final String VALID_AID = getAppointmentA().getAId().toString();
    private static final String VALID_NRIC = getAppointmentA().getNric().toString();
    private static final String VALID_DATE = getAppointmentA().getDate().toString();
    private static final String VALID_DETAIL = getAppointmentA().getDetails().toString();

    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(getAppointmentA());
        assertEquals(getAppointmentA(), appointment.toModelType());
    }

    @Test
    public void toModelType_invalidAId_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(INVALID_AID, VALID_NRIC, VALID_DATE, VALID_DETAIL);
        String expectedMessage = AppointmentId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullAId_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(null, VALID_NRIC, VALID_DATE, VALID_DETAIL);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, AppointmentId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(VALID_AID, INVALID_NRIC, VALID_DATE, VALID_DETAIL);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_AID, null, VALID_DATE, VALID_DETAIL);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(VALID_AID, VALID_NRIC, INVALID_DATE, VALID_DETAIL);
        String expectedMessage = "Date should be in the format dd-MM-yyyy";
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(VALID_AID, VALID_NRIC, null, VALID_DETAIL);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidDetail_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(VALID_AID, VALID_NRIC, VALID_DATE, INVALID_DETAIL);
        String expectedMessage = "Details should not be blank";
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullDetail_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment = new JsonAdaptedAppointment(VALID_AID, VALID_NRIC, VALID_DATE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, AppointmentDetails.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }
}
