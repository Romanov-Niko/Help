package csvparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Parser {

    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';

    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String cvsLine, char separators) {


        return parseLine(cvsLine, separators, DEFAULT_QUOTE);
    }

    public static List<String> parseLine(String csvLine, char separators, char customQuote) {
        csvLine = filterString(csvLine);

        List<String> result = new ArrayList<>();
        if (csvLine == null || csvLine.isEmpty()) {
            return result;
        }

        int countQuotes = csvLine.length() - csvLine.replaceAll("\"","").length();

        StringBuilder currentValue = new StringBuilder();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;

        char[] characters = csvLine.toCharArray();
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

    private static String filterString(String code) {
        String partialFiltered = code.replaceAll("/\\*[^*/]*\\*/", "");
        String fullFiltered = partialFiltered.replaceAll("//.*(?=\\n)", "");
        return fullFiltered;
    }
}
