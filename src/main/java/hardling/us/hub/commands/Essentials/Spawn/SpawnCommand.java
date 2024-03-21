package hardling.us.hub.commands.Essentials.Spawn;

import hardling.us.hub.util.SettingsUtils;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;

public class SpawnCommand extends BaseCommand {
    @MainCommand(name = "spawn", permission = "Teleport to spawn", inGameOnly = true, aliases = { "sp", "spw" })
    public boolean sendMessage(CommandArgs command) {
        SettingsUtils.teleportToSpawn(command.getPlayer());
        return false;
    }
}
