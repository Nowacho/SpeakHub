package hardling.us.hub.tablist.impl;

import hardling.us.hub.tablist.impl.playerversion.PlayerVersion;
import hardling.us.hub.tablist.impl.playerversion.PlayerVersionHandler;
import hardling.us.hub.tablist.impl.utils.SkinTexture;
import hardling.us.hub.tablist.impl.utils.TabColumn;
import hardling.us.hub.tablist.impl.utils.TabEntry;
import hardling.us.hub.tablist.impl.utils.TabLayout;
import hardling.us.hub.tablist.interfaces.IEAdapter;
import hardling.us.hub.util.CC;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class IETablist {

    public static final List<String> NAME = new ArrayList<>();
    public static final List<String> ENTRY = new ArrayList<>();

    static {
        for (int i = 0; i < 80; i++) {
            NAME.add((i < 10 ? "\\u00010" : "\\u0001") + i);
        }

        for (int i = 1; i <= 15; i++) {
            String entry = ChatColor.values()[i].toString();
            ENTRY.add(ChatColor.RED + entry);
            ENTRY.add(ChatColor.GREEN + entry);
            ENTRY.add(ChatColor.DARK_RED + entry);
            ENTRY.add(ChatColor.DARK_GREEN + entry);
            ENTRY.add(ChatColor.BLUE + entry);
            ENTRY.add(ChatColor.DARK_BLUE + entry);
        }
    }

    private final Player player;
    private final Scoreboard scoreboard;

    @Getter private final boolean legacy;

    private String header;
    private String footer;

    private final IEAdapter adapter;

    private final Set<TabEntry> currentEntrySet = new HashSet<>();

    public IETablist(Player player, IEAdapter adapter) {
        Scoreboard scoreboard = (this.player = player).getScoreboard();
        ScoreboardManager manager = player.getServer().getScoreboardManager();

        if (scoreboard == manager.getMainScoreboard()) {
            player.setScoreboard(scoreboard = manager.getNewScoreboard());
        }

        this.scoreboard = scoreboard;

        legacy = PlayerVersionHandler.version.getPlayerVersion(this.player) == PlayerVersion.v1_7;

        this.adapter = adapter;

        fill();
        setupTeam();
    }


    public void setupTeam(){
        Team team1 = player.getScoreboard().getTeam("\\u000181");
        if (team1 == null) {
            team1 = player.getScoreboard().registerNewTeam("\\u000181");
        }
        team1.addEntry(player.getName());
        for (Player online : Bukkit.getOnlinePlayers()) {
            Team team = online.getScoreboard().getTeam("\\u000181");
            if (team == null) {
                team = online.getScoreboard().registerNewTeam("\\u000181");
            }
            team.addEntry(player.getName());
            team.addEntry(online.getName());
            team1.addEntry(online.getName());
            team1.addEntry(player.getName());
        }
    }

    public void fill() {
        for (int slot = 1; slot <= (legacy ? 60 : 80); slot++) {
            TabColumn column = TabColumn.getColumn(legacy, slot);

            if (column != null) {
                currentEntrySet.add(TablistManager.getInst.getIeHelper().createEntry(this, getTeamName(slot), column, column.getSlot(legacy, slot), slot, legacy));

                if (legacy) {
                    Team team = scoreboard.getTeam(NAME.get(slot - 1));

                    if (team != null) {
                        team.unregister();
                    }
                    team = scoreboard.registerNewTeam(NAME.get(slot - 1));
                    team.setPrefix("");
                    team.setSuffix("");

                    team.addEntry(ENTRY.get(slot - 1));
                }
            }
        }
    }

    private TabEntry getEntry(TabColumn column, int slot) {
        return currentEntrySet.stream().filter(entry -> entry.getColumn() == column && entry.getSlot() == slot).findFirst().orElse(null);
    }

    private String[] splitText(String text) {
        int length = text.length();

        if (length > 16) {
            String suffix;
            String prefix = text.substring(0, 16);

            if (prefix.charAt(15) == '§' || prefix.charAt(15) == '&') {
                prefix = prefix.substring(0, 15);
                suffix = text.substring(15, length);
            } else if (prefix.charAt(14) == '§' || prefix.charAt(14) == '&') {
                prefix = prefix.substring(0, 14);
                suffix = text.substring(14, length);
            } else {
                suffix = ChatColor.getLastColors(ChatColor.translateAlternateColorCodes('&', prefix)) + text.substring(16, length);
            }

            if (suffix.length() > 16) {
                suffix = suffix.substring(0, 16);
            }

            return new String[] { prefix, suffix };
        } else {
            return new String[] { text, "" };
        }
    }


    private void updateText(TabEntry entry, String text) {
        if (entry.getText().equals(text)) return;

        String[] newStrings = splitText(text);

        if (legacy) {
            Team team = scoreboard.getTeam(NAME.get(entry.getRawSlot()-1));
            if (team == null) {
                team = scoreboard.registerNewTeam(NAME.get(entry.getRawSlot()-1));
                team.addEntry(ENTRY.get(entry.getRawSlot() - 1));
            }
            team.setPrefix(ChatColor.translateAlternateColorCodes('&', newStrings[0]));
            if (newStrings.length > 1) {
                team.setSuffix(ChatColor.translateAlternateColorCodes('&', newStrings[1]));
            } else {
                team.setSuffix("");
            }

        } else {
            TablistManager.getInst.getIeHelper().updateText(this, entry, newStrings);
        }

        entry.setText(text);
    }

    private boolean updateTexture(TabEntry entry, SkinTexture texture) {
        if (texture == null || entry.getTexture().equals(texture)) return false;

        entry.setTexture(texture);

        TablistManager.getInst.getIeHelper().updateTexture(this, entry, texture);
        return true;
    }

    private void updatePing(TabEntry entry, int ping) {
        if (entry.getPing() == ping) return;

        entry.setPing(ping);

        TablistManager.getInst.getIeHelper().updatePing(this, entry, ping);
    }


    public void update() {
        if (!legacy) {
            String headerNow = adapter.getHeader(player);
            String footerNow = adapter.getFooter(player);

            if (!headerNow.equals(this.header) || !footerNow.equals(this.footer)) {
                TablistManager.getInst.getIeHelper().updateHeaderAndFooter(player, CC.translate(headerNow), CC.translate(footerNow));

                this.header = headerNow;
                this.footer = footerNow;
            }
        }

        Set<TabEntry> lastSet = new HashSet<>(currentEntrySet);

        for (TabLayout component : adapter.getSlots(player)) {
            TabEntry entry = getEntry(component.getColumn(), component.getSlot());
            if (entry != null) {
                lastSet.remove(entry);

                int ping = component.getPing();
                String text = component.getText();

                if (!legacy) {
                    if (!updateTexture(entry, component.getSkinTexture())) {
                        updatePing(entry, ping);
                        updateText(entry, text);
                    }
                } else {
                    updatePing(entry, ping);
                    updateText(entry, text);
                }
            }
        }

        for (TabEntry entry : lastSet) {
            if (!legacy) {
                if (!updateTexture(entry, TabListCommons.defaultTexture)) {
                    updatePing(entry, -1);
                    updateText(entry, "");
                }
            } else {
                updatePing(entry, -1);
                updateText(entry, "");
            }
        }

        lastSet.clear();
    }

    private String getTeamName(int index) {
        return ChatColor.values()[index / 10].toString()
                + ChatColor.values()[index % 10].toString()
                + ChatColor.RESET;
    }
}