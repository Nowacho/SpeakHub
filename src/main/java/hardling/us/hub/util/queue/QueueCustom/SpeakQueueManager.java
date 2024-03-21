package hardling.us.hub.util.queue.QueueCustom;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.Files.Config;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class SpeakQueueManager implements Listener {

	@Getter
	private List<SpeakQueue> queues;

	public SpeakQueueManager(SpeakHub plugin) {
		this.queues = new ArrayList<>();
	}

	public SpeakQueue getQueue(Player player) {
		return this.queues.stream().filter(queue -> queue.getPlayers().contains(player.getUniqueId())).findFirst().orElse(null);
	}

	public SpeakQueue getQueue(String serverName) {
		return this.queues.stream().filter(queue -> queue.getServer().equalsIgnoreCase(serverName)).findFirst().orElse(null);
	}

	public int getPriority(Player player) {
		if (player.hasPermission("speakhub.queue.bypass")) {
			return 0;
		}
        for (int i = 1; i <= Config.QUEUE_PRIORIETIES; i++){
			if (player.hasPermission("speakhub.queue." + i)) {
				return i;
			}
		}

		return Config.QUEUE_PRIORIETIES + 1;
	}

	@EventHandler
	public void handleQueueQuit(PlayerQuitEvent event) {
		Player p = event.getPlayer();
		this.queues.forEach(queue -> {
			if (queue.getPlayers().contains(p.getUniqueId())) {
				queue.removeFromQueue(p);
			}
			if (queue.getPlayerTaskMap().containsKey(p)) {
				queue.getPlayerTaskMap().get(p).cancel();
				queue.getPlayerTaskMap().remove(p);
			}
		});
	}
}
