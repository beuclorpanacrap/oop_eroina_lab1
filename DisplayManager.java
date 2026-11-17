import java.util.ArrayList;
import java.util.List;

public class DisplayManager {
    private final String name;
    private final List<Display> displays = new ArrayList<>();

    public DisplayManager(String name) {
        this.name = name;
    }
    public void assignDisplay(Display display){
        displays.add(display);
    }
    public void assist(){
        for (int i = 0; i < displays.size() -1; i++){
            Display current = displays.get(i);
            Display next = displays.get(i+1);
            System.out.println(current.compareWithMonitor(next));
        }
    }
    public Display buyDisplay(Display display) {
        if (displays.remove(display)) {
            return display;
        }
        return null;
    }
}