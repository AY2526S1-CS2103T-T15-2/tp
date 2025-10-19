package seedu.address.storage;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractId;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.policy.PolicyId;

/**
 * Jackson-friendly version of {@link Contract}.
 */
public class JsonAdaptedContract {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Contract's %s field is missing!";

    private final String cId;
    private final String name;
    private final String nric;
    private final String pId;
    private final String dateSigned;
    private final String expiryDate;

    /**
     * Constructs a {@code JsonAdaptedContract} with the given contract details.
     */
    @JsonCreator
    public JsonAdaptedContract(@JsonProperty("cId") String cId, @JsonProperty("name") String name,
                               @JsonProperty("nric") String nric, @JsonProperty("pId") String pId,
                               @JsonProperty("dateSigned") String dateSigned,
                               @JsonProperty("expiryDate") String expiryDate) {
        this.cId = cId;
        this.name = name;
        this.nric = nric;
        this.pId = pId;
        this.dateSigned = dateSigned;
        this.expiryDate = expiryDate;
    }

    /**
     * Converts a given {@code Contract} into this class for Jackson use.
     */
    public JsonAdaptedContract(Contract source) {
        cId = source.getCId().toString();
        name = source.getName().fullName;
        nric = source.getNric().toString();
        pId = source.getPId().toString();
        dateSigned = source.getDate().toString();
        expiryDate = source.getExpiryDate().toString();
    }

    /**
     * Converts this Jackson-friendly adapted contract object into the model's {@code Contract} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contract.
     */
    public Contract toModelType() throws IllegalValueException {
        if (cId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ContractId.class.getSimpleName()));
        }
        if (!ContractId.isValidContractId(cId)) {
            throw new IllegalValueException(ContractId.MESSAGE_CONSTRAINTS);
        }
        final ContractId modelCId = new ContractId(cId);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        if (pId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PolicyId.class.getSimpleName()));
        }
        if (!PolicyId.isValidPolicyId(pId)) {
            throw new IllegalValueException(PolicyId.MESSAGE_CONSTRAINTS);
        }
        final PolicyId modelPId = new PolicyId(pId);

        if (dateSigned == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDate.class.getSimpleName()));
        }
        try {
            LocalDate.parse(dateSigned);
        } catch (Exception e) {
            throw new IllegalValueException("Date should be in the format dd-MM-yyyy");
        }
        final LocalDate modelDateSigned = LocalDate.parse(dateSigned);

        try {
            LocalDate.parse(expiryDate);
        } catch (Exception e) {
            throw new IllegalValueException("Date should be in the format dd-MM-yyyy");
        }
        final LocalDate modelExpiryDate = LocalDate.parse(expiryDate);
        return new Contract(modelCId, modelName, modelNric, modelPId, modelDateSigned, modelExpiryDate);
    }
}
