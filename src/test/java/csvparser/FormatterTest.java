package csvparser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FormatterTest {

    private Formatter formatter;

    @BeforeEach
    public void setUp() {
        formatter = new Formatter();
    }

    @Test
    void givenCSVLineAndDashAsSeparator_whenFormat_thenReturnedLengthOfEachValueJoinedByDash() {
        String expected = "2-2-0-9";

        String actual = formatter.format(Arrays.asList("10", "AU", "", "Australia"), "-");

        assertEquals(expected, actual);
    }
}