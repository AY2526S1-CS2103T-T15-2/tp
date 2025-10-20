package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalData.APPOINTMENT_A;
import static seedu.address.testutil.TypicalData.APPOINTMENT_B;

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
        assertFalse(uniqueAppointmentList.contains(APPOINTMENT_A));
    }

    @Test
    public void contains_appointmentInList_returnsTrue() {
        uniqueAppointmentList.add(APPOINTMENT_A);
        assertTrue(uniqueAppointmentList.contains(APPOINTMENT_A));
    }

    @Test
    public void add_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.add(null));
    }

    @Test
    public void add_duplicateAppointment_throwsDuplicateAppointmentException() {
        uniqueAppointmentList.add(APPOINTMENT_A);
        assertThrows(DuplicateAppointmentException.class, () -> uniqueAppointmentList.add(APPOINTMENT_A));
    }

    @Test
    public void setAppointment_nullTargetAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> uniqueAppointmentList.setAppointment(null, APPOINTMENT_A));
    }

    @Test
    public void setAppointment_nullEditedAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> uniqueAppointmentList.setAppointment(APPOINTMENT_A, null));
    }

    @Test
    public void setAppointment_targetAppointmentNotInList_throwsAppointmentNotFoundException() {
        assertThrows(AppointmentNotFoundException.class,
                () -> uniqueAppointmentList.setAppointment(APPOINTMENT_A, APPOINTMENT_A));
    }

    @Test
    public void setAppointment_editedAppointmentIsSameAppointment_success() {
        uniqueAppointmentList.add(APPOINTMENT_A);
        uniqueAppointmentList.setAppointment(APPOINTMENT_A, APPOINTMENT_A);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(APPOINTMENT_A);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentHasSameIdentity_success() {
        uniqueAppointmentList.add(APPOINTMENT_A);
        Appointment editedAppointmentA = new Appointment(
                APPOINTMENT_A.getAId(),
                APPOINTMENT_B.getNric(),
                APPOINTMENT_A.getDate(),
                APPOINTMENT_B.getDetails()
        );
        uniqueAppointmentList.setAppointment(APPOINTMENT_A, editedAppointmentA);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(editedAppointmentA);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentHasDifferentIdentity_success() {
        uniqueAppointmentList.add(APPOINTMENT_A);
        uniqueAppointmentList.setAppointment(APPOINTMENT_A, APPOINTMENT_B);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(APPOINTMENT_B);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentHasNonUniqueIdentity_throwsDuplicateAppointmentException() {
        uniqueAppointmentList.add(APPOINTMENT_A);
        uniqueAppointmentList.add(APPOINTMENT_B);
        assertThrows(DuplicateAppointmentException.class,
                () -> uniqueAppointmentList.setAppointment(APPOINTMENT_A, APPOINTMENT_B));
    }

    @Test
    public void remove_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.remove(null));
    }

    @Test
    public void remove_appointmentDoesNotExist_throwsAppointmentNotFoundException() {
        assertThrows(AppointmentNotFoundException.class, () -> uniqueAppointmentList.remove(APPOINTMENT_A));
    }

    @Test
    public void remove_existingAppointment_removesAppointment() {
        uniqueAppointmentList.add(APPOINTMENT_A);
        uniqueAppointmentList.remove(APPOINTMENT_A);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointments_nullUniqueAppointmentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> uniqueAppointmentList.setAppointments((UniqueAppointmentList) null));
    }

    @Test
    public void setAppointments_uniqueAppointmentList_replacesOwnListWithProvidedUniqueAppointmentList() {
        uniqueAppointmentList.add(APPOINTMENT_A);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(APPOINTMENT_A);
        uniqueAppointmentList.setAppointments(expectedUniqueAppointmentList);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointments_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class,
                () -> uniqueAppointmentList.setAppointments((java.util.List<Appointment>) null));
    }

    @Test
    public void setAppointments_list_replacesOwnListWithProvidedList() {
        uniqueAppointmentList.add(APPOINTMENT_A);
        java.util.List<Appointment> appointmentList = java.util.List.of(APPOINTMENT_B);
        uniqueAppointmentList.setAppointments(appointmentList);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
    }

    @Test
    public void setAppointments_listWithDuplicateAppointments_throwsDuplicateAppointmentException() {
        java.util.List<Appointment> listWithDuplicateAppointments = java.util.List.of(APPOINTMENT_A, APPOINTMENT_A);
        assertThrows(DuplicateAppointmentException.class, ()
                -> uniqueAppointmentList.setAppointments(listWithDuplicateAppointments));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueAppointmentList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void iterator_success() {
        uniqueAppointmentList.add(APPOINTMENT_A);
        uniqueAppointmentList.add(APPOINTMENT_B);
        java.util.Iterator<Appointment> iterator = uniqueAppointmentList.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(APPOINTMENT_A, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(APPOINTMENT_B, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void equals() {
        UniqueAppointmentList anotherList = new UniqueAppointmentList();
        anotherList.add(APPOINTMENT_A);
        UniqueAppointmentList differentList = new UniqueAppointmentList();
        differentList.add(APPOINTMENT_B);

        // same values -> returns true
        UniqueAppointmentList listCopy = new UniqueAppointmentList();
        listCopy.add(APPOINTMENT_A);
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
        anotherList.add(APPOINTMENT_A);
        UniqueAppointmentList listCopy = new UniqueAppointmentList();
        listCopy.add(APPOINTMENT_A);
        assertEquals(anotherList.hashCode(), listCopy.hashCode());
    }

    @Test
    public void toString_success() {
        UniqueAppointmentList anotherList = new UniqueAppointmentList();
        anotherList.add(APPOINTMENT_A);
        String expected = "[" + APPOINTMENT_A.toString() + "]";
        assertEquals(expected, anotherList.toString());
    }
}
