package csvparser;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static csvparser.Main.toThreads;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainTest {

    @Test
    void main() throws IOException {
        List<String> expected = new ArrayList<>();
        expected.add("2*2*1*10");
        expected.add("2*2*1*9");
        expected.add("2*2*0*9");
        expected.add("2*2*12");
        expected.add("2*2*10");

        StringAsker asker = mock(StringAsker.class);
        when(asker.ask("File directory: ")).thenReturn("A:\\Lab_1\\src\\test\\resources");
        when(asker.ask("Number of threads: ")).thenReturn(String.valueOf(4));
        when(asker.ask("Separator: ")).thenReturn("*");

        toThreads(asker);

        verify(asker).ask("File directory: ");
        verify(asker).ask("Separator: ");
        verify(asker).ask("Number of threads: ");
        File file = new File("result.txt");
        Scanner scanner = new Scanner(file);
        List<String> lines = new ArrayList<>();
        while(scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }
        assertTrue(lines.containsAll(expected));
    }
}