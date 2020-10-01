package csvparser;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static csvparser.Main.doWork;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainTest {

    @Test
    void main() throws IOException {
        StringAsker asker = mock(StringAsker.class);
        when(asker.ask("ENTER FILE NAME: ")).thenReturn("country.csv");
        when(asker.ask("ENTER SEPARATOR: ")).thenReturn("*");

        doWork(asker);

        verify(asker).ask("ENTER FILE NAME: ");
        verify(asker).ask("ENTER SEPARATOR: ");
        File file = new File("result.txt");
        Scanner scanner = new Scanner(file);
        List<String> lines = new ArrayList<>();
        while(scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }
        assertEquals("2*2*0*9", lines.get(0));
    }
}