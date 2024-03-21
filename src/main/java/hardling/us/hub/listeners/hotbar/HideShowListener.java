package hardling.us.hub.listeners.hotbar;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.ArmorCreator;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.ConfigCreator;
import hardling.us.hub.util.Files.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;

public class HideShowListener implements Listener {

    private static ConfigCreator hideplayerFile = SpeakHub.getInst().getHideplayerFile();

    public static ItemStack ShowItem(Player player) {
            return (new ArmorCreator(
                    Material.valueOf(hideplayerFile.getString("SHOW-PLAYER.MATERIAL"))))
                    .setDurability(hideplayerFile.getInt("SHOW-PLAYER.DATA"))
                    .setDisplayName(hideplayerFile.getString("SHOW-PLAYER.DISPLAYNAME"))
                    .setLore(hideplayerFile.getStringList("SHOW-PLAYER.LORE"))
                    .setGlow(hideplayerFile.getBoolean("SHOW-PLAYER.ENCHANTED")).build();
    }

    public static ItemStack HideItem(Player player) {
        return (new ArmorCreator(
                Material.valueOf(hideplayerFile.getString("HIDE-PLAYER.MATERIAL"))))
                .setDurability(hideplayerFile.getInt("HIDE-PLAYER.DATA"))
                .setDisplayName(hideplayerFile.getString("HIDE-PLAYER.DISPLAYNAME"))
                .setLore(hideplayerFile.getStringList("HIDE-PLAYER.LORE"))
                .setGlow(hideplayerFile.getBoolean("HIDE-PLAYER.ENCHANTED")).build();
    }

    @EventHandler
    public void onHideShow(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getItemInHand();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            Iterator iterator;
            Player player1;
            if (stack != null && stack.isSimilar(HideItem(player))) {
                event.setCancelled(true);
                iterator = Bukkit.getServer().getOnlinePlayers().iterator();
                while (iterator.hasNext()) {
                    player1 = (Player) iterator.next();
                    player.hidePlayer(player1);
                }
                player.setItemInHand(ShowItem(player));
                player.updateInventory();
                if (hideplayerFile.getBoolean("HIDE-PLAYER.SOUND-ENABLED")) {
                    player.playSound(player.getLocation(), Sound.valueOf(hideplayerFile.getString("HIDE-PLAYER.SOUND")), 1.0F, 1.0F);
                }

                player.sendMessage(CC.translate(Lang.HIDE_PLAYER));
            }

            if (stack != null && stack.isSimilar(ShowItem(player))) {
                event.setCancelled(true);
                iterator = Bukkit.getServer().getOnlinePlayers().iterator();
                while (iterator.hasNext()) {
                    player1 = (Player) iterator.next();
                    player.showPlayer(player1);
                }
                player.setItemInHand(HideItem(player));
                player.updateInventory();
                if (hideplayerFile.getBoolean("SHOW-PLAYER.SOUND-ENABLED")) {
                    player.playSound(player.getLocation(), Sound.valueOf(hideplayerFile.getString("SHOW-PLAYER.SOUND")), 1.0F, 1.0F);
                }

                player.sendMessage(CC.translate(Lang.SHOW_PLAYER));
            }
        }
    }
}