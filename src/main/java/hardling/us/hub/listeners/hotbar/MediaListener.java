package hardling.us.hub.listeners.hotbar;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.ArmorCreator;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.ConfigCreator;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MediaListener implements Listener {

    private static ConfigCreator mediaFile = SpeakHub.getInst().getMediaFile();

    public static ItemStack Item(Player p) {
        return (new ArmorCreator(
                Material.valueOf(mediaFile.getString("MEDIA.MATERIAL"))))
                .setDurability(mediaFile.getInt("MEDIA.DATA"))
                .setDisplayName(mediaFile.getString("MEDIA.DISPLAYNAME"))
                .setLore(mediaFile.getStringList("MEDIA.LORE"))
                .setGlow(mediaFile.getBoolean("MEDIA.ENCHANTED")).build();
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player p = event.getPlayer();
            if (p.getInventory().getItemInHand().getType() == Material.valueOf(mediaFile.getString("MEDIA.MATERIAL"))) {
                if (mediaFile.getBoolean("MEDIA.SOUND-ENABLED")) {
                    p.playSound(p.getLocation(), Sound.valueOf(mediaFile.getString("MEDIA.SOUND")), 2.0F, 2.0F);
                }
                mediaFile.getStringList("MEDIA.MESSAGE").forEach((s) -> {
                    s = CC.Placeholders(p, s);
                    p.sendMessage(CC.translate(s));
                });
            }
        }
    }
}