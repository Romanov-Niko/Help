package csvparser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class ParserTest {

    @Test
    public void test_custom_separator() {
        String line = "10|AU|Australia";

        List<String> result = Parser.parseLine(line, '|');

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Australia");
    }

    @Test
    public void test_custom_separator_and_quote() {
        String line = "'10'|'AU'|'Australia'";

        List<String> result = Parser.parseLine(line, '|', '\'');

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Australia");
    }

    @Test
    public void test_custom_separator_and_quote_but_custom_quote_in_column() {
        String line = "'10'|'AU'|'Aus|tralia'";

        List<String> result = Parser.parseLine(line, '|', '\'');

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Aus|tralia");
    }

    @Test
    public void test_custom_separator_and_quote_but_double_quotes_in_column() {
        String line = "'10'|'AU'|'Aus\"\"tralia'";

        List<String> result = Parser.parseLine(line, '|', '\'');

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Aus\"tralia");
    }

    @Test
    public void test_parse_of_line_with_default_separator_and_quote() {
        String line = "10,AU,Aus\"\"tralia";

        List<String> result = Parser.parseLine(line);

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Aus\"tralia");
    }

    @Test
    public void test_parse_of_line_with_custom_separators_and_default_quote() {
        String line = "10|AU|Aus\"\"tralia";

        List<String> result = Parser.parseLine(line, '|');

        assertNotEquals(result, null);
        assertEquals(result.size(), 3);
        assertEquals(result.get(0), "10");
        assertEquals(result.get(1), "AU");
        assertEquals(result.get(2), "Aus\"tralia");
    }
}