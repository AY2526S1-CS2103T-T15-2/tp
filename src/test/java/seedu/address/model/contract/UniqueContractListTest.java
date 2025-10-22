package seedu.address.model.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.CONTRACT_A;
import static seedu.address.testutil.TypicalData.CONTRACT_B;

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
        assertFalse(uniqueContractList.contains(CONTRACT_A));
    }

    @Test
    public void contains_contractInList_returnsTrue() {
        uniqueContractList.add(CONTRACT_A);
        assertTrue(uniqueContractList.contains(CONTRACT_A));
    }

    @Test
    public void contains_contractWithSameIdentityFieldsInList_returnsTrue() {
        uniqueContractList.add(CONTRACT_A);
        Contract editedContractA = new Contract(
                CONTRACT_A.getCId(),
                CONTRACT_B.getName(),
                CONTRACT_A.getNric(),
                CONTRACT_A.getPId(),
                CONTRACT_A.getDate(),
                CONTRACT_A.getExpiryDate(),
                CONTRACT_A.getPremium()
        );
        assertTrue(uniqueContractList.contains(editedContractA));
    }

    @Test
    public void add_nullContract_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContractList.add(null));
    }

    @Test
    public void add_duplicateContract_throwsDuplicateContractException() {
        uniqueContractList.add(CONTRACT_A);
        assertThrows(DuplicateContractException.class, () -> uniqueContractList.add(CONTRACT_A));
    }

    @Test
    public void setContract_nullTargetContract_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContractList.setContract(null, CONTRACT_A));
    }

    @Test
    public void setContract_nullEditedContract_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContractList.setContract(CONTRACT_A, null));
    }

    @Test
    public void setContract_targetContractNotInList_throwsContractNotFoundException() {
        assertThrows(ContractNotFoundException.class, () -> uniqueContractList.setContract(CONTRACT_A, CONTRACT_A));
    }

    @Test
    public void setContract_editedContractIsSameContract_success() {
        uniqueContractList.add(CONTRACT_A);
        uniqueContractList.setContract(CONTRACT_A, CONTRACT_A);
        UniqueContractList expectedUniqueContractList = new UniqueContractList();
        expectedUniqueContractList.add(CONTRACT_A);
        assertEquals(expectedUniqueContractList, uniqueContractList);
    }

    @Test
    public void setContract_editedContractHasSameIdentity_success() {
        uniqueContractList.add(CONTRACT_A);
        Contract editedContractA = new Contract(
                CONTRACT_A.getCId(),
                CONTRACT_B.getName(),
                CONTRACT_A.getNric(),
                CONTRACT_A.getPId(),
                CONTRACT_B.getDate(),
                CONTRACT_B.getExpiryDate(),
                CONTRACT_B.getPremium()
        );
        uniqueContractList.setContract(CONTRACT_A, editedContractA);
        UniqueContractList expectedUniqueContractList = new UniqueContractList();
        expectedUniqueContractList.add(editedContractA);
        assertEquals(expectedUniqueContractList, uniqueContractList);
    }

    @Test
    public void setContract_editedContractHasDifferentIdentity_success() {
        uniqueContractList.add(CONTRACT_A);
        uniqueContractList.setContract(CONTRACT_A, CONTRACT_B);
        UniqueContractList expectedUniqueContractList = new UniqueContractList();
        expectedUniqueContractList.add(CONTRACT_B);
        assertEquals(expectedUniqueContractList, uniqueContractList);
    }

    @Test
    public void setContract_editedContractHasNonUniqueIdentity_throwsDuplicateContractException() {
        uniqueContractList.add(CONTRACT_A);
        uniqueContractList.add(CONTRACT_B);
        assertThrows(DuplicateContractException.class, () -> uniqueContractList.setContract(CONTRACT_A, CONTRACT_B));
    }

    @Test
    public void remove_nullContract_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContractList.remove(null));
    }

    @Test
    public void remove_contractDoesNotExist_throwsContractNotFoundException() {
        assertThrows(ContractNotFoundException.class, () -> uniqueContractList.remove(CONTRACT_A));
    }

    @Test
    public void remove_existingContract_removesContract() {
        uniqueContractList.add(CONTRACT_A);
        uniqueContractList.remove(CONTRACT_A);
        UniqueContractList expectedUniqueContractList = new UniqueContractList();
        assertEquals(expectedUniqueContractList, uniqueContractList);
    }

    @Test
    public void setContracts_nullUniqueContractList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueContractList.setContracts((UniqueContractList) null));
    }

    @Test
    public void setContracts_uniqueContractList_replacesOwnListWithProvidedUniqueContractList() {
        uniqueContractList.add(CONTRACT_A);
        UniqueContractList expectedUniqueContractList = new UniqueContractList();
        expectedUniqueContractList.add(CONTRACT_B);
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
        uniqueContractList.add(CONTRACT_A);
        java.util.List<Contract> contractList = java.util.List.of(CONTRACT_B);
        uniqueContractList.setContracts(contractList);
        UniqueContractList expectedUniqueContractList = new UniqueContractList();
    }

    @Test
    public void setContracts_listWithDuplicateContracts_throwsDuplicateContractException() {
        java.util.List<Contract> listWithDuplicateContracts = java.util.List.of(CONTRACT_A, CONTRACT_A);
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
        uniqueContractList.add(CONTRACT_A);
        uniqueContractList.add(CONTRACT_B);
        java.util.Iterator<Contract> iterator = uniqueContractList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(CONTRACT_A, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(CONTRACT_B, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void equals() {
        UniqueContractList anotherList = new UniqueContractList();
        anotherList.add(CONTRACT_A);
        UniqueContractList differentList = new UniqueContractList();
        differentList.add(CONTRACT_B);

        // same values -> returns true
        UniqueContractList listCopy = new UniqueContractList();
        listCopy.add(CONTRACT_A);
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
        anotherList.add(CONTRACT_A);
        UniqueContractList listCopy = new UniqueContractList();
        listCopy.add(CONTRACT_A);
        assertEquals(anotherList.hashCode(), listCopy.hashCode());
    }

    @Test
    public void toString_success() {
        UniqueContractList anotherList = new UniqueContractList();
        anotherList.add(CONTRACT_A);
        String expected = "[" + CONTRACT_A.toString() + "]";
        assertEquals(expected, anotherList.toString());
    }

}
