package pl.chatacctivities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.chatacctivities.ChatActivities;
import pl.chatacctivities.activities.Activity;
import pl.chatacctivities.data.Messages;

public abstract class BaseHandler<T extends Activity> implements CommandExecutor {

    private final Messages messages = ChatActivities.getInstance().getMessages();
    private T currentActivity;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(getMessage("mustBePlayer"));
            return true;
        }
        if(args[0].isEmpty()) {
            sender.sendMessage(getMessage("correctUsage") + "/" + label + "<" + getMessage("answer") + ">");
            return true;
        }
        boolean completed = currentActivity.onCommand((Player) sender, args[0]);
        if(completed) {
            setCurrentActivity(null);
        }
        return true;
    }

    public T getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(T activity) {
        this.currentActivity = activity;
    }

    private String getMessage(String key) {
        return this.messages.getMessage(key);
    }


}
