package hardling.us.hub.listeners.Editor.hotbar;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.listeners.Editor.MenuEditor;
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

public class HotBarEditor implements Listener {

    private static ItemStack panel = (new ArmorCreator(Material.STAINED_GLASS_PANE, 1, (short) 2)).setName("").build();
    private static List<String> lore1 = Arrays.asList(CC.getLINELIGH(), "&7Place FileUtil slot in the Item", CC.getLINELIGH());
    private static List<String> lore2 = Arrays.asList(CC.getLINELIGH(), "&7Click here to change item", CC.getLINELIGH());
    private static List<String> loreON = Arrays.asList(CC.getLINELIGH(), "&7Click here to disable", CC.getLINELIGH());
    private static List<String> loreOFF = Arrays.asList(CC.getLINELIGH(), "&7Click here to activate", CC.getLINELIGH());
    private static List<String> loreback = Arrays.asList(CC.getLINELIGH(), "&7Left-click to return to the hotbar menu", CC.getLINELIGH());
    private static ConfigCreator selectorFile = SpeakHub.get().getSelectorFile();
    private static ConfigCreator pvpFile = SpeakHub.getInst().getPvpFile();
    private static ConfigCreator hubselectorFile = SpeakHub.getInst().getHubselectorFile();
    private static ConfigCreator enderbuttFile = SpeakHub.getInst().getEnderbuttFile();
    private static ConfigCreator mediaFile = SpeakHub.getInst().getMediaFile();
    private static ConfigCreator cosmeticsFile = SpeakHub.getInst().getCosmeticsFile();
    private static ConfigCreator hideplayerFile = SpeakHub.getInst().getHideplayerFile();

    /***************************
     SELECTOR EDIT
     ***************************/

    public static void selectorEdit(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&aServer-Selector Editor"));

        inv.setItem(11, (new ArmorCreator(Material.SIGN)).setDisplayName("&aSlot Hotbar").setLore(lore1).build()); // SLOT
        inv.setItem(12, (new ArmorCreator(Material.valueOf(selectorFile.getString("SELECTOR.MATERIAL")))).setDisplayName(selectorFile.getString("SELECTOR.DISPLAYNAME")).setDurability(selectorFile.getInt("SELECTOR.DATA")).setLore(lore2).build()); // HOTBAR ITEM
        if (selectorFile.getBoolean("SELECTOR.SOUND_ENABLED")) { // ON / OFF // SOUND
            inv.setItem(13, (new ArmorCreator(Material.NOTE_BLOCK)).setDisplayName("&aSound activated").setLore(loreON).build());
        } else {
            inv.setItem(13, (new ArmorCreator(Material.JUKEBOX)).setDisplayName("&cSound disabled").setLore(loreOFF).build());
        }
        if (selectorFile.getBoolean("SELECTOR.ENCHANTED")) { // ON / OFF  // ENCHANTED
            inv.setItem(15, (new ArmorCreator(Material.ENCHANTMENT_TABLE)).setDisplayName("&aEnchantment activated").setLore(loreON).build());
        } else {
            inv.setItem(15, (new ArmorCreator(Material.ENDER_PORTAL_FRAME)).setDisplayName("&cEnchantment disabled").setLore(loreOFF).build());
        }
        if (selectorFile.getBoolean("SELECTOR.ENABLED")) { // ON / OFF
            inv.setItem(14, (new ArmorCreator(Material.WOOL)).setDisplayName("&aActivated").setDurability(5).setLore(loreON).build());
        } else {
            inv.setItem(14, (new ArmorCreator(Material.WOOL)).setDisplayName("&cDisabled").setDurability(14).setLore(loreOFF).build());
        }
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName("&cBack to the Hotbar Menu").setLore(loreback).build()); // BACK

        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F); // SOUND
        p.openInventory(inv);
    }


    @EventHandler
    public void onSelectorEditor(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&aServer-Selector Editor"))) {

            if (event.getSlot() == 11) { // SLOT
                SlotEditor.SEditor(p);
            }

            if (event.getSlot() == 12) { // ITEM
                ItemStack stack = event.getView().getItem(12);
                if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.valueOf(selectorFile.getString("SELECTOR.MATERIAL"))) || event.getSlotType() == null) {
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                    p.sendMessage(CC.translate("&cMaterial Invalid"));
                    return;
                }
                selectorFile.set("SELECTOR.MATERIAL", String.valueOf(event.getView().getItem(12).getType()));
                selectorFile.set("SELECTOR.DATA", event.getView().getItem(12).getDurability());
                selectorFile.save();
                p.sendMessage(CC.translate("&aMaterial saved correctly"));
                p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
                HotBarEditor.selectorEdit(p);
            }

            if (event.getSlot() == 13) { // SOUND
                if (selectorFile.getBoolean("SELECTOR.SOUND_ENABLED")) {
                    selectorFile.set("SELECTOR.SOUND_ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Sound Correctly"));
                } else {
                    selectorFile.set("SELECTOR.SOUND_ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Sound Correctly"));
                }
                selectorFile.save();
                selectorEdit(p);
            }
            if (event.getSlot() == 14) { // ON / OFF
                if (selectorFile.getBoolean("SELECTOR.ENABLED")) {
                    selectorFile.set("SELECTOR.ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Item Correctly"));
                } else {
                    selectorFile.set("SELECTOR.ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Item Correctly"));
                }
                selectorFile.save();
                selectorEdit(p);
            }
            if (event.getSlot() == 15) { // ENCHANTED
                if (selectorFile.getBoolean("SELECTOR.ENCHANTED")) {
                    selectorFile.set("SELECTOR.ENCHANTED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Enchantment Correctly"));
                } else {
                    selectorFile.set("SELECTOR.ENCHANTED", true);
                    p.sendMessage(CC.translate("&aActivated the Enchantment Correctly"));
                }
                selectorFile.save();
                selectorEdit(p);
            }
            if (event.getSlot() == 26) {
                MenuEditor.InventoryHotbar(p);
            }
            event.setCancelled(true);
        }

    }

    /***************************
     HUB-SELECTOR EDIT
     ***************************/

    public static void hubselectorEdit(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&bHub-Selector Editor"));

        inv.setItem(11, (new ArmorCreator(Material.SIGN)).setDisplayName("&aSlot Hotbar").setLore(lore1).build()); // SLOT
        inv.setItem(12, (new ArmorCreator(Material.valueOf(hubselectorFile.getString("HUB-SELECTOR.MATERIAL")))).setDisplayName(hubselectorFile.getString("HUB-SELECTOR.DISPLAYNAME")).setDurability(hubselectorFile.getInt("HUB-SELECTOR.DATA")).setLore(lore2).build()); // HOTBAR ITEM
        if (hubselectorFile.getBoolean("HUB-SELECTOR.SOUND_ENABLED")) { // ON / OFF // SOUND
            inv.setItem(13, (new ArmorCreator(Material.NOTE_BLOCK)).setDisplayName("&aSound activated").setLore(loreON).build());
        } else {
            inv.setItem(13, (new ArmorCreator(Material.JUKEBOX)).setDisplayName("&cSound disabled").setLore(loreOFF).build());
        }
        if (hubselectorFile.getBoolean("HUB-SELECTOR.ENCHANTED")) { // ON / OFF  // ENCHANTED
            inv.setItem(15, (new ArmorCreator(Material.ENCHANTMENT_TABLE)).setDisplayName("&aEnchantment activated").setLore(loreON).build());
        } else {
            inv.setItem(15, (new ArmorCreator(Material.ENDER_PORTAL_FRAME)).setDisplayName("&cEnchantment disabled").setLore(loreOFF).build());
        }
        if (hubselectorFile.getBoolean("HUB-SELECTOR.ENABLED")) { // ON / OFF
            inv.setItem(14, (new ArmorCreator(Material.WOOL)).setDisplayName("&aActivated").setDurability(5).setLore(loreON).build());
        } else {
            inv.setItem(14, (new ArmorCreator(Material.WOOL)).setDisplayName("&cDisabled").setDurability(14).setLore(loreOFF).build());
        }
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName("&cBack to the Hotbar Menu").setLore(loreback).build()); // BACK

        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F); // SOUND
        p.openInventory(inv);
    }


    @EventHandler
    public void onHubSelectorEditor(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&bHub-Selector Editor"))) {

            if (event.getSlot() == 11) { // SLOT
                SlotEditor.HEditor(p);
            }
            if (event.getSlot() == 12) {// ITEM
                ItemStack stack = event.getView().getItem(12);
                if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.valueOf(hubselectorFile.getString("HUB-SELECTOR.MATERIAL"))) || event.getSlotType() == null) {
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                    p.sendMessage(CC.translate("&cMaterial Invalid"));
                    return;
                }
                hubselectorFile.set("HUB-SELECTOR.MATERIAL", String.valueOf(event.getView().getItem(12).getType()));
                hubselectorFile.set("HUB-SELECTOR.DATA", event.getView().getItem(12).getDurability());
                hubselectorFile.save();
                p.sendMessage(CC.translate("&aMaterial saved correctly"));
                p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
                HotBarEditor.hubselectorEdit(p);
            }


            if (event.getSlot() == 13) { // SOUND
                if (hubselectorFile.getBoolean("HUB-SELECTOR.SOUND_ENABLED")) {
                    hubselectorFile.set("SELECTOR.SOUND_ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Sound Correctly"));
                } else {
                    hubselectorFile.set("HUB-SELECTOR.SOUND_ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Sound Correctly"));
                }
                hubselectorFile.save();
                hubselectorEdit(p);
            }
            if (event.getSlot() == 14) { // ON / OFF
                if (hubselectorFile.getBoolean("HUB-SELECTOR.ENABLED")) {
                    hubselectorFile.set("HUB-SELECTOR.ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Item Correctly"));
                } else {
                    hubselectorFile.set("HUB-SELECTOR.ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Item Correctly"));
                }
                hubselectorFile.save();
                hubselectorEdit(p);
            }
            if (event.getSlot() == 15) { // ENCHANTED
                if (hubselectorFile.getBoolean("HUB-SELECTOR.ENCHANTED")) {
                    hubselectorFile.set("HUB-SELECTOR.ENCHANTED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Enchantment Correctly"));
                } else {
                    hubselectorFile.set("HUB-SELECTOR.ENCHANTED", true);
                    p.sendMessage(CC.translate("&aActivated the Enchantment Correctly"));
                }
                hubselectorFile.save();
                hubselectorEdit(p);
            }
            if (event.getSlot() == 26) {
                MenuEditor.InventoryHotbar(p);
            }
            event.setCancelled(true);
        }
    }

    /***************************
     MEDIA EDIT
     ***************************/

    public static void mediaEdit(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&3Media Editor"));

        inv.setItem(11, (new ArmorCreator(Material.SIGN)).setDisplayName("&aSlot Hotbar").setLore(lore1).build()); // SLOT
        inv.setItem(12, (new ArmorCreator(Material.valueOf(mediaFile.getString("MEDIA.MATERIAL")))).setDisplayName(mediaFile.getString("MEDIA.DISPLAYNAME")).setDurability(mediaFile.getInt("MEDIA.DATA")).setLore(lore2).build()); // HOTBAR ITEM
        if (mediaFile.getBoolean("MEDIA.SOUND_ENABLED")) { // ON / OFF // SOUND
            inv.setItem(13, (new ArmorCreator(Material.NOTE_BLOCK)).setDisplayName("&aSound activated").setLore(loreON).build());
        } else {
            inv.setItem(13, (new ArmorCreator(Material.JUKEBOX)).setDisplayName("&cSound disabled").setLore(loreOFF).build());
        }
        if (mediaFile.getBoolean("MEDIA.ENCHANTED")) { // ON / OFF  // ENCHANTED
            inv.setItem(15, (new ArmorCreator(Material.ENCHANTMENT_TABLE)).setDisplayName("&aEnchantment activated").setLore(loreON).build());
        } else {
            inv.setItem(15, (new ArmorCreator(Material.ENDER_PORTAL_FRAME)).setDisplayName("&cEnchantment disabled").setLore(loreOFF).build());
        }
        if (mediaFile.getBoolean("MEDIA.ENABLED")) { // ON / OFF
            inv.setItem(14, (new ArmorCreator(Material.WOOL)).setDisplayName("&aActivated").setDurability(5).setLore(loreON).build());
        } else {
            inv.setItem(14, (new ArmorCreator(Material.WOOL)).setDisplayName("&cDisabled").setDurability(14).setLore(loreOFF).build());
        }

        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName("&cBack to the Hotbar Menu").setLore(loreback).build()); // BACK

        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F); // SOUND
        p.openInventory(inv);
    }


    @EventHandler
    public void onMediaEditor(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&3Media Editor"))) {

            if (event.getSlot() == 11) { // SLOT
                SlotEditor.MEditor(p);
            }
            if (event.getSlot() == 12) { // ITEM
                ItemStack stack = event.getCurrentItem();
                if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.valueOf(mediaFile.getString("MEDIA.MATERIAL"))) || event.getSlotType() == null) {
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                    p.sendMessage(CC.translate("&cMaterial Invalid"));
                    return;
                }
                mediaFile.set("MEDIA.MATERIAL", String.valueOf(event.getView().getItem(12).getType()));
                mediaFile.set("MEDIA.DATA", event.getView().getItem(12).getDurability());
                mediaFile.save();
                p.sendMessage(CC.translate("&aMaterial saved correctly"));
                p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
                HotBarEditor.mediaEdit(p);
            }

            if (event.getSlot() == 13) { // SOUND
                if (mediaFile.getBoolean("MEDIA.SOUND_ENABLED")) {
                    mediaFile.set("MEDIA.SOUND_ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Sound Correctly"));
                } else {
                    mediaFile.set("MEDIA.SOUND_ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Sound Correctly"));
                }
                mediaFile.save();
                mediaEdit(p);
            }
            if (event.getSlot() == 14) { // ON / OFF
                if (mediaFile.getBoolean("MEDIA.ENABLED")) {
                    mediaFile.set("MEDIA.ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Item Correctly"));
                } else {
                    mediaFile.set("MEDIA.ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Item Correctly"));
                }
                mediaFile.save();
                mediaEdit(p);
            }
            if (event.getSlot() == 15) { // ENCHANTED
                if (mediaFile.getBoolean("MEDIA.ENCHANTED")) {
                    mediaFile.set("MEDIA.ENCHANTED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Enchantment Correctly"));
                } else {
                    mediaFile.set("MEDIA.ENCHANTED", true);
                    p.sendMessage(CC.translate("&aActivated the Enchantment Correctly"));
                }
                mediaFile.save();
                mediaEdit(p);
            }
            if (event.getSlot() == 26) {
                MenuEditor.InventoryHotbar(p);
            }
            event.setCancelled(true);
        }
    }


    /***************************
     ENDERBUTT EDIT
     ***************************/

    public static void enderbuttEdit(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&dEnderButt Editor"));

        inv.setItem(11, (new ArmorCreator(Material.SIGN)).setDisplayName("&aSlot Hotbar").setLore(lore1).build()); // SLOT
        inv.setItem(12, (new ArmorCreator(Material.valueOf(enderbuttFile.getString("ENDERBUTT.MATERIAL")))).setDisplayName(enderbuttFile.getString("ENDERBUTT.DISPLAYNAME")).setDurability(enderbuttFile.getInt("ENDERBUTT.DATA")).setLore(lore1).build()); // HOTBAR ITEM
        if (enderbuttFile.getBoolean("ENDERBUTT.SOUND_ENABLED")) { // ON / OFF // SOUND
            inv.setItem(13, (new ArmorCreator(Material.NOTE_BLOCK)).setDisplayName("&aSound activated").setLore(loreON).build());
        } else {
            inv.setItem(13, (new ArmorCreator(Material.JUKEBOX)).setDisplayName("&cSound disabled").setLore(loreOFF).build());
        }
        if (enderbuttFile.getBoolean("ENDERBUTT.ENCHANTED")) { // ON / OFF  // ENCHANTED
            inv.setItem(15, (new ArmorCreator(Material.ENCHANTMENT_TABLE)).setDisplayName("&aEnchantment activated").setLore(loreON).build());
        } else {
            inv.setItem(15, (new ArmorCreator(Material.ENDER_PORTAL_FRAME)).setDisplayName("&cEnchantment disabled").setLore(loreOFF).build());
        }
        if (enderbuttFile.getBoolean("ENDERBUTT.ENABLED")) { // ON / OFF
            inv.setItem(14, (new ArmorCreator(Material.WOOL)).setDisplayName("&aActivated").setDurability(5).setLore(loreON).build());
        } else {
            inv.setItem(14, (new ArmorCreator(Material.WOOL)).setDisplayName("&cDisabled").setDurability(14).setLore(loreOFF).build());
        }
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName("&cBack to the Hotbar Menu").setLore(loreback).build()); // BACK

        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F); // SOUND
        p.openInventory(inv);
    }


    @EventHandler
    public void onEnderbuttEditor(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&dEnderButt Editor"))) {

            if (event.getSlot() == 11) { // SLOT
                SlotEditor.EEditor(p);
            }
            if (event.getSlot() == 12) { // ITEM
                ItemStack stack = event.getCurrentItem();
                if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.valueOf(enderbuttFile.getString("ENDERBUTT.MATERIAL"))) || event.getSlotType() == null) {
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                    p.sendMessage(CC.translate("&cMaterial Invalid"));
                    return;
                }
                enderbuttFile.set("ENDERBUTT.MATERIAL", String.valueOf(event.getView().getItem(12).getType()));
                enderbuttFile.set("ENDERBUTT.DATA", event.getView().getItem(12).getDurability());
                enderbuttFile.save();
                p.sendMessage(CC.translate("&aMaterial saved correctly"));
                p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
                HotBarEditor.enderbuttEdit(p);
            }
            if (event.getSlot() == 13) { // SOUND
                if (enderbuttFile.getBoolean("ENDERBUTT.SOUND_ENABLED")) {
                    enderbuttFile.set("ENDERBUTT.SOUND_ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Sound Correctly"));
                } else {
                    enderbuttFile.set("ENDERBUTT.SOUND_ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Sound Correctly"));
                }
                enderbuttFile.save();
                enderbuttEdit(p);
            }
            if (event.getSlot() == 14) { // ON / OFF
                if (enderbuttFile.getBoolean("ENDERBUTT.ENABLED")) {
                    enderbuttFile.set("ENDERBUTT.ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Item Correctly"));
                } else {
                    enderbuttFile.set("ENDERBUTT.ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Item Correctly"));
                }
                enderbuttFile.save();
                enderbuttEdit(p);
            }
            if (event.getSlot() == 15) { // ENCHANTED
                if (enderbuttFile.getBoolean("ENDERBUTT.ENCHANTED")) {
                    enderbuttFile.set("ENDERBUTT.ENCHANTED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Enchantment Correctly"));
                } else {
                    enderbuttFile.set("ENDERBUTT.ENCHANTED", true);
                    p.sendMessage(CC.translate("&aActivated the Enchantment Correctly"));
                }
                enderbuttFile.save();
                enderbuttEdit(p);
            }
            if (event.getSlot() == 26) {
                MenuEditor.InventoryHotbar(p);
            }
            event.setCancelled(true);
        }
    }

    /***************************
     SHOW/HIDE EDIT
     ***************************/

    public static void showhideEdit(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&bShow/Hide Editor"));

        inv.setItem(11, (new ArmorCreator(Material.SIGN)).setDisplayName("&aSlot Hotbar").setLore(lore1).build()); // SLOT
        inv.setItem(12, (new ArmorCreator(Material.valueOf(hideplayerFile.getString("SHOW-PLAYER.MATERIAL")))).setDisplayName(hideplayerFile.getString("SHOW-PLAYER.DISPLAYNAME")).setDurability(hideplayerFile.getInt("SHOW-PLAYER.DATA")).setLore(lore1).build());
        if (hideplayerFile.getBoolean("SHOW-PLAYER.SOUND_ENABLED") && hideplayerFile.getBoolean("HIDE-PLAYER.SOUND_ENABLED")) { // ON / OFF // SOUND
            inv.setItem(13, (new ArmorCreator(Material.NOTE_BLOCK)).setDisplayName("&aSound activated").setLore(loreON).build());
        } else {
            inv.setItem(13, (new ArmorCreator(Material.JUKEBOX)).setDisplayName("&cSound disabled").setLore(loreOFF).build());
        }
        if (hideplayerFile.getBoolean("SHOW-PLAYER.ENCHANTED") && hideplayerFile.getBoolean("HIDE-PLAYER.ENCHANTED")) { // ON / OFF  // ENCHANTED
            inv.setItem(15, (new ArmorCreator(Material.ENCHANTMENT_TABLE)).setDisplayName("&aEnchantment activated").setLore(loreON).build());
        } else {
            inv.setItem(15, (new ArmorCreator(Material.ENDER_PORTAL_FRAME)).setDisplayName("&cEnchantment disabled").setLore(loreOFF).build());
        }
        if (hideplayerFile.getBoolean("HIDE-SHOW.ENABLED")) { // ON / OFF
            inv.setItem(14, (new ArmorCreator(Material.WOOL)).setDisplayName("&aActivated").setDurability(5).setLore(loreON).build());
        } else {
            inv.setItem(14, (new ArmorCreator(Material.WOOL)).setDisplayName("&cDisabled").setDurability(14).setLore(loreOFF).build());
        }

        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName("&cBack to the Hotbar Menu").setLore(loreback).build()); // BACK

        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F); // SOUND
        p.openInventory(inv);
    }


    @EventHandler
    public void onShowHideEditor(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&bShow/Hide Editor"))) {

            if (event.getSlot() == 11) { // SLOT
                SlotEditor.SHEditor(p);
            }
            if (event.getSlot() == 12) { // ITEM
                HideShowEditor.SHEdit(p);
            }
            if (event.getSlot() == 13) { // SOUND
                if (hideplayerFile.getBoolean("SHOW-PLAYER.SOUND_ENABLED") && hideplayerFile.getBoolean("HIDE-PLAYER.SOUND_ENABLED")) {
                    hideplayerFile.set("SHOW-PLAYER.SOUND_ENABLED", false);
                    hideplayerFile.set("HIDE-SHOW.SOUND_ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Sound Correctly"));
                } else {
                    hideplayerFile.set("SHOW-PLAYER.SOUND_ENABLED", true);
                    hideplayerFile.set("HIDE-SHOW.SOUND_ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Sound Correctly"));
                }
                hideplayerFile.save();
                showhideEdit(p);
            }
            if (event.getSlot() == 14) { // ON / OFF
                if (hideplayerFile.getBoolean("HIDE-SHOW.ENABLED")) {
                    hideplayerFile.set("HIDE-SHOW.ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Item Correctly"));
                } else {
                    hideplayerFile.set("HIDE-SHOW.ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Item Correctly"));
                }
                hideplayerFile.save();
                showhideEdit(p);
            }
            if (event.getSlot() == 15) { // ENCHANTED
                if (hideplayerFile.getBoolean("SHOW-PLAYER.ENCHANTED") && hideplayerFile.getBoolean("HIDE-PLAYER.ENCHANTED")) {
                    hideplayerFile.set("SHOW-PLAYER.ENCHANTED", false);
                    hideplayerFile.set("HIDE-PLAYER.ENCHANTED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Enchantment Correctly"));
                } else {
                    hideplayerFile.set("SHOW-PLAYER.ENCHANTED", true);
                    hideplayerFile.set("HIDE-PLAYER.ENCHANTED", true);
                    p.sendMessage(CC.translate("&aActivated the Enchantment Correctly"));
                }
                hideplayerFile.save();
                showhideEdit(p);
            }
            if (event.getSlot() == 26) {
                MenuEditor.InventoryHotbar(p);
            }
            event.setCancelled(true);
        }
    }

    /***************************
     PVP MODE  EDIT
     ***************************/

    public static void pvpmodeEdit(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&cPvP-Mode Editor"));

        inv.setItem(11, (new ArmorCreator(Material.SIGN)).setDisplayName("&aSlot Hotbar").setLore(lore1).build()); // SLOT
        inv.setItem(12, (new ArmorCreator(Material.valueOf(pvpFile.getString("PVP.MATERIAL")))).setDisplayName(pvpFile.getString("PVP.DISPLAYNAME")).setDurability(pvpFile.getInt("PVP.DATA")).setLore(lore1).build());
        if (pvpFile.getBoolean("PVP.SOUND_ENABLED")) { // ON / OFF // SOUND
            inv.setItem(13, (new ArmorCreator(Material.NOTE_BLOCK)).setDisplayName("&aSound activated").setLore(loreON).build());
        } else {
            inv.setItem(13, (new ArmorCreator(Material.JUKEBOX)).setDisplayName("&cSound disabled").setLore(loreOFF).build());
        }
        if (pvpFile.getBoolean("PVP.ENCHANTED")) { // ON / OFF  // ENCHANTED
            inv.setItem(15, (new ArmorCreator(Material.ENCHANTMENT_TABLE)).setDisplayName("&aEnchantment activated").setLore(loreON).build());
        } else {
            inv.setItem(15, (new ArmorCreator(Material.ENDER_PORTAL_FRAME)).setDisplayName("&cEnchantment disabled").setLore(loreOFF).build());
        }
        if (pvpFile.getBoolean("PVP.ENABLED")) { // ON / OFF
            inv.setItem(14, (new ArmorCreator(Material.WOOL)).setDisplayName("&aActivated").setDurability(5).setLore(loreON).build());
        } else {
            inv.setItem(14, (new ArmorCreator(Material.WOOL)).setDisplayName("&cDisabled").setDurability(14).setLore(loreOFF).build());
        }
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName("&cBack to the Hotbar Menu").setLore(loreback).build()); // BACK

        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F); // SOUND
        p.openInventory(inv);
    }


    @EventHandler
    public void onPvPModeEditor(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&cPvP-Mode Editor"))) {

            if (event.getSlot() == 11) { // SLOT
                SlotEditor.PEditor(p);
            }
            if (event.getSlot() == 12) { // ITEM
                ItemStack stack = event.getCurrentItem();
                if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.valueOf(pvpFile.getString("PVP.MATERIAL"))) || event.getSlotType() == null) {
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                    p.sendMessage(CC.translate("&cMaterial Invalid"));
                    return;
                }
                pvpFile.set("PVP.MATERIAL", String.valueOf(event.getView().getItem(12).getType()));
                pvpFile.set("PVP.DATA", event.getView().getItem(12).getDurability());
                pvpFile.save();
                p.sendMessage(CC.translate("&aMaterial saved correctly"));
                p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
            }
            if (event.getSlot() == 13) { // SOUND
                if (pvpFile.getBoolean("PVP.SOUND_ENABLED")) {
                    pvpFile.set("PVP.SOUND_ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Sound Correctly"));
                } else {
                    pvpFile.set("PVP.SOUND_ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Sound Correctly"));
                }
                pvpFile.save();
                pvpmodeEdit(p);
            }
            if (event.getSlot() == 14) { // ON / OFF
                if (pvpFile.getBoolean("PVP.ENABLED")) {
                    pvpFile.set("PVP.ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Item Correctly"));
                } else {
                    pvpFile.set("PVP.ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Item Correctly"));
                }
                pvpFile.save();
                pvpmodeEdit(p);
            }
            if (event.getSlot() == 15) { // ENCHANTED
                if (pvpFile.getBoolean("PVP.ENCHANTED")) {
                    pvpFile.set("PVP.ENCHANTED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Enchantment Correctly"));
                } else {
                    pvpFile.set("PVP.ENCHANTED", true);
                    p.sendMessage(CC.translate("&aActivated the Enchantment Correctly"));
                }
                pvpFile.save();
                pvpmodeEdit(p);
            }
            if (event.getSlot() == 26) {
                MenuEditor.InventoryHotbar(p);
            }
            event.setCancelled(true);
        }
    }

    /***************************
     COSMETICS EDIT
     ***************************/

    public static void cosmeticsEdit(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, CC.translate("&4Cosmetics Editor"));

        inv.setItem(11, (new ArmorCreator(Material.SIGN)).setDisplayName("&aSlot Hotbar").setLore(lore1).build()); // SLOT
        inv.setItem(12, (new ArmorCreator(Material.valueOf(cosmeticsFile.getString("COSMETIC.MATERIAL")))).setDisplayName(cosmeticsFile.getString("COSMETIC.DISPLAYNAME")).setDurability(cosmeticsFile.getInt("COSMETIC.DATA")).setLore(lore1).build());
        if (cosmeticsFile.getBoolean("COSMETIC.SOUND_ENABLED")) { // ON / OFF // SOUND
            inv.setItem(13, (new ArmorCreator(Material.NOTE_BLOCK)).setDisplayName("&aSound activated").setLore(loreON).build());
        } else {
            inv.setItem(13, (new ArmorCreator(Material.JUKEBOX)).setDisplayName("&cSound disabled").setLore(loreOFF).build());
        }
        if (cosmeticsFile.getBoolean("COSMETIC.ENCHANTED")) { // ON / OFF  // ENCHANTED
            inv.setItem(15, (new ArmorCreator(Material.ENCHANTMENT_TABLE)).setDisplayName("&aEnchantment activated").setLore(loreON).build());
        } else {
            inv.setItem(15, (new ArmorCreator(Material.ENDER_PORTAL_FRAME)).setDisplayName("&cEnchantment disabled").setLore(loreOFF).build());
        }
        if (cosmeticsFile.getBoolean("COSMETIC.ENABLED")) { // ON / OFF
            inv.setItem(14, (new ArmorCreator(Material.WOOL)).setDisplayName("&aActivated").setDurability(5).setLore(loreON).build());
        } else {
            inv.setItem(14, (new ArmorCreator(Material.WOOL)).setDisplayName("&cDisabled").setDurability(14).setLore(loreOFF).build());
        }
        inv.setItem(26, (new ArmorCreator(Material.FEATHER)).setDisplayName("&cBack to the Hotbar Menu").setLore(loreback).build()); // BACK

        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null)
                inv.setItem(i, panel);
        }
        p.getPlayer().playSound(p.getPlayer().getLocation(), Sound.ANVIL_USE, 2.0F, 2.0F); // SOUND
        p.openInventory(inv);
    }


    @EventHandler
    public void onCosmeticsEditor(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate("&4Cosmetics Editor"))) {

            if (event.getSlot() == 11) { // SLOT
                SlotEditor.CEditor(p);
            }
            if (event.getSlot() == 12) { // ITEM
                ItemStack stack = event.getCurrentItem();
                if (stack == null || stack.getType().equals(Material.AIR) || stack.getType().equals(Material.valueOf(cosmeticsFile.getString("COSMETIC.MATERIAL"))) || event.getSlotType() == null) {
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
                    p.sendMessage(CC.translate("&cMaterial Invalid"));
                    return;
                }
                cosmeticsFile.set("COSMETIC.MATERIAL", String.valueOf(event.getView().getItem(12).getType()));
                cosmeticsFile.set("COSMETIC.DATA", event.getView().getItem(12).getDurability());
                cosmeticsFile.save();
                p.sendMessage(CC.translate("&aMaterial saved correctly"));
                p.playSound(p.getLocation(), Sound.VILLAGER_YES, 1.0F, 1.0F);
            }
            if (event.getSlot() == 13) { // SOUND
                if (cosmeticsFile.getBoolean("COSMETIC.SOUND_ENABLED")) {
                    cosmeticsFile.set("COSMETIC.SOUND_ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Sound Correctly"));
                } else {
                    cosmeticsFile.set("COSMETIC.SOUND_ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Sound Correctly"));
                }
                cosmeticsFile.save();
                cosmeticsEdit(p);
            }
            if (event.getSlot() == 14) { // ON / OFF
                if (cosmeticsFile.getBoolean("COSMETIC.ENABLED")) {
                    cosmeticsFile.set("COSMETIC.ENABLED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Item Correctly"));
                } else {
                    cosmeticsFile.set("COSMETIC.ENABLED", true);
                    p.sendMessage(CC.translate("&aActivated the Item Correctly"));
                }
                cosmeticsFile.save();
                cosmeticsEdit(p);
            }
            if (event.getSlot() == 15) { // ENCHANTED
                if (cosmeticsFile.getBoolean("COSMETIC.ENCHANTED")) {
                    cosmeticsFile.set("COSMETIC.ENCHANTED", false);
                    p.sendMessage(CC.translate("&cDeactivated the Enchantment Correctly"));
                } else {
                    cosmeticsFile.set("COSMETIC.ENCHANTED", true);
                    p.sendMessage(CC.translate("&aActivated the Enchantment Correctly"));
                }
                cosmeticsFile.save();
                cosmeticsEdit(p);
            }
            if (event.getSlot() == 26) {
                MenuEditor.InventoryHotbar(p);
            }
            event.setCancelled(true);
        }
    }
}