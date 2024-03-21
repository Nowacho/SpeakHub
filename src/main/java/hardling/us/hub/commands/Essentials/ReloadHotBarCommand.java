package hardling.us.hub.commands.Essentials;

import hardling.us.hub.util.CC;
import hardling.us.hub.util.SettingsUtils;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ReloadHotBarCommand extends BaseCommand {

    @MainCommand(name = "reloadhotbar", description = "Reload the hotbar", permission = "speakhub.command.reloadhotbar", inGameOnly = true, aliases = { "rh", "reloadh", "hr", "hotbarr" })
    public boolean sendMessage(CommandArgs command) {
        Player p = command.getPlayer();
        for (Player p1 : Bukkit.getOnlinePlayers()) {
            SettingsUtils.clearPlayer(p1);
            SettingsUtils.HotBarItems(p1);
            int i = Bukkit.getOnlinePlayers().size();
            p.sendMessage(CC.translate("&aThe " + i + " users hotbar was recharged"));
        }
        return false;
    }
}