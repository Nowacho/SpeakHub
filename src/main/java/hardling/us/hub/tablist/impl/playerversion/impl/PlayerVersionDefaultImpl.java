package hardling.us.hub.tablist.impl.playerversion.impl;

import hardling.us.hub.tablist.impl.playerversion.IPlayerVersion;
import hardling.us.hub.tablist.impl.playerversion.PlayerVersion;
import org.bukkit.entity.Player;

public class PlayerVersionDefaultImpl implements IPlayerVersion {
    @Override
    public PlayerVersion getPlayerVersion(Player player) {
        return PlayerVersion.getVersionFromRaw(0);
    }
}
