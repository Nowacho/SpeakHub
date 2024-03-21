package hardling.us.hub.ranks;

import org.bukkit.OfflinePlayer;

public interface Permission {
  String getName(OfflinePlayer paramOfflinePlayer);
  
  String getPrefix(OfflinePlayer paramOfflinePlayer);
  
  String getSuffix(OfflinePlayer paramOfflinePlayer);
  
  String getColor(OfflinePlayer paramOfflinePlayer);

  String getDisplay(OfflinePlayer paramOfflinePlayer);
}
