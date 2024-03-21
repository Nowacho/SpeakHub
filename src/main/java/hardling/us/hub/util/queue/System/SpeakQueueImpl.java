package hardling.us.hub.util.queue.System;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.queue.Queues;
import org.bukkit.entity.Player;

public class SpeakQueueImpl extends Queues {

    @Override
    public boolean inQueue(Player p) {
        return SpeakHub.getInst().getSpeakQueueManager().getQueue(p) != null;
    }

    @Override
    public String getQueueIn(Player p) {
        return SpeakHub.getInst().getSpeakQueueManager().getQueue(p).getServer();
    }

    @Override
    public int getPosition(Player p) {
        int i = SpeakHub.getInst().getSpeakQueueManager().getQueue(p).getPlayers().indexOf(p) + 1;
        return i;
    }

    @Override
    public int getSize(String p) {
        return SpeakHub.getInst().getSpeakQueueManager().getQueue(p).getSize();
    }

    @Override
    public void sendPlayer(Player p, String s) {
        if (SpeakHub.getInst().getSpeakQueueManager().getQueue(s) == null) {
            p.sendMessage(CC.translate("&cFailed to add you to the " + s + " queue."));
            return;
        }
        SpeakHub.getInst().getSpeakQueueManager().getQueue(s).addToQueue(p);
    }
}
