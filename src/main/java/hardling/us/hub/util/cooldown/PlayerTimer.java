package hardling.us.hub.util.cooldown;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PlayerTimer extends Timer {

    protected final Map<UUID, ScheduledFuture<?>> players;

    public PlayerTimer(ScheduledExecutorService executor, String name, int delay) {
        this(executor, name, delay, false);
    }

    public PlayerTimer(ScheduledExecutorService executor, String name, int delay, boolean persistable) {
        super(executor, name, delay, persistable);

        this.players = new HashMap<>();
        this.loadTimer();
    }

    @Override
    public void disable() {
        this.saveTimer();

        this.players.values().forEach(future -> future.cancel(true));
        this.players.clear();
    }

    protected void loadTimer() {
        if(!this.persistable) return;

        ConfigurationSection section = TimerManager.getInst().getTimersFile().getSection(this.name);
        if(section == null) return;

        section.getKeys(false).forEach(key -> this.activate(UUID.fromString(key), (int) section.getLong(key) / 1000));
    }

    public void saveTimer() {
        if(!this.persistable) return;

        ConfigurationSection section = TimerManager.getInst().getTimersFile().createSection(this.name);
        this.players.forEach((uuid, future) -> section.set(uuid.toString(), future.getDelay(TimeUnit.MILLISECONDS)));
    }



    public void activate(UUID uuid, int delay) {
        if(delay <= 0 || this.isActive(uuid)) return;

        TimerActivateEvent event = new TimerActivateEvent(uuid, this, delay);
        if(event.isCancelled()) return;

        this.players.put(uuid, this.scheduleExpiry(uuid, delay));
    }





    public boolean isActive(UUID uuid) {
        return this.players.containsKey(uuid);
    }



    private void sendMessage(UUID uuid) {
        if(this.expiryMessage == null) return;

        Player player = Bukkit.getPlayer(uuid);
        if(player != null) player.sendMessage(this.expiryMessage);
    }

    private ScheduledFuture<?> scheduleExpiry(UUID uuid, int delay) {
        return this.executor.schedule(() -> {
            try {
                new TimerExpireEvent(uuid, this);

                this.players.remove(uuid);
                this.sendMessage(uuid);
            } catch(Throwable t) {
                t.printStackTrace();
            }
        }, delay, TimeUnit.SECONDS);
    }
}
