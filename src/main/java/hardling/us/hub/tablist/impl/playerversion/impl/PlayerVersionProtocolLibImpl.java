package hardling.us.hub.tablist.impl.playerversion.impl;

import com.comphenix.protocol.ProtocolLibrary;
import hardling.us.hub.tablist.impl.playerversion.IPlayerVersion;
import hardling.us.hub.tablist.impl.playerversion.PlayerVersion;
import org.bukkit.entity.Player;

public class PlayerVersionProtocolLibImpl implements IPlayerVersion {

    @Override
    public PlayerVersion getPlayerVersion(Player player) {
        return PlayerVersion.getVersionFromRaw(
                ProtocolLibrary.getProtocolManager().getProtocolVersion(player)
        );
    }
}
