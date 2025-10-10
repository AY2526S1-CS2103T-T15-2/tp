package seedu.address.testutil;

import java.text.SimpleDateFormat;

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
    public static final SimpleDateFormat DEFAULT_DATE = new SimpleDateFormat();

    private ContractId cId;
    private Name name;
    private Nric nric;
    private PolicyId pId;
    private SimpleDateFormat dateSigned;

    /**
     * Creates a {@code ContractBuilder} with the default details.
     */
    public ContractBuilder() {
        cId = new ContractId(DEFAULT_CID);
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        pId = new PolicyId(DEFAULT_POLICY_ID);
        dateSigned = DEFAULT_DATE;
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
     * Sets the {@code SimpleDateFormat} of the {@code Contract} that we are building.
     */
    public ContractBuilder withDate(SimpleDateFormat date) {
        this.dateSigned = date;
        return this;
    }

    public Contract build() {
        return new Contract(cId, name, nric, pId, dateSigned);
    }
}
