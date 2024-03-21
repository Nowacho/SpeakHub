package hardling.us.hub.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import hardling.us.hub.SpeakHub;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadFactory;

public class TaskUtil {

    public interface Callable {
        void call();
    }

    public static ThreadFactory newThreadFactory(String name) {
        return new ThreadFactoryBuilder().setNameFormat(name).build();
    }

    public static void run(Runnable runnable) {
        SpeakHub.get().getServer().getScheduler().runTask(SpeakHub.get(), runnable);
    }

    public static void runAsync(Runnable runnable) {
        try {
            SpeakHub.get().getServer().getScheduler().runTaskAsynchronously(SpeakHub.get(), runnable);
        } catch (IllegalStateException e) {
            SpeakHub.get().getServer().getScheduler().runTask(SpeakHub.get(), runnable);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void runTimer(Runnable runnable, long delay, long timer) {
        SpeakHub.get().getServer().getScheduler().runTaskTimer(SpeakHub.get(), runnable, delay, timer);
    }

    public static int runTimer(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimer(SpeakHub.get(), delay, timer);
        return runnable.getTaskId();
    }

    public static void runLater(Runnable runnable, long delay) {
        SpeakHub.get().getServer().getScheduler().runTaskLater(SpeakHub.get(), runnable, delay);
    }

    public static void runLaterAsync(Runnable runnable, long delay) {
        try {
            SpeakHub.get().getServer().getScheduler().runTaskLaterAsynchronously(SpeakHub.get(), runnable, delay);
        } catch (IllegalStateException e) {
            SpeakHub.get().getServer().getScheduler().runTaskLater(SpeakHub.get(), runnable, delay);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void runTimerAsync(Runnable runnable, long delay, long timer) {
        try {
            SpeakHub.get().getServer().getScheduler().runTaskTimerAsynchronously(SpeakHub.get(), runnable, delay, timer);
        } catch (IllegalStateException e) {
            SpeakHub.get().getServer().getScheduler().runTaskTimer(SpeakHub.get(), runnable, delay, timer);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void runTimerAsync(BukkitRunnable runnable, long delay, long timer) {
        SpeakHub.get().getServer().getScheduler().runTaskTimerAsynchronously(SpeakHub.get(), runnable, delay, timer);
    }
}
