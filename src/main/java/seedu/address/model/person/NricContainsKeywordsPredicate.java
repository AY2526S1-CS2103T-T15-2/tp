package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Nric} matches any of the keywords given.
 */
public class NricContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public NricContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getNric().nric, keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NricContainsKeywordsPredicate)) {
            return false;
        }

        NricContainsKeywordsPredicate otherNricContainsKeywordsPredicate = (NricContainsKeywordsPredicate) other;
        return keywords.equals(otherNricContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
