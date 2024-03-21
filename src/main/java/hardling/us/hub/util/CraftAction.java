package hardling.us.hub.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CraftAction {
    public static void executeactions(Player player, String action) {
        action = action.replace("%player%", player.getName());
        action = CC.translate(action);

        if (action.startsWith("[console]")) {
            action = action.replace("[console] ", "");
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), action);
        }
        if (action.startsWith("[player]")) {
            action = action.replace("[player] ", "");
            player.performCommand(action);
        }
        if (action.startsWith("[message]")) {
            action = action.replace("[message] ", "");
            player.sendMessage(action);
        }
        if (action.startsWith("[broadcast]")) {
            action = action.replace("[broadcast] ", "");
            Bukkit.getServer().broadcastMessage(action);
        }
        }
    }

