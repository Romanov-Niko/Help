package csvparser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileReader {

    public Stream<String> read(String fileName) throws FileNotFoundException {
        URL fileURL = this.getClass().getClassLoader().getResource(fileName);
        Stream<String> fileLines = null;
        if (fileURL == null) {
            throw new FileNotFoundException("Missing file \"" + fileName + "\"");
        }
        try {
            fileLines = Files.lines(Paths.get(fileURL.toURI()));
        } catch (URISyntaxException | IOException exception) {
            exception.getStackTrace();
        }
        return fileLines;
    }
}
