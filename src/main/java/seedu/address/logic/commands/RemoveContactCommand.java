package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CONTACTS;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.NricContainsKeywordsPredicate;
import seedu.address.ui.ListPanelType;

/**
 * Deletes a contact identified using it's displayed index from the address book.
 */
public class RemoveContactCommand extends Command {

    public static final String COMMAND_WORD = "remove_contact";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the contact identified by the NRIC used in iCon.\n"
            + "Parameters: " + PREFIX_NRIC + " NRIC \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NRIC + " S1234567A";

    public static final String MESSAGE_DELETE_CONTACT_SUCCESS = "Deleted Contact: %1$s";

    public static final String MESSAGE_DELETE_CONTACT_FAILURE = "Could not delete contact "
            + "since no such NRIC exists within current contact list.";
    public static final String MESSAGE_REMOVE_CONTACT_PENDING = "Contracts exists under this contact. "
            + "Please remove before proceeding: %1$s";
    public static final String MESSAGE_REMOVE_CONTACT_HAS_APPOINTMENT = "Appointments exists under this contact. "
            + "Please remove before proceeding: %1$s";

    private final NricContainsKeywordsPredicate predicate;

    public RemoveContactCommand(NricContainsKeywordsPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
        List<Contact> lastShownList = model.getFilteredContactList();

        int index = findIndex(lastShownList, predicate);
        if (index == -1) {
            throw new CommandException(MESSAGE_DELETE_CONTACT_FAILURE);
        }
        Index contactIndex = Index.fromZeroBased(index);
        Contact contact = lastShownList.get(contactIndex.getZeroBased());

        //check for existing contracts
        if (!contact.getContracts().isEmpty()) {
            String existingContractIds = contact.getContractIdsAsString();
            throw new CommandException(String.format(MESSAGE_REMOVE_CONTACT_PENDING, existingContractIds));
        }
        //check existing appointments
        List<Appointment> appointmentList = getAppointmentListUnderContact(model, contact);
        if (!appointmentList.isEmpty()) {
            String existingAppointmentIds = stringifyAppointmentIds(appointmentList);
            throw new CommandException(String.format(MESSAGE_REMOVE_CONTACT_HAS_APPOINTMENT, existingAppointmentIds));
        }

        model.deleteContact(contact);
        return new CommandResult(String.format(MESSAGE_DELETE_CONTACT_SUCCESS,
                contact.getName() + " " + contact.getNric()), ListPanelType.CONTACT);
    }

    private static int findIndex(List<Contact> list, NricContainsKeywordsPredicate predicate) {
        return IntStream.range(0, list.size())
                .filter(i -> predicate.test(list.get(i)))
                .findFirst()
                .orElse(-1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveContactCommand)) {
            return false;
        }

        RemoveContactCommand otherRemoveContactCommand = (RemoveContactCommand) other;
        return predicate.equals(otherRemoveContactCommand.predicate);
    }

    private String stringifyAppointmentIds(List<Appointment> appointmentList) {
        return appointmentList.stream()
                .map(Appointment::getAId)
                .map(AppointmentId::toString)
                .collect(Collectors.joining(", "));
    }

    private List<Appointment> getAppointmentListUnderContact(Model model, Contact contact) {
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        ObservableList<Appointment> appointmentList = model.getFilteredAppointmentList();
        return appointmentList.stream()
                .filter(a -> a.getNric().equals(contact.getNric()))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
