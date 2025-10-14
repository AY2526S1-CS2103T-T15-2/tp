package seedu.address.model.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PolicyBuilder;

public class IdContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("abcdef");
        List<String> secondPredicateKeywordList = Arrays.asList("abcdef", "xyz123");

        IdContainsKeywordsPredicate firstPredicate = new IdContainsKeywordsPredicate(firstPredicateKeywordList);
        IdContainsKeywordsPredicate secondPredicate = new IdContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        IdContainsKeywordsPredicate firstPredicateCopy = new IdContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_idContainsKeywords_returnsTrue() {
        // One keyword
        IdContainsKeywordsPredicate predicate = new IdContainsKeywordsPredicate(Collections.singletonList("abcdef"));
        assertTrue(predicate.test(new PolicyBuilder().withId("abcdef").build()));

        // Multiple keywords
        predicate = new IdContainsKeywordsPredicate(Arrays.asList("abcdef", "xyz123"));
        assertTrue(predicate.test(new PolicyBuilder().withId("abcdef").build()));
        assertTrue(predicate.test(new PolicyBuilder().withId("xyz123").build()));

        // Only one matching keyword
        predicate = new IdContainsKeywordsPredicate(Arrays.asList("abcdef", "hijklm"));
        assertTrue(predicate.test(new PolicyBuilder().withId("abcdef").build()));

        // Partial matching keyword
        predicate = new IdContainsKeywordsPredicate(Arrays.asList("abc", "123"));
        assertTrue(predicate.test(new PolicyBuilder().withId("abcdef").build()));
        assertTrue(predicate.test(new PolicyBuilder().withId("xyz123").build()));
    }

    @Test
    public void test_idDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        IdContainsKeywordsPredicate predicate = new IdContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PolicyBuilder().withId("abcdef").build()));

        // Non-matching keyword
        predicate = new IdContainsKeywordsPredicate(Arrays.asList("xyz123"));
        assertFalse(predicate.test(new PolicyBuilder().withId("abcdef").build()));

        // Mismatch Casing Keyword
        predicate = new IdContainsKeywordsPredicate(Arrays.asList("ABCDEF"));
        assertFalse(predicate.test(new PolicyBuilder().withId("abcdef").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("key123", "keyabc");
        IdContainsKeywordsPredicate predicate = new IdContainsKeywordsPredicate(keywords);

        String expected = IdContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
