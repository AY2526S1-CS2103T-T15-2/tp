package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PolicyBuilder;

public class PolicyIdContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("abcdef");
        List<String> secondPredicateKeywordList = Arrays.asList("abcdef", "xyz123");

        PolicyIdContainsKeywordsPredicate firstPredicate =
                new PolicyIdContainsKeywordsPredicate(firstPredicateKeywordList);
        PolicyIdContainsKeywordsPredicate secondPredicate =
                new PolicyIdContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PolicyIdContainsKeywordsPredicate firstPredicateCopy =
                new PolicyIdContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different contact -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_idContainsKeywords_returnsTrue() {
        // One keyword
        PolicyIdContainsKeywordsPredicate predicate =
                new PolicyIdContainsKeywordsPredicate(Collections.singletonList("abcdef"));
        assertTrue(predicate.test(new PolicyBuilder().withId("abcdef").build()));

        // Multiple keywords
        predicate = new PolicyIdContainsKeywordsPredicate(Arrays.asList("abcdef", "xyz123"));
        assertTrue(predicate.test(new PolicyBuilder().withId("abcdef").build()));
        assertTrue(predicate.test(new PolicyBuilder().withId("xyz123").build()));

        // Only one matching keyword
        predicate = new PolicyIdContainsKeywordsPredicate(Arrays.asList("abcdef", "hijklm"));
        assertTrue(predicate.test(new PolicyBuilder().withId("abcdef").build()));

        // Partial matching keyword
        predicate = new PolicyIdContainsKeywordsPredicate(Arrays.asList("abc", "123"));
        assertTrue(predicate.test(new PolicyBuilder().withId("abcdef").build()));
        assertTrue(predicate.test(new PolicyBuilder().withId("xyz123").build()));
    }

    @Test
    public void test_idDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PolicyIdContainsKeywordsPredicate predicate = new PolicyIdContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PolicyBuilder().withId("abcdef").build()));

        // Non-matching keyword
        predicate = new PolicyIdContainsKeywordsPredicate(Arrays.asList("xyz123"));
        assertFalse(predicate.test(new PolicyBuilder().withId("abcdef").build()));

        // Mismatch Casing Keyword
        predicate = new PolicyIdContainsKeywordsPredicate(Arrays.asList("ABCDEF"));
        assertFalse(predicate.test(new PolicyBuilder().withId("abcdef").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("key123", "keyabc");
        PolicyIdContainsKeywordsPredicate predicate = new PolicyIdContainsKeywordsPredicate(keywords);

        String expected = PolicyIdContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
