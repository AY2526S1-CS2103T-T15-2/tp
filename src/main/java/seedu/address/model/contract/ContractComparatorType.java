package seedu.address.model.contract;

import java.util.Comparator;

/**
 * Enum to represent different types of contract comparators.
 */
public enum ContractComparatorType {
    UNORDERED(null),
    EXPIRY_DATE(Contract::compareByExpiryDate);

    public final Comparator<Contract> comparator;

    ContractComparatorType(Comparator<Contract> comparator) {
        this.comparator = comparator;
    }

}
