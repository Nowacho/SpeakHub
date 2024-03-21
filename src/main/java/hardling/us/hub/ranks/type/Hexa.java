package hardling.us.hub.ranks.type;

import com.hexa.core.plugin.HexaCoreAPI;
import hardling.us.hub.ranks.Permission;
import org.bukkit.OfflinePlayer;

public class Hexa implements Permission {

    @Override
    public String getName(OfflinePlayer player) {
        return HexaCoreAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getName();
    }

    @Override
    public String getPrefix(OfflinePlayer player) {
        return HexaCoreAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getPrefix();
    }

    @Override
    public String getSuffix(OfflinePlayer player) {
        return HexaCoreAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getSuffix();
    }

    @Override
    public String getColor(OfflinePlayer player) {
        return HexaCoreAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getColor().toString();
    }

    @Override
    public String getDisplay(OfflinePlayer player) {
        return HexaCoreAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getDisplayName();
    }
}
