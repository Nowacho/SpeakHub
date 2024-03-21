package hardling.us.hub.commands.Essentials;

import hardling.us.hub.util.CC;
import hardling.us.hub.util.SettingsUtils;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class BuildModeCommand extends BaseCommand {

    @MainCommand(name = "build", description = "switch to construction mode", permission = "speakhub.command.build", inGameOnly = true, aliases = { "buildmode", "modebuild" })
    public boolean sendMessage(CommandArgs command) {
        Player p = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length == 0) {
            if (p.getGameMode() == GameMode.ADVENTURE) {
                p.setGameMode(GameMode.CREATIVE);
                p.sendMessage(CC.translate("&aYou have enabled your &6&lBuild Mode&7."));
            } else if (p.getGameMode() == GameMode.SURVIVAL) {
                p.setGameMode(GameMode.CREATIVE);
                p.sendMessage(CC.translate("&aYou have enabled your &6&lBuild Mode&7."));
            } else if (p.getGameMode() == GameMode.CREATIVE) {
                SettingsUtils.clearPlayer(p);
                SettingsUtils.HotBarItems(p);
                p.setGameMode(GameMode.ADVENTURE);
                p.sendMessage(CC.translate("&cYou have disabled your &6&lBuild Mode&c!"));
            }
        } else {
            p.sendMessage(CC.translate("&cUsage: /buildmode"));
        }
        return false;
    }
}

