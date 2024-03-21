package hardling.us.hub.listeners.hotbar.cosmetics;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.ArmorCreator;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.ConfigCreator;
import hardling.us.hub.util.Files.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadsListener implements Listener {
    private static ConfigCreator headsFile = SpeakHub.getInst().getHeadsFile();

    public static void Inventory(Player p) {
        Inventory inv = Bukkit.createInventory(null, headsFile.getInt("MENU.INVENTORY"), CC.translate(headsFile.getString("MENU.TITLE")));
        // Remover Heads
        inv.setItem(headsFile.getInt("MENU.REMOVE-HEAD.SLOT"), (new ArmorCreator(Material.valueOf(headsFile.getString("MENU.REMOVE-HEAD.MATERIAL")))).setDisplayName(headsFile.getString("MENU.REMOVE-HEAD.DISPLAYNAME")).setLore(headsFile.getStringList("MENU.REMOVE-HEAD.LORE")).setDurability(headsFile.getInt("MENU.REMOVE-HEAD.DATA")).build());

        // Player Heads
        for (String string : headsFile.getConfigurationSection("MENU.PLAYER-HEADS").getKeys(false)) {
            ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            SkullMeta skull = (SkullMeta) item.getItemMeta();

            skull.setOwner(headsFile.getString("MENU.PLAYER-HEADS." + string + ".PLAYER-NAME"));
            skull.setDisplayName(CC.translate(headsFile.getString("MENU.PLAYER-HEADS." + string + ".DISPLAYNAME")));
            skull.setLore(CC.translate(headsFile.getStringList("MENU.PLAYER-HEADS." + string + ".LORE")));
            item.setItemMeta(skull);
            inv.setItem(headsFile.getInt("MENU.PLAYER-HEADS." + string + ".SLOT"), item);
        }

        // Block Heads
        for (String string1 : headsFile.getConfigurationSection("MENU.BLOCK-HEADS").getKeys(false)) {
            inv.setItem(headsFile.getInt("MENU.BLOCK-HEADS." + string1 + ".SLOT"),
                    new ArmorCreator(Material.valueOf(headsFile.getString("MENU.BLOCK-HEADS." + string1 + ".MATERIAL")))
                    .setDisplayName(headsFile.getString("MENU.BLOCK-HEADS." + string1 + ".DISPLAYNAME"))
                    .setLore(headsFile.getStringList("MENU.BLOCK-HEADS." + string1 + ".LORE"))
                    .setDurability(headsFile.getInt("MENU.BLOCK-HEADS." + string1 + ".DATA"))
                    .build());
        }

        // Glass Panel
        if (headsFile.getBoolean("MENU.GLASS_PANEL.ENABLED")) {
            ItemStack panel = (new ArmorCreator(Material.STAINED_GLASS_PANE, 1, (short) headsFile.getInt("MENU.GLASS_PANEL.ID"))).setName("").build();
            for (int i = 0; i < inv.getSize(); i++) {
                if (inv.getItem(i) == null)
                    inv.setItem(i, panel);
            }
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate(headsFile.getString("MENU.TITLE")))) {
            // Player Heads
            for (String string : headsFile.getConfigurationSection("MENU.PLAYER-HEADS").getKeys(false)) {
                if (event.getSlot() == headsFile.getInt("MENU.PLAYER-HEADS." + string + ".SLOT")) {
                    if (p.hasPermission(headsFile.getString("MENU.PLAYER-HEADS." + string + ".PERMISSION"))) {
                        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                        SkullMeta skull = (SkullMeta) item.getItemMeta();
                        skull.setOwner(headsFile.getString("MENU.PLAYER-HEADS." + string + ".PLAYER-NAME"));
                        skull.setDisplayName(CC.translate(headsFile.getString("MENU.PLAYER-HEADS." + string + ".DISPLAYNAME")));
                        item.setItemMeta(skull);
                        p.getInventory().setHelmet(item);
                        p.updateInventory();
                    } else {
                        p.sendMessage(CC.translate(Lang.NO_PERMS));
                    }
                }
            }

            // Block Heads
            for (String string1 : headsFile.getConfigurationSection("MENU.BLOCK-HEADS").getKeys(false)) {
                if (event.getSlot() == headsFile.getInt("MENU.BLOCK-HEADS." + string1 + ".SLOT")) {
                    if (p.hasPermission(headsFile.getString("MENU.BLOCK-HEADS." + string1 + ".PERMISSION"))) {
                        ItemStack block = new ArmorCreator(
                                Material.valueOf(headsFile.getString("MENU.BLOCK-HEADS." + string1 + ".MATERIAL")))
                                .setDisplayName(headsFile.getString("MENU.BLOCK-HEADS." + string1 + ".DISPLAYNAME"))
                                .setDurability(headsFile.getInt("MENU.BLOCK-HEADS." + string1 + ".DATA"))
                                .build();
                        p.getInventory().setHelmet(block);
                        p.updateInventory();
                    } else {
                        p.sendMessage(CC.translate(Lang.NO_PERMS));
                    }
                }
            }

            // Remover Heads
            if (event.getSlot() == headsFile.getInt("MENU.REMOVE-HEAD.SLOT")) {
                p.getInventory().setHelmet(null);
                p.updateInventory();
                p.closeInventory();
                p.sendMessage(CC.translate("&aYou have properly removed your head"));
            }

            event.setCancelled(true);
            p.closeInventory();
        }
    }
}
