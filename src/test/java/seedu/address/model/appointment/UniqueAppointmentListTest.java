package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.getAppointmentA;
import static seedu.address.testutil.TypicalData.getAppointmentB;

import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;

public class UniqueAppointmentListTest {

    private final UniqueAppointmentList uniqueAppointmentList = new UniqueAppointmentList();

    @Test
    public void contains_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.contains(null));
    }

    @Test
    public void contains_appointmentNotInList_returnsFalse() {
        assertFalse(uniqueAppointmentList.contains(getAppointmentA()));
    }

    @Test
    public void contains_appointmentInList_returnsTrue() {
        uniqueAppointmentList.add(getAppointmentA());
        assertTrue(uniqueAppointmentList.contains(getAppointmentA()));
    }

    @Test
    public void add_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.add(null));
    }

    @Test
    public void add_duplicateAppointment_throwsDuplicateAppointmentException() {
        uniqueAppointmentList.add(getAppointmentA());
        assertThrows(DuplicateAppointmentException.class, () -> uniqueAppointmentList.add(getAppointmentA()));
    }

    @Test
    public void setAppointment_nullTargetAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueAppointmentList.setAppointment(null, getAppointmentA()));
    }

    @Test
    public void setAppointment_nullEditedAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueAppointmentList.setAppointment(getAppointmentA(), null));
    }

    @Test
    public void setAppointment_targetAppointmentNotInList_throwsAppointmentNotFoundException() {
        assertThrows(AppointmentNotFoundException.class, () ->
                uniqueAppointmentList.setAppointment(getAppointmentA(), getAppointmentA()));
    }

    @Test
    public void setAppointment_editedAppointmentIsSameAppointment_success() {
        uniqueAppointmentList.add(getAppointmentA());
        uniqueAppointmentList.setAppointment(getAppointmentA(), getAppointmentA());
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(getAppointmentA());
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentHasSameIdentity_success() {
        uniqueAppointmentList.add(getAppointmentA());
        Appointment editedAppointmentA = new Appointment(
                getAppointmentA().getAId(),
                getAppointmentB().getNric(),
                getAppointmentA().getDate(),
                getAppointmentB().getDetails()
        );
        uniqueAppointmentList.setAppointment(getAppointmentA(), editedAppointmentA);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(editedAppointmentA);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentHasDifferentIdentity_success() {
        uniqueAppointmentList.add(getAppointmentA());
        uniqueAppointmentList.setAppointment(getAppointmentA(), getAppointmentB());
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(getAppointmentB());
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentHasNonUniqueIdentity_throwsDuplicateAppointmentException() {
        uniqueAppointmentList.add(getAppointmentA());
        uniqueAppointmentList.add(getAppointmentB());
        assertThrows(DuplicateAppointmentException.class, () ->
                uniqueAppointmentList.setAppointment(getAppointmentA(), getAppointmentB()));
    }

    @Test
    public void remove_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.remove(null));
    }

    @Test
    public void remove_appointmentDoesNotExist_throwsAppointmentNotFoundException() {
        assertThrows(AppointmentNotFoundException.class, () -> uniqueAppointmentList.remove(getAppointmentA()));
    }

    @Test
    public void remove_existingAppointment_removesAppointment() {
        uniqueAppointmentList.add(getAppointmentA());
        uniqueAppointmentList.remove(getAppointmentA());
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointments_nullUniqueAppointmentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueAppointmentList.setAppointments((UniqueAppointmentList) null));
    }

    @Test
    public void setAppointments_uniqueAppointmentList_replacesOwnListWithProvidedUniqueAppointmentList() {
        uniqueAppointmentList.add(getAppointmentA());
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(getAppointmentA());
        uniqueAppointmentList.setAppointments(expectedUniqueAppointmentList);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointments_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                uniqueAppointmentList.setAppointments((java.util.List<Appointment>) null));
    }

    @Test
    public void setAppointments_list_replacesOwnListWithProvidedList() {
        uniqueAppointmentList.add(getAppointmentA());
        java.util.List<Appointment> appointmentList = java.util.List.of(getAppointmentB());
        uniqueAppointmentList.setAppointments(appointmentList);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
    }

    @Test
    public void setAppointments_listWithDuplicateAppointments_throwsDuplicateAppointmentException() {
        java.util.List<Appointment> listWithDuplicateAppointments = java.util.List.of(getAppointmentA(),
                getAppointmentA());
        assertThrows(DuplicateAppointmentException.class, () ->
                uniqueAppointmentList.setAppointments(listWithDuplicateAppointments));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueAppointmentList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void iterator_success() {
        uniqueAppointmentList.add(getAppointmentA());
        uniqueAppointmentList.add(getAppointmentB());
        java.util.Iterator<Appointment> iterator = uniqueAppointmentList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(getAppointmentA(), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(getAppointmentB(), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void equals() {
        UniqueAppointmentList anotherList = new UniqueAppointmentList();
        anotherList.add(getAppointmentA());
        UniqueAppointmentList differentList = new UniqueAppointmentList();
        differentList.add(getAppointmentB());

        // same values -> returns true
        UniqueAppointmentList listCopy = new UniqueAppointmentList();
        listCopy.add(getAppointmentA());
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
        UniqueAppointmentList anotherList = new UniqueAppointmentList();
        anotherList.add(getAppointmentA());
        UniqueAppointmentList listCopy = new UniqueAppointmentList();
        listCopy.add(getAppointmentA());
        assertEquals(anotherList.hashCode(), listCopy.hashCode());
    }

    @Test
    public void toString_success() {
        UniqueAppointmentList anotherList = new UniqueAppointmentList();
        anotherList.add(getAppointmentA());
        String expected = "[" + getAppointmentA().toString() + "]";
        assertEquals(expected, anotherList.toString());
    }
}
