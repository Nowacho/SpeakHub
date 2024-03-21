package hardling.us.hub.listeners.hotbar;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.listeners.hotbar.pvp.PvPModeJoin;
import hardling.us.hub.listeners.hotbar.pvp.PvPModeLeave;
import hardling.us.hub.util.ArmorCreator;
import hardling.us.hub.util.Files.Config;
import hardling.us.hub.util.SettingsUtils;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

@Getter
public class PvPListener implements Listener {

    private final Set<UUID> enablePvP;
    private final Map<UUID, Long> enderpearl;

    public PvPListener() {
        enablePvP = new HashSet<>();
        enderpearl = new HashMap<>();
        Bukkit.getServer().getPluginManager().registerEvents(this, SpeakHub.getInst());
    }

    public void onDisable() {
        this.enablePvP.clear();
    }

    public void onJoinPvP(Player player) {
        SpeakHub.getInst().getPvPListener().enablePvP.add(player.getUniqueId());
        new PvPModeJoin(player);
    }

    public static ItemStack Item(Player p) {
        return (new ArmorCreator(
                Material.valueOf(Config.PVP_MATERIAL)))
                .setDurability(Config.PVP_DATA)
                .setDisplayName(Config.PVP_DISPLAYNAME)
                .setLore(Config.PVP_LORE)
                .setGlow(Config.PVP_ENCHANTED).build();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEnderPearl(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.useInteractedBlock() == Event.Result.DENY && event.useItemInHand() == Event.Result.DENY) return;
        if (!event.hasItem()) return;
        if (player.getGameMode() == GameMode.CREATIVE || event.getItem().getType() != Material.ENDER_PEARL) return;
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (!SpeakHub.getInst().getPvPListener().getEnablePvP().contains(player.getUniqueId())) return;
        if (player.getItemInHand().getType() != Material.ENDER_PEARL) return;

        if (SpeakHub.getInst().getPvPListener().getEnderpearl().containsKey(player.getUniqueId())) {
            if (SpeakHub.getInst().getPvPListener().getEnderpearl().get(player.getUniqueId()) - System.currentTimeMillis() <= 0L) {
                SpeakHub.getInst().getPvPListener().getEnderpearl().put(player.getUniqueId(), System.currentTimeMillis() + 16000L);
                return;
            }
            event.setUseItemInHand(Event.Result.DENY);
            player.updateInventory();
            return;
        }
        SpeakHub.getInst().getPvPListener().getEnderpearl().put(player.getUniqueId(), System.currentTimeMillis() + 16000L);
    }

    @EventHandler
    public static void onPvPMode(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if ((event.getAction() == Action.RIGHT_CLICK_AIR ||
                event.getAction() == Action.RIGHT_CLICK_BLOCK) &&
                p.getInventory().getItemInHand().getType() == Material.getMaterial(Config.PVP_MATERIAL)) {
            SettingsUtils.JoinPvP(p);
        }
    }

    @EventHandler
    private void Respawn(final PlayerDeathEvent event) {
        Player p = event.getEntity();

        (new BukkitRunnable() {
            public void run() {
                try {
                    Object nmsPlayer = event.getEntity().getClass().getMethod("getHandle", new Class[0]).invoke(event.getEntity());
                    Object con = nmsPlayer.getClass().getDeclaredField("playerConnection").get(nmsPlayer);
                    Class<?> EntityPlayer = Class.forName(nmsPlayer.getClass().getPackage().getName() + ".EntityPlayer");
                    Field minecraftServer = con.getClass().getDeclaredField("minecraftServer");
                    minecraftServer.setAccessible(true);
                    Object mcserver = minecraftServer.get(con);
                    Object playerlist = mcserver.getClass().getDeclaredMethod("getPlayerList", new Class[0]).invoke(mcserver);
                    Method moveToWorld = playerlist.getClass().getMethod("moveToWorld", EntityPlayer, int.class, boolean.class);
                    moveToWorld.invoke(playerlist, nmsPlayer, 0, Boolean.FALSE);

                    if (SpeakHub.getInst().getPvPListener().getEnablePvP().contains(p.getUniqueId())) {

                        event.getDrops().clear();

                        SettingsUtils.clearPlayer(p);
                        SettingsUtils.HotBarItems(p);
                        SettingsUtils.teleportToSpawn(p);

                        SpeakHub.getInst().getPvPListener().getEnablePvP().remove(p.getUniqueId());

                        new PvPModeLeave(p);

                        p.setGameMode(GameMode.ADVENTURE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).runTaskLater(SpeakHub.getInst(), 5L);
    }
}

