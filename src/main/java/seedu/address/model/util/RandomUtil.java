package seedu.address.model.util;

import java.util.Random;

/**
 * Contains utility methods for random generation.
 */
public class RandomUtil {

    private static final String ALPHANUMERIC_CHARACTERS = "abcdefghijkmnopqrstuvwxyz"
            + "ABCDEFGHJKLMNPQRSTUVWXYZ"
            + "0123456789";

    /**
     * Generates a random alphanumeric string of given length.
     */
    public static String generateAlphanum(int length) {
        Random random = new Random();
        return random.ints(0, ALPHANUMERIC_CHARACTERS.length())
                .mapToObj(ALPHANUMERIC_CHARACTERS::charAt)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
