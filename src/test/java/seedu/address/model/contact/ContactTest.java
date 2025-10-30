package seedu.address.model.contact;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.getAlice;
import static seedu.address.testutil.TypicalData.getBob;
import static seedu.address.testutil.TypicalData.getTypicalContracts;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.contract.Contract;
import seedu.address.testutil.ContactBuilder;

public class ContactTest {

    private static final List<Contract> contracts = getTypicalContracts();
    private static final Contract CONTRACT_A = contracts.get(0);
    private static final Contract CONTRACT_B = contracts.get(1);

    @Test
    public void checkContractIdString_success() {
        String validContractIdString = CONTRACT_A.getCId().toString();
        Contact firstContact = getAlice();
        firstContact.addContract(CONTRACT_A);

        assertEquals(validContractIdString, firstContact.getContractIdsAsString());
    }

    @Test
    public void checkMultipleContractIdString_success() {
        String validContractIdString = CONTRACT_A.getCId().toString()
                + ", " + CONTRACT_B.getCId().toString();
        Contact firstContact = getAlice();
        firstContact.addContract(CONTRACT_A);
        firstContact.addContract(CONTRACT_B);

        assertEquals(validContractIdString, firstContact.getContractIdsAsString());
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Contact contact = new ContactBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> contact.getTags().remove(0));
    }

    @Test
    public void isSameContact() {
        // same object -> returns true
        assertTrue(getAlice().isSameContact(getAlice()));

        // null -> returns false
        assertFalse(getAlice().isSameContact(null));

        // same nric, all other attributes different -> returns false
        Contact editedAlice = new ContactBuilder(getAlice()).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(getAlice().isSameContact(editedAlice));

        // different nric, all other attributes same -> returns false
        editedAlice = new ContactBuilder(getAlice()).withNric(VALID_NRIC_BOB).build();
        assertFalse(getAlice().isSameContact(editedAlice));

        // nric differs in case, all other attributes same -> returns true
        Contact editedBob = new ContactBuilder(getBob()).withNric(VALID_NRIC_BOB.toLowerCase()).build();
        assertTrue(getBob().isSameContact(editedBob));

        // nric, phone, name is same, all other attributes different -> returns true
        editedAlice = new ContactBuilder(getAlice()).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(getAlice().isSameContact(editedAlice));
    }

    @Test
    public void compareNameAlphabetical() {
        // same name -> returns 0
        assertEquals(0, Contact.compareNameAlphabetical(getAlice(), getAlice()));

        // same name in different case -> returns 0
        assertEquals(0, Contact.compareNameAlphabetical(getAlice(),
                new ContactBuilder(getAlice()).withName(getAlice().getName().fullName.toUpperCase()).build()));

        // first contact's name < second contact's name alphabetically -> returns -1
        assertEquals(-1, Contact.compareNameAlphabetical(getAlice(), getBob()));

        // first contact's name > second contact's name alphabetically -> returns 1
        assertEquals(1, Contact.compareNameAlphabetical(getBob(), getAlice()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Contact aliceCopy = new ContactBuilder(getAlice()).build();
        assertTrue(getAlice().equals(aliceCopy));

        // same object -> returns true
        assertTrue(getAlice().equals(getAlice()));

        // null -> returns false
        assertFalse(getAlice().equals(null));

        // different type -> returns false
        assertFalse(getAlice().equals(5));

        // different contact -> returns false
        assertFalse(getAlice().equals(getBob()));

        // different name -> returns false
        Contact editedAlice = new ContactBuilder(getAlice()).withName(VALID_NAME_BOB).build();
        assertFalse(getAlice().equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ContactBuilder(getAlice()).withPhone(VALID_PHONE_BOB).build();
        assertFalse(getAlice().equals(editedAlice));

        // different email -> returns false
        editedAlice = new ContactBuilder(getAlice()).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(getAlice().equals(editedAlice));

        // different address -> returns false
        editedAlice = new ContactBuilder(getAlice()).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(getAlice().equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ContactBuilder(getAlice()).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(getAlice().equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Contact.class.getCanonicalName() + "{name=" + getAlice().getName()
                + ", phone=" + getAlice().getPhone()
                + ", nric=" + getAlice().getNric()
                + ", email=" + getAlice().getEmail() + ", address=" + getAlice().getAddress()
                + ", tags=" + getAlice().getTags()
                + ", contracts=" + getAlice().getContracts() + "}";
        assertEquals(expected, getAlice().toString());
    }
}
