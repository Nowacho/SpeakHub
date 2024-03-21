package hardling.us.hub.commands.Essentials;

import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Lang;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.entity.Player;

public class PingCommand extends BaseCommand {

    @MainCommand(name = "ping", description = "Check your ping to the server", inGameOnly = true)
    public boolean sendMessage(CommandArgs command) {
        Player p = command.getPlayer();
        String string = Lang.COMMAND_PING;

        string = CC.Placeholders(p, string);
        p.sendMessage(CC.translate(string));
        return true;
    }
}