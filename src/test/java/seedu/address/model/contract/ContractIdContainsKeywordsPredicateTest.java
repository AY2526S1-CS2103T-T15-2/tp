package seedu.address.model.contract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ContractBuilder;

public class ContractIdContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("C1234A");
        List<String> secondPredicateKeywordList = Arrays.asList("C1234A", "C1234B");

        ContractIdContainsKeywordsPredicate firstPredicate =
                new ContractIdContainsKeywordsPredicate(firstPredicateKeywordList);
        ContractIdContainsKeywordsPredicate secondPredicate =
                new ContractIdContainsKeywordsPredicate(secondPredicateKeywordList);

        //same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        //same values -> returns true
        ContractIdContainsKeywordsPredicate firstPredicateCopy =
                new ContractIdContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        //different types -> returns false
        assertFalse(firstPredicate.equals(1));

        //null -> returns false
        assertFalse(firstPredicate.equals(null));

        //different contact -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_contractIdContainsKeywords_returnsTrue() {
        // One keyword
        ContractIdContainsKeywordsPredicate predicate =
                new ContractIdContainsKeywordsPredicate(Collections.singletonList("C1234A"));
        assertTrue(predicate.test(new ContractBuilder().withCId("C1234A").build()));

        // Multiple keywords
        predicate = new ContractIdContainsKeywordsPredicate(Arrays.asList("C1234A", "C1234B"));
        assertTrue(predicate.test(new ContractBuilder().withCId("C1234A").build()));
        assertTrue(predicate.test(new ContractBuilder().withCId("C1234B").build()));

        // Only one matching keyword
        predicate = new ContractIdContainsKeywordsPredicate(Arrays.asList("C9999Z", "C1234A"));
        assertTrue(predicate.test(new ContractBuilder().withCId("C1234A").build()));

        // Partial matching keyword
        predicate = new ContractIdContainsKeywordsPredicate(Arrays.asList("C12", "34B"));
        assertTrue(predicate.test(new ContractBuilder().withCId("C1234A").build()));
        assertTrue(predicate.test(new ContractBuilder().withCId("C1234B").build()));
    }

    @Test
    public void test_idDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContractIdContainsKeywordsPredicate predicate =
                new ContractIdContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ContractBuilder().withCId("C1234A").build()));

        // Non-matching keyword
        predicate = new ContractIdContainsKeywordsPredicate(Arrays.asList("C9999Z"));
        assertFalse(predicate.test(new ContractBuilder().withCId("C1234A").build()));

        // Mismatch Casing keyword
        predicate = new ContractIdContainsKeywordsPredicate(Arrays.asList("C1234A"));
        assertFalse(predicate.test(new ContractBuilder().withCId("abcdef").build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = Arrays.asList("C1234A", "C1234B");
        ContractIdContainsKeywordsPredicate predicate =
                new ContractIdContainsKeywordsPredicate(keywords);
        String expected = ContractIdContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
