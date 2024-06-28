package pl.chatacctivities.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.chatacctivities.activities.Activity;

public abstract class BaseHandler<T extends Activity> implements CommandExecutor {

    private T currentActivity;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            return true;
        }
        if(args[0].isEmpty()) {
            return true;
        }
        currentActivity.onCommand(args[0]);
        return true;
    }

    public T getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(T activity) {
        this.currentActivity = activity;
    }

}
