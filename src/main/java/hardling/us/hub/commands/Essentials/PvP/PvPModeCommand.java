package hardling.us.hub.commands.Essentials.PvP;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.ConfigCreator;
import hardling.us.hub.util.SettingsUtils;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.entity.Player;

public class PvPModeCommand extends BaseCommand {


    @MainCommand(name = "pvpmode", description = "PvP Commands Menu", permission = "", inGameOnly = true, aliases = { "pvp", "modepvp" })
    public boolean sendMessage(CommandArgs command) {
        Player p = command.getPlayer();
        String[] args = command.getArgs();
        ConfigCreator pvpFile = SpeakHub.getInst().getPvpFile();
        if (args.length < 1) {
            SettingsUtils.JoinPvP(p);
            return false;
        }
        if (args[0].equalsIgnoreCase("setspawn") && command.getSender().hasPermission("speakhub.command.pvpsetspawn")) {
            SettingsUtils.setlocationToString(p, "SPAWN_PVP");
            return true;
        } else if (args[0].equalsIgnoreCase("setinv") && command.getSender().hasPermission("speakhub.command.pvpsetinv")) {
            pvpFile.set("KIT.INVENTORY", p.getInventory().getContents());
            pvpFile.set("KIT.ARMOR", p.getInventory().getArmorContents());
            pvpFile.save();
            p.sendMessage(CC.translate("&aThe PvP Kit was correctly placed"));
            return true;
        } else if (args[0].equalsIgnoreCase("leave")) {
            SettingsUtils.leavePvP(p);
        } else {
            p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
            p.sendMessage(CC.translate("                   &c&lPvPMode &8- [&d&lSpeak&5&lHub&8]"));
            p.sendMessage(CC.translate(""));
            p.sendMessage(CC.translate("&7»&b /PvP SetSpawn &7- &fPvP mode spawn location"));
            p.sendMessage(CC.translate("&7»&b /PvP LeavePvP &7- &fLeave the PvP mode"));
            p.sendMessage(CC.translate("&7»&b /PvP SetInv &7- &fSet custom kit PvP"));
            p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
        }
        return false;
    }
}
