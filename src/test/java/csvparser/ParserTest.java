package csvparser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class ParserTest {

    @Test
    void DefaultSeparator() {
        String line = "10,AU,Aus\"\"tralia";
        List<String> result = Parser.parseLine(line);

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Aus\"tralia");
    }

    @Test
    void CustomSeparator() {
        String line = "10+AU+Australia";
        List<String> result = Parser.parseLine(line, '+');

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Australia");
    }

    @Test
    void CustomSeparatorWithDoubleQuotes() {
        String line = "10+AU+Aus\"\"tralia";
        List<String> result = Parser.parseLine(line, '+');

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Aus\"tralia");
    }
}