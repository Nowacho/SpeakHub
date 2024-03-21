package hardling.us.hub.listeners;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Config;
import hardling.us.hub.util.Files.Lang;
import hardling.us.hub.util.Util;
import hardling.us.hub.util.cooldown.Cooldown;
import hardling.us.hub.util.cooldown.TimerManager;
import lombok.Getter;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

@Getter
@Setter
public class ChatListener implements Listener {

    public boolean muted;
    private int delay;

    public ChatListener() {
        this.delay = Config.CHAT_DELAY;
    }


   /*public void toggleChat(Player p) {
        if (this.isMuted()) {
            this.muted = false;

            p.sendMessage(Lang.CHAT_UNMUTED
                    .replace("%player%", p.getName())
                    .replace("%prefix%", SpeakHub.getInst().getPermission().getPermission().getPrefix(p)));
            return;
        }

        this.muted = true;

        p.sendMessage(Lang.CHAT_MUTED
                .replace("%player%", p.getName())
                .replace("%prefix%", SpeakHub.getInst().getPermission().getPermission().getPrefix(p)));
    }*/

    public void setDelay(Player p, String time) {
        int duration = Util.parseSeconds(time);

        if (duration == -1) {
            p.sendMessage(Lang.CHAT_INVLID_DURATION);
            return;
        }

        this.delay = duration;

        p.sendMessage(Lang.CHAT_DELAY_ANNUNCEMENT
                .replace("%player%", p.getName())
                .replace("%prefix%", SpeakHub.getInst().getPermission().getPermission().getPrefix(p))
                .replace("%delay%", DurationFormatUtils.formatDurationWords(this.delay * 1000L, true, true)));
    }

    @EventHandler
    private void onChatFormat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        if (Config.CHAT_FORMAT_ENABLED) {
            String s = Config.CHAT_FORMAT_CHAT
                    .replace("%name%", p.getName())
                    .replace("%rank%", SpeakHub.getInst().getPermission().getChat().getPlayerPrefix(p))
                    .replace("%rank-suffix%", SpeakHub.getInst().getPermission().getChat().getPlayerSuffix(p))
                    .replace("%rank_color%", SpeakHub.getInst().getPermission().getPermission().getColor(p))
                    .replace("%rank_display%", SpeakHub.getInst().getPermission().getPermission().getDisplay(p))
                    .replace("%message%", p.hasPermission("speakhub.chat.color") ? CC.translate(event.getMessage()) : event.getMessage());

            event.setFormat(CC.translate(PlaceholderAPI.setPlaceholders(p, s)));
        }

        if (!Config.CHAT_MESSAGE_ENABLED) return;

        String Messages = event.getMessage().split(" ")[0];

        List<String> MessagesDisabled = Config.CHAT_MESSAGE_DISABLED;

        if (MessagesDisabled.contains(Messages.toLowerCase())) {
            p.sendMessage(Lang.DISABLED_MESSAGES);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChatCommand(PlayerCommandPreprocessEvent event) {
        Player p = event.getPlayer();

        if (!Config.CHAT_COMMANDS_ENABLED) return;

        String command = event.getMessage().split(" ")[0];
        List<String> CommandsDisabled = Config.CHAT_COMMANDS_DISABLED;

        if (command.startsWith("/")) {
            command = command.substring(1);
        }
        if (CommandsDisabled.contains(command.toLowerCase())) {
            p.sendMessage(CC.translate(Lang.DISABLED_COMMANDS));
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (this.isMuted() && !player.hasPermission("speakhub.chat.mute.bypass")) {

            event.setCancelled(true);

            player.sendMessage(Lang.CHAT_MUTED);

        } else if (this.getDelay() > 0 && !player.hasPermission("speakhub.chat.delay.bypass")) {

            Cooldown timer = TimerManager.getInst().getCooldownTimer();

            if (timer.isActive(player, "CHAT")) {
                event.setCancelled(true);
                player.sendMessage(Lang.CHAT_DELAY
                        .replace("%seconds%", timer.getTimeLeft(player, "CHAT")));
                return;
            }

            timer.activate(player, "CHAT", this.delay, null);
        }
    }
}