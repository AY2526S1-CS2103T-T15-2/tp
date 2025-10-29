package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.AppointmentBuilder;

public class AppointmentIdContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("abcdef");
        List<String> secondPredicateKeywordList = Arrays.asList("abcdef", "xyz123");

        AppointmentIdContainsKeywordsPredicate firstPredicate =
                new AppointmentIdContainsKeywordsPredicate(firstPredicateKeywordList);
        AppointmentIdContainsKeywordsPredicate secondPredicate =
                new AppointmentIdContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AppointmentIdContainsKeywordsPredicate firstPredicateCopy =
                new AppointmentIdContainsKeywordsPredicate(firstPredicateKeywordList);
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
        AppointmentIdContainsKeywordsPredicate predicate =
                new AppointmentIdContainsKeywordsPredicate(Collections.singletonList("abcdef"));
        assertTrue(predicate.test(new AppointmentBuilder().withId("abcdef").build()));

        // Multiple keywords
        predicate = new AppointmentIdContainsKeywordsPredicate(Arrays.asList("abcdef", "xyz123"));
        assertTrue(predicate.test(new AppointmentBuilder().withId("abcdef").build()));
        assertTrue(predicate.test(new AppointmentBuilder().withId("xyz123").build()));

        // Only one matching keyword
        predicate = new AppointmentIdContainsKeywordsPredicate(Arrays.asList("abcdef", "hijklm"));
        assertTrue(predicate.test(new AppointmentBuilder().withId("abcdef").build()));

        // Partial matching keyword
        predicate = new AppointmentIdContainsKeywordsPredicate(Arrays.asList("abc", "123"));
        assertTrue(predicate.test(new AppointmentBuilder().withId("abcdef").build()));
        assertTrue(predicate.test(new AppointmentBuilder().withId("xyz123").build()));
    }

    @Test
    public void test_idDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AppointmentIdContainsKeywordsPredicate predicate =
                new AppointmentIdContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new AppointmentBuilder().withId("abcdef").build()));

        // Non-matching keyword
        predicate = new AppointmentIdContainsKeywordsPredicate(Arrays.asList("xyz123"));
        assertFalse(predicate.test(new AppointmentBuilder().withId("abcdef").build()));

        // Mismatch Casing Keyword
        predicate = new AppointmentIdContainsKeywordsPredicate(Arrays.asList("ABCDEF"));
        assertFalse(predicate.test(new AppointmentBuilder().withId("abcdef").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("key123", "keyabc");
        AppointmentIdContainsKeywordsPredicate predicate = new AppointmentIdContainsKeywordsPredicate(keywords);

        String expected = AppointmentIdContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
