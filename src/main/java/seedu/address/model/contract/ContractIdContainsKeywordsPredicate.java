package seedu.address.model.contract;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Contract}'s {@code CId} matches any of the keywords given.
 */
public class ContractIdContainsKeywordsPredicate implements Predicate<Contract> {
    private final List<String> keywords;

    public ContractIdContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public boolean test(Contract contract) {
        return keywords.stream()
                .anyMatch(keyword -> contract.getCId().value.contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ContractIdContainsKeywordsPredicate)) {
            return false;
        }

        ContractIdContainsKeywordsPredicate cIdContainsKeywordsPredicate = (ContractIdContainsKeywordsPredicate) other;
        return keywords.equals(cIdContainsKeywordsPredicate.keywords);

    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("keywords", keywords)
                .toString();
    }

}
