package hardling.us.hub.commands.Essentials;

import hardling.us.hub.util.CC;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GameModeCommand extends BaseCommand {
    @MainCommand(name = "gm", description = "change the game mode", permission = "speakhub.command.gamemode", inGameOnly = true, aliases = { "gamemode" })
    public boolean sendMessage(CommandArgs command) {
        String[] args = command.getArgs();
        Player p = command.getPlayer();
        if (args.length == 0) {
            p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
            p.sendMessage(CC.translate("                   &d&lSpeak&5&lHub &8- [&3Gamemodes&8]"));
            p.sendMessage("");
            p.sendMessage(CC.translate("&7»&b /Gm &f(0/s) &7- &fSurvival"));
            p.sendMessage(CC.translate("&7»&b /Gm &f(1/c) &7- &fCreative"));
            p.sendMessage(CC.translate("&7»&b /Gm &f(2/FileUtil) &7- &fAdventure"));
            p.sendMessage(CC.translate("&7»&b /Gm &f(3/Sp) &7- &fSpectator"));
            p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
        } else if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("s")) {
            p.setGameMode(GameMode.SURVIVAL);
            p.sendMessage(CC.translate("&aYou changed the game mode to &b&lSurvival"));
        } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("c")) {
            p.setGameMode(GameMode.CREATIVE);
            p.sendMessage(CC.translate("&aYou changed the game mode to &3&lCreative"));
        } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("FileUtil")) {
            p.setGameMode(GameMode.ADVENTURE);
            p.sendMessage(CC.translate("&aYou changed the game mode to &4&lAdventure"));
        } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("sp")) {
            p.setGameMode(GameMode.SPECTATOR);
            p.sendMessage(CC.translate("&aYou changed the game mode to &d&lSpectator"));
        }
        return false;
    }
}