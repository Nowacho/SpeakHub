package hardling.us.hub.ranks.type;

import com.minexd.zoot.profile.Profile;
import hardling.us.hub.ranks.Permission;
import org.bukkit.OfflinePlayer;

public class Zoot implements Permission {

    @Override
    public String getName(OfflinePlayer p) {
        return Profile.getByUuid(p.getUniqueId()).getActiveGrant().getRank().getDisplayName();
    }

    @Override
    public String getPrefix(OfflinePlayer p) {
        return Profile.getByUuid(p.getUniqueId()).getActiveGrant().getRank().getPrefix();
    }

    @Override
    public String getSuffix(OfflinePlayer p) {
        return Profile.getByUuid(p.getUniqueId()).getActiveGrant().getRank().getSuffix();
    }

    @Override
    public String getColor(OfflinePlayer p) {
        char c = Profile.getByUuid(p.getUniqueId()).getActiveGrant().getRank().getColor().getChar();
        return "&" + c;
    }

    @Override
    public String getDisplay(OfflinePlayer p) {
        return this.getColor(p) + this.getName(p);
    }

}
