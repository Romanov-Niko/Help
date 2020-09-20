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
        if (cvsLine == null || cvsLine.isEmpty()) {
            return result;
        }

        int countQuotes = cvsLine.length() - cvsLine.replaceAll("\"","").length();

        StringBuilder currentValue = new StringBuilder();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] characters = cvsLine.toCharArray();
        for (int i=0; i< characters.length;i++) {
            if(i!=characters.length-1) {
                if ((characters[i] == '"') && (characters[i + 1] == '"') && (countQuotes % 2 != 0)) {
                    currentValue.append(characters[i]);
                    continue;
                }
            }
            if (inQuotes) {
                startCollectChar = true;
                if (characters[i] == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {
                    if (characters[i] == '"') {
                        if (!doubleQuotesInColumn) {
                            currentValue.append(characters[i]);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        currentValue.append(characters[i]);
                    }
                }
            } else {
                if (characters[i] == customQuote) {
                    inQuotes = true;
                    if (characters[0] != '"' && customQuote == '"') {
                        currentValue.append('"');
                    }
                    if (startCollectChar) {
                        currentValue.append('"');
                    }
                } else if (characters[i] == separators) {
                    result.add(currentValue.toString());
                    currentValue = new StringBuilder();
                    startCollectChar = false;
                } else if (characters[i] == '\n') {
                    break;
                } else {
                    currentValue.append(characters[i]);
                }
            }
        }
        result.add(currentValue.toString());
        return result;
    }
}
