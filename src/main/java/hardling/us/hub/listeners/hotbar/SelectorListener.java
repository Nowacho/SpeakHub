package hardling.us.hub.listeners.hotbar;


import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.ArmorCreator;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.CraftAction;
import hardling.us.hub.util.Files.Config;
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

public class SelectorListener implements Listener {

    public static ItemStack Item(Player player) {
        return (new ArmorCreator(
                Material.valueOf(Config.SELECTOR_MATERIAL)))
                .setDisplayName(Config.SELECTOR_DISPLAYNAME)
                .setGlow(Config.SELECTOR_ENCHANTED)
                .setDurability(Config.SELECTOR_DATA)
                .setLore(Config.SELECTOR_LORE)
                .build();
    }

    @EventHandler
    public void onInventorySelector(PlayerInteractEvent event) {
        ConfigCreator selectorFile = SpeakHub.getInst().getSelectorFile();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            if (player.getInventory().getItemInHand().getType() == Material.valueOf(Config.SELECTOR_MATERIAL)) {
                // INVENTORY
                Inventory inv = Bukkit.createInventory(null, Config.SELECTOR_INVENTORY_SLOTS, CC.translate(Config.SELECTOR_INVENTORY_TITLE));

                // ITEMS
                for (String servers : selectorFile.getConfigurationSection("SELECTOR.SERVERS").getKeys(false)) {
                    inv.setItem(selectorFile.getInt("SELECTOR.SERVERS." + servers + ".SLOT"),
                            (new ArmorCreator(Material.valueOf(selectorFile.getString("SELECTOR.SERVERS." + servers + ".MATERIAL"))))
                                    .setDisplayName(selectorFile.getString("SELECTOR.SERVERS." + servers + ".DISPLAYNAME"))
                                    .setLore(PlaceholderAPI.setPlaceholders(player, selectorFile.getStringList("SELECTOR.SERVERS." + servers + ".LORE")))
                                    .setDurability(selectorFile.getInt("SELECTOR.SERVERS." + servers + ".DATA"))
                                    .setGlow(selectorFile.getBoolean("SELECTOR.SERVERS." + servers + ".ENCHANTED"))
                                    .build());
                }

                // GLASS PANEL
                if (Config.SELECTOR_INVENTORY_GLASS_PANEL_ENABLED) {
                    ItemStack panel = (new ArmorCreator(Material.STAINED_GLASS_PANE, 1, (short) Config.SELECTOR_INVENTORY_GLASS_PANEL_ID)).setName("").build();
                    for (int i = 0; i < inv.getSize(); i++) {
                        if (inv.getItem(i) == null) {
                            inv.setItem(i, panel);
                        }
                    }
                }

                // SOUND
                if (Config.SELECTOR_SOUND_ENABLED) {
                    player.playSound(player.getLocation(), Sound.valueOf(Config.SELECTOR_SOUND), 2.0F, 2.0F);
                }

                player.openInventory(inv);
            }
        }
    }

    @EventHandler
    public void onClickItemSelector(InventoryClickEvent event) {
        ConfigCreator selectorFile = SpeakHub.getInst().getSelectorFile();
        Player player = (Player) event.getWhoClicked();
        for (String servers : selectorFile.getConfigurationSection("SELECTOR.SERVERS").getKeys(false)) {
            if (event.getInventory().getName().equalsIgnoreCase(CC.translate(selectorFile.getString("SELECTOR.INVENTORY.TITLE"))) && event.getSlot() == selectorFile.getInt("SELECTOR.SERVERS." + servers + ".SLOT")) {
                player.sendMessage(CC.translate(selectorFile.getString("SELECTOR.SERVERS." + servers + ".MESSAGE")));
                if (selectorFile.getBoolean("SELECTOR.SERVERS." + servers + ".BUNGEE.ENABLED")) {
                    BungeeChannel.sendToServer(player, selectorFile.getString("SELECTOR.SERVERS." + servers + ".BUNGEE.SERVER"));
                }
                List<String> commands = selectorFile.getStringList("SELECTOR.SERVERS." + servers + ".COMMANDS");
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