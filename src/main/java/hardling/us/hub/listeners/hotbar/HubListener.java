package hardling.us.hub.listeners.hotbar;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.ArmorCreator;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.CraftAction;
import hardling.us.hub.util.Files.ConfigCreator;
import hardling.us.hub.util.bungee.BungeeChannel;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class HubListener implements Listener {

    private static ConfigCreator hubselectorFile = SpeakHub.getInst().getHubselectorFile();

    public static ItemStack Item(Player player) {
        return (new ArmorCreator(
                Material.valueOf(hubselectorFile.getString("HUB-SELECTOR.MATERIAL"))))
                .setDurability(hubselectorFile.getInt("HUB-SELECTOR.DATA"))
                .setDisplayName(hubselectorFile.getString("HUB-SELECTOR.DISPLAYNAME"))
                .setLore(hubselectorFile.getStringList("HUB-SELECTOR.LORE"))
                .setGlow(hubselectorFile.getBoolean("HUB-SELECTOR.ENCHANTED")).build();
    }

    @EventHandler
    public void onInteractSelector(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (player.getInventory().getItemInHand().getType() == Material.valueOf(hubselectorFile.getString("HUB-SELECTOR.MATERIAL"))) {

                // SOUND
                if (hubselectorFile.getBoolean("HUB-SELECTOR.SOUND_ENABLED")) {
                    player.playSound(player.getLocation(), Sound.valueOf(hubselectorFile.getString("HUB-SELECTOR.SOUND")), 2.0F, 2.0F);
                }

                // INVENTORY
                Inventory inv = Bukkit.createInventory(null, hubselectorFile.getInt("HUB-SELECTOR.INVENTORY.SLOTS"), CC.translate(hubselectorFile.getString("HUB-SELECTOR.INVENTORY.TITLE")));

                // ITEMS
                for (String servers : hubselectorFile.getConfigurationSection("HUB-SELECTOR.HUBS").getKeys(false)) {
                    inv.setItem(hubselectorFile.getInt("HUB-SELECTOR.HUBS." + servers + ".SLOT"),
                            (new ArmorCreator(Material.valueOf(hubselectorFile.getString("HUB-SELECTOR.HUBS." + servers + ".MATERIAL"))))
                                    .setDisplayName(hubselectorFile.getString("HUB-SELECTOR.HUBS." + servers + ".DISPLAYNAME"))
                                    .setLore(PlaceholderAPI.setPlaceholders(player, hubselectorFile.getStringList("HUB-SELECTOR.HUBS." + servers + ".LORE")))
                                    .setDurability(hubselectorFile.getInt("HUB-SELECTOR.HUBS." + servers + ".DATA"))
                                    .setGlow(hubselectorFile.getBoolean("HUB-SELECTOR.HUBS." + servers + ".ENCHANTED"))
                                    .build());
                }

                // GLASS PANEL
                if (hubselectorFile.getBoolean("HUB-SELECTOR.INVENTORY.GLASS_PANEL.ENABLED")) {
                    ItemStack panel = (new ArmorCreator(Material.STAINED_GLASS_PANE, 1, (short) hubselectorFile.getInt("HUB-SELECTOR.INVENTORY.GLASS_PANEL.ID"))).setName("").build();

                    for (int i = 0; i < inv.getSize(); i++) {
                        if (inv.getItem(i) == null)
                            inv.setItem(i, panel);
                    }

                }

                player.openInventory(inv);
            }
        }
    }


    @EventHandler
    public void onClickItemSelector(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        for (String servers : hubselectorFile.getConfigurationSection("HUB-SELECTOR.HUBS").getKeys(false)) {

            if (event.getInventory().getName().equalsIgnoreCase(CC.translate(hubselectorFile.getString("HUB-SELECTOR.INVENTORY.TITLE")))
                    && event.getSlot() == hubselectorFile.getInt("HUB-SELECTOR.HUBS." + servers + ".SLOT")) {

                player.sendMessage(CC.translate(hubselectorFile.getString("HUB-SELECTOR.HUBS." + servers + ".MESSAGE")));

                if (hubselectorFile.getBoolean("HUB-SELECTOR.HUBS." + servers + ".BUNGEE.ENABLED")) {
                    BungeeChannel.sendToServer(player, hubselectorFile.getString("HUB-SELECTOR.HUBS." + servers + ".BUNGEE.SERVER"));
                }

                List<String> commands = hubselectorFile.getStringList("HUB-SELECTOR.HUBS." + servers + ".COMMANDS");
                commands.forEach(action -> {
                    action = action;
                    CraftAction.executeactions(player, action);
                });

                event.setCancelled(true);
                player.closeInventory();
            }
        }
    }
}
