package seedu.address.model.policy;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Policy}'s {@code Id} matches any of the keywords given.
 */
public class IdContainsKeywordsPredicate implements Predicate<Policy> {
    private final List<String> keywords;

    public IdContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public boolean test(Policy policy) {
        return keywords.stream()
                .anyMatch(keyword -> policy.getId().value.contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IdContainsKeywordsPredicate)) {
            return false;
        }

        IdContainsKeywordsPredicate otherIdContainsKeywordsPredicate = (IdContainsKeywordsPredicate) other;
        return keywords.equals(otherIdContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}