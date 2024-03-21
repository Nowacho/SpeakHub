package hardling.us.hub.util.queue.System;

import hardling.us.hub.util.queue.Queues;
import org.bukkit.entity.Player;

public class PortalOldImpl extends Queues {
    @Override
    public boolean inQueue(Player p) {
        me.joeleoli.portal.shared.queue.Queue queue = me.joeleoli.portal.shared.queue.Queue.getByPlayer(p.getUniqueId());
        return queue != null;
    }

    @Override
    public String getQueueIn(Player p) {
        me.joeleoli.portal.shared.queue.Queue queue = me.joeleoli.portal.shared.queue.Queue.getByPlayer(p.getUniqueId());
        return queue.getName();
    }

    @Override
    public int getPosition(Player p) {
        me.joeleoli.portal.shared.queue.Queue queue = me.joeleoli.portal.shared.queue.Queue.getByPlayer(p.getUniqueId());
        return queue.getPosition(p.getUniqueId());
    }

    @Override
    public int getSize(String server) {
        me.joeleoli.portal.shared.queue.Queue queue = me.joeleoli.portal.shared.queue.Queue.getByName(server);
        return queue.getPlayers().size();
    }

    @Override
    public void sendPlayer(Player p, String server) {
        p.performCommand("joinqueue " + server);
    }
}
