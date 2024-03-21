package hardling.us.hub.commands.Essentials.Queue;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LeaveQueueCommand extends BaseCommand {

    @MainCommand(name = "leavequeue", description = "Leave FileUtil server queue", inGameOnly = true)
    public boolean sendMessage(CommandArgs command) {
        Player p = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Usage: /leavequeue");
            return false;
        }
        if (SpeakHub.getInst().getSpeakQueueManager().getQueue(p) == null) {
            return false;
        }
        SpeakHub.getInst().getSpeakQueueManager().getQueue(p).removeFromQueue(p);
        return false;
    }
}
