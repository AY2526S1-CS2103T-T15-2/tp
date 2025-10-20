package seedu.address.model.contract;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents a Contract's premium.
 * Guarantees: immutable; is valid as declared in {@link #isValidContractPremium(BigDecimal)}
 */
public class ContractPremium {

    public static final String MESSAGE_CONSTRAINTS =
            "Contract premium should be a non-negative number.";
    public final BigDecimal value;

    /**
     * Constructs a {@code ContractPremium}.
     *
     * @param value A valid premium value.
     */
    public ContractPremium(BigDecimal value) {
        requireNonNull(value);
        checkArgument(isValidContractPremium(value), MESSAGE_CONSTRAINTS);
        // Rounds up to the nearest 2 decimal places
        this.value = value.setScale(2, RoundingMode.CEILING);
    }

    /**
     * Returns true if a given {@code BigDecimal} is a valid premium.
     */
    public static boolean isValidContractPremium(BigDecimal test) {
        requireNonNull(test);
        return test.compareTo(BigDecimal.ZERO) >= 0;
    }

    @Override
    public String toString() {
        return value.toPlainString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ContractPremium)) {
            return false;
        }

        ContractPremium otherContractPremium = (ContractPremium) other;
        return value.compareTo(otherContractPremium.value) == 0;
    }
}
