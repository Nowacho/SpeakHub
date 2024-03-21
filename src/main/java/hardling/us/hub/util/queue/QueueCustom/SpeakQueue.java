package hardling.us.hub.util.queue.QueueCustom;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Config;
import hardling.us.hub.util.Files.Lang;
import hardling.us.hub.util.bungee.BungeeChannel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

@Getter
public class SpeakQueue {

    @Setter
    private boolean paused;
    private SpeakHub plugin;
    private String server;
    private List<UUID> players;
    private Map<Player, BukkitTask> playerTaskMap;

    public SpeakQueue(String server) {
        this.plugin = SpeakHub.getInst();
        int queueDelay = Config.QUEUE_MESSAGE_DELAY;
        players = new ArrayList<>();
        this.playerTaskMap = new HashMap<>();
        this.paused = false;
        this.server = server;
        new BukkitRunnable() {
            @Override
            public void run() {
                players.forEach(player -> {
                    if (Bukkit.getPlayer(player).isOnline()) {
                        if (plugin.getSpeakQueueManager().getQueue(Bukkit.getPlayer(player)).isPaused()) {
                            for (String msg : Lang.QUEUE_PAUSED) {
                                Bukkit.getPlayer(player).sendMessage(CC.translate(msg
                                        .replaceAll("%queue-size%", String.valueOf(plugin.getSpeakQueueManager().getQueue(Bukkit.getPlayer(player)).getSize()))
                                        .replaceAll("%queue-server%", server)
                                        .replaceAll("%queue-position%", String.valueOf(plugin.getSpeakQueueManager().getQueue(Bukkit.getPlayer(player)).getPlayers().indexOf(player) + 1))));
                            }
                        } else {
                            for (String msg : Lang.QUEUE_PAUSED) {
                                Bukkit.getPlayer(player).sendMessage(CC.translate(msg
                                        .replaceAll("%queue-size%", String.valueOf(plugin.getSpeakQueueManager().getQueue(Bukkit.getPlayer(player)).getSize()))
                                        .replaceAll("%queue-server%", server)
                                        .replaceAll("%queue-position%", String.valueOf(plugin.getSpeakQueueManager().getQueue(Bukkit.getPlayer(player)).getPlayers().indexOf(player) + 1))));
                            }
                        }
                    } else {
                        players.remove(player);
                    }
                });
            }
        }.runTaskTimerAsynchronously(SpeakHub.getInst(), 20L * queueDelay, 20L * queueDelay);
    }

    public void addToQueue(Player player) {
        if (players.contains(player.getUniqueId())) {
            return;
        }
        if (plugin.getSpeakQueueManager().getPriority(player) == 0 && player.hasPermission("speakhub.queue.bypass")) {
            BungeeChannel.sendToServer(player, this.server);
            return;
        }
        List<String> message = Lang.QUEUE_JOINED;
        for (String msg : message) {
            player.sendMessage(CC.translate(msg.replaceAll("%queue-server%", this.server)));
        }
        players.add(player.getUniqueId());

        players.forEach(queuePlayer -> {
            int pos = players.indexOf(queuePlayer);
            if (Bukkit.getPlayer(queuePlayer) != player && this.plugin.getSpeakQueueManager().getPriority(player) < this.plugin.getSpeakQueueManager().getPriority(Bukkit.getPlayer(queuePlayer))) {
                if (Bukkit.getPlayer(players.get(pos)).isOnline()) {
                    List<String> msg = Lang.QUEUE_PRIORITY;
                    for (String mes : msg) {
                        Bukkit.getPlayer(players.get(pos)).sendMessage(CC.translate(mes));
                    }
                }
                Collections.swap(players, pos, players.size() - 1);
            }
        });
    }

    public void removeFromQueue(Player p) {
        if (!players.contains(p.getUniqueId())) {
            return;
        }
        players.remove(p.getUniqueId());
    }

    public int getSize() {
        return players.size();
    }

    public Player getPlayer(int p) {
        return Bukkit.getPlayer(players.get(p));
    }

    public void update() {
        if (!players.isEmpty()) {
            Player p = Bukkit.getPlayer(players.get(0));
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Connect");
            out.writeUTF(this.server);
            p.sendPluginMessage(SpeakHub.getInst(), "BungeeCord", out.toByteArray());
        }
    }
}