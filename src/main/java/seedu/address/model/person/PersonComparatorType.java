package seedu.address.model.person;

import java.util.Comparator;

/**
 * Enumeration for available comparators to be used by SortContactCommand.
 */
public enum PersonComparatorType {
    UNORDERED(null),
    ALPHABETICAL(Person::compareNameAlphabetical);

    public final Comparator<Person> comparator;

    PersonComparatorType(Comparator<Person> comparator) {
        this.comparator = comparator;
    }
}
