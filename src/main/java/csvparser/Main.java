package csvparser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static csvparser.Parser.parseLine;
import static java.util.stream.Collectors.toList;

public class Main {

    public static void main(String[] args) throws Exception {
        toThreads(new StringAsker(System.in, System.out));
    }

    public static void toThreads(StringAsker asker) throws IOException {
        File file = new File("result.txt");
        String absolutePath = file.getAbsolutePath();
        Files.write(Paths.get(absolutePath), new ArrayList<>(), StandardCharsets.UTF_8);
        String fileDirectory = asker.ask("File directory: ");
        String separator = asker.ask("Separator: ");
        String numberOfThreads = asker.ask("Number of threads: ");
        List<String> allLines = Main.listAllFiles(fileDirectory);
        ThreadPool threadPool = new ThreadPool();
        threadPool.process(Integer.parseInt(numberOfThreads), allLines, separator);
    }

    public static List<String> listAllFiles(String path){
        List<String> result = new ArrayList<>();
        try(Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        List<String> fileList = Files.readAllLines(filePath);
                        result.addAll(fileList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void synchronizeRows(File file) throws IOException {
        List<String> sortedLines = Files.lines(file.toPath())
                .parallel()
                .sorted(Comparator.comparing(line -> Integer.valueOf(line.split("\\| ")[0])))
                .map(line -> line.split("\\| ")[1])
                .collect(toList());
        String absolutePath = file.getAbsolutePath();

        Files.write(Paths.get(absolutePath), sortedLines, StandardCharsets.UTF_8);
    }
}
