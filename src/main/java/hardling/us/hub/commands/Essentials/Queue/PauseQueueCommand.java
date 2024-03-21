package hardling.us.hub.commands.Essentials.Queue;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import hardling.us.hub.util.queue.QueueCustom.SpeakQueue;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PauseQueueCommand extends BaseCommand {

    @MainCommand(name = "pausequeue", description = "Pause FileUtil server queue", inGameOnly = true)
    public boolean sendMessage(CommandArgs command) {
        Player p = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0) {
            p.sendMessage(ChatColor.RED + "Usage: /pausequeue <server>");
            return false;
        }
        SpeakQueue queue = SpeakHub.getInst().getSpeakQueueManager().getQueue(args[0]);
        if (queue == null) {
            p.sendMessage(CC.translate("&cCould not find the queue '" + args[0] + "."));
            return false;
        }
        p.sendMessage(CC.translate("&fYou have just toggled " + args[0] + "'s queue status to " + (queue.isPaused() ? "&ajoinable" : "&coff")));
        queue.setPaused(!queue.isPaused());
        return false;
    }
}
