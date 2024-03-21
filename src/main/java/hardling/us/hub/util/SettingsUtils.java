package hardling.us.hub.util;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.listeners.hotbar.*;
import hardling.us.hub.listeners.hotbar.pvp.PvPModeLeave;
import hardling.us.hub.util.Files.Config;
import hardling.us.hub.util.Files.ConfigCreator;
import hardling.us.hub.util.Files.Lang;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.List;

public class SettingsUtils {

    private static final ConfigCreator configFile = SpeakHub.getInst().getConfigFile();
    private static final ConfigCreator pvpFile = SpeakHub.getInst().getPvpFile();

    public static void HotBarItems(Player p) {
        if (Config.HOTBAR_ENDERBUTT_ENABLED) {
            p.getInventory().setItem(Config.HOTBAR_ENDERBUTT_SLOT - 1, EnderButtListener.Item(p));
        }
        if (Config.HOTBAR_COSMETIC_ENABLED) {
            p.getInventory().setItem(Config.HOTBAR_COSMETCI_SLOT - 1, CosmeticListener.Item(p));
        }
        if (Config.HOTBAR_PVP_ENABLED) {
            p.getInventory().setItem(Config.HOTBAR_PVP_SLOT - 1, PvPListener.Item(p));
        }
        if (Config.HOTBAR_HUBSELECTOR_ENABLED) {
            p.getInventory().setItem(Config.HOTBAR_HUBSELECTOR_SLOT - 1, HubListener.Item(p));
        }
        if (Config.HOTBAR_SELECTOR_ENABLED) {
            p.getInventory().setItem(Config.HOTBAR_SELECTOR_SLOT - 1, SelectorListener.Item(p));
        }
        if (Config.HOTBAR_HIDE_SHOW_ENABLED) {
            p.getInventory().setItem(Config.HOTBAR_HIDE_SHOW_SLOT - 1, HideShowListener.HideItem(p));
        }
        if (Config.HOTBAR_MEDIA_ENABLED) {
            p.getInventory().setItem(Config.HOTBAR_MEDIA_SLOT - 1, MediaListener.Item(p));
        }
    }

    public static void clearPlayer(Player p) {
        p.getInventory().setArmorContents(null);
        p.getInventory().clear();
        p.setFoodLevel(20);
        p.setHealth(p.getMaxHealth());
        p.getActivePotionEffects().clear();
    }

    public static void leavePvP(Player p) {
        if (SpeakHub.getInst().getPvPListener().getEnablePvP().contains(p.getUniqueId())) {
            SpeakHub.getInst().getPvPListener().getEnablePvP().remove(p.getUniqueId());

            new PvPModeLeave(p);

            clearPlayer(p);
            HotBarItems(p);
            teleportToSpawn(p);

            p.setGameMode(GameMode.ADVENTURE);
        }
        p.sendMessage(CC.translate(Lang.PVP_MODE_NEED_HAVE_ACTIVATED));
    }

    public static void reloadConfig(Player p) {
        long time = System.currentTimeMillis();
        ConfigCreator tabFile = SpeakHub.getInst().getTabFile();
        ConfigCreator scoreboardFile = SpeakHub.getInst().getScoreboardFile();
        ConfigCreator licenseFile = SpeakHub.getInst().getLicenseFile();
        ConfigCreator enderbuttFile = SpeakHub.getInst().getEnderbuttFile();
        ConfigCreator cosmeticsFile = SpeakHub.getInst().getCosmeticsFile();
        ConfigCreator hubselectorFile = SpeakHub.getInst().getHubselectorFile();
        ConfigCreator hideplayerFile = SpeakHub.getInst().getHideplayerFile();
        ConfigCreator mediaFile = SpeakHub.getInst().getMediaFile();
        ConfigCreator selectorFile = SpeakHub.getInst().getSelectorFile();
        ConfigCreator headFile = SpeakHub.get().getHeadsFile();
        ConfigCreator langFile = SpeakHub.getInst().getLangFile();
        ConfigCreator oufitFile = SpeakHub.getInst().getOufitsFile();
        ConfigCreator particlesFile = SpeakHub.getInst().getParticlesFile();

        langFile.save();
        scoreboardFile.save();
        tabFile.save();
        headFile.save();
        selectorFile.save();
        hubselectorFile.save();
        hideplayerFile.save();
        pvpFile.save();
        enderbuttFile.save();
        cosmeticsFile.save();
        configFile.save();
        mediaFile.save();
        licenseFile.save();
        oufitFile.save();
        particlesFile.save();

        p.sendMessage(CC.translate(SpeakHub.getInst().getPrefix() + "&fhas been reloaded &asuccessfully. &7(" + (System.currentTimeMillis() - time) + "ms)"));
    }

    public static void JoinPvP(Player p) {
        // CONFIRM IF IT COMPLIES WITH THE CONFIGURATION
        if (configFile.contains("COORDINATES.SPAWN_PVP") && configFile.contains("COORDINATES.SPAWN") && pvpFile.contains("KIT.ARMOR") && pvpFile.contains("KIT.INVENTORY")) {
            SpeakHub.getInst().getPvPListener().onJoinPvP(p);

            clearPlayer(p);
            teleporToSpawnPvP(p);

            ItemStack[] armor = ((List<ItemStack>) pvpFile.get("KIT.ARMOR")).stream().toArray(ItemStack[]::new);
            ItemStack[] contents = ((List<ItemStack>) pvpFile.get("KIT.INVENTORY")).stream().toArray(ItemStack[]::new);

            p.getInventory().setArmorContents(armor);
            p.getInventory().setContents(contents);
            p.updateInventory();
            p.setGameMode(GameMode.SURVIVAL);


            // DISABLES THE VANISH ON THE PLAYER
            Iterator iterator;
            Player p1;
            iterator = Bukkit.getServer().getOnlinePlayers().iterator();
            while (iterator.hasNext()) {
                p1 = (Player) iterator.next();
                p.showPlayer(p1);
            }

            // SOUND JOIN
            if (!Config.PVP_SOUND_ENABLED) return;
            p.playSound(p.getLocation(), Sound.valueOf(Config.PVP_SOUND), 1.0F, 1.0F);
        } else {
            p.sendMessage(CC.translate(Lang.PVP_MODE_NOT_CONFIGURED));
        }
    }

    public static void setlocationToString(Player p, String s) {
        String conrdinates = "COORDINATES." + s + ".";

        configFile.set(conrdinates + "WORLD", p.getLocation().getWorld().getName());
        configFile.set(conrdinates + "X", p.getLocation().getX());
        configFile.set(conrdinates + "Y", p.getLocation().getY());
        configFile.set(conrdinates + "Z", p.getLocation().getZ());
        configFile.set(conrdinates + "YAW", p.getLocation().getYaw());
        configFile.set(conrdinates + "PITCH", p.getLocation().getPitch());
        configFile.save();
        p.sendMessage(CC.translate(Lang.SPAWN_COORDINATES_SAVE));
    }

    public static Location getlocationToString(String s) {
        String conrdinates = "COORDINATES." + s + ".";

        World worldName = Bukkit.getWorld(String.valueOf(configFile.getString(conrdinates + "WORLD")));

        double x = Double.valueOf(configFile.getDouble(conrdinates + "X"));
        double y = Double.valueOf(configFile.getDouble(conrdinates + "Y"));
        double z = Double.valueOf(configFile.getDouble(conrdinates + "Z"));

        float yaw = Float.valueOf(configFile.getString(conrdinates + "YAW"));
        float pitch = Float.valueOf(configFile.getString(conrdinates + "PITCH"));

        return new Location(worldName, x, y, z, yaw, pitch);
    }

    public static void teleportToSpawn(Player p) {
        if (configFile.contains("COORDINATES.SPAWN") && Config.SETTINGS_PLAYER_TELEPORT_ON_JOIN_SPAWN) {
            p.teleport(getlocationToString("SPAWN"));
            p.sendMessage(Lang.TELEPORT_SPAWN);
            return;
        }
        p.sendMessage(CC.translate(Lang.SPAWN_COORDINATES_NOT_EXIST));
    }

    public static void teleporToSpawnPvP(Player p) {
        if (configFile.contains("COORDINATES.SPAWN_PVP")) {
            p.teleport(getlocationToString("SPAWN_PVP"));
            p.sendMessage(Lang.PVP_MODE_JOIN);
            return;
        }
        p.sendMessage(CC.translate(Lang.SPAWN_PVP_COORDINATES_NOT_EXIST));
    }
}
