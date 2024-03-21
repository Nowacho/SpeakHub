package hardling.us.hub.tablist.impl;

import hardling.us.hub.tablist.impl.utils.Manager;
import hardling.us.hub.tablist.interfaces.IEAdapter;
import hardling.us.hub.tablist.interfaces.IEHelper;
import hardling.us.hub.tablist.protocol.ProtocolLibTabImpl;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum TablistManager implements Listener, Manager {

    getInst;

    private boolean enabled;
    private JavaPlugin plugin;
    private IEAdapter adapter;
    private IEHelper ieHelper;
    private Thread thread;

    private final Map<UUID, IETablist> tabStorage = new ConcurrentHashMap<>();

    @Override
    public void onEnable(JavaPlugin plugin) {
        if (enabled) return;

        this.enabled = true;
        this.plugin = plugin;



        this.setupNMS();

        thread = new Thread(() -> {
            while (plugin.isEnabled()) {
                try {
                    tabStorage.forEach((uniqueId, tab) -> tab.update());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

            try {
                Thread.sleep(300L);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        });

        thread.setName("Tab Thread");
        thread.setDaemon(true);
    }

    private void setupNMS() {
        String serverVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        if (Bukkit.getPluginManager().getPlugin("ProtocolLib") != null) {
            this.ieHelper = new ProtocolLibTabImpl();
        } else {
        }
    }

    public void setAdapter(IEAdapter adapter) {
        this.adapter = adapter;

        if (!thread.isAlive()) {
            thread.start();
        }

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID uniqueId = player.getUniqueId();

        tabStorage.put(uniqueId, new IETablist(player, adapter));
    }

    private void handleDisconnect(Player player) {
        Team team = player.getScoreboard().getTeam("\\u000181");

        if (team != null) {
            team.unregister();
        }

        tabStorage.remove(player.getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        handleDisconnect(event.getPlayer());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerKick(PlayerKickEvent event) {
        handleDisconnect(event.getPlayer());
    }

}
