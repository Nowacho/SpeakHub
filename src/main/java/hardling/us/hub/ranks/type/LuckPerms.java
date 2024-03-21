package hardling.us.hub.ranks.type;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.ranks.Permission;
import hardling.us.hub.util.CC;
import org.bukkit.OfflinePlayer;

public class LuckPerms implements Permission {

    @Override
    public String getName(OfflinePlayer player) {
        return CC.translate(SpeakHub.getInst().getPermission().getChat().getPrimaryGroup(String.valueOf((SpeakHub.getInst().getPermission().getPlugin().getServer().getWorlds().get(0)).getName()), player));
    }

    @Override
    public String getPrefix(OfflinePlayer player) {
        return CC.translate(SpeakHub.getInst().getPermission().getChat().getPlayerPrefix(String.valueOf((SpeakHub.getInst().getPermission().getPlugin().getServer().getWorlds().get(0)).getName()), player));
    }

    @Override
    public String getSuffix(OfflinePlayer player) {
        return CC.translate(SpeakHub.getInst().getPermission().getChat().getPlayerSuffix(String.valueOf((SpeakHub.getInst().getPermission().getPlugin().getServer().getWorlds().get(0)).getName()), player));
    }

    @Override
    public String getColor(OfflinePlayer player) {
        return CC.translate(SpeakHub.getInst().getPermission().getChat().getPrimaryGroup(String.valueOf((SpeakHub.getInst().getPermission().getPlugin().getServer().getWorlds().get(0)).getName()), player));
    }

    @Override
    public String getDisplay(OfflinePlayer player) {
        return this.getName(player) + this.getColor(player);
    }
}
