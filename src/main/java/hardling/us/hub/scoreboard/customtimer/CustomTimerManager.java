package hardling.us.hub.scoreboard.customtimer;

import java.util.ArrayList;
import java.util.List;

public class CustomTimerManager {
    private List<CustomTimer> customTimers = new ArrayList<>();

    public void createTimer(CustomTimer timer) {
        this.customTimers.add(timer);
    }

    public void deleteTimer(CustomTimer timer) {
        this.customTimers.remove(timer);
    }

    public CustomTimer getCustomTimer(String name) {
        return this.customTimers.stream().filter(timer -> timer.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<CustomTimer> getCustomTimers() {
        return this.customTimers;
    }

    public void setCustomTimers(List<CustomTimer> customTimers) {
        this.customTimers = customTimers;
    }
}
