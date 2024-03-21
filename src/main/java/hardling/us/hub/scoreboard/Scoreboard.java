package hardling.us.hub.scoreboard;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.scoreboard.assemble.AssembleAdapter;
import hardling.us.hub.scoreboard.customtimer.CustomTimer;
import hardling.us.hub.util.Animation;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Config;
import hardling.us.hub.util.Util;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Scoreboard implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        if (Config.SCOREBOARD_TITLE_ANIMATED_ENABLED) {
            return CC.translate(Animation.getScoreboardTitle());
        } else {
            return CC.translate(Config.SCOREBOARD_TITLE_STATIC_NORMAL);
        }
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> toReturn = new ArrayList<>();
        Collection<CustomTimer> customTimers = SpeakHub.getInst().getCustomTimerManager().getCustomTimers();
        String bars = Config.SCOREBOARD_BARS;
        String arrow = Config.SCOREBOARD_ARROW;
        if (SpeakHub.getInst().getQueues().inQueue(player)) {
            // Queue Mode
            Config.SCOREBOARD_IN_QUEUE.stream()
                    .map(CC::translate)
                    .map(line -> CC.Placeholders(player, line))
                    .map(line -> line.replace("%arrow%", arrow))
                    .forEach(toReturn::add);
        } else if (SpeakHub.getInst().getPvPListener().getEnablePvP().contains(player.getUniqueId())) {
            // PvP Mode
            for (String s : Config.SCOREBOARD_PVP_MODE) {
                boolean skip = false;
                s = CC.Placeholders(player, s);
                s = s.replace("%arrow%", arrow);
                // EnderPearl Cooldown
                if (s.contains("%enderpearl%")) {
                    if (SpeakHub.getInst().getPvPListener().getEnderpearl().containsKey(player.getUniqueId()) && SpeakHub.getInst().getPvPListener().getEnderpearl().get(player.getUniqueId()) - System.currentTimeMillis() >= 0L) {
                        long millisLeft = SpeakHub.getInst().getPvPListener().getEnderpearl().get(player.getUniqueId()) - System.currentTimeMillis();
                        s = s.replace("%enderpearl%", DurationFormatUtils.formatDurationWords(millisLeft, true, true));
                    } else {
                        skip = true;
                        s = s.replace("%enderpearl%", "&7&o0");
                    }
                }
                toReturn.add(s);
            }
        } else {
            // Scoreboard Normal
            Config.SCOREBOARD_LINES.stream()
                    .map(CC::translate)
                    .map(line -> CC.Placeholders(player, line))
                    .map(line -> line.replace("%arrow%", arrow))
                    .forEach(toReturn::add);
        }
        for (CustomTimer timer : customTimers) {
            toReturn.add("&0" + timer.getScoreboard() + "&7: &f&o" + Util.getRemaining(timer.getRemaining(), false));
        }
        toReturn.add(0, "&0" + bars);
        toReturn.add("&7" + bars);
        if (Config.SCOREBOARD_FOOTER_ANIMATED_ENABLED) {
            toReturn.add(Animation.getScoreboardFooter());
        } else {
            toReturn.add(Config.SCOREBOARD_FOOTER_STATIC_NORMAL);
        }
        return toReturn;
    }

}
