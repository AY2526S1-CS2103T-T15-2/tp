package seedu.address.model.contact;

import java.util.Comparator;

/**
 * Enumeration for available comparators to be used by SortContactCommand.
 */
public enum ContactComparatorType {
    UNORDERED(null),
    ALPHABETICAL(Contact::compareNameAlphabetical);

    public final Comparator<Contact> comparator;

    ContactComparatorType(Comparator<Contact> comparator) {
        this.comparator = comparator;
    }
}
