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

    //Used to round off to 2 decimal places
    private static final int SCALE = 2;

    public final BigDecimal value;

    /**
     * Constructs a {@code ContractPremium}.
     *
     * @param value A valid premium value.
     */
    public ContractPremium(BigDecimal value) {
        requireNonNull(value);
        BigDecimal normalized = value.setScale(SCALE, RoundingMode.HALF_UP);
        checkArgument(isValidContractPremium(normalized), MESSAGE_CONSTRAINTS);
        // Rounds up to the nearest 2 decimal places
        this.value = normalized;
    }

    /**
     * Constructs a {@code ContractPremium}.
     *
     * @param value A valid premium value in string format.
     */
    public ContractPremium(String value) {
        requireNonNull(value);
        BigDecimal decimalValue = new BigDecimal(value);
        BigDecimal normalized = decimalValue.setScale(SCALE, RoundingMode.HALF_UP);
        checkArgument(isValidContractPremium(normalized), MESSAGE_CONSTRAINTS);
        // Rounds up to the nearest 2 decimal places
        this.value = normalized;
    }

    /**
     * Returns true if a given {@code BigDecimal} is a valid premium.
     */
    public static boolean isValidContractPremium(BigDecimal test) {
        if (test == null) {
            return false;
        }
        try {
            BigDecimal normalized = test.setScale(SCALE, RoundingMode.HALF_UP);
            return normalized.compareTo(BigDecimal.ZERO) >= 0;
        } catch (ArithmeticException e) {
            return false;
        }
    }

    /**
     * Returns true if a given {@code String} is a valid premium.
     */
    public static boolean isValidContractPremium(String test) {
        if (test == null) {
            return false;
        }
        try {
            BigDecimal value = new BigDecimal(test);
            BigDecimal normalized = value.setScale(SCALE, RoundingMode.HALF_UP);
            return normalized.compareTo(BigDecimal.ZERO) >= 0;
        } catch (NumberFormatException | ArithmeticException e) {
            return false;
        }
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

    @Override
    public int hashCode() {
        return value.stripTrailingZeros().hashCode();
    }
}
