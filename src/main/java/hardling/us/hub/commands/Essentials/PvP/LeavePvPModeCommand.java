package hardling.us.hub.commands.Essentials.PvP;

import hardling.us.hub.util.SettingsUtils;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.entity.Player;

public class LeavePvPModeCommand extends BaseCommand {
    @MainCommand(name = "leavepvpmode", description = "Exit pvp mode", inGameOnly = true, aliases = { "leavepvp", "pvpleave" })
    public boolean sendMessage(CommandArgs command) {
        Player p = command.getPlayer();
        SettingsUtils.leavePvP(p);
        return false;
    }
}
