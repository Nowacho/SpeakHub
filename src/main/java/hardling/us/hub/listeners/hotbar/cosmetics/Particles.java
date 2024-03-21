package hardling.us.hub.listeners.hotbar.cosmetics;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.listeners.hotbar.cosmetics.particles.ParticleEffect;
import hardling.us.hub.listeners.hotbar.cosmetics.particles.ParticleParameter;
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

public class Particles implements Listener {

    private static ConfigCreator particlesFile = SpeakHub.getInst().getParticlesFile();

    public static void ParticleInv(Player p) {
        Inventory inv = Bukkit.createInventory(null, particlesFile.getInt("MENU.INVENTORY"), CC.translate(particlesFile.getString("MENU.TITLE")));
        for (String string : particlesFile.getConfigurationSection("MENU.PARTICLES").getKeys(false)) {
            inv.setItem(particlesFile.getInt("MENU.PARTICLES." + string + ".SLOT"),
                    (new ArmorCreator(Material.valueOf(particlesFile.getString("MENU.PARTICLES." + string + ".MATERIAL"))))
                            .setDisplayName(particlesFile.getString("MENU.PARTICLES." + string + ".DISPLAYNAME"))
                            .setLore(particlesFile.getStringList("MENU.PARTICLES." + string + ".LORE"))
                            .setDurability(particlesFile.getInt("MENU.PARTICLES." + string + ".DATA"))
                            .setGlow(particlesFile.getBoolean("MENU.PARTICLES." + string + ".ENCHANTED"))
                            .build());
        }
        inv.setItem(particlesFile.getInt("MENU.CLEAR-PARTICLES.SLOT"),
                (new ArmorCreator(Material.valueOf(particlesFile.getString("MENU.CLEAR-PARTICLES.MATERIAL"))))
                .setDisplayName(particlesFile.getString("MENU.CLEAR-PARTICLES.DISPLAYNAME"))
                .setLore(particlesFile.getStringList("MENU.CLEAR-PARTICLES.LORE")).build());
        if (particlesFile.getBoolean("MENU.GLASS_PANEL.ENABLED")) {
            ItemStack panel = (new ArmorCreator(Material.STAINED_GLASS_PANE, 1, (short) particlesFile.getInt("MENU.GLASS_PANEL.ID"))).setName("").build();
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
        if (event.getClickedInventory().getTitle().equalsIgnoreCase(CC.translate(particlesFile.getString("MENU.TITLE")))) {
            for (String string : particlesFile.getConfigurationSection("MENU.PARTICLES").getKeys(false)) {
                if (event.getSlot() == particlesFile.getInt("MENU.PARTICLES." + string + ".SLOT")) {
                    if (p.hasPermission(particlesFile.getString("MENU.PARTICLES." + string + ".PERMISSION"))) {
                        if (ParticleParameter.set.containsKey(p)) {
                            Bukkit.getScheduler().cancelTask(ParticleParameter.set.get(p));
                            ParticleParameter.set.replace(p, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SpeakHub.getInst(), () -> ParticleEffect.valueOf(particlesFile.getString("MENU.PARTICLES." + string + ".PARTICLE")).display(10.0F, 15.0F, 0.0F, 0.0F, 0, p.getLocation().add(-0.0D, 2.0D, 0.0D), 10.0D), 0L, 0L));
                            p.sendMessage(CC.translate(particlesFile.getString("MENU.PARTICLES." + string + ".MESSAGE")));
                            p.closeInventory();
                        } else {
                            ParticleParameter.set.put(p, Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(SpeakHub.getInst(), () -> ParticleEffect.valueOf(particlesFile.getString("MENU.PARTICLES." + string + ".PARTICLE")).display(10.0f, 15.0f, 0.0f, 0.0f, 0, p.getLocation().add(-0.0, 2.0, 0.0), 10.0), 0L, 0L));
                            p.sendMessage(CC.translate(particlesFile.getString("MENU.PARTICLES." + string + ".MESSAGE")));
                            p.closeInventory();
                        }
                    } else {
                        p.sendMessage(CC.translate(Lang.NO_PERMS));
                        event.setCancelled(true);
                    }
                }
            }
            if (event.getSlot() == particlesFile.getInt("MENU.CLEAR-PARTICLES.SLOT")) {
                if (ParticleParameter.set.containsKey(p)) {
                    Bukkit.getScheduler().cancelTask(ParticleParameter.set.get(p));
                    ParticleParameter.set.clear();
                    p.sendMessage(CC.translate(particlesFile.getString("MENU.CLEAR-PARTICLES.MESSAGE")));
                    event.setCancelled(true);
                    p.closeInventory();
                }
            }
        }
    }
}