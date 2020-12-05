package csvparser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.System.lineSeparator;

public class ThreadPool {

    public void process(int numberOfThreads, List<String> lines, String separator) throws IOException {
        ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
        long startTime = System.nanoTime();
        int lineNumber = 1;
        for (String line : lines) {
            service.execute(new MyTask(lineNumber++, line, separator));
        }
        service.shutdown();
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.nanoTime();
        long elapsedTime = (endTime-startTime)/1000000;
        System.out.println("Execution time: "+elapsedTime+"ms");
        Main.synchronizeRows(new File("result.txt"));
    }

    public static class MyTask implements Runnable {

        String line;
        String separator;
        int lineNumber;
        Formatter formatter = new Formatter();

        public MyTask(int lineNumber, String line, String separator) {
            this.line = line;
            this.separator = separator;
            this.lineNumber = lineNumber;
        }

        @Override
        public void run() {
            try {
                FileWriter fstream = new FileWriter("result.txt", true);
                BufferedWriter out = new BufferedWriter(fstream);
                out.write(lineNumber +"| "+formatter.format(Parser.parseLine(line), separator) + lineSeparator());
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
