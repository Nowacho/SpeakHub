package hardling.us.hub.util.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BukkitCompleter implements TabCompleter {
    private final Map<String, Map.Entry<Method, Object>> completers = new HashMap<>();

    void addCompleter(String label, Method m, Object obj) {
        this.completers.put(label, new AbstractMap.SimpleEntry<>(m, obj));
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        for (int i = args.length; i >= 0; i--) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(label.toLowerCase());
            for (int x = 0; x < i; x++) {
                if (!args[x].equals("") && !args[x].equals(" "))
                    buffer.append("." + args[x].toLowerCase());
            }
            String cmdLabel = buffer.toString();
            if (this.completers.containsKey(cmdLabel)) {
                Map.Entry<Method, Object> entry = this.completers.get(cmdLabel);
                try {
                    return (List<String>) ((Method) entry.getKey()).invoke(entry.getValue(), new Object[]{ new CommandArgs(sender, command, label, args, (cmdLabel
                            .split("\\.")).length - 1) });
                } catch (IllegalArgumentException | IllegalAccessException |
                         java.lang.reflect.InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
