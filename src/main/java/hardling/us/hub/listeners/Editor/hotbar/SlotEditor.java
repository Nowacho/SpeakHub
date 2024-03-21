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

public class SlotEditor implements Listener {

    private static List<String> loreback = Arrays.asList(CC.getLINELIGH(), "&7Left-click to return to the main menu", CC.getLINELIGH());
    private static ItemStack panel = (new ArmorCreator(Material.STAINED_GLASS_PANE, 1, (short) 2)).setName("").build();
    private static String title = "&cBack to the Hotbar Menu";
    String slot = CC.translate("&aSwitched to slot ");

    /***************************
     SELECTOR EDIT
     ***************************/
    public static void SEditor(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&aServer-Selector Slot Editor"));
        // SELECT SLOT
        for (int i = 9; i <= 17; i++) {
            inv.setItem(i, (new ArmorCreator(Material.WOOL)).setDisplayName("&aSlot " + (i - 8)).setDurability(5).build());
        }

        // BACK MENU
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName(title).setLore(loreback).build());

        // GLASS PANEL
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F);
        p.openInventory(inv);
    }

    @EventHandler
    public void onSEditor(InventoryClickEvent event) {
        ConfigCreator selectorFile = SpeakHub.get().getSelectorFile();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        Player p = (Player) event.getWhoClicked();
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&aServer-Selector Slot Editor"))) {
            // SELECT SLOT
            for (int i = 9; i <= 17; i++) {
                if (event.getSlot() == i) {
                    selectorFile.set("SELECTOR.SLOT", i - 8);
                    selectorFile.save();
                    p.sendMessage(CC.translate(slot + "&7(" + (i - 8) + ")"));
                    p.closeInventory();
                }
            }
            // BACK MENU
            if (event.getSlot() == 26) {
                HotBarEditor.selectorEdit(p);
            }
            event.setCancelled(true);
        }
    }

    /***************************
     HUB-SELECTOR EDIT
     ***************************/
    public static void HEditor(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&bHub-Selector Slot Editor"));
        // SELECT SLOT
        for (int i = 9; i <= 17; i++) {
            inv.setItem(i, (new ArmorCreator(Material.WOOL)).setDisplayName("&aSlot " + (i - 8)).setDurability(5).build());
        }

        // BACK MENU
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName(title).setLore(loreback).build());

        // GLASS PANEL
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F);
        p.openInventory(inv);
    }

    @EventHandler
    public void onHEditor(InventoryClickEvent event) {
         ConfigCreator hubselectorFile = SpeakHub.getInst().getHubselectorFile();
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&bHub-Selector Slot Editor"))) {
            // SELECT SLOT
            for (int i = 9; i <= 17; i++) {
                if (event.getSlot() == i) {
                    hubselectorFile.set("HUB-SELECTOR.SLOT", i - 8);
                    hubselectorFile.save();
                    p.sendMessage(CC.translate(slot + "&7(" + (i - 8) + ")"));
                    p.closeInventory();
                }
            }
            // BACK MENU
            if (event.getSlot() == 26) {
                HotBarEditor.hubselectorEdit(p);
            }
            event.setCancelled(true);
        }
    }

    /***************************
     MEDIA EDIT
     ***************************/
    public static void MEditor(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&3Media Slot Editor"));
        // SELECT SLOT
        for (int i = 9; i <= 17; i++) {
            inv.setItem(i, (new ArmorCreator(Material.WOOL)).setDisplayName("&aSlot " + (i - 8)).setDurability(5).build());
        }

        // BACK MENU
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName(title).setLore(loreback).build());

        // GLASS PANEL
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F);
        p.openInventory(inv);
    }

    @EventHandler
    public void onMEditor(InventoryClickEvent event) {
        ConfigCreator mediaFile = SpeakHub.getInst().getMediaFile();
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&3Media Slot Editor"))) {
            // SELECT SLOT
            for (int i = 9; i <= 17; i++) {
                if (event.getSlot() == i) {
                    mediaFile.set("MEDIA.SLOT", i - 8);
                    mediaFile.save();
                    p.sendMessage(CC.translate(slot + "&7(" + (i - 8) + ")"));
                    p.closeInventory();
                }
            }
            // BACK MENU
            if (event.getSlot() == 26) {
                HotBarEditor.mediaEdit(p);
            }
            event.setCancelled(true);
        }
    }

    /***************************
     ENDERBUTT EDIT
     ***************************/
    public static void EEditor(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&4EnderButt Slot Editor"));
        // SELECT SLOT
        for (int i = 9; i <= 17; i++) {
            inv.setItem(i, (new ArmorCreator(Material.WOOL)).setDisplayName("&aSlot " + (i - 8)).setDurability(5).build());
        }

        // BACK MENU
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName(title).setLore(loreback).build());

        // GLASS PANEL
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F);
        p.openInventory(inv);
    }

    @EventHandler
    public void onEEditor(InventoryClickEvent event) {
        ConfigCreator enderbuttFile = SpeakHub.getInst().getEnderbuttFile();
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&4EnderButt Slot Editor"))) {
            // SELECT SLOT
            for (int i = 9; i <= 17; i++) {
                if (event.getSlot() == i) {
                    enderbuttFile.set("ENDERBUTT.SLOT", i - 8);
                    enderbuttFile.save();
                    p.sendMessage(CC.translate(slot + "&7(" + (i - 8) + ")"));
                    p.closeInventory();
                }
            }
            // BACK MENU
            if (event.getSlot() == 26) {
                HotBarEditor.enderbuttEdit(p);
            }
            event.setCancelled(true);
        }
    }


    /***************************
     SHOW/HIDE EDIT
     ***************************/
    public static void SHEditor(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&bShow/Hide Slot Editor"));
        // SELECT SLOT
        for (int i = 9; i <= 17; i++) {
            inv.setItem(i, (new ArmorCreator(Material.WOOL)).setDisplayName("&aSlot " + (i - 8)).setDurability(5).build());
        }

        // BACK MENU
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName(title).setLore(loreback).build());

        // GLASS PANEL
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F);
        p.openInventory(inv);
    }

    @EventHandler
    public void onSHditor(InventoryClickEvent event) {
        ConfigCreator hideplayerFile = SpeakHub.getInst().getHideplayerFile();
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&bShow/Hide Slot Editor"))) {
            // SELECT SLOT
            for (int i = 9; i <= 17; i++) {
                if (event.getSlot() == i) {
                    hideplayerFile.set("HIDE-SHOW.SLOT", i - 8);
                    hideplayerFile.save();
                    p.sendMessage(CC.translate(slot + "&7(" + (i - 8) + ")"));
                    p.closeInventory();
                }
            }

            // BACK MENU
            if (event.getSlot() == 26) {
                HotBarEditor.showhideEdit(p);
            }
            event.setCancelled(true);
        }
    }

    /***************************
     PVP-MODE EDIT
     ***************************/
    public static void PEditor(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&cPvP Slot Editor"));
        // SELECT SLOT
        for (int i = 9; i <= 17; i++) {
            inv.setItem(i, (new ArmorCreator(Material.WOOL)).setDisplayName("&aSlot " + (i - 8)).setDurability(5).build());
        }

        // BACK MENU
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName(title).setLore(loreback).build());

        // GLASS PANEL
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F);
        p.openInventory(inv);
    }

    @EventHandler
    public void onPEditor(InventoryClickEvent event) {
        ConfigCreator pvpFile = SpeakHub.getInst().getPvpFile();
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&cPvP Slot Editor"))) {
            // SELECT SLOT
            for (int i = 9; i <= 17; i++) {
                if (event.getSlot() == i) {
                    pvpFile.set("PVP.SLOT", i - 8);
                    pvpFile.save();
                    p.sendMessage(CC.translate(slot + "&7(" + (i - 8) + ")"));
                    p.closeInventory();
                }
            }

            // BACK MENU
            if (event.getSlot() == 26) {
                HotBarEditor.pvpmodeEdit(p);
            }
            event.setCancelled(true);
        }
    }

    public static void CEditor(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&4Cosmetic Slot Editor"));
        // SELECT SLOT
        for (int i = 9; i <= 17; i++) {
            inv.setItem(i, (new ArmorCreator(Material.WOOL)).setDisplayName("&aSlot " + (i - 8)).setDurability(5).build());
        }

        // BACK MENU
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName(title).setLore(loreback).build());

        // GLASS PANEL
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F);
        p.openInventory(inv);
    }

    @EventHandler
    public void onCEditor(InventoryClickEvent event) {
        ConfigCreator cosmeticsFile = SpeakHub.getInst().getCosmeticsFile();
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&4Cosmetic Slot Editor"))) {

            // SELECT SLOT
            for (int i = 9; i <= 17; i++) {
                if (event.getSlot() == i) {
                    cosmeticsFile.set("COSMETIC.SLOT", i - 8);
                    cosmeticsFile.save();
                    p.sendMessage(CC.translate(slot + "&7(" + (i - 8) + ")"));
                    p.closeInventory();
                }
            }

            // BACK MENU
            if (event.getSlot() == 26) {
                HotBarEditor.cosmeticsEdit(p);
            }
            event.setCancelled(true);
        }
    }
}