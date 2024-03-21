package hardling.us.hub.util.queue;

import org.bukkit.entity.Player;

public abstract class Queues {
    public abstract boolean inQueue(Player Players);

    public abstract String getQueueIn(Player Players);

    public abstract int getPosition(Player Players);

    public abstract int getSize(String String);

    public abstract void sendPlayer(Player Player, String String);
}
