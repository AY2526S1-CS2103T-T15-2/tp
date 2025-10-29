package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedContact.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.getBenson;
import static seedu.address.testutil.TypicalData.getCarl;
import static seedu.address.testutil.TypicalData.getDaniel;
import static seedu.address.testutil.TypicalData.getTypicalContacts;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contact.Address;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.Email;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Nric;
import seedu.address.model.contact.Phone;

public class JsonAdaptedContactTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_NRIC = "T000000E";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_NAME = getBenson().getName().toString();
    private static final String VALID_PHONE = getBenson().getPhone().toString();
    private static final String VALID_NRIC = getBenson().getNric().toString();
    private static final String VALID_EMAIL = getBenson().getEmail().toString();
    private static final String VALID_ADDRESS = getBenson().getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = getBenson().getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    private static final String VALID_NAME_2 = getCarl().getName().toString();
    private static final String VALID_PHONE_2 = getCarl().getPhone().toString();
    private static final String VALID_NRIC_2 = getCarl().getNric().toString();
    private static final String VALID_EMAIL_2 = getCarl().getEmail().toString();
    private static final String VALID_ADDRESS_2 = getCarl().getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS_2 = getCarl().getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    private static final String VALID_NAME_3 = getDaniel().getName().toString();
    private static final String VALID_PHONE_3 = getDaniel().getPhone().toString();
    private static final String VALID_NRIC_3 = getDaniel().getNric().toString();
    private static final String VALID_EMAIL_3 = getDaniel().getEmail().toString();
    private static final String VALID_ADDRESS_3 = getDaniel().getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS_3 = getDaniel().getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    private static final List<Contact> CONTACTS_WITH_CONTRACTS = getTypicalContacts().stream().limit(3).toList();
    private static final Contact ALICE_WITH_CONTRACT = CONTACTS_WITH_CONTRACTS.get(0);
    private static final String VALID_NAME_4 = ALICE_WITH_CONTRACT.getName().toString();
    private static final String VALID_PHONE_4 = ALICE_WITH_CONTRACT.getPhone().toString();
    private static final String VALID_NRIC_4 = ALICE_WITH_CONTRACT.getNric().toString();
    private static final String VALID_EMAIL_4 = ALICE_WITH_CONTRACT.getEmail().toString();
    private static final String VALID_ADDRESS_4 = ALICE_WITH_CONTRACT.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS_4 = ALICE_WITH_CONTRACT.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedContract> VALID_CONTRACTS = ALICE_WITH_CONTRACT.getContracts().stream()
            .map(JsonAdaptedContract::new)
            .collect(Collectors.toUnmodifiableList());
    private static final JsonAdaptedContract INVALID_CONTRACT = new JsonAdaptedContract(
            "#C123A",
            VALID_NAME_4,
            VALID_NRIC_4,
            "abcdef",
            "2023-01-01",
            "2025-01-01",
            "1000.00"
    );



    @Test
    public void toModelType_validContactDetails_returnsContact() throws Exception {
        JsonAdaptedContact contact = new JsonAdaptedContact(getBenson());
        assertEquals(getBenson(), contact.toModelType());
    }

    @Test
    public void toModelType_validContactDetailsWithContract_returnsContact() throws Exception {
        JsonAdaptedContact contact = new JsonAdaptedContact(ALICE_WITH_CONTRACT);
        assertEquals(ALICE_WITH_CONTRACT, contact.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(INVALID_NAME, VALID_PHONE, VALID_NRIC,
                        VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_CONTRACTS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(null, VALID_PHONE, VALID_NRIC,
                VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_CONTRACTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME, INVALID_PHONE, VALID_NRIC,
                        VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_CONTRACTS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_NAME, null,
                VALID_EMAIL, VALID_NRIC, VALID_ADDRESS, VALID_TAGS, VALID_CONTRACTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME, VALID_PHONE, INVALID_NRIC,
                        VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_CONTRACTS);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_NAME, VALID_PHONE, null,
                VALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_CONTRACTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME, VALID_PHONE, VALID_NRIC,
                        INVALID_EMAIL, VALID_ADDRESS, VALID_TAGS, VALID_CONTRACTS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_NAME_2, VALID_PHONE_2, VALID_NRIC_2,
                null, VALID_ADDRESS_2, VALID_TAGS_2, VALID_CONTRACTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedContact contact = new JsonAdaptedContact(VALID_NAME, VALID_PHONE, VALID_NRIC,
                VALID_EMAIL, null, VALID_TAGS, VALID_CONTRACTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, contact::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME, VALID_PHONE, VALID_NRIC,
                        VALID_EMAIL, VALID_ADDRESS, invalidTags, VALID_CONTRACTS);
        assertThrows(IllegalValueException.class, contact::toModelType);
    }

    @Test
    public void toModelType_invalidContracts_throwsIllegalValueException() {
        List<JsonAdaptedContract> invalidContracts = new ArrayList<>(VALID_CONTRACTS);
        List<JsonAdaptedTag> validTags = new ArrayList<>(VALID_TAGS);
        invalidContracts.add(INVALID_CONTRACT);
        JsonAdaptedContact contact =
                new JsonAdaptedContact(VALID_NAME_4, VALID_PHONE_4, VALID_NRIC_4,
                        VALID_EMAIL_4, VALID_ADDRESS_4, validTags, invalidContracts);
        assertThrows(IllegalValueException.class, contact::toModelType);
    }

}
