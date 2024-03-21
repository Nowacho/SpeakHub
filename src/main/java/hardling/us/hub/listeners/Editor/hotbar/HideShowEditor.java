package hardling.us.hub.listeners.Editor.hotbar;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.ArmorCreator;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.ConfigCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class HideShowEditor implements Listener {

    private static ItemStack panel = (new ArmorCreator(Material.STAINED_GLASS_PANE, 1, (short) 2)).setName("").build();
    private static ConfigCreator hideplayerFile = SpeakHub.getInst().getHideplayerFile();
    private static List<String> loreback = Arrays.asList(CC.getLINELIGH(), "&7Left click to return to the old menu", CC.getLINELIGH());

    /***************************
     SHOW/HIDE EDIT
     ***************************/

    public static void SHEdit(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&bShow/Hide Item Editor"));
        // GLASS PANEL
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, panel);
            }
        }
        // SHOW
        inv.setItem(3, (new ArmorCreator(Material.WOOL)).setDisplayName("&aShow").setDurability(5).build());
        // HIDE
        inv.setItem(5, (new ArmorCreator(Material.WOOL)).setDisplayName("&cHide").setDurability(14).build());
        // SHOW ITEM CHANGER
        inv.setItem(12, (new ArmorCreator(Material.AIR)).build());
        // HIDE ITEM CHANGER
        inv.setItem(14, (new ArmorCreator(Material.AIR)).build());
        // BACK MENU
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName("&cBack to the Hotbar Menu").setLore(loreback).build());
        // SOUND
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F);
        p.openInventory(inv);
    }

    @EventHandler
    public void onSHEditor(InventoryClickEvent event) {
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&bShow/Hide Item Editor"))) {
            ItemStack stack = event.getCurrentItem();
            Player p = (Player) event.getWhoClicked();
            if (event.getSlot() == 12) {
                if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.valueOf(hideplayerFile.getString("SHOW-PLAYER.MATERIAL"))) || event.getSlotType() == null) {
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                    p.sendMessage(CC.translate("&cMaterial Invalid"));
                    return;
                }
                hideplayerFile.set("SHOW-PLAYER.MATERIAL", String.valueOf(event.getView().getItem(12).getType()));
                hideplayerFile.set("SHOW-PLAYER.DATA", event.getView().getItem(12).getDurability());
                hideplayerFile.save();
                p.sendMessage(CC.translate("&aMaterial saved correctly"));
                p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
            }
            if (event.getSlot() == 14) {
                if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.valueOf(hideplayerFile.getString("HIDE-PLAYER.MATERIAL"))) || event.getSlotType() == null) {
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                    p.sendMessage(CC.translate("&cMaterial Invalid"));
                    return;
                }
                hideplayerFile.set("HIDE-PLAYER.MATERIAL", String.valueOf(event.getView().getItem(14).getType()));
                hideplayerFile.set("HIDE-PLAYER.DATA", event.getView().getItem(14).getDurability());
                hideplayerFile.save();
                p.sendMessage(CC.translate("&aMaterial saved correctly"));
                p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
            }
            if (event.getSlot() == 26) {
                HotBarEditor.showhideEdit(p);
            }
            event.setCancelled(true);
        }
    }
}