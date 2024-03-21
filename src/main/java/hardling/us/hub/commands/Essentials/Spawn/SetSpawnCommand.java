package hardling.us.hub.commands.Essentials.Spawn;

import hardling.us.hub.util.SettingsUtils;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;

public class SetSpawnCommand extends BaseCommand {

    @MainCommand(name = "setspawn", description = "Place FileUtil spawning area", permission = "speakhub.command.setspawn", inGameOnly = true, aliases = { "sspawn", "stspawn", "ssp" })
    public boolean sendMessage(CommandArgs command) {
        SettingsUtils.setlocationToString(command.getPlayer(), "SPAWN");
        return false;
    }
}
