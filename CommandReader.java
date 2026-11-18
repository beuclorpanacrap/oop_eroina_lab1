
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandReader {

    private final List<DisplayManager> managers = new ArrayList<>();
    private final List<Display> displays = new ArrayList<>();
    private final Logger logger;

    public CommandReader(Logger logger) {
        this.logger = logger;
    }

    public void readCommands(String filePath) {
        try {
            int counter = 1;

            for (String line : Files.readAllLines(Path.of(filePath))) {
                String[] parts = line.trim().split("\\s+");

                switch (parts[0]) {
                    case "MANAGER":
                        logger.trace("MANAGER command detected");

                        DisplayManager manager = new DisplayManager(parts[1], logger);
                        managers.add(manager);
                        break;

                    case "DISPLAY":
                        logger.trace("DISPLAY command detected");

                        int width = Integer.parseInt(parts[2]);
                        int height = Integer.parseInt(parts[3]);
                        float ppi = Float.parseFloat(parts[4]);

                        Display display = new Display(parts[1], width, height, ppi, logger);
                        displays.add(display);
                        break;

                    case "ASSIGN":
                        logger.trace("ASSIGN command detected");

                        Display foundDisplay = null;
                        for (Display d : displays) {
                            if (d.getModel().equals(parts[1])) {
                                foundDisplay = d;
                                break;
                            }
                        }

                        DisplayManager foundManager = null;
                        for (DisplayManager m : managers) {
                            if (m.getName().equals(parts[2])) {
                                foundManager = m;
                                break;
                            }
                        }

                        if (foundManager != null && foundDisplay != null) {foundManager.assignDisplay(foundDisplay);}
                        else {logger.error("Couldn't assign display % to manager %", parts[1], parts[2]);}
                        break;

                    case "COMPARE":
                        logger.trace("COMPARE command detected");

                        Display foundDisplay1 = null;
                        Display foundDisplay2 = null;

                        for (Display d : displays) {
                            if (d.getModel().equals(parts[1])) {
                                foundDisplay1 = d;
                                break;
                            }
                        }

                        for (Display d : displays) {
                            if (d.getModel().equals(parts[2])) {
                                foundDisplay2 = d;
                                break;
                            }
                        }

                        if (foundDisplay1 != null && foundDisplay2 != null) {System.out.println(foundDisplay1.compareWithMonitor(foundDisplay2));}
                        else {logger.error("Couldn't compare display % with display %", parts[1], parts[2]);}
                        break;

                    case "ASSIST":
                        logger.trace("ASSIST command detected");

                        DisplayManager assistManager = null;
                        for (DisplayManager m : managers) {
                            if (m.getName().equals(parts[1])) {
                                assistManager = m;
                                break;
                            }
                        }

                        if (assistManager != null) {assistManager.assist();}
                        else {logger.error("Manager % not found", parts[1]);}
                        break;

                    case "BUY":
                        logger.trace("BUY command detected");
                        Display buyDisplay = null;
                        for (Display d : displays) {
                            if (d.getModel().equals(parts[1])) {
                                buyDisplay = d;
                                break;
                            }
                        }
                        DisplayManager buyManager = null;
                        for (DisplayManager m : managers) {
                            if (m.getName().equals(parts[2])) {
                                buyManager = m;
                                break;
                            }
                        } if (buyManager != null && buyDisplay != null) {buyManager.buyDisplay(buyDisplay);}
                        else {logger.error("Couldn't buy display % from manager %", parts[1], parts[2]);}
                        break;
                    default:
                        logger.error("Unknown command: %", parts[0]);
                }

                counter++;
            }
        } catch (IOException e) {
            logger.error("Couldn't read file %", filePath);
        }
    }
}
