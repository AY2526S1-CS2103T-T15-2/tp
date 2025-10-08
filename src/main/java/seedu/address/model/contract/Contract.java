package seedu.address.model.contract;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;

import java.util.Date;

//import seedu.address.model.person.Nric;
//import seedu.address.model.policy.Id;

/**
 * Represents a Contract in the iCon.
 */
public class Contract {

    private final Id cId
    private final Name name;
    private final Nric nric;
    private final Id pId;
    private final Date dateSigned;


    /**
     * Constructor for a Contract object.
     * @param name Name of policy holder
     * @param nric NRIC of policy holder
     * @param pId ID of policy
     */
    public Contract(Name name, Nric nric, Id pId) {
        this.name = name;
        this.nric = nric;
        this.pId = pId;
        this.dateSigned = new Date();
    }

    public Id getCId() {
        return cId;
    }

    public Name getName() {
        return name;
    }

    public Nric getNric() {
        return nric;
    }

   public Id getPId() {
        return pId;
    }

    public Date getDate() {
        return dateSigned;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("nric", nric)
                .add("pId", pId)
                .add("dateSigned", dateSigned)
                .toString();
        )

}
