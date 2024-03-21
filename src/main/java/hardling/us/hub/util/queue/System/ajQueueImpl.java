package hardling.us.hub.util.queue.System;

import hardling.us.hub.util.queue.Queues;
import org.bukkit.entity.Player;
import us.ajg0702.queue.api.AjQueueAPI;
import us.ajg0702.queue.api.players.AdaptedPlayer;
import us.ajg0702.queue.api.players.QueuePlayer;
import us.ajg0702.queue.api.queues.QueueServer;

public class ajQueueImpl extends Queues {
    @Override
    public boolean inQueue(Player p0) {
        AdaptedPlayer adaptedPlaye = AjQueueAPI.getInstance().getPlatformMethods().getPlayer(p0.getUniqueId());
        if (AjQueueAPI.getInstance().getQueueManager().findPlayerInQueues(adaptedPlaye) == null) {
            return false;
        }
        return true;
    }

    @Override
    public String getQueueIn(Player p0) {
        AdaptedPlayer adaptedPlaye = AjQueueAPI.getInstance().getPlatformMethods().getPlayer(p0.getUniqueId());
        QueuePlayer queuePlayer = AjQueueAPI.getInstance().getQueueManager().findPlayerInQueues(adaptedPlaye).get(0);
        return queuePlayer.getQueueServer().getName();
    }

    @Override
    public int getPosition(Player p0) {
        AdaptedPlayer adaptedPlaye = AjQueueAPI.getInstance().getPlatformMethods().getPlayer(p0.getUniqueId());
        QueuePlayer queuePlayer = AjQueueAPI.getInstance().getQueueManager().findPlayerInQueues(adaptedPlaye).get(0);
        return queuePlayer.getPosition();
    }

    @Override
    public int getSize(String p0) {
        QueueServer server = AjQueueAPI.getInstance().getQueueManager().findServer(p0);
        return server.getQueue().size();
    }

    @Override
    public void sendPlayer(Player p0, String p1) {
        AdaptedPlayer adaptedPlaye = AjQueueAPI.getInstance().getPlatformMethods().getPlayer(p0.getUniqueId());
        AjQueueAPI.getInstance().getQueueManager().addToQueue(adaptedPlaye, p1);
    }
}
