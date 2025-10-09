package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class RandomUtilTest {
    @Test
    public void generateAlphanum() {
        int length = 6;
        assertEquals(length, RandomUtil.generateAlphanum(length).length());

        length = 10;
        assertEquals(length, RandomUtil.generateAlphanum(length).length());

        length = 2;
        assertEquals(length, RandomUtil.generateAlphanum(length).length());
    }
}
