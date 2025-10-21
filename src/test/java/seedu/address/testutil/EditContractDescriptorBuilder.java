package seedu.address.testutil;

import java.time.LocalDate;

import seedu.address.logic.commands.EditContractCommand.EditContractDescriptor;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.policy.PolicyId;

/**
 * A utility class to help with building EditContractDescriptor objects.
 */
public class EditContractDescriptorBuilder {

    private EditContractDescriptor descriptor;

    public EditContractDescriptorBuilder() {
        descriptor = new EditContractDescriptor();
    }

    public EditContractDescriptorBuilder(EditContractDescriptor descriptor) {
        this.descriptor = new EditContractDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditContractDescriptor} with fields containing {@code Contract}'s details
     */
    public EditContractDescriptorBuilder(Contract contract) {
        descriptor = new EditContractDescriptor();
        descriptor.setCId(contract.getCId());
        descriptor.setPId(contract.getPId());
        descriptor.setNric(contract.getNric());
        descriptor.setName(contract.getName());
        descriptor.setDate(contract.getDate());
        descriptor.setExpiryDate(contract.getExpiryDate());
    }

    /**
     * Sets the {@code cId} of the {@code EditContractDescriptor} that we are building.
     */
    public EditContractDescriptorBuilder withCId(String cId) {
        descriptor.setCId(new ContractId(cId));
        return this;
    }

    /**
     * Sets the {@code pId} of the {@code EditContractDescriptor} that we are building.
     */
    public EditContractDescriptorBuilder withPId(String pId) {
        descriptor.setPId(new PolicyId(pId));
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code EditContractDescriptor} that we are building.
     */
    public EditContractDescriptorBuilder withNric(String nric) {
        descriptor.setNric(new Nric(nric));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditContractDescriptor} that we are building.
     */
    public EditContractDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code date} of the {@code EditContractDescriptor} that we are building.
     */
    public EditContractDescriptorBuilder withDate(String date) {
        descriptor.setDate(LocalDate.parse(date));
        return this;
    }

    /**
     * Sets the {@code date} of the {@code EditContractDescriptor} that we are building.
     */
    public EditContractDescriptorBuilder withExpiryDate(String date) {
        descriptor.setExpiryDate(LocalDate.parse(date));
        return this;
    }

    public EditContractDescriptor build() {
        return descriptor;
    }
}
