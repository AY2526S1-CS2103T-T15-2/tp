package seedu.address.model.contract;

import java.time.LocalDate;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.contract.exceptions.InvalidContractDatesException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.policy.PolicyId;

/**
 * Represents a Contract in the iCon.
 */
public class Contract {

    private final ContractId cId;
    private final Name name;
    private final Nric nric;
    private final PolicyId pId;
    private final LocalDate dateSigned;
    private final LocalDate expiryDate;

    /**
     * Constructor for a Creating a new Contract object.
     * @param nric NRIC of policy holder
     * @param pId ID of policy
     * @param date Date the contract was signed
     */
    public Contract(Nric nric, PolicyId pId, LocalDate date, LocalDate expiry) {
        this.cId = ContractId.generate();
        this.name = new Name("Placeholder");
        this.nric = nric;
        this.pId = pId;
        if (date.isBefore(expiry)) {
            this.dateSigned = date;
            this.expiryDate = expiry;
        } else {
            throw new InvalidContractDatesException();
        }
    }

    /**
     * Constructor for a Contract object with all fields specified for JSON.
     * @param cId Contract ID
     * @param name Name of policyholder
     * @param nric NRIC of policyholder
     * @param pId ID of policy
     * @param date Date the contract was signed
     */
    public Contract(ContractId cId, Name name, Nric nric, PolicyId pId, LocalDate date, LocalDate expiry) {
        this.cId = cId;
        this.name = name;
        this.nric = nric;
        this.pId = pId;
        if (date.isBefore(expiry)) {
            this.dateSigned = date;
            this.expiryDate = expiry;
        } else {
            throw new InvalidContractDatesException();
        }
    }

    public ContractId getCId() {
        return cId;
    }

    public Name getName() {
        return name;
    }

    public Nric getNric() {
        return nric;
    }

    public PolicyId getPId() {
        return pId;
    }

    public LocalDate getDate() {
        return dateSigned;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    /**
     * Checks if the given contract is the same as this contract.
     */
    public boolean isSameContract(Contract otherContract) {
        if (otherContract == this) {
            return true;
        }

        return otherContract != null
                && otherContract.getNric().equals(getNric())
                && otherContract.getPId().equals(getPId())
                && otherContract.getDate().equals(getDate())
                && otherContract.getExpiryDate().equals(getExpiryDate());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Contract)) {
            return false;
        }

        Contract otherContract = (Contract) other;
        return otherContract.getCId().equals(getCId())
                && otherContract.getName().equals(getName())
                && otherContract.getNric().equals(getNric())
                && otherContract.getPId().equals(getPId())
                && otherContract.getDate().equals(getDate())
                && otherContract.getExpiryDate().equals(getExpiryDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(cId, name, nric, pId, dateSigned, expiryDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("cId", cId)
                .add("name", name)
                .add("nric", nric)
                .add("pId", pId)
                .add("dateSigned", dateSigned)
                .add("expiryDate", expiryDate)
                .toString();
    }

}
