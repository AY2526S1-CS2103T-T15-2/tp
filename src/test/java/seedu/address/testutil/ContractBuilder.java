package seedu.address.testutil;

import java.time.LocalDate;

import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.policy.PolicyId;

/**
 * A utility class to help with building Contract objects.
 */
public class ContractBuilder {

    public static final String DEFAULT_CID = "C1234A";
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_NRIC = "S1234567A";
    public static final String DEFAULT_POLICY_ID = "P1234A";
    public static final LocalDate DEFAULT_DATE = LocalDate.parse("2023-01-01");
    public static final LocalDate DEFAULT_EXPIRY = LocalDate.parse("2024-01-01");

    private ContractId cId;
    private Name name;
    private Nric nric;
    private PolicyId pId;
    private LocalDate dateSigned;
    private LocalDate expiry;

    /**
     * Creates a {@code ContractBuilder} with the default details.
     */
    public ContractBuilder() {
        cId = new ContractId(DEFAULT_CID);
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        pId = new PolicyId(DEFAULT_POLICY_ID);
        dateSigned = DEFAULT_DATE;
        expiry = DEFAULT_EXPIRY;
    }

    /**
     * Initializes the ContractBuilder with the data of {@code contractToCopy}.
     */
    public ContractBuilder(Contract contractToCopy) {
        cId = contractToCopy.getCId();
        name = contractToCopy.getName();
        nric = contractToCopy.getNric();
        pId = contractToCopy.getPId();
        dateSigned = contractToCopy.getDate();
        expiry = contractToCopy.getExpiryDate();
    }

    /**
     * Sets the {@code Name} of the {@code Contract} that we are building.
     */
    public ContractBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Contract} that we are building.
     */
    public ContractBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code PolicyId} of the {@code Contract} that we are building.
     */
    public ContractBuilder withPolicyId(String pId) {
        this.pId = new PolicyId(pId);
        return this;
    }

    /**
     * Sets the {@code LocalDate} of the {@code Contract} that we are building.
     */
    public ContractBuilder withDate(LocalDate date) {
        this.dateSigned = date;
        return this;
    }

    /**
     * Sets the {@code LocalDate} expiry date of the {@code Contract} that we are building.
     */
    public ContractBuilder withExpiryDate(LocalDate expiry) {
        this.expiry = expiry;
        return this;
    }

    /**
     * Sets the {@code ContractId} of the {@code Contract} that we are building.
     */
    public ContractBuilder withCId(String cId) {
        this.cId = new ContractId(cId);
        return this;
    }

    public Contract build() {
        return new Contract(cId, name, nric, pId, dateSigned, expiry);
    }
}
