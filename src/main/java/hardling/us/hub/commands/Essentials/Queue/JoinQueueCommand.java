package hardling.us.hub.commands.Essentials.Queue;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import hardling.us.hub.util.queue.QueueCustom.SpeakQueue;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class JoinQueueCommand extends BaseCommand {

    @MainCommand(name = "joinqueue", description = "Join FileUtil server queue", inGameOnly = true, aliases = { "queue", "playqueue", "play" })
    public boolean sendMessage(CommandArgs command) {
        Player p = command.getPlayer();
        String[] args = command.getArgs();
        SpeakQueue queue = SpeakHub.getInst().getSpeakQueueManager().getQueue(args[0]);
        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Usage: /joinqueue <server>");
            return false;
        }
        if (queue == null) {
            p.sendMessage(CC.translate("&cCould not find the queue '" + args[0] + "."));
            return false;
        }
        queue.addToQueue(p);
        return false;
    }
}
