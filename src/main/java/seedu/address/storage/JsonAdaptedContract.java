package seedu.address.storage;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Messages;
import seedu.address.model.contact.Name;
import seedu.address.model.contact.Nric;
import seedu.address.model.contract.Contract;
import seedu.address.model.contract.ContractId;
import seedu.address.model.contract.ContractPremium;
import seedu.address.model.contract.exceptions.InvalidContractDatesException;
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
    private final String premium;

    /**
     * Constructs a {@code JsonAdaptedContract} with the given contract details.
     */
    @JsonCreator
    public JsonAdaptedContract(@JsonProperty("cId") String cId, @JsonProperty("name") String name,
                               @JsonProperty("nric") String nric, @JsonProperty("pId") String pId,
                               @JsonProperty("dateSigned") String dateSigned,
                               @JsonProperty("expiryDate") String expiryDate, @JsonProperty("premium") String premium) {
        this.cId = cId;
        this.name = name;
        this.nric = nric;
        this.pId = pId;
        this.dateSigned = dateSigned;
        this.expiryDate = expiryDate;
        this.premium = premium;
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
        premium = source.getPremium().toString();
    }

    /**
     * Converts this Jackson-friendly adapted contract object into the model's {@code Contract} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted contract.
     */
    public Contract toModelType() throws IllegalValueException {
        checkcId(cId);
        final ContractId modelCId = new ContractId(cId);

        checkName(name);
        final Name modelName = new Name(name);

        checkNric(nric);
        final Nric modelNric = new Nric(nric);

        checkPId(pId);
        final PolicyId modelPId = new PolicyId(pId);

        final LocalDate modelDateSigned = checkDateSigned(dateSigned);

        final LocalDate modelExpiryDate = checkExpiryDate(expiryDate);

        if (modelExpiryDate.isBefore(modelDateSigned)) {
            throw new InvalidContractDatesException();
        }

        checkPremium(premium);
        final ContractPremium modelPremium = new ContractPremium(premium);

        return new Contract(modelCId, modelName, modelNric, modelPId, modelDateSigned, modelExpiryDate, modelPremium);
    }

    /**
     * Checks for null and validity of cId.
     */
    private void checkcId(String cId) throws IllegalValueException {
        if (cId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    ContractId.class.getSimpleName()));
        }
        if (!ContractId.isValidContractId(cId)) {
            throw new IllegalValueException(ContractId.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Checks for null and validity of name.
     */
    private void checkName(String name) throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Checks for null and validity of nric.
     */
    private void checkNric(String nric) throws IllegalValueException {
        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Nric.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Checks for null and validity of pId.
     */
    private void checkPId(String pId) throws IllegalValueException {
        if (pId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PolicyId.class.getSimpleName()));
        }
        if (!PolicyId.isValidPolicyId(pId)) {
            throw new IllegalValueException(PolicyId.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Checks for null and validity of dateSigned.
     * Returns LocalDate if valid.
     */
    private LocalDate checkDateSigned(String dateSigned) throws IllegalValueException {
        LocalDate returnDate;
        if (dateSigned == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDate.class.getSimpleName()));
        }
        try {
            returnDate = LocalDate.parse(dateSigned);
        } catch (Exception e) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_DATE_FORMAT);
        }
        return returnDate;
    }

    /**
     * Checks for null and validity of expiryDate.
     * Returns LocalDate if valid.
     */
    private LocalDate checkExpiryDate(String expiryDate) throws IllegalValueException {
        LocalDate returnDate;
        if (expiryDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDate.class.getSimpleName()));
        }
        try {
            returnDate = LocalDate.parse(expiryDate);
        } catch (Exception e) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_DATE_FORMAT);
        }
        return returnDate;
    }

    /**
     * Checks for null and validity of premium.
     */
    private void checkPremium(String premium) throws IllegalValueException {
        if (premium == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    seedu.address.model.contract.ContractPremium.class.getSimpleName()));
        }
        if (!ContractPremium.isValidContractPremium(premium)) {
            throw new IllegalValueException(ContractPremium.MESSAGE_CONSTRAINTS);
        }
    }
}
