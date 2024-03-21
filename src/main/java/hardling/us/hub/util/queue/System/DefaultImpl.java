package hardling.us.hub.util.queue.System;

import hardling.us.hub.util.bungee.BungeeChannel;
import hardling.us.hub.util.queue.Queues;
import org.bukkit.entity.Player;

public class DefaultImpl extends Queues {
  public boolean inQueue(Player player) {
    return false;
  }
  
  public String getQueueIn(Player player) {
    return "";
  }
  
  public int getPosition(Player player) {
    return 0;
  }

  public int getSize(String server) {
    return 0;
  }

  public void sendPlayer(Player player, String server) {
    BungeeChannel.sendToServer(player, server);
  }
}
