package hardling.us.hub.commands.Essentials.Teleport;

import hardling.us.hub.util.CC;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportPossCommand extends BaseCommand {
    public boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @MainCommand(name = "tppos", description = "Teleport you to FileUtil place", permission = "speakhub.command.tppos", inGameOnly = true, aliases = { "teleportcoords" })
    public boolean sendMessage(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length != 3) {
            player.sendMessage(CC.translate("&cUsage: /tppos [x] [y] [z]"));
        } else if (!isDouble(args[0]) || !isDouble(args[1]) || !isDouble(args[2])) {
            player.sendMessage(CC.translate("&cUsage: /tppos [x] [y] [z]"));
        } else {
            double x = Double.parseDouble(args[0]) + 0.5D;
            double y = Double.parseDouble(args[1]) + 0.5D;
            double z = Double.parseDouble(args[2]) + 0.5D;
            Location loc = new Location(player.getWorld(), x, y, z, player.getLocation().getYaw(), player.getLocation().getPitch());
            player.teleport(loc);
            player.sendMessage(CC.translate("&eYou have been teleported to the coordinates &c" + x + "&e, &c" + y + "&e, &c" + z + "&e."));
        }
        return false;
    }
}
