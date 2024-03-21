package hardling.us.hub.listeners.hotbar.cosmetics;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.ArmorCreator;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.ConfigCreator;
import hardling.us.hub.util.Files.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class OutfitsListener implements Listener {

    private static ConfigCreator oufitsFile = SpeakHub.getInst().getOufitsFile();

    public static void OutfitInv(Player p) {
        Inventory inv = Bukkit.createInventory(null, oufitsFile.getInt("MENU.INVENTORY"), CC.translate(oufitsFile.getString("MENU.TITLE")));

        // Remover Outfits
        inv.setItem(oufitsFile.getInt("MENU.CLEAR-OUTFIT.SLOT"), (new ArmorCreator(Material.valueOf(oufitsFile.getString("MENU.CLEAR-OUTFIT.MATERIAL")))).setDisplayName(oufitsFile.getString("MENU.CLEAR-OUTFIT.DISPLAYNAME")).setLore(oufitsFile.getStringList("MENU.CLEAR-OUTFIT.LORE")).build());

        // Outfits Item
        for (String string : oufitsFile.getConfigurationSection("MENU.OUTFITS").getKeys(false)) {
            inv.setItem(oufitsFile.getInt("MENU.OUTFITS." + string + ".SLOT"),
                    (new ArmorCreator(Material.valueOf(oufitsFile.getString("MENU.OUTFITS." + string + ".MATERIAL"))))
                            .setDisplayName(oufitsFile.getString("MENU.OUTFITS." + string + ".DISPLAYNAME"))
                            .setLore(oufitsFile.getStringList("MENU.OUTFITS." + string + ".LORE"))
                            .color(convert(oufitsFile.getString("MENU.OUTFITS." + string + ".COLOR"))).build());
        }

        // Glass Panel
        if (oufitsFile.getBoolean("MENU.GLASS_PANEL.ENABLED")) {
            ItemStack panel = (new ArmorCreator(Material.STAINED_GLASS_PANE, 1, (short) oufitsFile.getInt("MENU.GLASS_PANEL.ID"))).setName("").build();
            for (int i = 0; i < inv.getSize(); i++) {
                if (inv.getItem(i) == null) {
                    inv.setItem(i, panel);
                }
            }
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate(oufitsFile.getString("MENU.TITLE")))) {
            for (String string : oufitsFile.getConfigurationSection("MENU.OUTFITS").getKeys(false)) {
                if (event.getSlot() == oufitsFile.getInt("MENU.OUTFITS." + string + ".SLOT")) {
                    if (player.hasPermission(oufitsFile.getString("MENU.OUTFITS." + string + ".PERMISSION"))) {
                        ItemStack helmet = (new ArmorCreator(Material.valueOf(oufitsFile.getString("MENU.OUTFITS." + string + ".ARMOR.HELMET")))).color(convert(oufitsFile.getString("MENU.OUTFITS." + string + ".ARMOR.COLOR"))).build();
                        ItemStack chestplate = (new ArmorCreator(Material.valueOf(oufitsFile.getString("MENU.OUTFITS." + string + ".ARMOR.CHESTPLATE")))).color(convert(oufitsFile.getString("MENU.OUTFITS." + string + ".ARMOR.COLOR"))).build();
                        ItemStack leggings = (new ArmorCreator(Material.valueOf(oufitsFile.getString("MENU.OUTFITS." + string + ".ARMOR.LEGGINGS")))).color(convert(oufitsFile.getString("MENU.OUTFITS." + string + ".ARMOR.COLOR"))).build();
                        ItemStack boots = (new ArmorCreator(Material.valueOf(oufitsFile.getString("MENU.OUTFITS." + string + ".ARMOR.BOOTS")))).color(convert(oufitsFile.getString("MENU.OUTFITS." + string + ".ARMOR.COLOR"))).build();
                        player.getInventory().setHelmet(helmet);
                        player.getInventory().setChestplate(chestplate);
                        player.getInventory().setLeggings(leggings);
                        player.getInventory().setBoots(boots);
                        player.updateInventory();
                        player.sendMessage(CC.translate(oufitsFile.getString("MENU.OUTFITS." + string + ".MESSAGE")));
                    } else {
                        player.sendMessage(CC.translate(Lang.NO_PERMS));
                    }
                }

                // Clear Outfit
                if (event.getSlot() == oufitsFile.getInt("MENU.CLEAR-OUTFIT.SLOT")){
                    player.getInventory().setHelmet(null);
                    player.getInventory().setChestplate(null);
                    player.getInventory().setLeggings(null);
                    player.getInventory().setBoots(null);
                    player.sendMessage(CC.translate(oufitsFile.getString("MENU.CLEAR-OUTFIT.MESSAGE")));
                }
            }
            player.closeInventory();
            event.setCancelled(true);
        }
    }

    public static Color convert(String colorString) {
        if (colorString.contains("0"))
            return Color.BLACK;
        if (colorString.contains("1"))
            return Color.BLUE;
        if (colorString.contains("2"))
            return Color.GREEN;
        if (colorString.contains("3"))
            return Color.TEAL;
        if (colorString.contains("4"))
            return Color.RED;
        if (colorString.contains("5"))
            return Color.PURPLE;
        if (colorString.contains("6"))
            return Color.ORANGE;
        if (colorString.contains("7"))
            return Color.SILVER;
        if (colorString.contains("8"))
            return Color.GRAY;
        if (colorString.contains("9"))
            return Color.BLUE;
        if (colorString.contains("FileUtil"))
            return Color.LIME;
        if (colorString.contains("e"))
            return Color.YELLOW;
        if (colorString.contains("b"))
            return Color.AQUA;
        if (colorString.contains("d"))
            return Color.FUCHSIA;
        if (colorString.contains("f"))
            return Color.WHITE;
        return Color.BLACK;
    }
}