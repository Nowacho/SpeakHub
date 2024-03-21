package hardling.us.hub.util.cooldown;


import hardling.us.hub.util.Files.ConfigCreator;
import hardling.us.hub.util.TaskUtil;
import lombok.Getter;

import java.util.concurrent.ScheduledThreadPoolExecutor;

@Getter
public class TimerManager {

    @Getter private static TimerManager inst;

    private ConfigCreator timersFile;
    private final Cooldown cooldownTimer;
    private final ScheduledThreadPoolExecutor executor;

    public TimerManager() {
        inst = this;

        this.executor = new ScheduledThreadPoolExecutor(1, TaskUtil.newThreadFactory("Timer Thread - %d"));
        this.executor.setRemoveOnCancelPolicy(true);

        this.cooldownTimer = new Cooldown(this.executor);
    }

    public void disable() {
        this.timersFile.save();
        this.cooldownTimer.disable();

        this.executor.shutdownNow();
    }

    public void deleteAllTimers() {

        this.cooldownTimer.disable();

        this.timersFile.getFile().delete();
    }
}
