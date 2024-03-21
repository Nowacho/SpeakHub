package hardling.us.hub.scoreboard.customtimer;

import hardling.us.hub.SpeakHub;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class CustomTimer {
    private String name;

    private String scoreboard;

    public long startMillis;

    public long endMillis;

    public long getRemaining;

    public BukkitTask task;

    public CustomTimer(final String name, String scoreboard, long startMillis, final long endMillis) {
        setName(name);
        setScoreboard(scoreboard);
        setStartMillis(startMillis);
        setEndMillis(endMillis);
        this.task = (new BukkitRunnable() {
            public void run() {
                if (endMillis < System.currentTimeMillis()) {
                    SpeakHub.getInst().getCustomTimerManager().deleteTimer(SpeakHub.getInst().getCustomTimerManager().getCustomTimer(name));
                    cancel();
                }
            }
        }).runTaskTimerAsynchronously((Plugin) SpeakHub.getInst(), 0L, 20L);
    }

    public long getRemaining() {
        return this.endMillis - System.currentTimeMillis();
    }

    public void cancel() {
        SpeakHub.getInst().getCustomTimerManager().deleteTimer(this);
    }

    public String getName() {
        return this.name;
    }

    public String getScoreboard() {
        return this.scoreboard;
    }

    public long getStartMillis() {
        return this.startMillis;
    }

    public long getEndMillis() {
        return this.endMillis;
    }

    public long getGetRemaining() {
        return this.getRemaining;
    }

    public BukkitTask getTask() {
        return this.task;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScoreboard(String scoreboard) {
        this.scoreboard = scoreboard;
    }

    public void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }

    public void setEndMillis(long endMillis) {
        this.endMillis = endMillis;
    }

    public void setGetRemaining(long getRemaining) {
        this.getRemaining = getRemaining;
    }

    public void setTask(BukkitTask task) {
        this.task = task;
    }
}
