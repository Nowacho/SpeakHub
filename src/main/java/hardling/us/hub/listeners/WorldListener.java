package hardling.us.hub.listeners;


import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Config;
import hardling.us.hub.util.Files.Lang;
import hardling.us.hub.util.SettingsUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WorldListener implements Listener {


    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Location to = event.getTo();
        Location from = event.getFrom();

        if (to.getBlockX() == from.getBlockX() && to.getBlockY() == from.getBlockY() && to.getBlockZ() == from.getBlockZ())
            return;

        if (Border(event.getTo())) {
            event.setTo(event.getFrom());
            event.getPlayer().sendMessage(CC.translate(Lang.BORDER_CANNOT_MOVE));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTeleport(PlayerTeleportEvent event) {
        Location to = event.getTo();
        Location from = event.getFrom();

        if (to.getBlockX() == from.getBlockX() && to.getBlockY() == from.getBlockY() && to.getBlockZ() == from.getBlockZ())
            return;

        if (Border(event.getTo())) {
            event.setTo(event.getFrom());
            event.getPlayer().sendMessage(CC.translate(Lang.BORDER_CANNOT_MOVE));
        }
    }

    public static boolean Border(Location location) {
        if (Config.SETTINGS_BORDER_SIZE <= 0)
            return false;
        return Math.abs(location.getBlockX()) > Config.SETTINGS_BORDER_SIZE
                || Math.abs(location.getBlockZ()) > Config.SETTINGS_BORDER_SIZE;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        event.setCancelled(Config.SETTINGS_MISC_ENTITY_SPAWN);
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        // Creative
        if (event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(false);
            return;
        }
        // PvP Mode
        if (SpeakHub.getInst().getPvPListener().getEnablePvP().contains(p.getUniqueId())) {
            event.setCancelled(false);
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockForm(BlockFormEvent event) {
        event.setCancelled(Config.SETIINGS_BLOCK_FORM);
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        event.setCancelled(Config.SETTINGS_BLOCK_BURN);
    }

    @EventHandler
    public void onBlockFade(BlockFadeEvent event) {
        event.setCancelled(Config.SETTINGS_BLOCK_FADE);
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }
        event.setCancelled(Config.SETTINGS_BLOCK_BREAK);
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }
        event.setCancelled(Config.SETTINGS_BLOCK_PLACE);
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        event.setCancelled(Config.SETTINGS_MISC_MOBS_SPAWN);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        event.setCancelled(Config.SETTINGS_ITEMS_DROP);
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) {
            event.setCancelled(Config.SETTINGS_PLAYER_HUNGER);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() == null) return;
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            event.setCancelled(false);
        } else {
            Material material = event.getClickedBlock().getType();
            if (material == Material.CHEST ||
                    material == Material.ANVIL ||
                    material == Material.DROPPER ||
                    material == Material.NOTE_BLOCK ||
                    material == Material.DISPENSER ||
                    material == Material.FENCE_GATE ||
                    material == Material.BEACON ||
                    material == Material.FURNACE ||
                    material == Material.LEVER ||
                    material == Material.WOOD_BUTTON ||
                    material == Material.STONE_BUTTON ||
                    material == Material.TRAP_DOOR ||
                    material == Material.TRAPPED_CHEST ||
                    material == Material.ENDER_CHEST ||
                    material == Material.WOODEN_DOOR) {
                event.setCancelled(Config.SETTINGS_PLAYER_INTERACT);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeafDecay(LeavesDecayEvent event) {
        event.setCancelled(Config.SETTINGS_MISC_LEAVES_DECAY);
    }

    @EventHandler
    public void onFireSpread(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.SPREAD) {
            event.setCancelled(Config.SETTINGS_BLOCK_FIRE_SPREAD);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        EntityDamageEvent.DamageCause cause = event.getCause();
        if (Config.SETTINGS_PLAYER_FALL_DAMAGE && cause == EntityDamageEvent.DamageCause.FALL) {
            event.setCancelled(true);
        } else if (Config.SETTINGS_PLAYER_DROWNING && cause == EntityDamageEvent.DamageCause.DROWNING) {
            event.setCancelled(true);
        } else if (Config.SETTINGS_PLAYER_FIRE_DAMAGE && (cause == EntityDamageEvent.DamageCause.FIRE || cause == EntityDamageEvent.DamageCause.FIRE_TICK || cause == EntityDamageEvent.DamageCause.LAVA)) {
            event.setCancelled(true);
        } else if (Config.SETTINGS_MISC_VOID_DEATH && cause == EntityDamageEvent.DamageCause.VOID) {
            if (!(event.getEntity() instanceof Player)) {
                return;
            }
            Player player = (Player) event.getEntity();
            player.setFallDistance(0.0F);
            SpeakHub.getInst();
            Bukkit.getScheduler().scheduleSyncDelayedTask(SpeakHub.getInst(), () -> SettingsUtils.teleportToSpawn(player), 3L);
            event.setCancelled(true);
        }
    }

    // PvP Mode
    @EventHandler
    private void onDamage(EntityDamageEvent event) {
        if (SpeakHub.getInst().getPvPListener().getEnablePvP().contains(event.getEntity().getUniqueId())) return;
        event.setCancelled(true);
    }

    // PvP Mode
    @EventHandler
    public void EntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && !SpeakHub.getInst().getPvPListener().getEnablePvP().contains(event.getEntity().getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        if (event.getDamager() instanceof Player && !SpeakHub.getInst().getPvPListener().getEnablePvP().contains(event.getDamager().getUniqueId())) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    private void onPlayerFish(PlayerFishEvent event) {
        event.setCancelled(Config.SETTINGS_PLAYER_FISH);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!Config.SETTINGS_MISC_DEATH_MESSAGE)
            return;
        event.setDeathMessage(null);
    }

    @EventHandler
    private void onEntityExplode(EntityExplodeEvent event) {
        event.setCancelled(Config.SETTINGS_MISC_ENTITY_EXPLODE);
    }

    @Deprecated
    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        event.setCancelled(Config.SETTINGS_ITEMS_PICKUP);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent event) {
        event.setCancelled(Config.SETTINGS_MISC_WEATHER);
    }
}