package hardling.us.hub.tablist;

import com.google.common.collect.Sets;
import hardling.us.hub.SpeakHub;
import hardling.us.hub.tablist.impl.TabListCommons;
import hardling.us.hub.tablist.impl.utils.SkinTexture;
import hardling.us.hub.tablist.impl.utils.TabColumn;
import hardling.us.hub.tablist.impl.utils.TabLayout;
import hardling.us.hub.tablist.interfaces.IEAdapter;
import hardling.us.hub.util.Animation;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Config;
import hardling.us.hub.util.Files.ConfigCreator;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.util.Set;

public class TabAdapter implements IEAdapter {

    public static TabType tabType;

    public TabAdapter() {
        TabAdapter.tabType = TabType.getType("NORMAL");
    }

    @Override
    public String getHeader(Player player) {
        if (Config.TABLIST_HEADER_ANIMATED_ENABLED) {
            return CC.translate(Animation.getTabListHeader());
        } else {
            return CC.translate(Config.TABLIST_HEADER_STATIC_NORMAL);
        }
    }

    @Override
    public String getFooter(Player player) {
        if (Config.TABLIST_FOOTER_ANIMATED_ENABLED) {
            return CC.translate(Animation.getTabListFooter());
        } else {
            return CC.translate(Config.TABLIST_FOOTER_STATIC_NORMAL);
        }
    }

    @Override
    public Set<TabLayout> getSlots(Player player) {
        ConfigCreator tabFile = SpeakHub.getInst().getTabFile();
        Set<TabLayout> tabObjects = Sets.newHashSet();
        if (tabType == TabType.DEFAULT) {
            for (int i = 1; i < 21; i++) {
                tabObjects.add(new TabLayout(TabColumn.LEFT, i,
                        replaceLobby(tabFile.getString("TABLIST.LEFT." + i + ".text"), player),
                        skin(tabFile.getString("TABLIST.LEFT." + i + ".head"), player), 0));
                tabObjects.add(new TabLayout(TabColumn.MIDDLE, i,
                        replaceLobby(tabFile.getString("TABLIST.CENTER." + i + ".text"), player),
                        skin(tabFile.getString("TABLIST.CENTER." + i + ".head"), player), 0));
                tabObjects.add(new TabLayout(TabColumn.RIGHT, i,
                        replaceLobby(tabFile.getString("TABLIST.RIGHT." + i + ".text"), player),
                        skin(tabFile.getString("TABLIST.RIGHT." + i + ".head"), player), 0));
                tabObjects.add(new TabLayout(TabColumn.FAR_RIGHT, i,
                        replaceLobby(tabFile.getString("TABLIST.EXTERNAL-RIGHT." + i + ".text"), player),
                        skin(tabFile.getString("TABLIST.EXTERNAL-RIGHT." + i + ".head"), player), 0));
            }
        } else {

        }
        return tabObjects;
    }

    public String replaceLobby(String string, Player player) {
        string = CC.Placeholders(player, string);
        string = string.replace("%arrow%", Config.TABLIST_ARROW);
        string = string.replace("%bars%", Config.TABLIST_BARS);
        if (SpeakHub.getInst().getQueues().inQueue(player)) {
            string = string.replace("%queue-server%", "" + SpeakHub.getInst().getQueues().getQueueIn(player));
            string = string.replace("%queue-position%", "" + SpeakHub.getInst().getQueues().getPosition(player));
            string = string.replace("%queue-size%", "" + SpeakHub.getInst().getQueues().getSize(SpeakHub.getInst().getQueues().getQueueIn(player)));
        } else {
            string = string.replace("%queue-server%", "&7None");
            string = string.replace("%queue-position%", "&70");
            string = string.replace("%queue-size%", "&70");
        }

        return string;
    }

    @SneakyThrows
    public SkinTexture skin(String string, Player player) {
        SkinTexture skin = TabListCommons.defaultTexture;
        switch (string) {
            /***************
             * HEADS PLAYER
             **************/
            case "%player%":
                skin = TabListCommons.getSkinData(player.getUniqueId());
                break;
            case "%discord_head%":
                skin = TabListCommons.DISCORD_TEXTURE;
                break;
            case "%waring%":
                skin = TabListCommons.WARNING_TEXTURE;
                break;
            case "%teamspeak_head%":
                skin = TabListCommons.TEAMSPEAK_TEXTURE;
                break;
            case "%twitter_head%":
                skin = TabListCommons.TWITTER_TEXTURE;
                break;
            case "%store_head%":
                skin = TabListCommons.STORE_TEXTURE;
                break;
            case "%youtube%":
                skin = TabListCommons.YOUTUBE_TEXTURE;
                break;
            case "%facebook%":
                skin = TabListCommons.FACEBOOK_TEXTURE;
                break;
            case "%yosoybyproxx%":
                skin = TabListCommons.YOSOYBYPROXX_TEXTURE;
                break;
            case "%clock%":
                skin = TabListCommons.CLOCK_TEXTURE;
                break;
            case "%ping%":
                skin = TabListCommons.PING_TEXTURE;
                break;
            case "%chest_gold%":
                skin = TabListCommons.CHESTGOLD_TEXTURE;
                break;
            case "%ender_pearl%":
                skin = TabListCommons.ENDERPEARL_TEXTURE;
                break;
            case "%winrar%":
                skin = TabListCommons.WINRAR_TEXTURE;
                break;
            case "%chest%":
                skin = TabListCommons.CHEST_TEXTURE;
                break;
            case "%ender_chest%":
                skin = TabListCommons.ENDERCHEST_TEXTURE;
                break;
            case "%furnace%":
                skin = TabListCommons.FURNANCE_TEXTURE;
                break;
            case "%dirt%":
                skin = TabListCommons.DIRT_TEXTURE;
                break;
            case "%tv%":
                skin = TabListCommons.TV_TEXTURE;
                break;
            case "%beacon%":
                skin = TabListCommons.BEANCON_TEXTURE;
                break;
            case "%monitor%":
                skin = TabListCommons.MONITOR_TEXTURE;
                break;
            case "%crown%":
                skin = TabListCommons.CROWN_TEXTURE;
                break;
            case "%castle%":
                skin = TabListCommons.CASTLE_TEXTURE;
                break;
            case "%compass%":
                skin = TabListCommons.COMPASS_TEXTURE;
                break;
            case "%earth%":
                skin = TabListCommons.EARTH_TEXTURE;
                break;
            case "%sword%":
                skin = TabListCommons.SWORD_TEXTURE;
                break;
            case "%statistic%":
                skin = TabListCommons.STATISTIC_TEXTURE;
                break;
            case "%ghost%":
                skin = TabListCommons.GHOST_TEXTURE;
                break;
            case "%potion%":
                skin = TabListCommons.POTION_TEXTURE;
                break;


            /*************
             * HEADS COMMUN
             **************/
            case "%green%":
                skin = TabListCommons.GREEN_DOT;
                break;
            case "%blue%":
                skin = TabListCommons.BLUE_DOT;
                break;
            case "%dark_blue%":
                skin = TabListCommons.DARK_BLUE_DOT;
                break;
            case "%dark_aqua%":
                skin = TabListCommons.DARK_AQUA_DOT;
                break;
            case "%purple%":
                skin = TabListCommons.DARK_PURPLE_DOT;
                break;
            case "%pink%":
                skin = TabListCommons.LIGHT_PURPLE_DOT;
                break;
            case "%gray%":
                skin = TabListCommons.GRAY_DOT;
                break;
            case "%red%":
                skin = TabListCommons.RED_DOT;
                break;
            case "%yellow%":
                skin = TabListCommons.YELLOW_DOT;
                break;
            case "%dark_green%":
                skin = TabListCommons.DARK_GREEN_DOT;
                break;
            case "%dark_red%":
                skin = TabListCommons.DARK_RED_DOT;
                break;
            case "%gold%":
                skin = TabListCommons.GOLD_DOT;
                break;
            case "%aqua%":
                skin = TabListCommons.AQUA_DOT;
                break;
            case "%white%":
                skin = TabListCommons.WHITE_DOT;
                break;
            case "%dark_gray%":
                skin = TabListCommons.DARK_GRAY;
                break;
            case "%black%":
                skin = TabListCommons.BLACK_DOT;
                break;
            case "%dark_gray_dot%":
                skin = TabListCommons.DARK_GRAY_DOT;
                break;
        }
        return skin;
    }
}