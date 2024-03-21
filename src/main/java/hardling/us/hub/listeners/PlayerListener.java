package hardling.us.hub.listeners;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.listeners.hotbar.cosmetics.particles.ParticleParameter;
import hardling.us.hub.listeners.hotbar.pvp.PvPModeLeave;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Config;
import hardling.us.hub.util.Files.Lang;
import hardling.us.hub.util.SettingsUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        event.setJoinMessage(null);

        SettingsUtils.clearPlayer(p);
        SettingsUtils.HotBarItems(p);
        SettingsUtils.teleportToSpawn(p);

        p.setGameMode(GameMode.ADVENTURE);

        if (Lang.JOIN_ENABLED) {
            Lang.JOIN_MESSAGE.forEach(s -> {
                s = CC.Placeholders(p, s);
                p.sendMessage(CC.translate(s));
            });
        }

        if (p.hasPermission("speakhub.join.message") && Lang.PLAYER_DONATOR_JOIN_ENABLED)
            for (String msg : Lang.PLAYER_DONATOR_JOIN_MESSAGE) {
                msg = CC.Placeholders(p, msg);
                Bukkit.broadcastMessage(CC.translate(msg));
            }

        if (p.getUniqueId().equals("3cbd0963-bfb4-4257-afd4-6b0ea5842899")) {
            p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
            p.sendMessage(CC.translate("            " + SpeakHub.getInst().getPrefix() + "&8- [&aHardling Development&8]"));
            p.sendMessage(CC.translate(""));
            p.sendMessage(CC.translate(" &8|▶ &dAuthor&8: &f" + SpeakHub.getInst().getDescription().getAuthors()));
            p.sendMessage(CC.translate(" &8|▶ &dVersion&8: &f" + SpeakHub.getInst().getDescription().getVersion()));
            p.sendMessage(CC.translate(" &8|▶ &dLicense&8: &f" + Config.LICENSE));
            p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
        }

    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onDisconect(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        event.setQuitMessage(null);

        if (ParticleParameter.set.containsKey(p)) {
            Bukkit.getScheduler().cancelTask(ParticleParameter.set.get(p));
            ParticleParameter.set.clear();
        }
        if (SpeakHub.getInst().getQueues().inQueue(event.getPlayer())) {
            SpeakHub.getInst().getSpeakQueueManager().getQueue(event.getPlayer()).removeFromQueue(event.getPlayer());
        }
        SpeakHub.getInst().getPvPListener().getEnablePvP().remove(p.getUniqueId());
        new PvPModeLeave(p);
        if (p.hasPermission("speakhub.quit.message") && Lang.PLAYER_DONATOR_QUIT_ENABLED) {
            for (String msg : Lang.PLAYER_DONATOR_QUIT_MESSAGE) {
                msg = CC.Placeholders(p, msg);
                Bukkit.broadcastMessage(CC.translate(msg));
            }
        }
    }
}
