
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
                if (line.isBlank() || line.trim().startsWith("#")) {
                    counter++;
                    continue;
                }

                try {
                    String[] parts = line.trim().split("\\s+");

                    switch (parts[0]) {
                        case "MANAGER":
                            logger.trace("MANAGER command detected");

                            if (parts.length != 2) {
                                logger.error("Line %: MANAGER requires exactly 1 name", counter);
                                break;
                            }

                            boolean exists = false;
                            for (DisplayManager manager : managers) {
                                if (manager.getName().equals(parts[1])) {
                                    exists = true;
                                    break;
                                }
                            }

                            if (!exists) {
                                DisplayManager manager = new DisplayManager(parts[1], logger);
                                managers.add(manager);
                            } else {
                                logger.error("Line %: Manager % already exists", counter, parts[1]);
                            }
                            break;

                        case "DISPLAY":
                            logger.trace("DISPLAY command detected");

                            if (parts.length != 5) {
                                logger.error("Line %: DISPLAY requires a model, width, height and ppi", counter);
                                break;
                            }

                            int width = Integer.parseInt(parts[2]);
                            int height = Integer.parseInt(parts[3]);
                            float ppi = Float.parseFloat(parts[4]);

                            Display display = new Display(parts[1], width, height, ppi, logger);
                            displays.add(display);
                            break;

                        case "ASSIGN":
                            logger.trace("ASSIGN command detected");

                            if (parts.length != 3) {
                                logger.error("Line %: ASSIGN requires a display and a manager", counter);
                                break;
                            }

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
                            else {logger.error("Line %: Couldn't assign display % to manager %", counter, parts[1], parts[2]);}
                            break;

                        case "COMPARE":
                            logger.trace("COMPARE command detected");

                            if (parts.length != 3) {
                                logger.error("Line %: COMPARE requires exactly 2 displays", counter);
                                break;
                            }

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
                            else {logger.error("Line %: Couldn't compare display % with display %", counter, parts[1], parts[2]);}
                            break;

                        case "ASSIST":
                            logger.trace("ASSIST command detected");

                            if (parts.length != 2) {
                                logger.error("Line %: ASSIST requires exactly 1 manager", counter);
                                break;
                            }

                            DisplayManager assistManager = null;
                            for (DisplayManager m : managers) {
                                if (m.getName().equals(parts[1])) {
                                    assistManager = m;
                                    break;
                                }
                            }

                            if (assistManager != null) {assistManager.assist();}
                            else {logger.error("Line %: Manager % not found", counter, parts[1]);}
                            break;

                        case "BUY":
                            logger.trace("BUY command detected");

                            if (parts.length != 3) {
                                logger.error("Line %: BUY requires a display and a manager", counter);
                                break;
                            }

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
                            }

                            if (buyManager != null && buyDisplay != null) {
                                Display bought = buyManager.buyDisplay(buyDisplay);

                                if (bought == null) {
                                    logger.error("Line %: Purchase of % by % failed", counter, parts[1], parts[2]);
                                }
                            } else {
                                logger.error("Line %: Display % or manager % not found", counter, parts[1], parts[2]);
                            }
                            break;

                        default:
                            logger.error("Line %: Unknown command: %", counter, parts[0]);
                    }

                } catch (NumberFormatException e) {
                    logger.error("Line %: Invalid number", counter);
                } catch (ArrayIndexOutOfBoundsException e) {
                    logger.error("Line %: Missing arguments", counter);
                }

                counter++;
            }
        } catch (IOException e) {
            logger.error("Couldn't read file %", filePath);
        }
    }
}
