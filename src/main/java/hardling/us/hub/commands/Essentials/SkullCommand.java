package hardling.us.hub.commands.Essentials;

import hardling.us.hub.util.CC;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SkullCommand extends BaseCommand {

    @MainCommand(name = "skull", description = "Get the head of FileUtil player", permission = "speakhub.command.skull", inGameOnly = true)
    public boolean sendMessage(CommandArgs command) {
        Player p = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length == 0) {
            p.sendMessage(CC.translate("&cUsage: /Skull <name>"));
            return false;
        }
        p.getInventory().addItem(new ItemStack[]{ hardling.us.hub.util.BukkitUtils.getSkull(args[0]) });
        return false;
    }
}