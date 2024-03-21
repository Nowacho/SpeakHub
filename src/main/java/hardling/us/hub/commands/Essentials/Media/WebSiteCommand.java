package hardling.us.hub.commands.Essentials.Media;

import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Lang;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;

public class WebSiteCommand extends BaseCommand {
    @MainCommand(name = "website", description = "Check out the server website", inGameOnly = true, aliases = { "web" })
    public boolean sendMessage(CommandArgs command) {
        Lang.SOCIAL_WEBSITE.forEach(s -> {
            s = CC.Placeholders(command.getPlayer(), s);
            command.getSender().sendMessage(CC.translate(s));
        });
        return false;
    }
}
