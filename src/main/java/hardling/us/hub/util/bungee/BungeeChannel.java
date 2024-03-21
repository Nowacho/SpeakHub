package hardling.us.hub.util.bungee;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import hardling.us.hub.SpeakHub;
import org.bukkit.entity.Player;

public class BungeeChannel {


    public static void sendToServer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage( SpeakHub.getInst(), "BungeeCord", out.toByteArray());
    }

}
