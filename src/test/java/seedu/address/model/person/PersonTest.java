package seedu.address.model.person;

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
import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    private static final List<Contract> contracts = getTypicalContracts();
    private static final Contract CONTRACT_A = contracts.get(0);
    private static final Contract CONTRACT_B = contracts.get(1);

    @Test
    public void checkContractIdString_success() {
        String validContractIdString = CONTRACT_A.getCId().toString();
        Person firstPerson = getAlice();
        firstPerson.addContract(CONTRACT_A);

        assertEquals(validContractIdString, firstPerson.getContractIdsAsString());
    }

    @Test
    public void checkMultipleContractIdString_success() {
        String validContractIdString = CONTRACT_A.getCId().toString()
                + ", " + CONTRACT_B.getCId().toString();
        Person firstPerson = getAlice();
        firstPerson.addContract(CONTRACT_A);
        firstPerson.addContract(CONTRACT_B);

        assertEquals(validContractIdString, firstPerson.getContractIdsAsString());
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(getAlice().isSamePerson(getAlice()));

        // null -> returns false
        assertFalse(getAlice().isSamePerson(null));

        // same nric, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(getAlice()).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(getAlice().isSamePerson(editedAlice));

        // different nric, all other attributes same -> returns false
        editedAlice = new PersonBuilder(getAlice()).withNric(VALID_NRIC_BOB).build();
        assertFalse(getAlice().isSamePerson(editedAlice));

        // nric differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(getBob()).withNric(VALID_NRIC_BOB.toLowerCase()).build();
        assertTrue(getBob().isSamePerson(editedBob));
    }

    @Test
    public void compareNameAlphabetical() {
        // same name -> returns 0
        assertEquals(0, Person.compareNameAlphabetical(getAlice(), getAlice()));

        // same name in different case -> returns 0
        assertEquals(0, Person.compareNameAlphabetical(getAlice(),
                new PersonBuilder(getAlice()).withName(getAlice().getName().fullName.toUpperCase()).build()));

        // first person's name < second person's name alphabetically -> returns -1
        assertEquals(-1, Person.compareNameAlphabetical(getAlice(), getBob()));

        // first person's name > second person's name alphabetically -> returns 1
        assertEquals(1, Person.compareNameAlphabetical(getBob(), getAlice()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(getAlice()).build();
        assertTrue(getAlice().equals(aliceCopy));

        // same object -> returns true
        assertTrue(getAlice().equals(getAlice()));

        // null -> returns false
        assertFalse(getAlice().equals(null));

        // different type -> returns false
        assertFalse(getAlice().equals(5));

        // different person -> returns false
        assertFalse(getAlice().equals(getBob()));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(getAlice()).withName(VALID_NAME_BOB).build();
        assertFalse(getAlice().equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(getAlice()).withPhone(VALID_PHONE_BOB).build();
        assertFalse(getAlice().equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(getAlice()).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(getAlice().equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(getAlice()).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(getAlice().equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(getAlice()).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(getAlice().equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + getAlice().getName()
                + ", phone=" + getAlice().getPhone()
                + ", nric=" + getAlice().getNric()
                + ", email=" + getAlice().getEmail() + ", address=" + getAlice().getAddress()
                + ", tags=" + getAlice().getTags()
                + ", contracts=" + getAlice().getContracts() + "}";
        assertEquals(expected, getAlice().toString());
    }
}
