package seedu.address.model.policy;

import java.util.Random;

/**
 * Represents a Policy's id that a user can reference.
 * Guarantees: immutable.
 */
public class Id {

    private static final String ID_CHARACTERS = "abcdefghijklmnopqrstuvwxyz"
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789";
    private static final int ID_LENGTH = 6;

    public final String value;

    private Id(String id) {
        value = id;
    }

    /**
     * Generates a random alphanumeric id to assign to a Policy.
     */
    public static Id generate() {
        Random random = new Random();
        String id = random.ints(0, ID_CHARACTERS.length())
                .map(ID_CHARACTERS::charAt)
                .limit(ID_LENGTH)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

        return new Id(id);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Id)) {
            return false;
        }

        Id otherId = (Id) other;
        return value.equals(otherId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
