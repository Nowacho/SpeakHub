package hardling.us.hub.commands;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.listeners.Editor.MenuEditor;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.SettingsUtils;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.entity.Player;

public class SpeakHubCommand extends BaseCommand {
    @MainCommand(name = "speakhub", description = "Main command", inGameOnly = true, aliases = { "dev", "developers", "hub", "developer", "hardling" })
    public boolean sendMessage(CommandArgs command) {
        Player p = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length == 0) {
            p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
            p.sendMessage(CC.translate("                   &d&lSpeak&5&lHub &8- [&3&lHardling Development&8]"));
            p.sendMessage("");
            p.sendMessage(CC.translate("&7»&b /SpeakHub &fReload &7- &fReload all Config"));
            p.sendMessage(CC.translate("&7»&b /SpeakHub &fInfo &7- &fInformation about the plugin"));
            p.sendMessage(CC.translate("&7»&b /SpeakHub &fEditor &7- &fConfigure the Plugin in Quick Way"));
            p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
        } else if (args[0].equalsIgnoreCase("info")) {
            p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
            p.sendMessage(CC.translate("               &d&lSpeak&5&lHub &8- [&3&lHardling Development&8]"));
            p.sendMessage("");
            p.sendMessage(CC.translate("&7»&b Version&7: &f" + SpeakHub.getInst().getDescription().getVersion()));
            p.sendMessage(CC.translate("&7»&b Authors&7: &f" + SpeakHub.getInst().getDescription().getAuthors()));
            p.sendMessage(CC.translate("&7»&b Discord&7: &fhttps://discord.hardling.us"));
            p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
        } else if (p.hasPermission("speakhub.command.reload") && args[0].equalsIgnoreCase("reload")) {
            SettingsUtils.reloadConfig(p);
        } else if (p.hasPermission("speakhub.command.editor") && args[0].equalsIgnoreCase("editor")) {
            MenuEditor.InventoryEditor(p);
            p.sendMessage(CC.translate("&aYou opened the &d&lSpeak&5&lHub &aeditor"));
        }
        return false;
    }
}