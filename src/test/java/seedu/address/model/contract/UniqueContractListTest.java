package seedu.address.model.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.getContractA;
import static seedu.address.testutil.TypicalData.getContractB;

import org.junit.jupiter.api.Test;

import seedu.address.model.contract.exceptions.ContractNotFoundException;
import seedu.address.model.contract.exceptions.DuplicateContractException;

public class UniqueContractListTest {

    private final UniqueContractList uniqueContractList = new UniqueContractList();

    @Test
    public void contains_nullContract_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContractList.contains(null));
    }

    @Test
    public void contains_contractNotInList_returnsFalse() {
        assertFalse(uniqueContractList.contains(getContractA()));
    }

    @Test
    public void contains_contractInList_returnsTrue() {
        uniqueContractList.add(getContractA());
        assertTrue(uniqueContractList.contains(getContractA()));
    }

    @Test
    public void contains_contractWithSameIdentityFieldsInList_returnsTrue() {
        uniqueContractList.add(getContractA());
        Contract editedContractA = new Contract(
                getContractA().getCId(),
                getContractB().getName(),
                getContractA().getNric(),
                getContractA().getPId(),
                getContractA().getDate(),
                getContractA().getExpiryDate(),
                getContractA().getPremium()
        );
        assertTrue(uniqueContractList.contains(editedContractA));
    }

    @Test
    public void add_nullContract_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContractList.add(null));
    }

    @Test
    public void add_duplicateContract_throwsDuplicateContractException() {
        uniqueContractList.add(getContractA());
        assertThrows(DuplicateContractException.class, () -> uniqueContractList.add(getContractA()));
    }

    @Test
    public void setContract_nullTargetContract_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContractList.setContract(null, getContractA()));
    }

    @Test
    public void setContract_nullEditedContract_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContractList.setContract(getContractA(), null));
    }

    @Test
    public void setContract_targetContractNotInList_throwsContractNotFoundException() {
        assertThrows(ContractNotFoundException.class, () -> uniqueContractList.setContract(getContractA(),
                getContractA()));
    }

    @Test
    public void setContract_editedContractIsSameContract_success() {
        uniqueContractList.add(getContractA());
        uniqueContractList.setContract(getContractA(), getContractA());
        UniqueContractList expectedUniqueContractList = new UniqueContractList();
        expectedUniqueContractList.add(getContractA());
        assertEquals(expectedUniqueContractList, uniqueContractList);
    }

    @Test
    public void setContract_editedContractHasSameIdentity_success() {
        uniqueContractList.add(getContractA());
        Contract editedContractA = new Contract(
                getContractA().getCId(),
                getContractB().getName(),
                getContractA().getNric(),
                getContractA().getPId(),
                getContractB().getDate(),
                getContractB().getExpiryDate(),
                getContractB().getPremium()
        );
        uniqueContractList.setContract(getContractA(), editedContractA);
        UniqueContractList expectedUniqueContractList = new UniqueContractList();
        expectedUniqueContractList.add(editedContractA);
        assertEquals(expectedUniqueContractList, uniqueContractList);
    }

    @Test
    public void setContract_editedContractHasDifferentIdentity_success() {
        uniqueContractList.add(getContractA());
        uniqueContractList.setContract(getContractA(), getContractB());
        UniqueContractList expectedUniqueContractList = new UniqueContractList();
        expectedUniqueContractList.add(getContractB());
        assertEquals(expectedUniqueContractList, uniqueContractList);
    }

    @Test
    public void setContract_editedContractHasNonUniqueIdentity_throwsDuplicateContractException() {
        uniqueContractList.add(getContractA());
        uniqueContractList.add(getContractB());
        assertThrows(DuplicateContractException.class, () -> uniqueContractList.setContract(getContractA(),
                getContractB()));
    }

    @Test
    public void remove_nullContract_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContractList.remove(null));
    }

    @Test
    public void remove_contractDoesNotExist_throwsContractNotFoundException() {
        assertThrows(ContractNotFoundException.class, () -> uniqueContractList.remove(getContractA()));
    }

    @Test
    public void remove_existingContract_removesContract() {
        uniqueContractList.add(getContractA());
        uniqueContractList.remove(getContractA());
        UniqueContractList expectedUniqueContractList = new UniqueContractList();
        assertEquals(expectedUniqueContractList, uniqueContractList);
    }

    @Test
    public void setContracts_nullUniqueContractList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContractList.setContracts((UniqueContractList) null));
    }

    @Test
    public void setContracts_uniqueContractList_replacesOwnListWithProvidedUniqueContractList() {
        uniqueContractList.add(getContractA());
        UniqueContractList expectedUniqueContractList = new UniqueContractList();
        expectedUniqueContractList.add(getContractB());
        uniqueContractList.setContracts(expectedUniqueContractList);
        assertEquals(expectedUniqueContractList, uniqueContractList);
    }

    @Test
    public void setContracts_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueContractList.setContracts((java.util.List<Contract>) null));
    }

    @Test
    public void setContracts_list_replacesOwnListWithProvidedList() {
        uniqueContractList.add(getContractA());
        java.util.List<Contract> contractList = java.util.List.of(getContractB());
        uniqueContractList.setContracts(contractList);
        UniqueContractList expectedUniqueContractList = new UniqueContractList();
    }

    @Test
    public void setContracts_listWithDuplicateContracts_throwsDuplicateContractException() {
        java.util.List<Contract> listWithDuplicateContracts = java.util.List.of(getContractA(), getContractA());
        assertThrows(DuplicateContractException.class, ()
            -> uniqueContractList.setContracts(listWithDuplicateContracts));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueContractList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void iterator_success() {
        uniqueContractList.add(getContractA());
        uniqueContractList.add(getContractB());
        java.util.Iterator<Contract> iterator = uniqueContractList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(getContractA(), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(getContractB(), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void equals() {
        UniqueContractList anotherList = new UniqueContractList();
        anotherList.add(getContractA());
        UniqueContractList differentList = new UniqueContractList();
        differentList.add(getContractB());

        // same values -> returns true
        UniqueContractList listCopy = new UniqueContractList();
        listCopy.add(getContractA());
        assertTrue(anotherList.equals(listCopy));

        // same object -> returns true
        assertTrue(anotherList.equals(anotherList));

        // null -> returns false
        assertFalse(anotherList.equals(null));

        // different types -> returns false
        assertFalse(anotherList.equals(5));

        // different list -> returns false
        assertFalse(anotherList.equals(differentList));
    }

    @Test
    public void hashCode_success() {
        UniqueContractList anotherList = new UniqueContractList();
        anotherList.add(getContractA());
        UniqueContractList listCopy = new UniqueContractList();
        listCopy.add(getContractA());
        assertEquals(anotherList.hashCode(), listCopy.hashCode());
    }

    @Test
    public void toString_success() {
        UniqueContractList anotherList = new UniqueContractList();
        anotherList.add(getContractA());
        String expected = "[" + getContractA().toString() + "]";
        assertEquals(expected, anotherList.toString());
    }

}
