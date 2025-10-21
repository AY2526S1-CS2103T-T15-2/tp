package seedu.address.model.appointment;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Appointment}'s {@code Id} matches any of the keywords given.
 */
public class AppointmentIdContainsKeywordsPredicate implements Predicate<Appointment> {
    private final List<String> keywords;

    public AppointmentIdContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public boolean test(Appointment appointment) {
        return keywords.stream()
                .anyMatch(keyword -> appointment.getAId().value.contains(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentIdContainsKeywordsPredicate)) {
            return false;
        }

        AppointmentIdContainsKeywordsPredicate otherIdContainsKeywordsPredicate =
                (AppointmentIdContainsKeywordsPredicate) other;
        return keywords.equals(otherIdContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}

