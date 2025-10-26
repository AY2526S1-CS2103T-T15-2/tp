package seedu.address.logic.commands;

import java.time.LocalDate;

/**
 * Utility class that contains methods for the Command classes to use.
 */
public class CommandUtil {

    /**
     * Checks if {@code LocalDate signed} occurs before {@code LocalDate expiry}
     * @return
     */
    public static boolean isValidDateSignedAndExpiry(LocalDate signed, LocalDate expiry) {
        return signed.isBefore(expiry);
    }
}
