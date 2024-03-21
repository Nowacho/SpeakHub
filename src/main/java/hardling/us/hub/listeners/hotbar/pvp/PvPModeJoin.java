package hardling.us.hub.listeners.hotbar.pvp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PvPModeJoin extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;

    public PvPModeJoin(Player player) {
        this.player = player;
        Bukkit.getPluginManager().callEvent(this);
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
