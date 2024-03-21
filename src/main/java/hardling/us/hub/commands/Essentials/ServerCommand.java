package hardling.us.hub.commands.Essentials;

import hardling.us.hub.SpeakHub;
import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.ConfigCreator;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;

public class ServerCommand extends BaseCommand {

    @MainCommand(name = "server", description = "Add Server or Remove Server(ServerSelector and HubSelector)", permission = "speakhub.command.server", inGameOnly = true, aliases = { "srv", })
    public boolean sendMessage(CommandArgs command) {
        ConfigCreator selectorFile = SpeakHub.getInst().getSelectorFile();
        ConfigCreator hubselectorFile = SpeakHub.getInst().getHubselectorFile();
        Player p = command.getPlayer();
        String[] args = command.getArgs();
        if (args.length == 0) {
            p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));
            p.sendMessage(CC.translate("                   &d&lSpeak&5&lHub &8- [&b&lServer&8]"));
            p.sendMessage("");
            p.sendMessage(CC.translate("&7»&b /Server &fAdd <Selector/Hub> <Name>  &7- &fAdd FileUtil server to the list"));
            p.sendMessage(CC.translate("&7»&b /Server &fDelete <Selector/Hub> <Name>&7- &fRemove FileUtil server from the list"));
            p.sendMessage(CC.translate("&7»&b /Server &fSetIcon <Selector/Hub> <Name>&7- &fAdd FileUtil Server Icon"));
            p.sendMessage(CC.translate("&7»&b /Server &fSetName <Selector/Hub> <Name> <NewName>&7- &fChange the name server"));
            p.sendMessage(CC.translate("&8&m+----------------------------------------------+"));

        }
        if (args.length == 1) {
            p.sendMessage(CC.translate("&cYou have to mention one of the 2 selectors <Selector/Hub>."));
            return false;
        }
        if (args.length == 2) {
            p.sendMessage(CC.translate("&cMention the name for the server"));
            return false;
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (args[1].equalsIgnoreCase("selector")) {
                String name = "SELECTOR.SERVERS." + ChatColor.stripColor(args[2]) + ".";
                selectorFile.set(name + "DISPLAYNAME", args[2]);
                selectorFile.set(name + "LORE", Arrays.asList(CC.getLINELIGH(), "&7Default Lore Pls Change on Selector.yml", CC.getLINELIGH()));
                selectorFile.set(name + "ENCHANTED", true);
                selectorFile.set(name + "MATERIAL", "FISHING_ROD");
                selectorFile.set(name + "DATA", 0);
                int i = (int) (Math.random() * selectorFile.getInt("SELECTOR.INVENTORY.SLOTS") + 1);
                selectorFile.set(name + "SLOT", i);
                selectorFile.set(name + "MESSAGE", "&aSent you to the server &d&l " + args[2]);
                selectorFile.set(name + "BUNGEE.ENABLE", false);
                selectorFile.set(name + "BUNGEE.SERVER", ChatColor.stripColor(args[2].toLowerCase()));
                selectorFile.set(name + "COMMANDS", Collections.singletonList("[player] play " + ChatColor.stripColor(args[2].toLowerCase())));
                selectorFile.save();
                p.sendMessage(CC.translate("&aServer successfully added " + args[2]));
                return false;
            }
            if (args[1].equalsIgnoreCase("hub")) {
                String name = "HUB-SELECTOR.HUBS." + ChatColor.stripColor(args[2]) + ".";
                hubselectorFile.set(name + "DISPLAYNAME", args[2]);
                hubselectorFile.set(name + "LORE", Arrays.asList(CC.getLINELIGH(), "&7Default Lore Pls Change on HubSelector.yml", CC.getLINELIGH()));
                hubselectorFile.set(name + "ENCHANTED", true);
                hubselectorFile.set(name + "MATERIAL", "QUARTZ_BLOCK");
                hubselectorFile.set(name + "DATA", 0);
                int i = (int) (Math.random() * hubselectorFile.getInt("HUB-SELECTOR.INVENTORY.SLOTS") + 1);
                hubselectorFile.set(name + "SLOT", i);
                hubselectorFile.set(name + "MESSAGE", "&aSent you to the server &d&l " + args[2]);
                hubselectorFile.set(name + "BUNGEE.ENABLE", false);
                hubselectorFile.set(name + "BUNGEE.SERVER", ChatColor.stripColor(args[1].toLowerCase()));
                hubselectorFile.set(name + "COMMANDS", Collections.singletonList("[player] play " + ChatColor.stripColor(args[2].toLowerCase())));
                hubselectorFile.save();
                p.sendMessage(CC.translate("&aHub successfully added " + args[2]));
                return false;
            }
        } else if (args[0].equalsIgnoreCase("delete")) {
            if (args[1].equalsIgnoreCase("selector")) {
                if (selectorFile.contains("SELECTOR.SERVERS." + args[2])) {
                    selectorFile.set("SELECTOR.SERVERS." + args[2], null);
                    selectorFile.save();
                    p.sendMessage(CC.translate("&FileUtil" + args[2] + " kit was removed correctly"));
                } else {
                    p.sendMessage(CC.translate("&cThis server does not exist"));
                }
                return false;
            }

            if (args[1].equalsIgnoreCase("hub")) {
                if (hubselectorFile.contains("HUB-SELECTOR.HUBS." + args[2])) {
                    hubselectorFile.set("HUB-SELECTOR.HUBS." + args[2], null);
                    hubselectorFile.save();
                    p.sendMessage(CC.translate("&FileUtil" + args[2] + " kit was removed correctly"));
                } else {
                    p.sendMessage(CC.translate("&cThis server does not exist"));
                }
                return false;
            }
        } else if (args[0].equalsIgnoreCase("seticon")) {
            if (args[1].equalsIgnoreCase("selector")) {
                if (selectorFile.contains("SELECTOR.SERVERS." + args[2] + ".MATERIAL")) {
                    if (p.getInventory().getItemInHand().getType().equals(Material.AIR) || p.getInventory().getItemInHand().getType().equals(Material.valueOf(selectorFile.getString("SELECTOR.SERVERS." + args[2] + ".MATERIAL")))) {
                        p.sendMessage(CC.translate("&cMaterial Invalid"));
                        return false;
                    }
                    selectorFile.set("SELECTOR.SERVERS." + args[2] + ".MATERIAL", String.valueOf(p.getInventory().getItemInHand().getType()));
                    selectorFile.set("SELECTOR.SERVERS." + args[2] + ".DATA", p.getInventory().getItemInHand().getDurability());
                    selectorFile.save();
                    p.sendMessage(CC.translate("&aSaved successfully " + args[2]));
                } else {
                    p.sendMessage(CC.translate("&cThis server does not exist"));
                }
                return false;
            }

            if (args[1].equalsIgnoreCase("hub")) {
                if (hubselectorFile.contains("HUB-SELECTOR.HUBS." + args[2] + ".MATERIAL")) {
                    if (p.getInventory().getItemInHand().getType().equals(Material.AIR) || p.getInventory().getItemInHand().getType().equals(Material.valueOf(hubselectorFile.getString("HUB-SELECTOR.HUBS." + args[2] + ".MATERIAL")))) {
                        p.sendMessage(CC.translate("&cMaterial Invalid"));
                        return false;
                    }
                    hubselectorFile.set("HUB-SELECTOR.HUBS." + args[2] + ".MATERIAL", String.valueOf(p.getInventory().getItemInHand().getType()));
                    hubselectorFile.set("HUB-SELECTOR.HUBS." + args[2] + ".DATA", p.getInventory().getItemInHand().getDurability());
                    hubselectorFile.save();
                    p.sendMessage(CC.translate("&aSaved successfully " + args[2]));
                } else {
                    p.sendMessage(CC.translate("&cThis server does not exist"));
                }
                return false;
            }
        } else if (args[0].equalsIgnoreCase("setname")) {
            if (args.length == 3) {
                p.sendMessage(CC.translate("&cMention the new name to put"));
                return false;
            }
            if (args[1].equalsIgnoreCase("selector")) {
                if (selectorFile.contains("SELECTOR.SERVERS." + args[2] + ".DISPLAYNAME")) {
                    selectorFile.set("SELECTOR.SERVERS." + args[2] + ".DISPLAYNAME", args[3]);
                    selectorFile.save();
                    p.sendMessage(CC.translate("&FileUtil" + args[2] + " name has been correctly changed to &8(" + args[3] + "&8)"));
                } else {
                    p.sendMessage(CC.translate("&cThis server does not exist"));
                }
                return false;
            }

            if (args[1].equalsIgnoreCase("hub")) {
                if (hubselectorFile.contains("HUB-SELECTOR.HUBS." + args[2] + ".DISPLAYNAME")) {
                    hubselectorFile.set("HUB-SELECTOR.HUBS." + args[2] + ".DISPLAYNAME", args[3]);
                    hubselectorFile.save();
                    p.sendMessage(CC.translate("&FileUtil" + args[2] + " name has been correctly changed to &8(" + args[3] + "&8)"));
                } else {
                    p.sendMessage(CC.translate("&cThis server does not exist"));
                }
                return false;
            }
        }
        return false;
    }
}