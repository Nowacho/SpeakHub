package hardling.us.hub.tablist.impl.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.OfflinePlayer;

@Getter
@Setter
@AllArgsConstructor
public class TabEntry {

    private final String name;
    private final TabColumn column;
    private final OfflinePlayer player;
    private final int rawSlot;

    private String text;
    private int slot, ping;
    private SkinTexture texture;

}