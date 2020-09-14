package csvparser;

import java.io.File;
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
        Scanner scanner = new Scanner(System.in);
        System.out.print("ENTER FILE NAME: ");
        String fileName = scanner.next();
        System.out.print("ENTER SEPARATOR: ");
        String separator = scanner.next();
        FileReader fileReader = new FileReader();
        Formatter formatter = new Formatter();
        List<String> lines = fileReader.read(fileName).collect(toList());
        List<String> parsedLines = new ArrayList<>();
        lines.forEach(line -> parsedLines.add(formatter.format(parseLine(line), separator)));
        File file = new File("result.txt");
        String absolutePath = file.getAbsolutePath();
        Files.write(Paths.get(absolutePath), parsedLines, StandardCharsets.UTF_8);
        System.out.println(absolutePath);
    }
}
