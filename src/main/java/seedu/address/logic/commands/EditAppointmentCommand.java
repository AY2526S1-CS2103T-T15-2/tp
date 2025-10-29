package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DETAILS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.time.LocalDate;
import java.util.Optional;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDetails;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.person.Nric;
import seedu.address.ui.ListPanelType;

/**
 * Edits the details of an existing appointment in the address book.
 */
public class EditAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "edit_appointment";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the appointment identified "
            + "by the appointment id. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters:"
            + PREFIX_NRIC + "NRIC "
            + PREFIX_DATE + "DATE "
            + PREFIX_DETAILS + "DETAILS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_AID + "A1234A" + " "
            + PREFIX_DATE + "2020-10-10" + " "
            + PREFIX_DETAILS + "Run through Health coverage";

    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Edited Appointment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in iCon.";

    private final AppointmentId aId;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    /**
     * @param aId of the appointment in the filtered appointment list to edit
     * @param editAppointmentDescriptor details to edit the person with
     */
    public EditAppointmentCommand(AppointmentId aId, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireNonNull(aId);
        requireNonNull(editAppointmentDescriptor);

        this.aId = aId;
        this.editAppointmentDescriptor = editAppointmentDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(aId);

        if (!model.hasAppointment(aId)) {
            throw new CommandException(String.format(Messages.MESSAGE_APPOINTMENT_NOT_FOUND, aId));
        }
        Appointment appointmentToEdit = model.getAppointment(aId);
        Appointment editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor, model);

        if (!appointmentToEdit.isSameAppointment(editedAppointment) && model.hasAppointment(editedAppointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        model.setAppointment(appointmentToEdit, editedAppointment);
        model.updateFilteredAppointmentList(Model.PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS,
            editedAppointment.getAId().toString()), ListPanelType.APPOINTMENT);
    }

    /**
     * Creates and returns an {@code Appointment} with the details of {@code appointmentToEdit}
     * edited with {@code editaAppointmentDescriptor}.
     */
    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
                                                       EditAppointmentDescriptor editAppointmentDescriptor,
                                                       Model model) throws CommandException {
        assert appointmentToEdit != null;

        AppointmentId currentId = appointmentToEdit.getAId();
        Nric updatedNric = editAppointmentDescriptor.getNric(model).orElse(appointmentToEdit.getNric());
        LocalDate updatedAppointmentDate = editAppointmentDescriptor.getDate().orElse(appointmentToEdit.getDate());
        AppointmentDetails updatedDetails = editAppointmentDescriptor.getDetails()
                .orElse(appointmentToEdit.getDetails());

        return new Appointment(currentId, updatedNric, updatedAppointmentDate, updatedDetails);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAppointmentCommand)) {
            return false;
        }

        EditAppointmentCommand otherEditAppointmentCommand = (EditAppointmentCommand) other;

        return aId.equals(otherEditAppointmentCommand.aId)
                && editAppointmentDescriptor.equals(otherEditAppointmentCommand.editAppointmentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("aId", aId)
                .add("editAppointmentDescriptor", editAppointmentDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the appointment with. Each non-empty field value will replace the
     * corresponding field value of the appointment.
     */
    public static class EditAppointmentDescriptor {
        private AppointmentId aId;
        private Nric nric;
        private LocalDate appDate;
        private AppointmentDetails details;

        public EditAppointmentDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setAId(toCopy.aId);
            setNric(toCopy.nric);
            setDate(toCopy.appDate);
            setDetails(toCopy.details);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(nric, appDate, details);
        }

        public void setAId(AppointmentId aId) {
            this.aId = aId;
        }

        public Optional<AppointmentId> getAId() {
            return Optional.ofNullable(aId);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public Optional<Nric> getNric(Model model) throws CommandException {
            // Requires precondition null checker as hasPerson requires non null
            if (nric == null) {
                return Optional.ofNullable(nric);
            }
            if (!model.hasPerson(nric)) {
                throw new CommandException(Messages.MESSAGE_PERSON_NOT_FOUND);
            }
            return Optional.ofNullable(nric);
        }

        public void setDate(LocalDate appDate) {
            this.appDate = appDate;
        }

        public Optional<LocalDate> getDate() {
            return Optional.ofNullable(appDate);
        }

        public void setDetails(AppointmentDetails details) {
            this.details = details;
        }

        public Optional<AppointmentDetails> getDetails() {
            return Optional.ofNullable(details);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentDescriptor)) {
                return false;
            }

            EditAppointmentDescriptor otherEditAppointmentDescriptor = (EditAppointmentDescriptor) other;

            return getAId().equals(otherEditAppointmentDescriptor.getAId())
                    && getNric().equals(otherEditAppointmentDescriptor.getNric())
                    && getDate().equals(otherEditAppointmentDescriptor.getDate())
                    && getDetails().equals(otherEditAppointmentDescriptor.getDetails());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("nric", nric)
                    .add("appDate", appDate)
                    .add("details", details)
                    .toString();
        }
    }
}
