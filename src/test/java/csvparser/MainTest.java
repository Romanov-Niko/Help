package csvparser;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static csvparser.Main.doWork;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainTest {

    @Test
    void main() throws IOException {
        StringAsker asker = mock(StringAsker.class);
        when(asker.ask("ENTER FILE NAME: ")).thenReturn("country.csv");
        when(asker.ask("ENTER SEPARATOR: ")).thenReturn("-");

        doWork(asker);

        verify(asker).ask("ENTER FILE NAME: ");
        verify(asker).ask("ENTER SEPARATOR: ");
    }
}