package hardling.us.hub.commands.Essentials;

import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Config;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.Bukkit;

public class BroadcastCommand extends BaseCommand {

    @MainCommand(name = "broadcast", description = "speaks for the broadcast", permission = "speakhub.command.broadcast", aliases = { "bc", "brc", "bct" })
    public boolean sendMessage(CommandArgs command) {
        String[] args = command.getArgs();
        if (args.length != 0) {
           String msg = Config.CHAT_BROADCAST;
                msg = msg.replace("%message%", args[0]);
                Bukkit.broadcastMessage(CC.translate(msg));
        } else {
            command.getSender().sendMessage(CC.translate("&cCorrect Usage: /broadcast <message>"));
        }
        return false;
    }
}