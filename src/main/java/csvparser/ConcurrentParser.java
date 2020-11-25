package csvparser;

import javax.annotation.processing.Processor;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.System.lineSeparator;

public class ConcurrentParser {

    public void process(int numberOfThreads, List<String> lines, String separator, String absolutePath) {
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        Formatter formatter = new Formatter();
        System.out.println("Start = " + LocalDateTime.now());
        for (String line : lines) {
            service.execute(new MyTask(line, separator, absolutePath, formatter));
        }
        service.shutdown();
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End = " + LocalDateTime.now());
    }

    public static class MyTask implements Runnable {

        String line;
        String separator;
        String absolutePath;
        Formatter formatter;

        public MyTask( String line, String separator, String absolutePath, Formatter formatter) {
            this.line = line;
            this.separator = separator;
            this.absolutePath = absolutePath;
            this.formatter = formatter;
        }

        @Override
        public void run() {
            try{
                FileWriter fstream = new FileWriter("result.txt",true);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write(formatter.format(Parser.parseLine(line), separator)+lineSeparator());
                out.close();
            }catch (Exception e){
                System.err.println("Error while writing to file: " +
                        e.getMessage());
            }
        }
    }
}
