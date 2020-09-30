package csvparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static csvparser.Parser.parseLine;
import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) throws Exception {
        doWork(new StringAsker(System.in, System.out));
    }

    public static void doWork(StringAsker asker) throws IOException {
        String fileName = asker.ask("ENTER FILE NAME: ");
        String separator = asker.ask("ENTER SEPARATOR: ");
        FileReader fileReader = new FileReader();
        Formatter formatter = new Formatter();
        List<String> lines = fileReader.read(fileName).collect(toList());
        List<String> parsedLines = new ArrayList<>();
        lines.forEach(line -> parsedLines.add(formatter.format(parseLine(line), separator)));
        File file = new File("result.txt");
        String absolutePath = file.getAbsolutePath();
        Files.write(Paths.get(absolutePath), parsedLines, StandardCharsets.UTF_8);
    }
}
