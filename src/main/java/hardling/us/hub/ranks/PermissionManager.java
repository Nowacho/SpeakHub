package hardling.us.hub.ranks;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.CC;
import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

@Getter
public final class PermissionManager {
    private final SpeakHub plugin;

    private String name;

    private Permission permission;

    private Chat chat;

    public PermissionManager(SpeakHub plugin) {
        this.plugin = plugin;
    }

    public void loadHook() {
        // AQUACORE
        if (Bukkit.getPluginManager().getPlugin("AquaCore") != null) {
            this.setPermission(new AquaCore(), "AquaCore");
            CC.log(SpeakHub.get().getPrefix() + "&aRank system configured as&8: &fAquaCore");
        }
        // VAULT
        if (Bukkit.getPluginManager().getPlugin("Vault") != null && this.loadVault() && this.getChat().getName().contains("LuckPerms")) {
            this.setPermission(new hardling.us.hub.ranks.type.LuckPerms(), "LuckPerms");
            CC.log(SpeakHub.get().getPrefix() + "&aRank system configured as&8: &fLuckPerms");
        }
        // MIZU
        if (Bukkit.getPluginManager().getPlugin("Mizu") != null) {
            this.setPermission(new Mizu(), "Mizu");
            CC.log(SpeakHub.get().getPrefix() + "&aRank system configured as&8: &fMizu");
        }
        // HEXA
        if (Bukkit.getPluginManager().getPlugin("Hexa") != null) {
            this.setPermission(new Hexa(), "Hexa");
            CC.log(SpeakHub.get().getPrefix() + "&aRank system configured as&8: &fHexa");
        }
        if (SpeakHub.getInst().getPermission().getPlugin() == null) {
            Bukkit.getPluginManager().disablePlugin(plugin);
            Bukkit.getScheduler().cancelTasks(plugin);
        }
    }

    private boolean loadVault() {
        RegisteredServiceProvider<Chat> service = this.plugin.getServer().getServicesManager().getRegistration(Chat.class);
        if (service != null) this.chat = service.getProvider();
        return this.chat != null;
    }

    private void setPermission(Permission permission, String name) {
        this.permission = permission;
        this.name = name;
    }
}
