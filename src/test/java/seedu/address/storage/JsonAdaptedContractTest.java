package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedContract.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.getContractB;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Nric;
import seedu.address.model.contract.ContractId;
import seedu.address.model.contract.ContractPremium;
import seedu.address.model.contract.exceptions.InvalidContractDatesException;
import seedu.address.model.policy.PolicyId;

public class JsonAdaptedContractTest {

    private static final String INVALID_CID = "!1234A";
    private static final String INVALID_NAME = "Ra¢hel";
    private static final String INVALID_NRIC = "!123D";
    private static final String INVALID_PID = "P1234!";
    private static final String INVALID_DATE = "NOT A DATE";
    private static final String INVALID_EXPIRY = "NOT A DATE";
    private static final String INVALID_PERIOD_EXPIRY = "2000-01-01";
    private static final String INVALID_PREMIUM = "-100.00";

    private static final String VALID_CID = getContractB().getCId().toString();
    private static final String VALID_NAME = getContractB().getName().toString();
    private static final String VALID_NRIC = getContractB().getNric().toString();
    private static final String VALID_PID = getContractB().getPId().toString();
    private static final String VALID_DATE = getContractB().getDate().toString();
    private static final String VALID_EXPIRY = getContractB().getExpiryDate().toString();
    private static final String VALID_PREMIUM = getContractB().getPremium().toString();

    @Test
    public void toModelType_validContractDetails_returnsContract() throws Exception {
        JsonAdaptedContract contract = new JsonAdaptedContract(getContractB());
        assertEquals(getContractB(), contract.toModelType());
    }

    @Test
    public void toModelType_invalidCId_throwsIllegalValueException() {
        JsonAdaptedContract contract =
                new JsonAdaptedContract(INVALID_CID, VALID_NAME, VALID_NRIC, VALID_PID,
                        VALID_DATE, VALID_EXPIRY, VALID_PREMIUM);
        String expectedMessage = ContractId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_nullCId_throwsIllegalValueException() {
        JsonAdaptedContract contract = new JsonAdaptedContract(null, VALID_NAME, VALID_NRIC, VALID_PID,
                VALID_DATE, VALID_EXPIRY, VALID_PREMIUM);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ContractId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedContract contract =
                new JsonAdaptedContract(VALID_CID, INVALID_NAME, VALID_NRIC, VALID_PID,
                        VALID_DATE, VALID_EXPIRY, VALID_PREMIUM);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedContract contract = new JsonAdaptedContract(VALID_CID, null, VALID_NRIC, VALID_PID,
                VALID_DATE, VALID_EXPIRY, VALID_PREMIUM);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedContract contract =
                new JsonAdaptedContract(VALID_CID, VALID_NAME, INVALID_NRIC, VALID_PID,
                        VALID_DATE, VALID_EXPIRY, VALID_PREMIUM);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedContract contract = new JsonAdaptedContract(VALID_CID, VALID_NAME, null, VALID_PID,
                VALID_DATE, VALID_EXPIRY, VALID_PREMIUM);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_invalidPId_throwsIllegalValueException() {
        JsonAdaptedContract contract =
                new JsonAdaptedContract(VALID_CID, VALID_NAME, VALID_NRIC, INVALID_PID,
                        VALID_DATE, VALID_EXPIRY, VALID_PREMIUM);
        String expectedMessage = PolicyId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_nullPId_throwsIllegalValueException() {
        JsonAdaptedContract contract = new JsonAdaptedContract(VALID_CID, VALID_NAME, VALID_NRIC, null,
                VALID_DATE, VALID_EXPIRY, VALID_PREMIUM);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PolicyId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedContract contract =
                new JsonAdaptedContract(VALID_CID, VALID_NAME, VALID_NRIC, VALID_PID,
                        INVALID_DATE, VALID_EXPIRY, VALID_PREMIUM);
        String expectedMessage = Messages.MESSAGE_INVALID_DATE_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedContract contract = new JsonAdaptedContract(VALID_CID, VALID_NAME, VALID_NRIC, VALID_PID,
                null, VALID_EXPIRY, VALID_PREMIUM);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_invalidExpiry_throwsIllegalValueException() {
        JsonAdaptedContract contract =
                new JsonAdaptedContract(VALID_CID, VALID_NAME, VALID_NRIC, VALID_PID,
                        VALID_DATE, INVALID_EXPIRY, VALID_PREMIUM);
        String expectedMessage = Messages.MESSAGE_INVALID_DATE_FORMAT;
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_invalidPeriodExpiry_throwsIllegalValueException() {
        JsonAdaptedContract contract =
                new JsonAdaptedContract(VALID_CID, VALID_NAME, VALID_NRIC, VALID_PID,
                        VALID_DATE, INVALID_PERIOD_EXPIRY, VALID_PREMIUM);
        String expectedMessage = Messages.MESSAGE_INVALID_EXPIRY_DATE;
        assertThrows(InvalidContractDatesException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_nullExpiry_throwsIllegalValueException() {
        JsonAdaptedContract contract = new JsonAdaptedContract(VALID_CID, VALID_NAME, VALID_NRIC, VALID_PID,
                VALID_DATE, null, VALID_PREMIUM);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_invalidPremium_throwsIllegalValueException() {
        JsonAdaptedContract contract =
                new JsonAdaptedContract(VALID_CID, VALID_NAME, VALID_NRIC, VALID_PID,
                        VALID_DATE, VALID_EXPIRY, INVALID_PREMIUM);
        String expectedMessage = ContractPremium.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_nullPremium_throwsIllegalValueException() {
        JsonAdaptedContract contract = new JsonAdaptedContract(VALID_CID, VALID_NAME, VALID_NRIC, VALID_PID,
                VALID_DATE, VALID_EXPIRY, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ContractPremium.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

}
