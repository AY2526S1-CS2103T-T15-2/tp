package seedu.address.testutil;

import java.time.LocalDate;

import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentDetails;
import seedu.address.model.appointment.AppointmentId;
import seedu.address.model.person.Nric;

/**
 * A utility class to help with building EditAppointmentDescriptor objects.
 */
public class EditAppointmentDescriptorBuilder {

    private EditAppointmentDescriptor descriptor;

    public EditAppointmentDescriptorBuilder() {
        descriptor = new EditAppointmentDescriptor();
    }

    /**
     * Returns an {@code EditAppointmentDescriptor} with fields containing {@code appointment}'s details
     */
    public EditAppointmentDescriptorBuilder(Appointment appointment) {
        descriptor = new EditAppointmentDescriptor();
        descriptor.setAId(appointment.getAId());
        descriptor.setNric(appointment.getNric());
        descriptor.setDate(appointment.getDate());
        descriptor.setDetails(appointment.getDetails());
    }

    /**
     * Sets the {@code Appointment Id} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withAId(String appointmentId) {
        descriptor.setAId(new AppointmentId(appointmentId));
        return this;
    }

    /**
     * Sets the {@code Appointment Nric} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withNric(String appointmentNric) {
        descriptor.setNric(new Nric(appointmentNric));
        return this;
    }

    /**
     * Sets the {@code Appointment Date} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withDate(String appointmentDate) {
        descriptor.setDate(LocalDate.parse(appointmentDate));
        return this;
    }

    /**
     * Sets the {@code Appointment Details} of the {@code EditAppointmentDescriptor} that we are building.
     */
    public EditAppointmentDescriptorBuilder withDetails(String appointmentDetails) {
        descriptor.setDetails(new AppointmentDetails(appointmentDetails));
        return this;
    }

    public EditAppointmentDescriptor build() {
        return descriptor;
    }
}
