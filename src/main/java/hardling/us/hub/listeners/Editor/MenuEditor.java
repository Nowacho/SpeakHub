package hardling.us.hub.listeners.Editor;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.listeners.Editor.hotbar.HotBarEditor;
import hardling.us.hub.util.ArmorCreator;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.ConfigCreator;
import hardling.us.hub.util.Files.Lang;
import hardling.us.hub.util.SettingsUtils;
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

public class MenuEditor implements Listener {

    private static ItemStack panel = (new ArmorCreator(Material.STAINED_GLASS_PANE, 1, (short) 2)).setName("").build();
    private static String displaynameback = CC.translate("&7Back to previous menu");
    private static List<String> loreback = Arrays.asList(CC.getLINELIGH(), "&7Left-click to return to the main menu", CC.getLINELIGH());
    private static ConfigCreator selectorFile = SpeakHub.getInst().getSelectorFile();
    private static ConfigCreator pvPFile = SpeakHub.getInst().getPvpFile();
    private static ConfigCreator hubSelectorFile = SpeakHub.getInst().getHubselectorFile();
    private static ConfigCreator enderButtFile = SpeakHub.getInst().getEnderbuttFile();
    private static ConfigCreator mediaFile = SpeakHub.getInst().getMediaFile();
    private static ConfigCreator cosmeticsFile = SpeakHub.getInst().getCosmeticsFile();
    private static ConfigCreator hidePlayerFile = SpeakHub.getInst().getHideplayerFile();
    private static ConfigCreator configFile = SpeakHub.getInst().getConfigFile();
    private static ConfigCreator scoreboardFile = SpeakHub.getInst().getScoreboardFile();
    private static ConfigCreator tabFile = SpeakHub.getInst().getTabFile();

    /***************************************************
     *                  MAIN MENU EDITOR
     ****************************************************/
    public static void InventoryEditor(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&dSpeakHub In-Game Editor"));
        // HOTBAR EDITOR
        inv.setItem(4, (new ArmorCreator(Material.INK_SACK)).setDisplayName("&7Hotbar Editor").setLore(Arrays.asList(CC.getLINELIGH(), "&7Semi full hotbar editor", CC.getLINELIGH())).setDurability(5).build());

        // PROVIDER BOOLEAN
        inv.setItem(10, (new ArmorCreator(Material.INK_SACK)).setDisplayName("&7Provider Boolean").setLore(Arrays.asList(CC.getLINELIGH(), "&7Boolean editor of providers", CC.getLINELIGH())).setDurability(12).build());

        // SPEAKHUB CONFIG
        inv.setItem(16, (new ArmorCreator(Material.INK_SACK)).setDisplayName("&7Speakhub Config").setLore(Arrays.asList(CC.getLINELIGH(), "&7General editor on plugin configuration", CC.getLINELIGH())).setDurability(10).build());

        // QUEUE CHANGE MODE
        inv.setItem(22, (new ArmorCreator(Material.INK_SACK)).setDisplayName("&7Queue Mode").setLore(Arrays.asList(CC.getLINELIGH(), "&7Change queue mode", CC.getLINELIGH())).setDurability(9).build());

        // GLASS PANE
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.CHEST_OPEN, 2.0F, 2.0F);
        p.openInventory(inv);
    }

    @EventHandler
    public void onEditor(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&dSpeakHub In-Game Editor"))) {
            // PROVIDER BOOLEAN
            if (event.getSlot() == 10) {
                InventoryProvider(p);
            }

            // HOTBAR EDITOR
            if (event.getSlot() == 4) {
                InventoryHotbar(p);
            }

            // SPEAKHUB CONFIG
            if (event.getSlot() == 16) {
                invSpeakHubConfig(p);
            }

            // // QUEUE CHANGE MODE
            if (event.getSlot() == 22) {
                InventoryQueue(p);
            }
            event.setCancelled(true);
        }
    }


    /***************************************************
     *                  HOTBAR EDITOR
     ****************************************************/
    public static void InventoryHotbar(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&dHotBar Editor"));
        List<String> lore1 = Arrays.asList(CC.getLINELIGH(), "&fLeft Click to edit the Item", CC.getLINELIGH());
        // SELECTOR
        inv.setItem(10, (new ArmorCreator(Material.valueOf(selectorFile.getString("SELECTOR.MATERIAL")))).setDisplayName(selectorFile.getString("SELECTOR.DISPLAYNAME")).setDurability(selectorFile.getInt("SELECTOR.DATA")).setLore(lore1).build());

        // HUB-SELECTOR
        inv.setItem(11, (new ArmorCreator(Material.valueOf(hubSelectorFile.getString("HUB-SELECTOR.MATERIAL")))).setDisplayName(hubSelectorFile.getString("HUB-SELECTOR.DISPLAYNAME")).setDurability(hubSelectorFile.getInt("HUB-SELECTOR.DATA")).setLore(lore1).build());

        // MEDIA
        inv.setItem(12, (new ArmorCreator(Material.valueOf(mediaFile.getString("MEDIA.MATERIAL")))).setDisplayName(mediaFile.getString("MEDIA.DISPLAYNAME")).setDurability(mediaFile.getInt("MEDIA.DATA")).setLore(lore1).build());

        // ENDERBUTT
        inv.setItem(13, (new ArmorCreator(Material.valueOf(enderButtFile.getString("ENDERBUTT.MATERIAL")))).setDisplayName(enderButtFile.getString("ENDERBUTT.DISPLAYNAME")).setDurability(enderButtFile.getInt("ENDERBUTT.DATA")).setLore(lore1).build());

        // SHOW/HIDE PLAYER
        inv.setItem(14, (new ArmorCreator(Material.valueOf(hidePlayerFile.getString("SHOW-PLAYER.MATERIAL")))).setDisplayName(hidePlayerFile.getString("SHOW-PLAYER.DISPLAYNAME")).setDurability(hidePlayerFile.getInt("SHOW-PLAYER.DATA")).setLore(lore1).build());

        // PVP MODE
        inv.setItem(15, (new ArmorCreator(Material.valueOf(pvPFile.getString("PVP.MATERIAL")))).setDisplayName(pvPFile.getString("PVP.DISPLAYNAME")).setDurability(pvPFile.getInt("PVP.DATA")).setLore(lore1).build());

        // COSMETICS
        inv.setItem(16, (new ArmorCreator(Material.valueOf(cosmeticsFile.getString("COSMETIC.MATERIAL")))).setDisplayName(cosmeticsFile.getString("COSMETIC.DISPLAYNAME")).setDurability(cosmeticsFile.getInt("COSMETIC.DATA")).setLore(lore1).build());

        // BACK MENU
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName(displaynameback).setLore(loreback).build());

        // GLASS PANEL
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.CLICK, 2.0F, 2.0F);
        p.openInventory(inv);
    }

    @EventHandler
    public void onHotbarEditor(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&dHotBar Editor"))) {
            // SELECTOR
            if (event.getSlot() == 10) {
                HotBarEditor.selectorEdit(p);
                p.sendMessage(CC.translate("&aYou have opened the server-selector editor"));
            }

            // HUB SELECTOR
            if (event.getSlot() == 11) {
                HotBarEditor.hubselectorEdit(p);
                p.sendMessage(CC.translate("&aYou have opened the hub-selector editor"));
            }

            // MEDIA
            if (event.getSlot() == 12) {
                HotBarEditor.mediaEdit(p);
                p.sendMessage(CC.translate("&aYou have opened the media editor"));
            }

            // ENDERBUTT
            if (event.getSlot() == 13) {
                HotBarEditor.enderbuttEdit(p);
                p.sendMessage(CC.translate("&aYou have opened the enderbutt editor"));
            }

            // SHOWPLAYER
            if (event.getSlot() == 14) {
                HotBarEditor.showhideEdit(p);
                p.sendMessage(CC.translate("&aYou have opened the show/hide editor"));
            }

            // PVP MODE
            if (event.getSlot() == 15) {
                HotBarEditor.pvpmodeEdit(p);
                p.sendMessage(CC.translate("&aYou have opened the pvp mode editor"));
            }

            // COSMETICS
            if (event.getSlot() == 16) {
                HotBarEditor.cosmeticsEdit(p);
                p.sendMessage(CC.translate("&aYou have opened the cosmetics editor"));
            }

            // BACK MENU
            if (event.getSlot() == 26) {
                InventoryEditor(p);
            }
            event.setCancelled(true);
        }
    }

    /***************************************************
     *              SPEAKHUB CONFIG EDITOR
     ****************************************************/
    public static void invSpeakHubConfig(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&dSpeakHub Config"));
        // SET SPAWN PVP
        inv.setItem(0, (new ArmorCreator(Material.INK_SACK).setDurability(5).setDisplayName("&cSet SpawnPvP").setLore(Arrays.asList(CC.getLINELIGH(), "&7Set FileUtil Spawn for PvP Mode", CC.getLINELIGH())).build()));

        // CLEAR KIT PVP MDOE
        inv.setItem(6, (new ArmorCreator(Material.HOPPER)).setDisplayName("&bClear Kit PvP").setLore(Arrays.asList(CC.getLINELIGH(), "&7Clear all kit pvp mode ", CC.getLINELIGH())).build());

        // STOP SERVER
        inv.setItem(11, (new ArmorCreator(Material.NETHER_STAR)).setDisplayName("&4&lStop Server").setLore(Arrays.asList(CC.getLINELIGH(), "&7Stop the Server completely", CC.getLINELIGH())).build());

        // RELOAD CONFIG (SPEAKHUB)
        inv.setItem(13, (new ArmorCreator(Material.WATCH)).setDisplayName("&6Reload Config").setLore(Arrays.asList(CC.getLINELIGH(), "&7Reload all configuration(SpeakHub)", CC.getLINELIGH())).build());

        // SET SPAWN WORLD
        inv.setItem(18, (new ArmorCreator(Material.INK_SACK).setDurability(10)).setDisplayName("&3Set Spawn World").setLore(Arrays.asList(CC.getLINELIGH(), "&7Place FileUtil Spawn zone world", CC.getLINELIGH())).build());

        // SET KIT PVP MODE
        if (pvPFile.contains("KIT.ARMOR") || pvPFile.contains("KIT.INVENTORY")) {
            inv.setItem(24, (new ArmorCreator(Material.STORAGE_MINECART)).setDisplayName("&9Set KitPvP").setLore(Arrays.asList(CC.getLINELIGH(), "&7The PvP Kit is found with items", CC.getLINELIGH())).build());
        } else {
            inv.setItem(24, (new ArmorCreator(Material.MINECART)).setDisplayName("&9Set KitPvP").setLore(Arrays.asList(CC.getLINELIGH(), "&7The PvP Kit is completely empty.", CC.getLINELIGH())).build());
        }

        // BACK MENU
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName(displaynameback).setLore(loreback).build());
        // GLASS PANEL
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.CLICK, 2.0F, 2.0F);
        p.openInventory(inv);
    }

    @EventHandler
    public void onSpeakHubConfig(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&dSpeakHub Config"))) {
            // SET SPAWN PVP
            if (event.getSlot() == 0) {
                SettingsUtils.setlocationToString(p, "SPAWN_PVP");
            }

            // CLEAR KIT PVP
            if (event.getSlot() == 6) {
                pvPFile.set("KIT", null);
                pvPFile.save();
                p.sendMessage(CC.translate(Lang.CLEAR_KIT_PVP));
                invSpeakHubConfig(p);
            }

            // STOP SERVER
            if (event.getSlot() == 11) {
                Bukkit.getServer().shutdown();
            }

            // RELOAD ALL CONFIG
            if (event.getSlot() == 13) {
                SettingsUtils.reloadConfig(p);
            }

            // SET SPAWN WORLD
            if (event.getSlot() == 18) {
                SettingsUtils.setlocationToString(p, "SPAWN");
            }

            // SET KIT PVP
            if (event.getSlot() == 24) {
                pvPFile.set("KIT.INVENTORY", p.getInventory().getContents());
                pvPFile.set("KIT.ARMOR", p.getInventory().getArmorContents());
                pvPFile.save();
                p.sendMessage(CC.translate("&aThe PvP Kit was correctly placed"));
                invSpeakHubConfig(p);
            }

            // BACK MENU
            if (event.getSlot() == 26) {
                InventoryEditor(p);
            }
            event.setCancelled(true);
        }
    }

    /***************************************************
     *                 PROVIDER EDITOR
     ***************************************************/
    public static void InventoryProvider(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&bProvider Editor"));
        List<String> lore = Arrays.asList(CC.getLINELIGH(), "&7Left Click to disable", CC.getLINELIGH());
        List<String> lore2 = Arrays.asList(CC.getLINELIGH(), "&7Left Click to enable", CC.getLINELIGH());
        // SCOREBOARD ENABLE
        if (scoreboardFile.getBoolean("SCOREBOARD.ENABLED")) {
            inv.setItem(15, (new ArmorCreator(Material.WOOL).setDurability(5)).setDisplayName("&aScoreboard").setLore(lore).build());
        } else {
            // SCOREBOARD DISABLED
            inv.setItem(15, (new ArmorCreator(Material.WOOL).setDurability(14)).setDisplayName("&cScoreboard").setLore(lore2).build());
        }

        // TABLIST ENABLE
        if (tabFile.getBoolean("TABLIST.ENABLED")) {
            inv.setItem(11, (new ArmorCreator(Material.WOOL).setDurability(5)).setDisplayName("&aTabList").setLore(lore).build());
        } else {
            // TABLIST DISABLED
            inv.setItem(11, (new ArmorCreator(Material.WOOL).setDurability(14)).setDisplayName("&cTabList").setLore(lore2).build());
        }

        // BACK MENU
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName(displaynameback).setLore(loreback).build());

        // GLASS PANEL
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.CLICK, 2.0F, 2.0F);
        p.openInventory(inv);
    }

    @EventHandler
    public void onProviderEditor(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&bProvider Editor"))) {
            // TABLIST
            if (event.getSlot() == 11) {
                if (tabFile.getBoolean("TABLIST.ENABLED")) {
                    tabFile.set("TABLIST.ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the TabList Correctly"));
                } else {
                    tabFile.set("TABLIST.ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the TabList Correctly"));
                }
                tabFile.save();
                p.sendMessage(CC.translate(Lang.STOP_SERVER));
                InventoryProvider(p);
            }

            // SCOREBOARD
            if (event.getSlot() == 15) {
                if (scoreboardFile.getBoolean("SCOREBOARD.ENABLED")) {
                    scoreboardFile.set("SCOREBOARD.ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Scoreboard Correctly"));
                } else {
                    scoreboardFile.set("SCOREBOARD.ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Scoreboard Correctly"));
                }
                scoreboardFile.save();
                p.sendMessage(CC.translate(Lang.STOP_SERVER));
                InventoryProvider(p);
            }

            // BACK MENU
            if (event.getSlot() == 26) {
                InventoryEditor(p);
            }
            event.setCancelled(true);
        }
    }

    /***************************************************
     *                  QUEUE EDITOR
     ***************************************************/
    public static void InventoryQueue(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&aQueue Editor"));
        List<String> lore1 = Arrays.asList(CC.getLINELIGH(), "&7Left click to select Queue", CC.getLINELIGH());
        //  EZQUEUE
        inv.setItem(10, (new ArmorCreator(Material.INK_SACK)).setDisplayName("&aEzQueue Queue").setDurability(13).setLore(lore1).build());

        // AJQUEUE
        inv.setItem(12, (new ArmorCreator(Material.INK_SACK)).setDisplayName("&3AjQueue Queue").setDurability(12).setLore(lore1).build());

        // PORTAL-QUEUE
        inv.setItem(14, (new ArmorCreator(Material.INK_SACK)).setDisplayName("&dPortal Queue").setDurability(11).setLore(lore1).build());

        // SPEAK QUEUE
        inv.setItem(16, (new ArmorCreator(Material.INK_SACK)).setDisplayName("&4Speak Queue").setDurability(10).setLore(lore1).build());

        // NONE
        inv.setItem(22, (new ArmorCreator(Material.RECORD_11)).setDisplayName("&7None").setLore(lore1).build());

        // BACK MENU
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName(displaynameback).setLore(loreback).build());

        // GLASS PANEL
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.CLICK, 2.0F, 2.0F);
        p.openInventory(inv);
    }

    @EventHandler
    public void onQueueEditor(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&aQueue Editor"))) {
            // EZQUEUE
            if (event.getSlot() == 10) {
                configFile.set("SETTINGS.QUEUE", "EZQUEUE");
                p.sendMessage(CC.translate(Lang.QUEUE_CONFIGURED + "&fEzQueue"));
            }

            // AJQUEUE
            if (event.getSlot() == 12) {
                configFile.set("SETTINGS.QUEUE", "AJQUEUE");
                p.sendMessage(CC.translate(Lang.QUEUE_CONFIGURED + "&fajQueue"));
            }

            // PORTAL
            if (event.getSlot() == 14) {
                configFile.set("SETTINGS.QUEUE", "PORTAL");
                p.sendMessage(CC.translate(Lang.QUEUE_CONFIGURED + "&fPortalOld"));
            }

            // SPEAKQUEUE
            if (event.getSlot() == 16) {
                configFile.set("SETTINGS.QUEUE", "SPEAKQUEUE");
                p.sendMessage(CC.translate(Lang.QUEUE_CONFIGURED + "&fSpeakQueue"));
            }

            // NONE
            if (event.getSlot() == 22) {
                configFile.set("SETTINGS.QUEUE", "NONE");
                p.sendMessage(CC.translate(Lang.QUEUE_CONFIGURED + "&fDefault"));
            }

            // UPDATE CONFIG - MESSAGE
            if (event.getSlot() == 10 || event.getSlot() == 12 || event.getSlot() == 14 || event.getSlot() == 16 || event.getSlot() == 22) {
                configFile.save();
                p.sendMessage(CC.translate(Lang.STOP_SERVER));
            }

            // BACK MENU
            if (event.getSlot() == 26) {
                InventoryEditor(p);
            }
            event.setCancelled(true);
        }
    }
}