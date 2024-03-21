package hardling.us.hub.commands.Essentials;

import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Lang;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FlyCommand extends BaseCommand {

    @MainCommand(name = "fly", description = "Fly", permission = "speakhub.command.fly", inGameOnly = true)
    public boolean sendMessage(CommandArgs command) {
        Player p = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length == 0) {
            if (!p.getAllowFlight()) {
                p.setAllowFlight(true);
                p.sendMessage(CC.translate("&eYou have &aenabled &eyour &6&lFlight Mode"));
            }
            p.setAllowFlight(false);
            p.sendMessage(CC.translate("&eYou have &cdisabled &eyour &6&lFlight Mode"));
        } else if (!p.hasPermission("speakhub.command.fly.others")) {
            p.sendMessage(CC.translate(Lang.NO_PERMS));
        } else {
            Player t = Bukkit.getPlayer(args[0]);
            if (t == null) {
                p.sendMessage(CC.translate(Lang.OFFLINE_PLAYER
                        .replace("%player%", args[1])));
            } else if (t.getAllowFlight()) {
                t.setAllowFlight(false);
                p.sendMessage(CC.translate("&eYou have &cdisabled &e" + t.getName() + "'s &6&lFlight Mode"));
            } else {
                t.setAllowFlight(true);
                p.sendMessage(CC.translate("&eYou have &aenabled &e" + t.getName() + "'s &6&lFlight Mode"));
            }
        }
        return false;
    }
}
