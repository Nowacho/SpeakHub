package hardling.us.hub.commands.Essentials.Teleport;

import hardling.us.hub.util.BukkitUtils;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Lang;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeleportAllCommand extends BaseCommand {
    @MainCommand(name = "teleportall", description = "Teleports all players to you", permission = "speakhub.command.teleportall", inGameOnly = true, aliases = { "tpall" })
    public boolean sendMessage(CommandArgs command) {
        Player player = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length != 0) {
            player.sendMessage(ChatColor.RED + "Usage: /teleportall");
        } else {
            for (Player target : BukkitUtils.getOnlinePlayers()) {
                target.teleport(player);
                target.sendMessage(CC.translate(Lang.TELEPORT_TELEPORTALL_FROM
                        .replace("%player%", player.getName())));
            }
            player.sendMessage(CC.translate(Lang.TELEPORT_TELEPORT_TO));
        }
        return false;
    }
}
