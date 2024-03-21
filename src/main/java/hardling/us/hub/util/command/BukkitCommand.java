package hardling.us.hub.util.command;

import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class BukkitCommand extends Command {
    private final Plugin owningPlugin;

    private final CommandExecutor executor;

    BukkitCompleter completer;

    BukkitCommand(String label, CommandExecutor executor, Plugin owner) {
        super(label);
        this.executor = executor;
        this.owningPlugin = owner;
        this.usageMessage = "";
    }

    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        boolean success;
        if (!this.owningPlugin.isEnabled())
            return false;
        if (!testPermission(sender))
            return true;
        try {
            success = this.executor.onCommand(sender, this, commandLabel, args);
        } catch (Throwable ex) {
            throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + this.owningPlugin
                    .getDescription().getFullName(), ex);
        }
        if (!success && this.usageMessage.length() > 0)
            for (String line : this.usageMessage.replace("<command>", commandLabel).split("\n"))
                sender.sendMessage(line);
        return success;
    }

    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws CommandException, IllegalArgumentException {
        List<String> completions = null;
        try {
            if (this.completer != null)
                completions = this.completer.onTabComplete(sender, this, alias, args);
            if (completions == null && this.executor instanceof TabCompleter)
                completions = ((TabCompleter) this.executor).onTabComplete(sender, this, alias, args);
        } catch (Throwable ex) {
            StringBuilder message = new StringBuilder();
            message.append("Unhandled exception during tab completion for command '/").append(alias).append(' ');
            for (String arg : args)
                message.append(arg).append(' ');
            message.deleteCharAt(message.length() - 1).append("' in plugin ")
                    .append(this.owningPlugin.getDescription().getFullName());
            throw new CommandException(message.toString(), ex);
        }
        if (completions == null)
            return super.tabComplete(sender, alias, args);
        return completions;
    }
}
