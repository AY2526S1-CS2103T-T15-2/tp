package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NricContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NricContainsKeywordsPredicate firstPredicate = new NricContainsKeywordsPredicate(firstPredicateKeywordList);
        NricContainsKeywordsPredicate secondPredicate = new NricContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NricContainsKeywordsPredicate firstPredicateCopy = new NricContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nricContainsKeywords_returnsTrue() {
        // One keyword
        NricContainsKeywordsPredicate predicate =
                new NricContainsKeywordsPredicate(Collections.singletonList("T1234567A"));
        assertTrue(predicate.test(new PersonBuilder().withNric("T1234567A").build()));

        // Multiple keywords
        predicate = new NricContainsKeywordsPredicate(Arrays.asList("T1234567A", "T1234567B"));
        assertTrue(predicate.test(new PersonBuilder().withNric("T1234567A").build()));

        // Mixed-case keywords
        predicate = new NricContainsKeywordsPredicate(Arrays.asList("T1234567a", "t1234567B"));
        assertTrue(predicate.test(new PersonBuilder().withNric("T1234567B").build()));
    }

    @Test
    public void test_nricDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NricContainsKeywordsPredicate predicate = new NricContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("T1234567A").build()));

        // Non-matching keyword
        predicate = new NricContainsKeywordsPredicate(Arrays.asList("T1234567A"));
        assertFalse(predicate.test(new PersonBuilder().withNric("T1234567B").build()));

        // Keywords match phone, email and address, but does not match nric
        predicate = new NricContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        NricContainsKeywordsPredicate predicate = new NricContainsKeywordsPredicate(keywords);

        String expected = NricContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
