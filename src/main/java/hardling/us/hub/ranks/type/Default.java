package hardling.us.hub.ranks.type;

import hardling.us.hub.ranks.Permission;
import org.bukkit.OfflinePlayer;

public class Default implements Permission {

    @Override
    public String getName(OfflinePlayer player) {
        return "None";
    }

    @Override
    public String getPrefix(OfflinePlayer player) {
        return "None";
    }

    @Override
    public String getSuffix(OfflinePlayer player) {
        return "None";
    }

    @Override
    public String getColor(OfflinePlayer player) {
        return "None";
    }

    @Override
    public String getDisplay(OfflinePlayer player) {
        return "None";
    }
}
