package csvparser;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class Formatter {

    public String format(List<String> parsedLine, String separator) {
        return parsedLine.stream()
                .map(String::length)
                .map(Object::toString)
                .collect(joining(separator));
    }
}
