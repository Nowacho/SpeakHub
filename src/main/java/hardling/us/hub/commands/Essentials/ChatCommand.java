package hardling.us.hub.commands.Essentials;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Lang;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatCommand extends BaseCommand {
    @MainCommand(name = "chat", description = "Control chat settings ", permission = "speakhub.command.chat", inGameOnly = true, aliases = { "cc", "cs", "chatsettings", "chatcontrol" })
    public boolean sendMessage(CommandArgs command) {
        String[] args = command.getArgs();
        Player p = command.getPlayer();
        if (args.length == 0) {
            chat_usage(p);
        }

        if (args.length == 1) {
            switch (args[0]) {
                case "clear":
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.hasPermission("speakhub.clearchat.bypass")) continue;

                        for (int i = 0; i < 100; i++) {
                            player.sendMessage("\n");
                        }

                        Lang.COMMAND_CHAT_CLEAR.forEach(s -> {
                            s = CC.Placeholders(p, s);
                            player.sendMessage(CC.translate(s));
                        });
                    }
                    return false;
                case "cooldown":
                    if (args.length == 2) {
                        p.sendMessage(CC.translate(Lang.COMMAND_USE_CHAT_COOLDOWN));
                        return false;
                    }
                    SpeakHub.getInst().getChatListener().setDelay(p, args[2]);
                    return false;
                /*case "mute":
                    SpeakHub.getInst().getChatListener().toggleChat(p);
                    return false;*/
                default:
                    chat_usage(p);
            }
        }
        return false;
    }

    private void chat_usage(Player p) {
        p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
        p.sendMessage(CC.translate("               &b&lChat Settings &8- &7[&5Speak&dHub&7]"));
        p.sendMessage(CC.translate(""));
        p.sendMessage(CC.translate("&7» &b/Chat &fClear &7- &fClears the player's chat&7."));
        p.sendMessage(CC.translate("&7» &b/Chat &fCooldown <delay> &7- &fSet a cooldown to the chat&7."));
     //   p.sendMessage(CC.translate("&7» &b/Chat &fMute &7- &fchange to mute the chat and unmute it&7."));
        p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
    }
}
