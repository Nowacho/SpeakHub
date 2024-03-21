package hardling.us.hub.tablist.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import hardling.us.hub.tablist.impl.IETablist;
import hardling.us.hub.tablist.impl.TabListCommons;
import hardling.us.hub.tablist.impl.utils.SkinTexture;
import hardling.us.hub.tablist.impl.utils.TabColumn;
import hardling.us.hub.tablist.impl.utils.TabEntry;
import hardling.us.hub.tablist.interfaces.IEHelper;
import hardling.us.hub.util.CC;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class ProtocolLibTabImpl implements IEHelper {

    @Override
    public TabEntry createEntry(IETablist tablist, String name, TabColumn column, int slot, int rawSlot, boolean legacy) {
        UUID uniqueId = UUID.randomUUID();

        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        WrappedGameProfile profile = new WrappedGameProfile(uniqueId, !legacy ? name : IETablist.ENTRY.get(rawSlot - 1) + "");
        PlayerInfoData playerInfoData = new PlayerInfoData(profile, 1, EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(!legacy ? "" : profile.getName()));

        if (!legacy) {
            playerInfoData.getProfile().getProperties().put("textures", new WrappedSignedProperty("textures", TabListCommons.defaultTexture.SKIN_VALUE, TabListCommons.defaultTexture.SKIN_SIGNATURE));
        }

        packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));
        sendPacket(tablist.getPlayer(), packet);

        return new TabEntry(name, column, new OfflinePlayer() {
            public boolean isOnline() {
                return true;
            }

            public boolean willBeOnline() {
                return false;
            }

            public String getName() {
                return name;
            }

            public UUID getUniqueId() {
                return uniqueId;
            }

            public boolean isBanned() {
                return false;
            }

            public void setBanned(boolean banned) {
            }

            public boolean isWhitelisted() {
                return false;
            }

            public void setWhitelisted(boolean whitelisted) {
            }

            public Player getPlayer() {
                return null;
            }

            public long getFirstPlayed() {
                return 0;
            }

            public long getLastPlayed() {
                return 0;
            }

            public long getLastLogin() {
                return 0;
            }

            public long getLastLogout() {
                return 0;
            }

            public boolean hasPlayedBefore() {
                return false;
            }

            public Location getBedSpawnLocation() {
                return null;
            }

            public Map<String, Object> serialize() {
                return null;
            }

            public boolean isOp() {
                return false;
            }

            public void setOp(boolean op) {
            }
        }, rawSlot, "", slot, -1, TabListCommons.defaultTexture);
    }


    @Override
    public void updateText(IETablist tablist, TabEntry entry, String[] newStrings) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_DISPLAY_NAME);

        WrappedGameProfile profile = new WrappedGameProfile(
                entry.getPlayer().getUniqueId(),
                entry.getName()
        );
        PlayerInfoData playerInfoData = new PlayerInfoData(
                profile,
                entry.getPing(),
                EnumWrappers.NativeGameMode.SURVIVAL,
                WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', newStrings.length > 1 ? newStrings[0] + newStrings[1] : newStrings[0]))
        );

        packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));
        sendPacket(tablist.getPlayer(), packet);
    }


    @Override
    public void updateTexture(IETablist tablist, TabEntry entry, SkinTexture skin) {
        WrappedGameProfile profile = new WrappedGameProfile(entry.getPlayer().getUniqueId(), entry.getName());
        PlayerInfoData playerInfoData = new PlayerInfoData(profile, entry.getPing(), EnumWrappers.NativeGameMode.SURVIVAL, WrappedChatComponent.fromText(CC.translate(entry.getText())));

        playerInfoData.getProfile().getProperties().clear();
        playerInfoData.getProfile().getProperties().put("textures", new WrappedSignedProperty("textures", skin.SKIN_VALUE, skin.SKIN_SIGNATURE));


        PacketContainer remove = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        remove.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        remove.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));


        PacketContainer add = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        add.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        add.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

        sendPacket(tablist.getPlayer(), remove);
        sendPacket(tablist.getPlayer(), add);
    }


    @Override
    public void updatePing(IETablist tablist, TabEntry entry, int ping) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.PLAYER_INFO);
        packet.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.UPDATE_LATENCY);

        WrappedGameProfile profile = new WrappedGameProfile(
                entry.getPlayer().getUniqueId(),
                entry.getName());

        PlayerInfoData playerInfoData = new PlayerInfoData(
                profile,
                ping,
                EnumWrappers.NativeGameMode.SURVIVAL,
                WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', entry.getText()))
        );

        packet.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));
        sendPacket(tablist.getPlayer(), packet);
    }

    @Override
    public void updateHeaderAndFooter(Player player, String header, String footer) {
        PacketContainer headerAndFooter = new PacketContainer(PacketType.Play.Server.PLAYER_LIST_HEADER_FOOTER);

        headerAndFooter.getChatComponents().write(0, WrappedChatComponent.fromText(header));
        headerAndFooter.getChatComponents().write(1, WrappedChatComponent.fromText(footer));

        sendPacket(player, headerAndFooter);
    }

    @Override
    public SkinTexture getTexture(Player player) {
        WrappedGameProfile profile = WrappedGameProfile.fromPlayer(player);
        Collection<WrappedSignedProperty> property = profile.getProperties().get("textures");
        if (property != null && property.size() > 0) {
            WrappedSignedProperty actual = property.iterator().next();
            return new SkinTexture(actual.getValue(), actual.getSignature());
        }
        return TabListCommons.STEVE_TEXTURE;
    }

    private static void sendPacket(Player player, PacketContainer packetContainer){
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packetContainer);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
