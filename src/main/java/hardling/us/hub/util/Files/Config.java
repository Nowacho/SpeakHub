package hardling.us.hub.util.Files;

import hardling.us.hub.SpeakHub;

import java.util.List;

public class Config {

    private static final ConfigCreator configFile = SpeakHub.getInst().getConfigFile();
    private static final ConfigCreator cosmeticsFile = SpeakHub.getInst().getCosmeticsFile();
    private static final ConfigCreator tabFile = SpeakHub.getInst().getTabFile();
    private static final ConfigCreator scoreboardFile = SpeakHub.getInst().getScoreboardFile();
    private static final ConfigCreator selectorFile = SpeakHub.get().getSelectorFile();
    private static final ConfigCreator licenseFile = SpeakHub.getInst().getLicenseFile();
    private static final ConfigCreator enderbuttFile = SpeakHub.getInst().getEnderbuttFile();
    private static final ConfigCreator pvpFile = SpeakHub.getInst().getPvpFile();
    private static final ConfigCreator hubselectorFile = SpeakHub.getInst().getHubselectorFile();
    private static final ConfigCreator hideplayerFile = SpeakHub.getInst().getHideplayerFile();
    private static final ConfigCreator mediaFile = SpeakHub.getInst().getMediaFile();

    public static String LICENSE = licenseFile.getString("LICENSE");

    public static boolean LAUNCH_PAD_SOUND_ENABLED = configFile.getBoolean("LAUNCH-PAD.SOUND-ENABLED");
    public static boolean LAUNCH_PAD_ENABLED = configFile.getBoolean("LAUNCH-PAD.ENABLED");
    public static boolean CHAT_COMMANDS_ENABLED = configFile.getBoolean("CHAT.COMMANDS.ENABLED");
    public static double LAUNCH_PAD_JUMP_MULTIPLY = configFile.getDouble("LAUNCH-PAD.JUMP.MULTIPLY");
    public static double LAUNCH_PAD_JUMP_HEIGHT = configFile.getDouble("LAUNCH-PAD.JUMP.HEIGHT");
    public static String LAUNCH_PAD_MATERIAL = configFile.getString("LAUNCH-PAD.MATERIAL");
    public static String LAUNCH_PAD_SOUND = configFile.getString("LAUNCH-PAD.SOUND");

    public static int CHAT_DELAY = configFile.getInt("CHAT.FORMAT.DELAY");
    public static boolean CHAT_FORMAT_ENABLED = configFile.getBoolean("CHAT.FORMAT.ENABLED");
    public static boolean CHAT_MESSAGE_ENABLED = configFile.getBoolean("CHAT.MESSAGES.ENABLED");
    public static String CHAT_BROADCAST = configFile.getString("CHAT.FORMAT.BROADCAST");
    public static String CHAT_FORMAT_CHAT = configFile.getString("CHAT.FORMAT.CHAT");
    public static List<String> CHAT_MESSAGE_DISABLED = configFile.getStringList("CHAT.MESSAGES.DISABLED");
    public static List<String> CHAT_COMMANDS_DISABLED = configFile.getStringList("CHAT.COMMANDS.DISABLED");

    public static int SELECTOR_INVENTORY_SLOTS = selectorFile.getInt("SELECTOR.INVENTORY.SLOTS");
    public static int SELECTOR_DATA = selectorFile.getInt("SELECTOR.DATA");
    public static int SELECTOR_INVENTORY_GLASS_PANEL_ID = selectorFile.getInt("SELECTOR.INVENTORY.GLASS_PANEL.ID");
    public static boolean SELECTOR_INVENTORY_GLASS_PANEL_ENABLED = selectorFile.getBoolean("SELECTOR.INVENTORY.GLASS_PANEL.ENABLED");
    public static boolean SELECTOR_ENCHANTED = selectorFile.getBoolean("SELECTOR.ENCHANTED");
    public static boolean SELECTOR_SOUND_ENABLED = selectorFile.getBoolean("SELECTOR.SOUND_ENABLED");
    public static String SELECTOR_INVENTORY_TITLE = selectorFile.getString("SELECTOR.INVENTORY.TITLE");
    public static String SELECTOR_SOUND = selectorFile.getString("SELECTOR.SOUND");
    public static String SELECTOR_DISPLAYNAME = selectorFile.getString("SELECTOR.DISPLAYNAME");
    public static String SELECTOR_MATERIAL =  selectorFile.getString("SELECTOR.MATERIAL");
    public static List<String> SELECTOR_LORE = selectorFile.getStringList("SELECTOR.LORE");

    public static int PVP_DATA = pvpFile.getInt("PVP.DATA");
    public static boolean PVP_ENCHANTED = pvpFile.getBoolean("PVP.ENCHANTED");
    public static boolean PVP_SOUND_ENABLED = pvpFile.getBoolean("PVP.SOUND-ENABLED");
    public static List<String> PVP_LORE = pvpFile.getStringList("PVP.LORE");
    public static String PVP_SOUND = pvpFile.getString("PVP.SOUND");
    public static String PVP_MATERIAL = pvpFile.getString("PVP.MATERIAL");
    public static String PVP_DISPLAYNAME = pvpFile.getString("PVP.DISPLAYNAME");

    public static int LUNAR_API_JOIN_PLAYER_TIME = configFile.getInt("LUNAR-API.JOIN-PLAYER.TIME");
    public static boolean LUNAR_API_ENABLE = configFile.getBoolean("LUNAR-API.ENABLED");
    public static boolean LUNAR_API_JOIN_PLAYER_ENABLE = configFile.getBoolean("LUNAR-API.JOIN-PLAYER.ENABLED");
    public static boolean LUNAR_API_NAME_TAGS_ENABLE = configFile.getBoolean("LUNAR-API.NAME-TAGS.ENABLED");
    public static String LUNAR_API_JOIN_PLAYER_TITLE_MESSAGE = configFile.getString("LUNAR-API.JOIN-PLAYER.TITLE.MESSAGE");
    public static String LUNAR_API_JOIN_PLAYER_SUBTITLE_MESSAGE = configFile.getString("LUNAR-API.JOIN-PLAYER.SUBTITLE.MESSAGE");
    public static List<String> LUNAR_API_NAME_TAGS_STAFF_TAG = configFile.getStringList("LUNAR-API.NAME-TAGS.TAGS.STAFF.TAG");
    public static List<String> LUNAR_API_NAME_TAGS_PVP_MODE_TAG = configFile.getStringList("LUNAR-API.NAME-TAGS.TAGS.PVP-MODE.TAG");
    public static List<String> LUNAR_API_NAME_TAGS_QUEUE_TAG = configFile.getStringList("LUNAR-API.NAME-TAGS.TAGS.QUEUE.TAG");
    public static List<String> LUNAR_API_NAME_TAGS_DEFAULT_TAG = configFile.getStringList("LUNAR-API.NAME-TAGS.TAGS.DEFAULT.TAG");

    public static int QUEUE_MESSAGE_DELAY = configFile.getInt("QUEUE.MESSAGE-DELAY");
    public static int QUEUE_PRIORIETIES = configFile.getInt("QUEUE.PRIORITIES");

    public static int HOTBAR_COSMETCI_SLOT = cosmeticsFile.getInt("COSMETIC.SLOT");
    public static int HOTBAR_ENDERBUTT_SLOT = enderbuttFile.getInt("ENDERBUTT.SLOT");
    public static int HOTBAR_PVP_SLOT = pvpFile.getInt("PVP.SLOT");
    public static int HOTBAR_HUBSELECTOR_SLOT = hubselectorFile.getInt("HUB-SELECTOR.SLOT");
    public static int HOTBAR_SELECTOR_SLOT = selectorFile.getInt("SELECTOR.SLOT");
    public static int HOTBAR_HIDE_SHOW_SLOT = hideplayerFile.getInt("HIDE-SHOW.SLOT");
    public static int HOTBAR_MEDIA_SLOT = mediaFile.getInt("MEDIA.SLOT");
    public static boolean HOTBAR_ENDERBUTT_ENABLED = enderbuttFile.getBoolean("ENDERBUTT.ENABLED");
    public static boolean HOTBAR_COSMETIC_ENABLED = cosmeticsFile.getBoolean("COSMETIC.ENABLED");
    public static boolean HOTBAR_PVP_ENABLED = pvpFile.getBoolean("PVP.ENABLED");
    public static boolean HOTBAR_HUBSELECTOR_ENABLED = hubselectorFile.getBoolean("HUB-SELECTOR.ENABLED");
    public static boolean HOTBAR_SELECTOR_ENABLED = selectorFile.getBoolean("SELECTOR.ENABLED");
    public static boolean HOTBAR_HIDE_SHOW_ENABLED = hideplayerFile.getBoolean("HIDE-SHOW.ENABLED");
    public static boolean HOTBAR_MEDIA_ENABLED = mediaFile.getBoolean("MEDIA.ENABLED");

    public static int SETTINGS_BORDER_SIZE = configFile.getInt("SETTINGS.BORDER.SIZE");
    public static boolean SETIINGS_MISC_OPTIMIZE_WORLD = configFile.getBoolean("SETTINGS.MISC.OPTIMIZE_WORLD");
    public static boolean SETIINGS_BLOCK_FORM = configFile.getBoolean("SETTINGS.BLOCK.FORM");
    public static boolean SETTINGS_BLOCK_BURN = configFile.getBoolean("SETTINGS.BLOCK.BURN");
    public static boolean SETTINGS_BLOCK_FADE = configFile.getBoolean("SETTINGS.BLOCK.FADE");
    public static boolean SETTINGS_BLOCK_BREAK = configFile.getBoolean("SETTINGS.BLOCK.BREAK");
    public static boolean SETTINGS_BLOCK_PLACE = configFile.getBoolean("SETTINGS.BLOCK.PLACE");
    public static boolean SETTINGS_BLOCK_FIRE_SPREAD = configFile.getBoolean("SETTINGS.BLOCK.FIRE-SPREAD");
    public static boolean SETTINGS_ITEMS_DROP = configFile.getBoolean("SETTINGS.ITEMS.DROP");
    public static boolean SETTINGS_ITEMS_PICKUP = configFile.getBoolean("SETTINGS.ITEMS.PICKUP");
    public static boolean SETTINGS_PLAYER_HUNGER = configFile.getBoolean("SETTINGS.PLAYER.HUNGER");
    public static boolean SETTINGS_PLAYER_FISH = configFile.getBoolean("SETTINGS.PLAYER.FISH");
    public static boolean SETTINGS_PLAYER_INTERACT = configFile.getBoolean("SETTINGS.PLAYER.INTERACT");
    public static boolean SETTINGS_PLAYER_FALL_DAMAGE = configFile.getBoolean("SETTINGS.PLAYER.FALL-DAMAGE");
    public static boolean SETTINGS_PLAYER_FIRE_DAMAGE = configFile.getBoolean("SETTINGS.PLAYER.FIRE-DAMAGE");
    public static boolean SETTINGS_PLAYER_DROWNING = configFile.getBoolean("SETTINGS.PLAYER.DROWNING");
    public static boolean SETTINGS_PLAYER_TELEPORT_ON_JOIN_SPAWN = configFile.getBoolean("SETTINGS.PLAYER.TELEPORT-ON-JOIN-SPAWN");
    public static boolean SETTINGS_MISC_MOBS_SPAWN = configFile.getBoolean("SETTINGS.MISC.MOBS-SPAWN");
    public static boolean SETTINGS_MISC_LEAVES_DECAY = configFile.getBoolean("SETTINGS.MISC.LEAVES-DECAY");
    public static boolean SETTINGS_MISC_ENTITY_EXPLODE = configFile.getBoolean("SETTINGS.MISC.ENTITY-EXPLODE");
    public static boolean SETTINGS_MISC_WEATHER = configFile.getBoolean("SETTINGS.MISC.WEATHER");
    public static boolean SETTINGS_MISC_DEATH_MESSAGE = configFile.getBoolean("SETTINGS.MISC.DEATH-MESSAGE");
    public static boolean SETTINGS_MISC_VOID_DEATH = configFile.getBoolean("SETTINGS.MISC.VOID-DEATH");
    public static boolean SETTINGS_MISC_ENTITY_SPAWN = configFile.getBoolean("SETTINGS.MISC.ENTITY-SPAWN");
    public static String SETTINGS_QUEUE = configFile.getString("SETTINGS.QUEUE");

    public static boolean TABLIST_ENABLED = tabFile.getBoolean("TABLIST.ENABLED");
    public static boolean TABLIST_HEADER_ANIMATED_ENABLED = tabFile.getBoolean("TABLIST.HEADER.ANIMATED.ENABLED");
    public static boolean TABLIST_FOOTER_ANIMATED_ENABLED = tabFile.getBoolean("TABLIST.FOOTER.ANIMATED.ENABLED");
    public static double TABLIST_FOOTER_ANIMATED_INTERVAL = tabFile.getDouble("TABLIST.FOOTER.ANIMATED.INTERVAL");
    public static double TABLIST_HEADER_ANIMATED_INTERVAL = tabFile.getDouble("TABLIST.HEADER.ANIMATED.INTERVAL");
    public static String TABLIST_HEADER_STATIC_NORMAL = tabFile.getString("TABLIST.HEADER.STATIC.NORMAL");
    public static String TABLIST_FOOTER_STATIC_NORMAL = tabFile.getString("TABLIST.FOOTER.STATIC.NORMAL");
    public static String TABLIST_ARROW = tabFile.getString("TABLIST.ARROW");
    public static String TABLIST_BARS = tabFile.getString("TABLIST.BARS");
    public static List<String> TABLIST_HEADER_ANIMATED_ANIMATION = tabFile.getStringList("TABLIST.HEADER.ANIMATED.ANIMATION");
    public static List<String> TABLIST_FOOTER_ANIMATED_ANIMATION = tabFile.getStringList("TABLIST.FOOTER.ANIMATED.ANIMATION");

    public static boolean SCOREBOARD_TITLE_ANIMATED_ENABLED = scoreboardFile.getBoolean("SCOREBOARD.TITLE.ANIMATED.ENABLED");
    public static boolean SCOREBOARD_ENABLE = scoreboardFile.getBoolean("SCOREBOARD.ENABLED");
    public static boolean SCOREBOARD_FOOTER_ANIMATED_ENABLED = scoreboardFile.getBoolean("SCOREBOARD.FOOTER.ANIMATED.ENABLED");
    public static double SCOREBOARD_TITLE_ANIMATED_INTERVAL = scoreboardFile.getDouble("SCOREBOARD.TITLE.ANIMATED.INTERVAL");
    public static double SCOREBOARD_FOOTER_ANIMATED_INTERVAL = scoreboardFile.getDouble("SCOREBOARD.FOOTER.ANIMATED.INTERVAL");
    public static String SCOREBOARD_TITLE_STATIC_NORMAL = scoreboardFile.getString("SCOREBOARD.TITLE.STATIC.NORMAL");
    public static String SCOREBOARD_FOOTER_STATIC_NORMAL = scoreboardFile.getString("SCOREBOARD.FOOTER.STATIC.NORMAL");
    public static String SCOREBOARD_BARS = scoreboardFile.getString("SCOREBOARD.BARS");
    public static String SCOREBOARD_ARROW = scoreboardFile.getString("SCOREBOARD.ARROW");
    public static List<String> SCOREBOARD_PVP_MODE = scoreboardFile.getStringList("SCOREBOARD.PVP-MODE");
    public static List<String> SCOREBOARD_IN_QUEUE = scoreboardFile.getStringList("SCOREBOARD.IN-QUEUE");
    public static List<String> SCOREBOARD_TITLE_ANIMATED_ANIMATION = scoreboardFile.getStringList("SCOREBOARD.TITLE.ANIMATED.ANIMATION");
    public static List<String> SCOREBOARD_FOOTER_ANIMATED_ANIMATION = scoreboardFile.getStringList("SCOREBOARD.FOOTER.ANIMATED.ANIMATION");
    public static List<String> SCOREBOARD_LINES = scoreboardFile.getStringList("SCOREBOARD.LINES");


    public static String SOCIAL_STORE = configFile.getString("SOCIAL.STORE");
    public static String SOCIAL_TWITTER = configFile.getString("SOCIAL.TWITTER");
    public static String SOCIAL_TEAMSPEAK = configFile.getString("SOCIAL.TEAMSPEAK");
    public static String SOCIAL_DISCORD = configFile.getString("SOCIAL.DISCORD");
    public static String SOCIAL_WEBSITE = configFile.getString("SOCIAL.WEBSITE");
}
