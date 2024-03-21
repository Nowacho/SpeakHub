package hardling.us.hub.listeners;


import hardling.us.hub.util.Files.Config;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class LaunchPadListener implements Listener {

    @EventHandler
    public void onUseLaunch(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (!Config.LAUNCH_PAD_ENABLED) return;
        if (player.getLocation().getBlock().getType() == Material.valueOf(Config.LAUNCH_PAD_MATERIAL)) {
            player.setVelocity(player.getLocation().getDirection()
                    .multiply(Config.LAUNCH_PAD_JUMP_MULTIPLY)
                    .setY(Config.LAUNCH_PAD_JUMP_HEIGHT));
            if (Config.LAUNCH_PAD_SOUND_ENABLED) {
                player.playSound(player.getLocation(), Sound.valueOf(Config.LAUNCH_PAD_SOUND), 2.0f, 2.0f);
            }
        }
    }
}
