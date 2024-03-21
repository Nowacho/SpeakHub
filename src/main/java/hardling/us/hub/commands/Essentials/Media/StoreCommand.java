package hardling.us.hub.commands.Essentials.Media;

import hardling.us.hub.util.CC;
import hardling.us.hub.util.Files.Lang;
import hardling.us.hub.util.command.BaseCommand;
import hardling.us.hub.util.command.CommandArgs;
import hardling.us.hub.util.command.MainCommand;

public class StoreCommand extends BaseCommand {
    @MainCommand(name = "store", description = "Check out the server Store", inGameOnly = true, aliases = { "shop", "tienda" })
    public boolean sendMessage(CommandArgs command) {
        Lang.SOCIAL_STORE.forEach(s ->{
            s = CC.Placeholders(command.getPlayer(), s);
              command.getSender().sendMessage(CC.translate(s));
        });
        return false;
    }
}
