package hardling.us.hub.util.command;

import hardling.us.hub.commands.Essentials.*;
import hardling.us.hub.commands.Essentials.Media.*;
import hardling.us.hub.commands.Essentials.PvP.LeavePvPModeCommand;
import hardling.us.hub.commands.Essentials.PvP.PvPModeCommand;
import hardling.us.hub.commands.Essentials.Queue.JoinQueueCommand;
import hardling.us.hub.commands.Essentials.Queue.LeaveQueueCommand;
import hardling.us.hub.commands.Essentials.Queue.PauseQueueCommand;
import hardling.us.hub.commands.Essentials.Spawn.SetSpawnCommand;
import hardling.us.hub.commands.Essentials.Spawn.SpawnCommand;
import hardling.us.hub.commands.Essentials.Teleport.TeleportAllCommand;
import hardling.us.hub.commands.Essentials.Teleport.TeleportCommand;
import hardling.us.hub.commands.Essentials.Teleport.TeleportHereCommand;
import hardling.us.hub.commands.Essentials.Teleport.TeleportPossCommand;
import hardling.us.hub.commands.SpeakHubCommand;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class CommandManager {

    private SpeakHubCommand speakHubCommand;
    private DiscordCommand discordCommand;
    private FlyCommand flyCommand;
    private TeleportCommand teleportCommand;
    private TeleportAllCommand teleportAllCommand;
    private TeleportHereCommand teleportHereCommand;
    private TeleportPossCommand teleportPossCommand;
    private SetSpawnCommand setSpawnCommand;
    private SpawnCommand spawnCommand;
    private TeamSpeakCommand teamSpeakCommand;
    private WebSiteCommand webSiteCommand;
    private StoreCommand storeCommand;
    private TwitterCommand twitterCommand;
    private SkullCommand skullCommand;
    private CustomTimerCommand customTimer;
    private BuildModeCommand buildModeCommand;
    private LeavePvPModeCommand leavePvPModeCommand;
    private PvPModeCommand pvPModeCommand;
    private ChatCommand clearChatCommand;
    private PingCommand pingCommand;
    private GameModeCommand gameModeCommandM;
    private BroadcastCommand broadcastCommand;
    private JoinQueueCommand joinQueueCommand;
    private LeaveQueueCommand leaveQueueCommand;
    private PauseQueueCommand pauseQueueCommand;
    private ReloadHotBarCommand reloadHotBarCommand;
    private ServerCommand serverCommand;

    @Register
    public void loadCommands() {
        for (Field field : getClass().getDeclaredFields()) {
            if (BaseCommand.class.isAssignableFrom(field.getType()) && field.getType().getSuperclass() == BaseCommand.class) {
                field.setAccessible(true);
                try {
                    Constructor<?> constructor = field.getType().getDeclaredConstructor(new Class[0]);
                    constructor.newInstance(new Object[0]);
                } catch (ReflectiveOperationException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}