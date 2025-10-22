package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModelStub;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentComparatorType;
import seedu.address.ui.ListPanelType;

public class SortAppointmentCommandTest {

    @Test
    public void constructor_nullComparatorType_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortAppointmentCommand(null));
    }

    @Test
    public void execute_sortByComparatorType_successful() {
        ModelStubAcceptingComparator modelStub = new ModelStubAcceptingComparator();

        CommandResult commandResult = new SortAppointmentCommand(AppointmentComparatorType.UNORDERED)
                .execute(modelStub);
        assertEquals(commandResult,
                new CommandResult(SortAppointmentCommand.MESSAGE_SUCCESS_UNORDERED, ListPanelType.APPOINTMENT));

        commandResult = new SortAppointmentCommand(AppointmentComparatorType.ALPHABETICAL).execute(modelStub);
        assertEquals(commandResult,
                new CommandResult(SortAppointmentCommand.MESSAGE_SUCCESS_ALPHABETICAL, ListPanelType.APPOINTMENT));
    }

    @Test
    public void equals() {
        SortAppointmentCommand sortInsertion = new SortAppointmentCommand(AppointmentComparatorType.UNORDERED);
        SortAppointmentCommand sortAlphabetical = new SortAppointmentCommand(AppointmentComparatorType.ALPHABETICAL);

        // same object -> returns true
        assertTrue(sortInsertion.equals(sortInsertion));

        // same values -> returns true
        SortAppointmentCommand sortInsertionCopy = new SortAppointmentCommand(AppointmentComparatorType.UNORDERED);
        assertTrue(sortInsertion.equals(sortInsertionCopy));

        // different types -> returns false
        assertFalse(sortInsertion.equals(1));

        // null -> returns false
        assertFalse(sortInsertion.equals(null));

        // different comparator type -> returns false
        assertFalse(sortInsertion.equals(sortAlphabetical));
    }

    @Test
    public void toStringMethod() {
        AppointmentComparatorType comparatorType = AppointmentComparatorType.UNORDERED;
        SortAppointmentCommand sortAppointmentCommand = new SortAppointmentCommand(comparatorType);
        String expected = SortAppointmentCommand.class.getCanonicalName() + "{comparatorType=" + comparatorType + "}";
        assertEquals(expected, sortAppointmentCommand.toString());
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingComparator extends ModelStub {
        @Override
        public void sortAppointments(Comparator<Appointment> comparator) {

        }
    }
}
