import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class CommandReader {
    private final Logger logger;

    public CommandReader(Logger logger) {
        this.logger = logger;
    }
    public void readCommands(String filePath) {
        try {
            int counter = 1;
            for (String line : Files.readAllLines(Path.of(filePath))) {
                System.out.println(counter + ": " + line);
                counter++;
            }
        } catch (IOException e) {
            logger.error("Couldn't read file %", filePath);
        }
    }
}