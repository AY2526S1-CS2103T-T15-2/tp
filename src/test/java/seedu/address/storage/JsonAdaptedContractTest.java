package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedContract.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.CONTRACT_B;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contract.ContractId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.policy.PolicyId;

import java.time.LocalDate;

public class JsonAdaptedContractTest {

    private static final String INVALID_CID = "!1234A";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_NRIC = "!123D";
    private static final String INVALID_PID = "P1234!";
    private static final String INVALID_DATE = "NOT A DATE";

    private static final String VALID_CID = CONTRACT_B.getCId().toString();
    private static final String VALID_NAME = CONTRACT_B.getName().toString();
    private static final String VALID_NRIC = CONTRACT_B.getNric().toString();
    private static final String VALID_PID = CONTRACT_B.getPId().toString();
    private static final String VALID_DATE = CONTRACT_B.getDate().toString();

    @Test
    public void toModelType_validContractDetails_returnsContract() throws Exception {
        JsonAdaptedContract contract = new JsonAdaptedContract(CONTRACT_B);
        assertEquals(CONTRACT_B, contract.toModelType());
    }

    @Test
    public void toModelType_invalidCId_throwsIllegalValueException() {
        JsonAdaptedContract contract =
                new JsonAdaptedContract(INVALID_CID, VALID_NAME, VALID_NRIC, VALID_PID, VALID_DATE);
        String expectedMessage = ContractId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_nullCId_throwsIllegalValueException() {
        JsonAdaptedContract contract = new JsonAdaptedContract(null, VALID_NAME, VALID_NRIC, VALID_PID, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, ContractId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedContract contract =
                new JsonAdaptedContract(VALID_CID, INVALID_NAME, VALID_NRIC, VALID_PID, VALID_DATE);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedContract contract = new JsonAdaptedContract(VALID_CID, null, VALID_NRIC, VALID_PID, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedContract contract =
                new JsonAdaptedContract(VALID_CID, VALID_NAME, INVALID_NRIC, VALID_PID, VALID_DATE);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedContract contract = new JsonAdaptedContract(VALID_CID, VALID_NAME, null, VALID_PID, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_invalidPId_throwsIllegalValueException() {
        JsonAdaptedContract contract =
                new JsonAdaptedContract(VALID_CID, VALID_NAME, VALID_NRIC, INVALID_PID, VALID_DATE);
        String expectedMessage = PolicyId.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_nullPId_throwsIllegalValueException() {
        JsonAdaptedContract contract = new JsonAdaptedContract(VALID_CID, VALID_NAME, VALID_NRIC, null, VALID_DATE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, PolicyId.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedContract contract =
                new JsonAdaptedContract(VALID_CID, VALID_NAME, VALID_NRIC, VALID_PID, INVALID_DATE);
        String expectedMessage = "Date should be in the format dd-MM-yyyy";
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedContract contract = new JsonAdaptedContract(VALID_CID, VALID_NAME, VALID_NRIC, VALID_PID, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contract::toModelType);
    }

}
