package hardling.us.hub.util.Files;

import hardling.us.hub.SpeakHub;

import java.util.List;

public class Lang {

    private static final ConfigCreator langFile = SpeakHub.getInst().getLangFile();
    private static final String prefix = SpeakHub.getInst().getPrefix();

    public static String STOP_SERVER = "&cStop the Server to view changes&7.";

    public static String CLEAR_KIT_PVP = prefix + "&aThe pvp kit is empty&7.";

    public static String NO_PERMS = langFile.getString("NO-PERMS");

    public static String OFFLINE_PLAYER = langFile.getString("OFFLINE-PLAYER");

    public static String HIDE_PLAYER = langFile.getString("HIDE-PLAYER");

    public static String SHOW_PLAYER = langFile.getString("SHOW-PLAYER");

    public static String CHAT_INVLID_DURATION = prefix + "&cPlease use a valid time duration&7.";
    public static String CHAT_UNMUTED = prefix + "&aGlobal chat is now enabled&7.";
    public static String CHAT_MUTED = prefix + "&cGlobal chat is now disabled&7.";
    public static String CHAT_DELAY = langFile.getString("COMMAND.CHAT.CHAT_DELAY");
    public static String CHAT_DELAY_ANNUNCEMENT = langFile.getString("COMMAND.CHAT_DELAY_ANNUNCEMENT");

    public static String COMMAND_USE_CHAT_COOLDOWN = prefix + "&c/Chat Cooldown <delay>";
    public static String COMMAND_PING = langFile.getString("COMMAND.PING");
    public static List<String> COMMAND_CHAT_CLEAR = langFile.getStringList("COMMAND.CHAT.CLEAR");

    public static String TELEPORT_SPAWN = langFile.getString("TELEPORT.SPAWN");
    public static String TELEPORT_TELEPORT_TO = langFile.getString("TELEPORT.TELEPORT-TO");
    public static String TELEPORT_TELEPORTALL_FROM = langFile.getString("TELEPORT.TELEPORTALL-FROM");
    public static String TELEPORT_TELEPORTHERE_TO = langFile.getString("TELEPORT.TELEPORTHERE-TO");

    public static String DISABLED_MESSAGES = langFile.getString("DISABLED-MESSAGES");
    public static String DISABLED_COMMANDS = langFile.getString("DISABLED-COMMANDS");

    public static boolean JOIN_ENABLED = langFile.getBoolean("JOIN_MESSAGE.ENABLED");
    public static List<String> JOIN_MESSAGE = langFile.getStringList("JOIN_MESSAGE.MESSAGE");

    public static boolean PLAYER_DONATOR_QUIT_ENABLED = langFile.getBoolean("PLAYER-DONATOR.QUIT.ENABLED");
    public static boolean PLAYER_DONATOR_JOIN_ENABLED = langFile.getBoolean("PLAYER-DONATOR.JOIN.ENABLED");
    public static List<String> PLAYER_DONATOR_JOIN_MESSAGE = langFile.getStringList("PLAYER-DONATOR.JOIN.MESSAGE");
    public static List<String> PLAYER_DONATOR_QUIT_MESSAGE = langFile.getStringList("PLAYER-DONATOR.QUIT.MESSAGE");

    public static String PVP_MODE_NOT_CONFIGURED = prefix + "&cPvP mode is not configured for use&7.";
    public static String PVP_MODE_NEED_HAVE_ACTIVATED = prefix + "&cYou need to have the PvP-Mode activated";
    public static String PVP_MODE_JOIN = langFile.getString("PVP-MODE-JOIN");

    public static String SPAWN_COORDINATES_NOT_EXIST = prefix + "&cSpawn coordinates do not exist&7.";
    public static String SPAWN_PVP_COORDINATES_NOT_EXIST = prefix + "&cSpawnPVP coordinates do not exist&7.";
    public static String SPAWN_PVP_COORDINATES_SAVE = prefix + "&aSpawnPvP coordinates saved correctly&7.";
    public static String SPAWN_COORDINATES_SAVE = prefix + "&aSpawn coordinates saved correctly&7.";

    public static String BORDER_CANNOT_MOVE = langFile.getString("BORDER-CANNOT-MOVE");

    public static String QUEUE_CONFIGURED = prefix + "&aQueue system configured as&8: ";
    public static List<String> QUEUE_PAUSED = langFile.getStringList("QUEUE.PAUSED");
    public static List<String> QUEUE_JOINED = langFile.getStringList("QUEUE.JOINED");
    public static List<String> QUEUE_PRIORITY = langFile.getStringList("QUEUE.PRIORITY");

    public static List<String> SOCIAL_DISCORD = langFile.getStringList("SOCIAL.DISCORD");
    public static List<String> SOCIAL_STORE = langFile.getStringList("SOCIAL.STORE");
    public static List<String> SOCIAL_TEAMSPEAK = langFile.getStringList("SOCIAL.TEAMSPEAK");
    public static List<String> SOCIAL_TWITTER = langFile.getStringList("SOCIAL.TWITTER");
    public static List<String> SOCIAL_WEBSITE = langFile.getStringList("SOCIAL.WEBSITE");
}
