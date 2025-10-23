package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

/**
 * Contains additional helper methods for testing appointment commands.
 */
public class AppointmentCommandTestUtil {

    public static final String VALID_APPOINTMENT_ID_A = "A1234A";
    public static final String VALID_APPOINTMENT_ID_B = "ABC123";
    public static final String VALID_APPOINTMENT_ID_C = "123abc";
    public static final String VALID_APPOINTMENT_ID_D = "xyzABC";
    public static final String VALID_APPOINTMENT_ID_E = "123456";
    public static final String INVALID_APPOINTMENT_ID = "!#$%^&";

    public static final String VALID_APPOINTMENT_DETAILS_A = "Placeholder details";
    public static final String VALID_APPOINTMENT_DETAILS_B = "Appointment details 123";

    public static final String VALID_APPOINTMENT_DATE_A = "2025-01-01";
    public static final String VALID_APPOINTMENT_DATE_B = "1999-12-30";
    public static final String INVALID_APPOINTMENT_DATE = "Not a date";

    public static final String VALID_APPOINTMENT_NRIC_1 = "S1234567A";
    public static final String VALID_APPOINTMENT_NRIC_2 = "S1234567B";
    public static final String INVALID_APPOINTMENT_NRIC = "T1234!67D";

    public static final String APPOINTMENT_DATE_DESC_A = " " + PREFIX_DATE + VALID_APPOINTMENT_DATE_A;
    public static final String APPOINTMENT_DATE_DESC_B = " " + PREFIX_DATE + VALID_APPOINTMENT_DATE_B;
    public static final String INVALID_APPOINTMENT_DATE_DESC = " " + PREFIX_DATE + INVALID_APPOINTMENT_DATE;


    public static final String APPOINTMENT_NRIC_DESC_A = " " + PREFIX_NRIC + VALID_APPOINTMENT_NRIC_1;
    public static final String APPOINTMENT_NRIC_DESC_B = " " + PREFIX_NRIC + VALID_APPOINTMENT_NRIC_2;
    public static final String INVALID_APPOINTMENT_NRIC_DESC = " " + PREFIX_NRIC + INVALID_APPOINTMENT_NRIC;

    public static final String APPOINTMENT_DETAILS_DESC_A = " " + PREFIX_DETAILS + VALID_APPOINTMENT_DETAILS_A;
    public static final String APPOINTMENT_DETAILS_DESC_B = " " + PREFIX_DETAILS + VALID_APPOINTMENT_DETAILS_B;
    public static final String INVALID_APPOINTMENT_DETAILS_DESC = " " + PREFIX_DETAILS;
}
