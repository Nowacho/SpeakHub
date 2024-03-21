package hardling.us.hub.listeners.hotbar;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.listeners.hotbar.cosmetics.HeadsListener;
import hardling.us.hub.listeners.hotbar.cosmetics.OutfitsListener;
import hardling.us.hub.listeners.hotbar.cosmetics.Particles;
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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CosmeticListener implements Listener {

    private static ConfigCreator cosmeticsFile = SpeakHub.getInst().getCosmeticsFile();

    public static ItemStack Item(Player p) {
        return (new ArmorCreator(
                Material.valueOf(cosmeticsFile.getString("COSMETIC.MATERIAL"))))
                .setDurability(cosmeticsFile.getInt("COSMETIC.DATA"))
                .setDisplayName(cosmeticsFile.getString("COSMETIC.DISPLAYNAME"))
                .setLore(cosmeticsFile.getStringList("COSMETIC.LORE"))
                .setGlow(cosmeticsFile.getBoolean("COSMETIC.ENCHANTED")).build();
    }

    public void CosmeticInventory(Player p) {
        Inventory inv = Bukkit.createInventory(null, cosmeticsFile.getInt("MENU.INVENTORY"), CC.translate(cosmeticsFile.getString("MENU.TITLE")));
        inv.setItem(cosmeticsFile.getInt("MENU.ITEMS.HEADS.SLOT"), (new ArmorCreator(Material.valueOf(cosmeticsFile.getString("MENU.ITEMS.HEADS.MATERIAL")))).setDisplayName(cosmeticsFile.getString("MENU.ITEMS.HEADS.TITLE")).setLore(cosmeticsFile.getStringList("MENU.ITEMS.HEADS.LORE")).build());
        inv.setItem(cosmeticsFile.getInt("MENU.ITEMS.ARMORS.SLOT"), (new ArmorCreator(Material.valueOf(cosmeticsFile.getString("MENU.ITEMS.ARMORS.MATERIAL")))).setDisplayName(cosmeticsFile.getString("MENU.ITEMS.ARMORS.TITLE")).setLore(cosmeticsFile.getStringList("MENU.ITEMS.ARMORS.LORE")).build());
        inv.setItem(cosmeticsFile.getInt("MENU.ITEMS.PARTICLES.SLOT"), (new ArmorCreator(Material.valueOf(cosmeticsFile.getString("MENU.ITEMS.PARTICLES.MATERIAL")))).setDisplayName(cosmeticsFile.getString("MENU.ITEMS.PARTICLES.TITLE")).setLore(cosmeticsFile.getStringList("MENU.ITEMS.PARTICLES.LORE")).build());

        if (cosmeticsFile.getBoolean("MENU.GLASS_PANEL.ENABLED")) {
            ItemStack panel = (new ArmorCreator(Material.STAINED_GLASS_PANE, 1, (short) cosmeticsFile.getInt("MENU.GLASS_PANEL.ID"))).setName("").build();
            for (int i = 0; i < inv.getSize(); i++) {
                if (inv.getItem(i) == null)
                    inv.setItem(i, panel);
            }
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = event.getPlayer();
            if (player.getItemInHand().getType() == Material.getMaterial(cosmeticsFile.getString("COSMETIC.MATERIAL"))) {
                CosmeticInventory(player);
                if (cosmeticsFile.getBoolean("COSMETIC.SOUND_ENABLED")) {
                    player.playSound(player.getLocation(), Sound.valueOf(cosmeticsFile.getString("COSMETIC.SOUND")), 2.0F, 2.0F);
                }
            }
        }
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if(event.getSlotType() == InventoryType.SlotType.OUTSIDE) return;

        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate(cosmeticsFile.getString("MENU.TITLE")))) {
            if (event.getSlot() == cosmeticsFile.getInt("MENU.ITEMS.ARMORS.SLOT")) {
                if (player.hasPermission(cosmeticsFile.getString("MENU.ITEMS.ARMORS.PERMISSION"))) {
                    OutfitsListener.OutfitInv(player);
                } else {
                    player.sendMessage(CC.translate(Lang.NO_PERMS));
                }
            } else if (event.getSlot() == cosmeticsFile.getInt("MENU.ITEMS.PARTICLES.SLOT")) {
                if (player.hasPermission(cosmeticsFile.getString("MENU.ITEMS.PARTICLES.PERMISSION"))) {
                    Particles.ParticleInv(player);
                } else {
                    player.sendMessage(CC.translate(Lang.NO_PERMS));
                }
            } else if (event.getSlot() == cosmeticsFile.getInt("MENU.ITEMS.HEADS.SLOT")) {
                if (player.hasPermission(cosmeticsFile.getString("MENU.ITEMS.HEADS.PERMISSION"))) {
                    HeadsListener.Inventory(player);
                } else {
                    player.sendMessage(CC.translate(Lang.NO_PERMS));
                }
            }
            event.setCancelled(true);
        }
    }
}
