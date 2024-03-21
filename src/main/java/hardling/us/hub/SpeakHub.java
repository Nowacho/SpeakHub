package hardling.us.hub;

import hardling.us.hub.listeners.*;
import hardling.us.hub.listeners.Editor.MenuEditor;
import hardling.us.hub.listeners.Editor.hotbar.HideShowEditor;
import hardling.us.hub.listeners.Editor.hotbar.HotBarEditor;
import hardling.us.hub.listeners.Editor.hotbar.SlotEditor;
import hardling.us.hub.listeners.hotbar.*;
import hardling.us.hub.listeners.hotbar.cosmetics.HeadsListener;
import hardling.us.hub.listeners.hotbar.cosmetics.OutfitsListener;
import hardling.us.hub.listeners.hotbar.cosmetics.Particles;
import hardling.us.hub.ranks.PermissionManager;
import hardling.us.hub.scoreboard.Scoreboard;
import hardling.us.hub.scoreboard.assemble.Assemble;
import hardling.us.hub.scoreboard.assemble.AssembleStyle;
import hardling.us.hub.scoreboard.customtimer.CustomTimerManager;
import hardling.us.hub.tablist.TabAdapter;
import hardling.us.hub.tablist.impl.TablistManager;
import hardling.us.hub.util.Animation;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Config;
import hardling.us.hub.util.Files.ConfigCreator;
import hardling.us.hub.util.command.CommandFramework;
import hardling.us.hub.util.command.CommandManager;
import hardling.us.hub.util.cooldown.TimerManager;
import hardling.us.hub.util.queue.QueueCustom.SpeakQueue;
import hardling.us.hub.util.queue.QueueCustom.SpeakQueueManager;
import hardling.us.hub.util.queue.Queues;
import hardling.us.hub.util.queue.System.*;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@Getter
@Setter
public class SpeakHub extends JavaPlugin {

    @Setter
    private String prefix = CC.translate("&7[&dSpeak&5Hub&7] &7");
    private static SpeakHub plugin;
    @Getter
    private static SpeakHub inst;
    @Setter
    private ConfigCreator configFile;
    @Setter
    private ConfigCreator licenseFile;
    @Setter
    private ConfigCreator tabFile;
    @Setter
    private ConfigCreator scoreboardFile;
    @Setter
    private ConfigCreator particlesFile;
    @Setter
    private ConfigCreator oufitsFile;
    @Setter
    private ConfigCreator headsFile;
    @Setter
    private ConfigCreator langFile;
    @Setter
    private ConfigCreator selectorFile;
    @Setter
    private ConfigCreator pvpFile;
    @Setter
    private ConfigCreator mediaFile;
    @Setter
    private ConfigCreator hubselectorFile;
    @Setter
    private ConfigCreator hideplayerFile;
    @Setter
    private ConfigCreator enderbuttFile;
    @Setter
    private ConfigCreator cosmeticsFile;
    private Queues Queues;
    private ChatListener chatListener;
    private CustomTimerManager customTimerManager;
    private CommandFramework commandFramework;
    private CommandManager commandManager;
    private SpeakQueueManager speakQueueManager;
    private PvPListener PvPListener;
    private PermissionManager permission;

    @Override
    public void onEnable() {
        inst = this;
        plugin = this;
        this.loadFiles();
        this.loadAll();
            CC.log(this.getPrefix() + "&aSuccessfully loaded plugin");
        /*String a2 = "http://108.175.15.151:8080/api/client";
        String a3 = "7eb937c5b91cbeb7210340180a1955fbcf991196";
        try {
            while (!new FileUtil.getConfig(this, Config.LICENSE, a2, a3).a5()) {
                while (!new FileUtil.getFile(this, Config.LICENSE, a2, a3).a4()) {
                    Bukkit.getPluginManager().disablePlugin(this);
                    Bukkit.getScheduler().cancelTasks(this);
                    return;
                }
            }
           CC.log(this.getPrefix() + "&aSuccessfully loaded plugin");
        } catch (Exception e) {
                CC.log(CC.getLINEERROR());
                CC.log(this.getPrefix() + e.getMessage());
                CC.log(CC.getLINEERROR());
                Bukkit.getPluginManager().disablePlugin(this);
                Bukkit.getScheduler().cancelTasks(this);
        }*/
    }

    @Override
    public void onDisable() {
        PvPListener.onDisable();
        CC.log(this.getPrefix() + "&aDisabling plugin");
    }

    public void loadAll() {
        this.loadListeners();
        this.loadManagers();
        this.setupQueue();
    }

    private void loadFiles() {
        try {
            this.licenseFile = new ConfigCreator("license.yml");
            this.configFile = new ConfigCreator("config.yml");
            this.langFile = new ConfigCreator("lang/lang.yml");
            this.tabFile = new ConfigCreator("provider/tab.yml");
            this.scoreboardFile = new ConfigCreator("provider/scoreboard.yml");
            this.particlesFile = new ConfigCreator("menu/particles.yml");
            this.oufitsFile = new ConfigCreator("menu/outfits.yml");
            this.headsFile = new ConfigCreator("menu/heads.yml");
            this.selectorFile = new ConfigCreator("hotbar/selector.yml");
            this.pvpFile = new ConfigCreator("hotbar/pvp.yml");
            this.mediaFile = new ConfigCreator("hotbar/media.yml");
            this.hubselectorFile = new ConfigCreator("hotbar/hubselector.yml");
            this.hideplayerFile = new ConfigCreator("hotbar/hideplayer.yml");
            this.enderbuttFile = new ConfigCreator("hotbar/enderbutt.yml");
            this.cosmeticsFile = new ConfigCreator("hotbar/cosmetics.yml");
        } catch (RuntimeException e) {
            CC.log(CC.getLINEERROR());
            CC.log(this.prefix + "&cYMLs did not load, please restart the server or contact support. ");
            CC.log(CC.getLINEERROR());
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getScheduler().cancelTasks(this);
        }
    }

    private void loadListeners() {
        PluginManager manager = this.getServer().getPluginManager();
        // LUNAR CLIENT API
        if (Bukkit.getPluginManager().isPluginEnabled("LunarClient-API")) {
            if (Config.LUNAR_API_ENABLE) {
                manager.registerEvents(new LunarListener(), this);
                CC.log(SpeakHub.this.getPrefix() + "&aLunarClient-API Expansion Successfully");
            } else {
                CC.log(SpeakHub.this.getPrefix() + "&cPlease put the LunarClient-API");
                Bukkit.getPluginManager().disablePlugin(this);
                Bukkit.getScheduler().cancelTasks(this);
            }
        }

        // PLAYER EVENTS
        manager.registerEvents(new ChatListener(), this);
        manager.registerEvents(new PlayerListener(), this);
        manager.registerEvents(new JumpListener(), this);
        manager.registerEvents(new LaunchPadListener(), this);

        // WOLRD EVENTS
        manager.registerEvents(new WorldListener(), this);

        // COSMETICS
        manager.registerEvents(new Particles(), this);
        manager.registerEvents(new HeadsListener(), this);
        manager.registerEvents(new OutfitsListener(), this);

        // HOTBAR ITEMS
        manager.registerEvents(new CosmeticListener(), this);
        manager.registerEvents(new SelectorListener(), this);
        manager.registerEvents(new EnderButtListener(), this);
        manager.registerEvents(new HubListener(), this);
        manager.registerEvents(new HideShowListener(), this);
        manager.registerEvents(new MediaListener(), this);

        // EDITORS
        manager.registerEvents(new MenuEditor(), this);
        manager.registerEvents(new HotBarEditor(), this);
        manager.registerEvents(new HideShowEditor(), this);
        manager.registerEvents(new SlotEditor(), this);
        CC.log(this.getPrefix() + "&aLoading Listeners");
    }


    private void loadManagers() {
        Animation.init();
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        customTimerManager = new CustomTimerManager();

        speakQueueManager = new SpeakQueueManager(this);

        new TimerManager();

        PvPListener = new PvPListener();

        permission = new PermissionManager(this);
        permission.loadHook();

        commandFramework = new CommandFramework(this);
        commandManager = new CommandManager();
        commandFramework.loadCommandsInFile();
        commandManager.loadCommands();

        // WOLRD OPTIMIZE
        if (Config.SETIINGS_MISC_OPTIMIZE_WORLD) {
            for (World world : Bukkit.getWorlds()) {
                world.setThundering(false);
                world.setStorm(false);
                world.setWeatherDuration(0);
                world.setGameRuleValue("doFireTick", "false");
                world.setGameRuleValue("mobGriefing", "false");
                world.setGameRuleValue("doMobGriefing", "false");
            }
        }

        if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            CC.log(SpeakHub.getInst().getPrefix() + "&cPlease put the PlaceholderAPI");
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getScheduler().cancelTasks(this);
            return;
        }
        CC.log(SpeakHub.getInst().getPrefix() + "&aPlaceholderAPI Expansion Successfully");

        // TABLIST
        if (Config.TABLIST_ENABLED) {
            TablistManager.getInst.onEnable(this);
            TablistManager.getInst.setAdapter(new TabAdapter());
        }
        // SCOREBOARD
        if (Config.SCOREBOARD_ENABLE) {
            Assemble assemble = new Assemble(this, new Scoreboard());
            assemble.setTicks(2);
            assemble.setAssembleStyle(AssembleStyle.VIPER);
        }
        CC.log(this.getPrefix() + "&aLoading Managers");
    }

    private void setupQueue() {
        Queues = null;
        switch (Config.SETTINGS_QUEUE) {
            case "AJQUEUE":
                Queues = new ajQueueImpl();
                CC.log(this.getPrefix() + "&aQueue system configured as&8: &fajQueue");
                break;
            case "PORTALOLD":
                Queues = new PortalOldImpl();
                CC.log(this.getPrefix() + "&afQueue system configured as&8: &fPortalOld");
                break;
            case "EZQUEUE":
                Queues = new EzQueueImpl();
                CC.log(this.getPrefix() + "&aQueue system configured as&8: &fEzQueue");
                break;
            case "SPEAKQUEUE":
                Queues = new SpeakQueueImpl();
                speakQueueManager.getQueues().clear();
                for (String server : this.getConfigFile().getConfigurationSection("QUEUE.SERVERS").getKeys(false)) {
                    speakQueueManager.getQueues().add(new SpeakQueue(this.getConfigFile().getString("QUEUE.SERVERS." + server + ".NAME")));
                }
                //speakQueueManager.getQueues().forEach(queue -> CC.log("&FileUtil" + queue.getServer() + " &fwas found and has been created!"));
                int queueDelay = Config.QUEUE_MESSAGE_DELAY;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        speakQueueManager.getQueues().forEach(queue -> {
                            if (!queue.isPaused() && !queue.getPlayers().isEmpty()) {
                                queue.update();
                                queue.removeFromQueue(queue.getPlayer(0));
                            }
                        });
                    }
                }.runTaskTimer(SpeakHub.getInst(), queueDelay * 20L, queueDelay * 20L);
                Bukkit.getPluginManager().registerEvents(new SpeakQueueManager(this), this);
                CC.log(this.getPrefix() + "&aQueue system configured as&8: &fSpeakQueue");
                break;
            default:
                Queues = new DefaultImpl();
                CC.log(this.getPrefix() + "&aQueue system configured as&8: &fDefault");
                break;
        }
        CC.log(this.getPrefix() + "&aLoading Queue System");
    }

    public static SpeakHub get() {
        return SpeakHub.getPlugin(SpeakHub.class);
    }
}