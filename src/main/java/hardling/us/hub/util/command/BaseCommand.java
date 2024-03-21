package hardling.us.hub.util.command;

import hardling.us.hub.SpeakHub;

public abstract class BaseCommand {

  public SpeakHub plugin = SpeakHub.getInst();
  
  public BaseCommand() {
    this.plugin.getCommandFramework().registerCommands(this, null);
  }
  
  public abstract boolean sendMessage(CommandArgs paramCommandArgs);
}
