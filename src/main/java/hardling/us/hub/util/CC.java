package hardling.us.hub.util;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.Files.Config;
import lombok.Getter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CC {

    @Getter
    private static String LINELICENSE = "&d=====&5============&8[&bLicense&8-&bSystem&8]&d============&5=====";
    @Getter
    private static String LINEERROR = "&c=====&4============&8[&bERROR&8]&4============&c=====";
    @Getter
    private static String LINENORMAL = "&d=====&5========================================&d=====";
    @Getter
    private static String LINELIGH = "&5&m-----&d&m&l---------------&5&m-----";

    public static List<String> translateFromArray(List<String> text) {
        List<String> messages = new ArrayList<>();
        for (String string : text)
            messages.add(translateFromString(string));
        return messages;
    }

    public static String translateFromString(String text) {
        return StringEscapeUtils.unescapeJava(ChatColor.translateAlternateColorCodes('&', text));
    }

    public static String translate(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static void log(String in) {
        Bukkit.getConsoleSender().sendMessage(translate(in));
    }

    public static List<String> translate(List<String> lines) {
        List<String> toReturn = new ArrayList<>();
        for (String line : lines)
            toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        return toReturn;
    }

    public static List<String> translate(String[] lines) {
        List<String> toReturn = new ArrayList<>();
        for (String line : lines) {
            if (line != null)
                toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        return toReturn;
    }

    public static String Placeholders(Player player, String s) {
        s = PlaceholderAPI.setPlaceholders(player, s);
        return s
                // GENERAL
                .replace("%slots%", String.valueOf(Bukkit.getMaxPlayers()))
                .replace("%placeholder:date%", Util.getDate())
                .replace("%placeholder:time%", Util.getHour())
                .replace("%players_online%", Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getServer().getMaxPlayers())
                .replace("%queue-server%", "" + SpeakHub.getInst().getQueues().getQueueIn(player))
                .replace("%queue-position%", "" + SpeakHub.getInst().getQueues().getPosition(player))
                .replace("%queue-size%", "" + SpeakHub.getInst().getQueues().getSize(SpeakHub.getInst().getQueues().getQueueIn(player)))

                // SOCIAL
                .replace("%store%", Config.SOCIAL_STORE)
                .replace("%twitter%", Config.SOCIAL_TWITTER)
                .replace("%teamspeak%", Config.SOCIAL_TEAMSPEAK)
                .replace("%discord%", Config.SOCIAL_DISCORD)
                .replace("%website%", Config.SOCIAL_WEBSITE)

                // PLAYER
                .replace("%name%", player.getName())
                .replace("%player%", player.getName())
                .replace("%rank%", SpeakHub.getInst().getPermission().getChat().getPlayerPrefix(player))
                .replace("%rank-suffix%", SpeakHub.getInst().getPermission().getChat().getPlayerSuffix(player))
                .replace("%rank_color%", SpeakHub.getInst().getPermission().getPermission().getColor(player))
                .replace("%rank_display%", SpeakHub.getInst().getPermission().getPermission().getDisplay(player))
                .replace("%kills%", String.valueOf(player.getStatistic(Statistic.PLAYER_KILLS)))
                .replace("%deaths%", String.valueOf(player.getStatistic(Statistic.DEATHS)));
    }
}
