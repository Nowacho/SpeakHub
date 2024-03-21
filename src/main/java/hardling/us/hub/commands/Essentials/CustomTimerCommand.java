package hardling.us.hub.commands.Essentials;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.scoreboard.customtimer.CustomTimer;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Util;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CustomTimerCommand extends BaseCommand {
    @MainCommand(name = "customtimer", description = "Add FileUtil custom timer to the scoreboard", permission = "speakhub.command.customtimer", inGameOnly = true, aliases = { "ct", "ctt" })
    public boolean sendMessage(CommandArgs command) {
        List<String> names;
        CustomTimer timer;
        long duration;
        String[] args = command.getArgs();
        Player p = command.getPlayer();
        if (args.length < 1) {
            sendUsage(p);
            return true;
        }
        switch (args[0]) {
            case "list":
                names = new ArrayList<>();
                SpeakHub.getInst().getCustomTimerManager().getCustomTimers().forEach(CustomTimer -> names.add(CustomTimer.getName()));
                if (names.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < names.size(); i++)
                        sb.append(names.get(i)).append(", ");
                    p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
                    p.sendMessage(CC.translate("                   &b&lCustomTimer &8- &7[&aList&7]"));
                    p.sendMessage(CC.translate(""));
                    p.sendMessage(CC.translate("&c&lCustomTimers actives:"));
                    p.sendMessage(CC.translate("&f" + sb.toString()));
                    p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
                    return true;
                }
                sendUsage(p);
                return false;
            case "stop":
                if (args.length < 2) {
                    sendUsage(p);
                    return true;
                }
                timer = SpeakHub.getInst().getCustomTimerManager().getCustomTimer(args[1]);
                if (timer == null) {
                    sendUsage(p);
                    p.sendMessage(CC.translate("&cTimer does not exist&7."));
                    return true;
                }
                p.sendMessage(CC.translate("&aTimer successfully stopped&7."));
                timer.cancel();
                return false;
            case "start":
                if (args.length < 4) {
                    sendUsage(p);
                    return true;
                }
                duration = Util.parse(args[2]);
                if (duration == -1L) {
                    p.sendMessage(CC.translate("&c" + args[1] + " is an invalid duration&7."));
                    return true;
                }
                if (duration < 1000L) {
                    p.sendMessage(CC.translate("&cThe time must last for atleast 1 second&7."));
                    return true;
                }
                if (SpeakHub.getInst().getCustomTimerManager().getCustomTimer(args[1]) != null) {
                    p.sendMessage(CC.translate("&cThath timer is already exist&7."));
                    return true;
                }
                SpeakHub.getInst().getCustomTimerManager().createTimer(new CustomTimer(Util.color(args[1]), Util.color(args[3].replace("-", " ")), System.currentTimeMillis(), System.currentTimeMillis() + duration));
                p.sendMessage(CC.translate("&aYou successfully created FileUtil timer &e" + args[1]));
                return false;
        }
        sendUsage(p);
        return false;
    }

    private void sendUsage(Player p) {
        p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
        p.sendMessage(CC.translate("                   &d&lScoreboard &8- &7[&bCustomTimer&7]"));
        p.sendMessage(CC.translate(""));
        p.sendMessage(CC.translate("&7» &b/CustomTimer &fstart <name> <time> <displayname>"));
        p.sendMessage(CC.translate("&7» &b/CustomTimer &fstop <name>"));
        p.sendMessage(CC.translate("&7» &b/CustomTimer &flist"));
        p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
    }
}
