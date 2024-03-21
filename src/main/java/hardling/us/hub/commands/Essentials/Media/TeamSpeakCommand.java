package hardling.us.hub.commands.Essentials.Media;

import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Lang;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;

public class TeamSpeakCommand extends BaseCommand {
    @MainCommand(name = "teamspeak", description = "Check out the server TeamSpeak", inGameOnly = true, aliases = { "ts", "ts3" })
    public boolean sendMessage(CommandArgs command) {
        Lang.SOCIAL_TEAMSPEAK.forEach(s -> {
          s = CC.Placeholders(command.getPlayer(), s);
          command.getSender().sendMessage(CC.translate(s));
        });
        return false;
    }
}
