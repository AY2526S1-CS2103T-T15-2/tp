package seedu.address.testutil;

import java.util.List;

import seedu.address.model.person.NricContainsKeywordsPredicate;

/**
 * A utility class containing a list of {@code NricContainsKeywordsPredicate} objects to be used in tests.
 */
public class TypicalNricPredicates {
    public static final NricContainsKeywordsPredicate PREDICATE_FIRST =
            new NricContainsKeywordsPredicate(List.of("S1234567A"));
    public static final NricContainsKeywordsPredicate PREDICATE_SECOND =
            new NricContainsKeywordsPredicate(List.of("S1234567B"));
    public static final NricContainsKeywordsPredicate PREDICATE_THIRD =
            new NricContainsKeywordsPredicate(List.of("S1234567C"));
}
