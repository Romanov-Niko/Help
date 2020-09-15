package csvparser;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {
        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {
        List<String> result = new ArrayList<>();
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }

        StringBuilder currentValue = new StringBuilder();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] characters = cvsLine.toCharArray();
        for (char character : characters) {
            if (inQuotes) {
                startCollectChar = true;
                if (character == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {
                    if (character == '\"') {
                        if (!doubleQuotesInColumn) {
                            currentValue.append(character);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        currentValue.append(character);
                    }
                }
            } else {
                if (character == customQuote) {
                    inQuotes = true;
                    if (characters[0] != '"' && customQuote == '\"') {
                        currentValue.append('"');
                    }
                    if (startCollectChar) {
                        currentValue.append('"');
                    }
                } else if (character == separators) {
                    result.add(currentValue.toString());
                    currentValue = new StringBuilder();
                    startCollectChar = false;
                } else if (character == '\n') {
                    break;
                } else {
                    currentValue.append(character);
                }
            }
        }
        result.add(currentValue.toString());
        return result;
    }
}
