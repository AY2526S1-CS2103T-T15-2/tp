package seedu.address.model.contract;


import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class ContractPremium {

    public static final String MESSAGE_CONSTRAINTS =
            "Contract premium should be a non-negative number.";
    public final float value;

    /**
     * Constructs a {@code ContractPremium}.
     *
     * @param value A valid premium value.
     */
    public ContractPremium(float value) {
        requireNonNull(value);
        checkArgument(isValidContractPremium(value), MESSAGE_CONSTRAINTS);
        //Rounds up to the nearest 2 decimal places
        this.value = (float) (Math.ceil(value * 100)) / 100;
    }
    /**
     * Returns true if a given float is a valid premium.
     */
    public static boolean isValidContractPremium(float test) {
        return test >= 0;
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ContractPremium)) {
            return false;
        }

        ContractPremium otherContractPremium = (ContractPremium) other;
        return value == otherContractPremium.value;
    }
}
