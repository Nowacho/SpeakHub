package hardling.us.hub.util.cooldown;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import hardling.us.hub.util.TaskUtil;
import hardling.us.hub.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Cooldown extends PlayerTimer {

    private final Table<UUID, String, ScheduledFuture<?>> cooldowns;

    public Cooldown(ScheduledExecutorService executor) {
        super(executor, "CooldownTimer", 0);

        this.cooldowns = HashBasedTable.create();
        this.setFormat(Util.FormatType.MILLIS_TO_SECONDS);
    }

    @Override
    public void disable() {
        this.cooldowns.values().forEach(future -> future.cancel(true));
        this.cooldowns.clear();
    }

    public void activate(Player player, String cooldown, int delay, String message) {
        this.activate(player.getUniqueId(), cooldown, delay, message);
    }

    private void activate(UUID uuid, String cooldown, int delay, String message) {
        if(delay <= 0 || this.isActive(uuid, cooldown)) return;
        this.cooldowns.put(uuid, cooldown, this.scheduleExpiry(uuid, cooldown, delay, message));
    }

    public void activate(Player player, String cooldown, int delay, String message, TaskUtil.Callable callable) {
        this.activate(player.getUniqueId(), cooldown, delay, message, callable);
    }

    private void activate(UUID uuid, String cooldown, int delay, String message, TaskUtil.Callable callable) {
        if(delay <= 0 || this.isActive(uuid, cooldown)) return;
        this.cooldowns.put(uuid, cooldown, this.scheduleExpiry(uuid, cooldown, delay, message, callable));
    }

    public void cancel(Player player, String cooldown) {
        this.cancel(player.getUniqueId(), cooldown);
    }

    private void cancel(UUID uuid, String cooldown) {
        if(!this.isActive(uuid)) return;

        this.cooldowns.remove(uuid, cooldown).cancel(true);
    }

    public boolean isActive(Player player, String cooldown) {
        return this.isActive(player.getUniqueId(), cooldown);
    }

    private boolean isActive(UUID uuid, String cooldown) {
        return this.cooldowns.contains(uuid, cooldown);
    }

    public long getCooldown(Player player, String cooldown) {
        return this.getCooldown(player.getUniqueId(), cooldown);
    }

    private long getCooldown(UUID uuid, String cooldown) {
        return this.cooldowns.get(uuid, cooldown).getDelay(TimeUnit.MILLISECONDS);
    }

    public String getTimeLeft(Player player, String cooldown) {
        return Util.formatTime(this.getCooldown(player, cooldown), this.format);
    }

    public String getDynamicTimeLeft(Player player, String cooldown) {
        long remaining = this.getCooldown(player, cooldown);

        if(remaining < 3_600_000L) {
            return Util.formatTime(remaining, Util.FormatType.MILLIS_TO_MINUTES);
        } else {
            return Util.formatTime(remaining, Util.FormatType.MILLIS_TO_HOURS);
        }
    }

    private ScheduledFuture<?> scheduleExpiry(UUID uuid, String cooldown, int delay, String message) {
        return this.executor.schedule(() -> {
            try {
                this.cooldowns.remove(uuid, cooldown);

                if(message == null) return;

                Player player = Bukkit.getPlayer(uuid);
                if(player != null) player.sendMessage(message);
            } catch(Throwable t) {
                t.printStackTrace();
            }
        }, delay, TimeUnit.SECONDS);
    }

    private ScheduledFuture<?> scheduleExpiry(UUID uuid, String cooldown, int delay, String message, TaskUtil.Callable callable) {
        return this.executor.schedule(() -> {
            try {
                this.cooldowns.remove(uuid, cooldown);
                callable.call();

                if(message == null) return;

                Player player = Bukkit.getPlayer(uuid);
                if(player != null) player.sendMessage(message);
            } catch(Throwable t) {
                t.printStackTrace();
            }
        }, delay, TimeUnit.SECONDS);
    }
}
