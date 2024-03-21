package hardling.us.hub.listeners.hotbar;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.ArmorCreator;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.ConfigCreator;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EnderButtListener implements Listener {

    private static ConfigCreator enderbuttFile  = SpeakHub.getInst().getEnderbuttFile();

    public static ItemStack Item(Player p) {
        return (new ArmorCreator(
                Material.valueOf(enderbuttFile.getString("ENDERBUTT.MATERIAL"))))
                .setDurability(enderbuttFile.getInt("ENDERBUTT.DATA"))
                .setDisplayName(enderbuttFile.getString("ENDERBUTT.DISPLAYNAME"))
                .setLore(enderbuttFile.getStringList("ENDERBUTT.LORE"))
                .setGlow(enderbuttFile.getBoolean("ENDERBUTT.ENCHANTED")).build();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.hasItem() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (event.getItem().getItemMeta() == null) return;
            if (event.getItem().getItemMeta().getDisplayName() == null) return;
            if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(CC.translate(enderbuttFile.getString("ENDERBUTT.DISPLAYNAME")))) {
                event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(2.5F));
                event.setCancelled(true);
                event.setUseItemInHand(Event.Result.DENY);
                event.getPlayer().updateInventory();
                if (enderbuttFile.getBoolean("ENDERBUTT.SOUND-ENABLED")) {
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.valueOf(enderbuttFile.getString("ENDERBUTT.SOUND")), 2.0F, 2.0F);
                }

            }
        }
    }
}
