package hardling.us.hub.tablist.interfaces;

import hardling.us.hub.tablist.impl.IETablist;
import hardling.us.hub.tablist.impl.utils.SkinTexture;
import hardling.us.hub.tablist.impl.utils.TabColumn;
import hardling.us.hub.tablist.impl.utils.TabEntry;
import org.bukkit.entity.Player;

public interface IEHelper {

    TabEntry createEntry(IETablist tablist, String name, TabColumn column, int slot, int rawSlot, boolean legacy);

    void updateText(IETablist tablist, TabEntry entry, String[] newStrings);

    void updateTexture(IETablist tablist, TabEntry entry, SkinTexture skinTexture);

    void updatePing(IETablist tablist, TabEntry entry, int ping);

    void updateHeaderAndFooter(Player player, String header, String footer);

    SkinTexture getTexture(Player player);

}
