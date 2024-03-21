package hardling.us.hub.commands.Essentials.Media;

import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Lang;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;

public class DiscordCommand extends BaseCommand {
    @MainCommand(name = "discord", description = "Check out the server Discord", inGameOnly = true,  aliases = { "dc", "dsc", "discordia" })
    public boolean sendMessage(CommandArgs command) {
        Lang.SOCIAL_DISCORD.forEach(s -> {
            s = CC.Placeholders(command.getPlayer(), s);
            command.getSender().sendMessage(CC.translate(s));
        });
        return false;
    }
}
