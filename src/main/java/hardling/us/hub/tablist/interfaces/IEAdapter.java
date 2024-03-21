package hardling.us.hub.tablist.interfaces;

import hardling.us.hub.tablist.impl.utils.TabLayout;
import org.bukkit.entity.Player;

import java.util.Set;

public interface IEAdapter {

    Set<TabLayout> getSlots(Player player);

    String getFooter(Player player);

    String getHeader(Player player);

}
