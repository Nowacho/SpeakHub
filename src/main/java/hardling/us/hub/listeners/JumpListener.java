package hardling.us.hub.listeners;

import hardling.us.hub.SpeakHub;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class JumpListener implements Listener {

    @EventHandler
    public void onPlayerToggleFlight(final PlayerToggleFlightEvent event) {
        final Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE || SpeakHub.getInst().getPvPListener().getEnablePvP().contains(player.getUniqueId())) {
            return;
        }
        if (SpeakHub.getInst().getPvPListener().getEnablePvP().contains(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        event.setCancelled(true);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.setVelocity(player.getLocation().getDirection().multiply(1.5D).setY(1));
        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1.0F, 0.0F);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() != GameMode.CREATIVE && player.getLocation().subtract(0.0D, 1.0D, 0.0D).getBlock().getType() != Material.AIR && !player.isFlying())
            player.setAllowFlight(true);
    }

    @EventHandler
    public void move(PlayerMoveEvent e) {
        final Player f = e.getPlayer();
        if (e.getTo().getY() < 2.0D)
            SpeakHub.getInst().getServer().getScheduler().scheduleSyncDelayedTask(SpeakHub.getInst(), new Runnable() {
                public void run() {
                    double y = f.getLocation().getY() - 2.0D;
                    Location l = new Location(f.getLocation().getWorld(), f.getLocation().getX(), y, f.getLocation().getZ(), f.getLocation().getYaw(), f.getLocation().getPitch());
                    f.getWorld().playEffect(l, Effect.ENDER_SIGNAL, 50, 30);
                }
            }, 10L);
    }
}
