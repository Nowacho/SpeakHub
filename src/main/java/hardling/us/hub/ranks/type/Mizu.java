package hardling.us.hub.ranks.type;

import com.broustudio.MizuAPI.MizuAPI;
import com.hexa.core.plugin.HexaCoreAPI;
import hardling.us.hub.ranks.Permission;
import org.bukkit.OfflinePlayer;

public class Mizu implements Permission {

    @Override
    public String getName(OfflinePlayer player) {
        return MizuAPI.getAPI().getRank(player.getUniqueId());
    }

    public String getPrefix(OfflinePlayer player) {
        return MizuAPI.getAPI().getRankPrefix(MizuAPI.getAPI().getRank(player.getUniqueId()));
    }

    @Override
    public String getSuffix(OfflinePlayer player) {
        return MizuAPI.getAPI().getRankSuffix(MizuAPI.getAPI().getRank(player.getUniqueId()));
    }

    @Override
    public String getColor(OfflinePlayer player) {
        return MizuAPI.getAPI().getRankColor(MizuAPI.getAPI().getRank(player.getUniqueId()));
    }

    @Override
    public String getDisplay(OfflinePlayer player) {
        return HexaCoreAPI.INSTANCE.getPlayerRank(player.getUniqueId()).getDisplayName();
    }
}
