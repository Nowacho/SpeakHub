package hardling.us.hub.ranks.type;

import cc.fyre.venom.VenomAPI;
import hardling.us.hub.ranks.Permission;
import org.bukkit.OfflinePlayer;

public class Venom implements Permission {
    @Override
    public String getName(OfflinePlayer player) {
        return VenomAPI.instance.getGrantHandler().findGlobalRank(player.getUniqueId()).getDisplayName();
    }

    @Override
    public String getPrefix(OfflinePlayer player) {
        return VenomAPI.instance.getGrantHandler().findGlobalRank(player.getUniqueId()).getPrefix();
    }

    @Override
    public String getSuffix(OfflinePlayer player) {
        return "";
    }

    @Override
    public String getColor(OfflinePlayer player) {
        return VenomAPI.instance.getGrantHandler().findGlobalRank(player.getUniqueId()).getColor();
    }

    @Override
    public String getDisplay(OfflinePlayer player) {
        return VenomAPI.instance.getGrantHandler().findGlobalRank(player.getUniqueId()).getDisplayName();
    }
}
