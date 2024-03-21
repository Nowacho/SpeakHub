package hardling.us.hub.util.queue.System;

import hardling.us.hub.util.queue.Queues;
import me.signatured.ezqueueshared.QueueInfo;
import me.signatured.ezqueuespigot.EzQueueAPI;
import org.bukkit.entity.Player;

public class EzQueueImpl extends Queues {

    public boolean inQueue(Player player) {
        QueueInfo info = QueueInfo.getQueueInfo(EzQueueAPI.getQueue(player));
        return (info != null);
    }

    public String getQueueIn(Player player) {
        return EzQueueAPI.getQueue(player);
    }

    public int getPosition(Player player) {
        return EzQueueAPI.getPosition(player);
    }

    public int getSize(String server) {
        return QueueInfo.getQueueInfo(server).getPlayersInQueue().size();
    }

    public void sendPlayer(Player player, String server) {
        EzQueueAPI.addToQueue(player, server);
    }
}
