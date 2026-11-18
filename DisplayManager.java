import java.util.ArrayList;
import java.util.List;

public class DisplayManager {
    private final String name;
    private final List<Display> displays = new ArrayList<>();
    private final Logger logger;

    public DisplayManager(String name, Logger logger) {
        this.name = name;
        this.logger = logger;
        logger.trace("Manager % created", name);
    }
    public void assignDisplay(Display display){
        displays.add(display);
        logger.trace("Display % assigned to manager %", display.getModel(), name);
    }
    public void assist(){
        if (displays.size() < 2){
            logger.warn("Manager % needs at least 2 displays to compare", name);
            return;
        }
        for (int i = 0; i < displays.size() -1; i++){
            Display current = displays.get(i);
            Display next = displays.get(i+1);
            System.out.println(current.compareWithMonitor(next));
        }}
    public Display buyDisplay(Display display) {
        if (displays.remove(display)) {
            logger.info("Manager % has bought a new display:  %", name, display.getModel());
            return display;
        }
        logger.error("Manager % failed to buy display: %", name, display.getModel());
        return null;
    }
}